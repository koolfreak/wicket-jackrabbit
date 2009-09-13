/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xaloon.wicket.component.mounting;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import org.wicketstuff.annotation.mount.MountDefinition;
import org.wicketstuff.annotation.scan.AnnotatedMountList;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;
import org.wicketstuff.config.MatchingResources;
import org.xaloon.wicket.component.util.Formatter;

/**
 * Extended class of AnnotatedMountScanner
 * Supports scanning packages with different annotations
 * e.g., new PageAnnotatedScanner().scan("org.xaloon.plugin.jlearn.page",MountPage.class), returns List classes for further usage
 * 
 * @author vytautas racelis
 */
public class PageAnnotatedScanner extends AnnotatedMountScanner {
	@Override
	public List<Class<?>> getPatternMatches(String pattern) {
		MatchingResources resources = new MatchingResources(pattern);
		List<Class<?>> mounts = resources.getAnnotatedMatches(MountPage.class);
		for (Class<?> mount : mounts) {
			if (!(Page.class.isAssignableFrom(mount))) {
				throw new PageAnnotationException("@MountPage annotated class should subclass Page: " + mount);
			}
		}
		return mounts;
	}

	@SuppressWarnings("unchecked")
	public List<Class<?>> scan(String packageName, Class<? extends Annotation> annotation) {
		List<Class<?>> result = new ArrayList<Class<?>>();
		for (Class<?> mount : getPackageMatches(packageName)) {
			Class<? extends Page> page = (Class<? extends Page>) mount;
			scanClass(page, result, annotation);
		}
		return result;
	}

	private void scanClass(Class<? extends Page> pageClass, List<Class<?>> result, Class<? extends Annotation> annotation) {
		Annotation foundAnnotation = pageClass.getAnnotation(annotation);
		if (foundAnnotation == null) {
			return;
		}
		result.add(pageClass);
	}

	@Override
	public AnnotatedMountList scanList(List<Class<?>> mounts) {
		return scanList (mounts, null);
	}
	/**
	 * Enhanced scanning of classes. it uses @MountPageGroup annotation and suffix to combine url.
	 * 
	 * e.g., we have on BasePage annotation @MountPageGroup(context="/test") and TestPage with annotation @MountPage(path="index")
	 * executing method scanList (..., ".jsp") would result link generation such as ".../test/index.jsp"
	 * 
	 * @param mounts
	 * @param suffix
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public AnnotatedMountList scanList(List<Class<?>> mounts, String suffix) {
		AnnotatedMountList list = new AnnotatedMountList();
		for (Class<?> mount : mounts) {
			Class<? extends Page> page = (Class<? extends Page>) mount;
			scanClass(page, list, suffix);
		}
		return list;
	}

	private void scanClass(Class<? extends Page> pageClass, AnnotatedMountList list, String suffix) {
		MountPage mountPage = pageClass.getAnnotation(MountPage.class);
		if (mountPage == null) {
			return;
		}
		MountPageGroup mountPageGroup = pageClass.getAnnotation(MountPageGroup.class);
		String context = (mountPageGroup != null) ? mountPageGroup.context() : "";
		String path = Formatter.fixUrl(context, mountPage.path()) + prepareSuffix(mountPage.path(), suffix);

		// find first annotation that is annotated with @MountDefinition
		Annotation pageSpecificMountDetails = null;
		Class<? extends Annotation> mountStrategyAnnotationClass = null;
		MountDefinition mountDefinition = null;
		Annotation[] annos = pageClass.getAnnotations();
		for (Annotation anno : annos) {
			mountDefinition = anno.annotationType().getAnnotation(MountDefinition.class);
			if (mountDefinition != null) {
				pageSpecificMountDetails = anno;
				mountStrategyAnnotationClass = anno.getClass();
				break;
			}
		}

		// default if no @MountDefinition annotated annotation is available
		if (pageSpecificMountDetails == null) {
			// primary
			list.add(getDefaultStrategy(path, pageClass));
			return;
		}

		// get the actual strategy we'll be creating
		Class<? extends IRequestTargetUrlCodingStrategy> strategyClass = mountDefinition.strategyClass();

		// need to determine the constructor - we support constructors that
		// take a String (mount path) and a Class (page)
		int standardArgs = 2;
		String[] argOrder = mountDefinition.argOrder();
		Class<?>[] paramTypes = new Class<?>[argOrder.length + standardArgs];
		Object[] initArgs = new Object[paramTypes.length];

		// deafult first two arguments - mount path and page class
		paramTypes[0] = String.class;
		paramTypes[1] = Class.class;
		initArgs[0] = null; // provided below
		initArgs[1] = pageClass;

		// get remaining constructor args which match those specified by
		// 'argOrder'
		for (int i = 0; i < argOrder.length; i++) {
			int index = i + standardArgs;
			Method method = null;

			try {
				method = mountStrategyAnnotationClass.getDeclaredMethod(argOrder[i]);
				paramTypes[index] = method.getReturnType();
				initArgs[index] = method.invoke(pageSpecificMountDetails);

				// can't default an annotation to null, so use this as a
				// workaround
				if (initArgs[index].equals(MountDefinition.NULL)) {
					initArgs[index] = null;
				}
			} catch (NoSuchMethodException e) {
				throw new PageAnnotationException("argOrder[" + i + "] = " + argOrder[i] + " not found in annotation "
						+ mountStrategyAnnotationClass.getName(), e);
			} catch (Exception e) {
				throw new PageAnnotationException("Unable to invoke method " + method + " on annotation " + pageSpecificMountDetails.getClass().getName(), e);
			}
		}

		// get matching constructor
		Constructor<? extends IRequestTargetUrlCodingStrategy> ctx = null;
		try {
			ctx = strategyClass.getConstructor(paramTypes);
		} catch (NoSuchMethodException e) {
			throw new PageAnnotationException("No constructor matching parameters defined by 'argOrder' found for " + strategyClass, e);
		}

		// create new instances
		try {
			// primary mount path
			initArgs[0] = path;
			list.add(ctx.newInstance(initArgs));

		} catch (Exception e) {
			throw new PageAnnotationException("Unable to invoke constructor " + ctx + " for " + strategyClass, e);
		}
	}

	private String prepareSuffix(String path, String suffix) {
		if (path.contains(".") || (suffix == null)) {
			return "";
		}
		return suffix;
	}
}
