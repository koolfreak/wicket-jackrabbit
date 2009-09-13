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
package org.xaloon.wicket.component.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.loader.ClassStringResourceLoader;
import org.xaloon.wicket.component.mounting.MountPage;
import org.xaloon.wicket.component.mounting.MountPageGroup;
import org.xaloon.wicket.component.plugin.impl.MenuPluginImpl;
import org.xaloon.wicket.component.util.KeyValue;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class MenuPluginObserver implements Observer {
	private static final ClassStringResourceLoader cslr = new ClassStringResourceLoader(MountPageGroup.class);
	private PluginManager pluginManager;
	
	public MenuPluginObserver (PluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}
	
	@SuppressWarnings("unchecked")
	public void update(Observable o, Object arg) {
		if (arg instanceof List) {
			processPageClassMenu ((List<Class<? extends WebPage>>)arg);
		}
	}
	
	private void processPageClassMenu(List<Class<? extends WebPage>> pcl) {
		
		Map<String, MenuPlugin> plugins = new HashMap<String, MenuPlugin>();
		for (Class<? extends WebPage> clz : pcl) {
			MountPageGroup mpg = clz.getAnnotation(MountPageGroup.class);
			String key = "DEFAULT";
			if (clz.equals(WebApplication.get().getHomePage())) {
				key = MenuPlugin.HOME_PAGE;
			} else if (mpg != null) {
				key = mpg.context();
			}
			MenuPlugin plugin = pluginManager.retrievePlugin(MenuPlugin.class, key);
			if (plugin == null) {
				plugin = new MenuPluginImpl(key);
				String name = cslr.loadStringResource(null, key, null, null);
				if (StringUtils.isEmpty(name)) {
					name = key;
				}
				plugin.setPluginName(name);
				plugin.setImageResourceKey (cslr.loadStringResource(null, plugin.getId() +".img", null, null));
				pluginManager.register(plugin);
			}
			if (!plugins.containsKey(key)) {
				plugins.put(key, plugin);
			}
			MountPage annotation = clz.getAnnotation(MountPage.class);
			if ((annotation != null) && annotation.visible()) {
				String name = new ClassStringResourceLoader(clz).loadStringResource(clz, clz.getName(), null, null);
				plugin.addMenuItem (new KeyValue<String, Class<? extends WebPage>> (name, clz));
			}
		}
		for (MenuPlugin plugin : plugins.values()) {
			if (!plugin.hasMenuItems ()) {
				pluginManager.unregister(plugin);
			}
		}
	}
}
