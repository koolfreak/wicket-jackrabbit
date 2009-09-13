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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.xaloon.wicket.component.util.ClassUtils;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
@Component
public class PluginManager implements Serializable {
	private static final Log log = LogFactory.getLog(PluginManager.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Class<?>, List<Plugin>> providerList = new LinkedHashMap<Class<?>, List<Plugin>> ();
	
	public void register(Plugin plugin) {
		if (StringUtils.isEmpty(plugin.getPluginName())) {
			log.error("Plugin was not registered, because it does not have a name: " + plugin.getId());
			return;
		}
		Class<?> key = ClassUtils.getFirstInterface (plugin.getClass());
		List<Plugin> itemList = providerList.get(key);
		if (itemList == null) {
			itemList = new ArrayList<Plugin>();
			providerList.put(key, itemList);
		}
		itemList.add(plugin);
		if (itemList.size() > 1) {
			Collections.sort(itemList, new PluginComparator());
		}
	}

	@SuppressWarnings("unchecked")
	public void init(WebApplicationContext context) {
		Collection<Plugin> items = context.getBeansOfType(Plugin.class).values();
		for (Plugin entry : items) {
			register (entry);
		}
	}

	public Map<Class<?>, List<Plugin>> retrieveAllPlugins() {
		return providerList;
	}

	class PluginComparator implements Comparator<Plugin> {
		public int compare(Plugin o1, Plugin o2) {
			return o1.getPluginName().compareTo(o2.getPluginName());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getPluginProviderList(Class<T> clz) {
 		List<T> result = (List<T>) providerList.get(clz);
		if (result == null) {
			result = new ArrayList<T>();
		}
		return result;
	}

	public void unregister(Plugin plugin) {
		Class<?> key = ClassUtils.getFirstInterface (plugin.getClass());
		if (providerList.containsKey(key)) {
			List<Plugin> pluginList = providerList.get(key);
			pluginList.remove(plugin);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T retrievePlugin(Class<T> clz, String id) {
		T result = null;
		if (providerList.containsKey(clz)) {
			List<Plugin> pluginList = providerList.get(clz);
			if ((pluginList != null) && !pluginList.isEmpty()) {
				for (Plugin plugin : pluginList) {
					if (id.equalsIgnoreCase(plugin.getId())) {
						result = (T)plugin;
						break;
					}
				}
			}
		}
		return result;
	}
}
