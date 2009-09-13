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
package org.xaloon.wicket.component.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.xaloon.wicket.component.basic.MenuPanel;
import org.xaloon.wicket.component.basic.MetaTagWebContainer;
import org.xaloon.wicket.component.plugin.MetaTagPlugin;
import org.xaloon.wicket.component.plugin.PluginManager;

/**
 * Abstract web page includes head rendering information, such as meta tags, page title, css links, other.
 * 
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public abstract class AbstractWebPage extends WebPage {
	@SpringBean
	private PluginManager pluginManager;
	private Class<? extends Page> virtualPageClass;	
	
	public AbstractWebPage (PageParameters params) {
		initHTMLHead("");
		initHeader ();
		initMenu(params);		
		initContent(params);
		initFooter ();
	}

	protected void initHeader() {}
	
	private final void initContent (PageParameters params) {
		Panel contentPanel = createContent ("content", params);
		if (contentPanel != null) {
			add (contentPanel);
		}
	}

	protected abstract Panel createContent(String id, PageParameters params);

	protected void initFooter() {}
	
	public void initHTMLHead(String prefix) {
		add (new Label ("title", prefix + getString ("title")));
		add (new MetaTagWebContainer("keywords", new Model<String>(getDefaultString ("keywords"))));
		add (new MetaTagWebContainer("description", new Model<String>(getDefaultString ("description"))));
		RepeatingView repeating = new RepeatingView("meta");
		for (Component metaTag : newTagList()) {
			WebMarkupContainer wmc = new WebMarkupContainer(repeating.newChildId());
			repeating.addOrReplace(wmc);
			wmc.add(metaTag);
		}
		add (repeating);
	}
	
	/**
	 * Default <meta../> information. Override and add new <i>Component</i> if something additional is required
	 * 
	 * @return
	 */
	protected List<Component> newTagList() {
		List<Component> result = new ArrayList<Component> ();
		for (MetaTagPlugin plugin : pluginManager.getPluginProviderList (MetaTagPlugin.class)) {
			if (plugin.isEnabled ()) {
				plugin.processMetaTag(result);
			}
		}
		return result;
	}
	
	protected void initMenu(PageParameters parameters) {
		add (new MenuPanel("menu", true));
	}
	
	public String getDefaultString (String key) {
		return getString(key, null, key);
	}
	public Class<? extends Page> getVirtualPageClass() {
		return virtualPageClass;
	}
	public void setVirtualPageClass(Class<? extends Page> virtualPageClass) {
		this.virtualPageClass = virtualPageClass;
	}
}
