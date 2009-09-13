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
package org.xaloon.wicket.component.application;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.IPageFactory;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.resource.loader.ClassStringResourceLoader;
import org.apache.wicket.session.DefaultPageFactory;
import org.springframework.util.ClassUtils;
import org.xaloon.wicket.component.basic.MetaTagWebContainer;
import org.xaloon.wicket.component.mounting.MountPanel;
import org.xaloon.wicket.component.page.AbstractWebPage;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class VirtualPageFactory implements IPageFactory {
	private static final Log log = LogFactory.getLog(VirtualPageFactory.class);
	private static final String NOT_FOUND = "Not found!";
	
	private IPageFactory factory = new DefaultPageFactory();
	
	public <C extends Page> Page newPage(Class<C> pageClass) {
		return newPageInternal (pageClass, null);
	}

	private <C extends Page> Page newPageInternal(Class<C> pageClass, PageParameters parameters) {
		MountPanel mp = pageClass.getAnnotation(MountPanel.class);
		if (mp != null) {
			try {
				
				Panel content = newInstance (mp, parameters);
				Class<? extends Page> layoutPageClass = AbstractWebApplication.get().getLayoutPageClass ();
				if (layoutPageClass != null) {
					Page page = createPage (layoutPageClass, parameters);
					page.addOrReplace(content);
					injectAdditionalInfo (page, pageClass);
					
					return page;
				}
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
		}
		return createPage (pageClass, parameters);
	}
	
	private Panel newInstance(MountPanel mp, PageParameters parameters) throws InstantiationException, IllegalAccessException {
		Class<? extends Panel> clz = mp.panel();
		try {
			if ((parameters != null) && ClassUtils.hasConstructor(clz, new Class[] {String.class, PageParameters.class})) {
				return clz.getConstructor(String.class, PageParameters.class).newInstance("content", parameters);
			} else if (ClassUtils.hasConstructor(clz, new Class[] {String.class})) {
				return clz.getConstructor(String.class).newInstance("content");
			}
		} catch (Exception e) {
			log.error("Panel was not created!", e);
		}
		return clz.newInstance();
	}

	private <C extends Page>  void injectAdditionalInfo(Page page, Class<C> originalPageClass) {
		if (page instanceof AbstractWebPage) {
			AbstractWebPage awp = (AbstractWebPage)page;
			ClassStringResourceLoader csrl = new ClassStringResourceLoader(originalPageClass);
			awp.setVirtualPageClass (originalPageClass);
			
			String title = getDefaultString (csrl.loadStringResource(originalPageClass, "title", null, null), NOT_FOUND);
			String keywords = getDefaultString (csrl.loadStringResource(originalPageClass, "keywords", null, null), NOT_FOUND);
			String description = getDefaultString (csrl.loadStringResource(originalPageClass, "description", null, null), NOT_FOUND);
			awp.addOrReplace(new Label ("title", title));
			awp.addOrReplace(new MetaTagWebContainer("keywords", new Model<String>(keywords)));
			awp.addOrReplace(new MetaTagWebContainer("description", new Model<String>(description)));
		}
	}

	private String getDefaultString(String value, String defaultValue) {
		if (!StringUtils.isEmpty(value)) {
			return value;
		}
		return defaultValue;
	}

	private <C extends Page> Page createPage(Class<C> pageClass, PageParameters parameters) {
		if (parameters == null) {
			return factory.newPage(pageClass);
		} 
		return factory.newPage(pageClass, parameters);
	}
	
	public <C extends Page> Page newPage(Class<C> pageClass, PageParameters parameters) {
		return newPageInternal(pageClass, parameters);
	}
}
