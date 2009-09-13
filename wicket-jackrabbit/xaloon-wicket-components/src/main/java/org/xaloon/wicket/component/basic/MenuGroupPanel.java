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
package org.xaloon.wicket.component.basic;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.xaloon.wicket.component.mounting.MountPageGroup;
import org.xaloon.wicket.component.plugin.MenuPlugin;
import org.xaloon.wicket.component.plugin.PluginManager;
import org.xaloon.wicket.component.util.KeyValue;

/**
 * MenuGroupPanel is used to generate dynamic group menu from scanned page class list. 
 * It generates group menu depending on @MountPageGroup annotation.
 * 
 * 1. You should scan page classes on application init method before using this panel.
 * e.g., PageAnnotationScannerContainer annotationScannerContainer = new PageAnnotationScannerContainer();
 * annotationScannerContainer.addObserver(new MenuPluginObserver(pluginManager));
 * annotationScannerContainer.scan(this, "org.xaloon", null);
 * 
 * 2. Use @MountPageGroup to define grouping box.
 * 
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class MenuGroupPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private PluginManager pluginManager;
	
	public MenuGroupPanel(String id) {
		super(id);
	}
	
	@Override
	protected void onBeforeRender() {
		initGroupMenu ();
		super.onBeforeRender();
	}

	private void initGroupMenu() {
		List<MenuPlugin> menuPluginList = pluginManager.getPluginProviderList(MenuPlugin.class);
		RepeatingView rv = new RepeatingView("menuContainer");
		add (rv);
		for (MenuPlugin menuPlugin : menuPluginList) {
			if (menuPlugin.isEnabled()) {
				WebMarkupContainer inner = new WebMarkupContainer(rv.newChildId());
				rv.add(inner);
				KeyValue<String, Class<? extends WebPage>> menuItem = menuPlugin.getMenuItems().get(0);
				BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("link", menuItem.getValue());
				if (menuItem.getValue().equals(WebApplication.get().getHomePage())) {
					link.setVisible(false);
				}
				inner.add(link);
				final String value = menuPlugin.getPluginName();
				if (StringUtils.isEmpty(value)) {
					link.setVisible(false);
				}
				String imagePath = menuPlugin.getImageResourceKey();
				if (!StringUtils.isEmpty(imagePath)) {
					Image image = new Image("groupTitle", new ResourceReference(MountPageGroup.class, imagePath)) {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						protected void onComponentTag(ComponentTag tag) {
							tag.setName("img");
							tag.addBehavior(new SimpleAttributeModifier("alt", "img"));
							if (!StringUtils.isEmpty(value)) {
								tag.addBehavior(new SimpleAttributeModifier("title", value));
							}
							tag.addBehavior(new SimpleAttributeModifier("style", "width:80px;height:80px;"));
							super.onComponentTag(tag);
							
						}
					};
					link.add(image);
				} else {
					Label groupTitle = new Label("groupTitle", value);				
					link.add(groupTitle);
				}
				
				
			}
		}
	}

}
