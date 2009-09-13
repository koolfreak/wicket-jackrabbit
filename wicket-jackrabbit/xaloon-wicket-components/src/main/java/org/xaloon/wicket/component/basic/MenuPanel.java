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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.xaloon.wicket.component.mounting.MountPageGroup;
import org.xaloon.wicket.component.mounting.MountingUtils;
import org.xaloon.wicket.component.page.AbstractWebPage;
import org.xaloon.wicket.component.plugin.MenuPlugin;
import org.xaloon.wicket.component.plugin.PluginManager;
import org.xaloon.wicket.component.util.KeyValue;

/**
 * MenuPanel is used to generate dynamic menu from scanned page class list. 
 * It generates menu boxes depending on @MountPageGroup annotation and menu link depending on @MountPage.
 * 
 * 1. You should scan page classes on application init method before using this panel.
 * e.g., PageAnnotationScannerContainer annotationScannerContainer = new PageAnnotationScannerContainer();
 * annotationScannerContainer.addObserver(new MenuPluginObserver(pluginManager));
 * annotationScannerContainer.scan(this, "org.xaloon", null);
 * 
 * 2. Use @MountPageGroup to define grouping box.
 * 
 * 3. Use @MountPage to define menu link. Remember, @MountPage is also used to generate bookmarkable page link.
 *  
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class MenuPanel extends Panel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean useConditionalMenu;
	
	@SpringBean
	private PluginManager pluginManager;
	
	public MenuPanel(String id, boolean useConditionalMenu) {
		super(id);
		this.useConditionalMenu = useConditionalMenu;
	}

	private void initHierarchyMenu(List<MenuPlugin> menuPluginList) {
		RepeatingView rv = new RepeatingView("menuContainer");
		add (rv);
		for (MenuPlugin menuPlugin : menuPluginList) {
			if (menuPlugin.isEnabled()) {
				WebMarkupContainer inner = new WebMarkupContainer(rv.newChildId());
				rv.add(inner);
				KeyValue<String, Class<? extends WebPage>> menuItem = menuPlugin.getMenuItems().get(0);
				BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("link", menuItem.getValue());
				inner.add(link);
				String value = menuPlugin.getPluginName();
				if (menuPlugin.getMenuItems().size() < 2) {
					value = menuItem.getKey();
				}
				Label groupTitle = new Label("tt", value);
				if (StringUtils.isEmpty(value)) {
					groupTitle.setVisible(false);
				}
				link.add(groupTitle);
				initMenu (inner, menuPlugin.getMenuItems ());
			}
		}
	}

	private void initMenu(WebMarkupContainer inner, List<KeyValue<String, Class<? extends WebPage>>> pageClassList) {
		WebMarkupContainer wmc = new WebMarkupContainer("itemContainer");
		wmc.setVisible(!pageClassList.isEmpty() && (pageClassList.size() > 1));
		inner.add(wmc);
		ListView<KeyValue<String, Class<? extends WebPage>>> lvl = new ListView<KeyValue<String,Class<? extends WebPage>>>("menuItem", pageClassList) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<KeyValue<String, Class<? extends WebPage>>> item) {
				KeyValue<String, Class<? extends WebPage>> mo = item.getModelObject();
				BookmarkablePageLink<String> link = new BookmarkablePageLink<String>("link", mo.getValue());
				String value = mo.getKey();
				if (StringUtils.isEmpty(value)) {
					value = mo.getValue().getName();
				}
				link.add(new Label("name", value));
				item.add(link);
			}
			
		};
		wmc.add(lvl);
	}
	
	@Override
	protected void onBeforeRender() {
		List<MenuPlugin> menuPluginList = new ArrayList<MenuPlugin>();
		if (useConditionalMenu) {
			MountPageGroup mpg = MountingUtils.getAnnotation(retrieveParentPageClass ());
			if (mpg != null) {
				MenuPlugin plugin = pluginManager.retrievePlugin(MenuPlugin.class, mpg.context());
				if (plugin != null) {
					MenuPlugin def = pluginManager.retrievePlugin(MenuPlugin.class, MenuPlugin.HOME_PAGE);
					if (def != null) {
						menuPluginList.add(def);
					}
					menuPluginList.add(plugin);
					
				}
			}			
		}
		if (menuPluginList.isEmpty()) {
			menuPluginList = pluginManager.getPluginProviderList(MenuPlugin.class);
		}
		initHierarchyMenu (menuPluginList);
		
		super.onBeforeRender();
	}

	private Class<?> retrieveParentPageClass() {
		MarkupContainer parentPage = getParent();
		Class<?> result = parentPage.getClass();
		if (parentPage instanceof AbstractWebPage) {
			Class<?> virtualPageClass = ((AbstractWebPage)parentPage).getVirtualPageClass();
			if (virtualPageClass != null) {
				result = virtualPageClass;
			}
		}
		return result;
	}
}
