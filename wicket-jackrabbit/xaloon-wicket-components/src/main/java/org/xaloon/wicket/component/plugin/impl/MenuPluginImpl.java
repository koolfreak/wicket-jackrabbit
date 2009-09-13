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
package org.xaloon.wicket.component.plugin.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.markup.html.WebPage;
import org.xaloon.wicket.component.plugin.AbstractPlugin;
import org.xaloon.wicket.component.plugin.MenuPlugin;
import org.xaloon.wicket.component.util.KeyValue;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class MenuPluginImpl extends AbstractPlugin implements MenuPlugin {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<KeyValue<String, Class<? extends WebPage>>> menuItems = new ArrayList<KeyValue<String, Class<? extends WebPage>>>();
	private String imageResourceKey;
	
	public MenuPluginImpl (String id) {
		setId(id);
		InjectorHolder.getInjector().inject(this);
	}

	public List<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}
	
	public void addMenuItem(KeyValue<String, Class<? extends WebPage>> clz) {
		menuItems.add(clz);
	}
	
	public boolean hasMenuItems() {
		return !menuItems.isEmpty();
	}
	
	public List<KeyValue<String, Class<? extends WebPage>>> getMenuItems() {
		return menuItems;
	}

	public String getImageResourceKey() {
		return imageResourceKey;
	}

	public void setImageResourceKey(String imageResourceKey) {
		this.imageResourceKey = imageResourceKey;
	}
}
