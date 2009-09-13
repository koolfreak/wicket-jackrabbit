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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.xaloon.wicket.component.application.AbstractWebApplication;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
@Component
public class PageAnnotationScannerContainer extends Observable
{

    private static List<Class<?>> pageClassList = new ArrayList<Class<?>>();
    private PageAnnotatedScanner scanner = new PageAnnotatedScanner();

    public void scan(WebApplication application, String packageName,
	    String suffix)
    {
	List<Class<?>> pcl = scanner.scan(packageName, MountPage.class);
	scanner.scanList(pcl, suffix).mount(application);
	pageClassList.addAll(pcl);

	setChanged();
	notifyObservers(pcl);
    }

    public static List<Class<?>> getPageClassList()
    {
	return pageClassList;
    }

    @SuppressWarnings("unchecked")
    public void scan(AbstractWebApplication application,
	    WebApplicationContext context)
    {
	scan(application, "org.xaloon.wicket.component", ".wspx");
	Collection<PageScanner> items = context.getBeansOfType(PageScanner.class).values();
	for (PageScanner entry : items)
	{
	    scan(application, entry.getPackageName(), entry.getSuffix());
	}
    }

    @SuppressWarnings("unchecked")
    public void scan(WebApplication application, WebApplicationContext context)
    {
	scan(application, "org.xaloon.wicket.component", ".wspx");
	Collection<PageScanner> items = context.getBeansOfType(PageScanner.class).values();
	for (PageScanner entry : items)
	{
	    scan(application, entry.getPackageName(), entry.getSuffix());
	}

    }
}
