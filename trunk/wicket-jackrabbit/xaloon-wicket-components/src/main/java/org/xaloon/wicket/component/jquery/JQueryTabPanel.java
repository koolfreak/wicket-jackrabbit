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
package org.xaloon.wicket.component.jquery;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.xaloon.wicket.component.jquery.tab.PageTabContainerPanel;
import org.xaloon.wicket.component.jqueryui.JQueryUITabBehaviorItem;

/**
 * JQuery UI based client side tabbed panel
 * 
 * @author vytautas racelis
 *
 */
public class JQueryTabPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String TAB_ID = "1";
	private static final String TAB_TITLE_ID = "9";
	
	private String theme;
	private JQueryUITabBehaviorItem queryUITabBehaviorItem;	
	private WebMarkupContainer contentContainer;
	private WebMarkupContainer titleContainer;
	
	public JQueryTabPanel(String id) {
		super(id);
		setOutputMarkupId(true);
	}
	
	public void init(List<ITab> tabs) {
		init (tabs, new JQueryBehavior(true));
	}
	
	public void init(List<? extends ITab> tabs, JQueryBehavior queryBehavior) {
		if (queryBehavior.isCreatedFromComponent()) {
			add (queryBehavior);
		}
		final WebMarkupContainer container = new WebMarkupContainer("tabs");
		container.setOutputMarkupId(true);
		add (container);
		queryUITabBehaviorItem = new JQueryUITabBehaviorItem() {
			private static final long serialVersionUID = 1L;
			@Override
			public String getMarkupId() {
				return container.getMarkupId();
			}
			
			@Override
			public String getTheme() {
				return (theme != null)?theme:super.getTheme();
			}
		};
		queryBehavior.addChild(queryUITabBehaviorItem);
		
		RepeatingView titles = new RepeatingView("tab");
		titleContainer = new WebMarkupContainer ("tc");
		titleContainer.setOutputMarkupId(true);
		container.add(titleContainer);
		titleContainer.add(titles);
		
		contentContainer = new WebMarkupContainer ("cc");
		contentContainer.setOutputMarkupId(true);
		container.add (contentContainer);
		
		RepeatingView contents = new RepeatingView("content");
		contentContainer.add(contents);
		
		
		for (int i=0; i<tabs.size(); i++) {
			final Panel content = tabs.get(i).getPanel(TAB_ID+i);
			WebMarkupContainer title = new WebMarkupContainer(TAB_ID+ TAB_TITLE_ID+i);
			title.setOutputMarkupId(true);			
			titles.add(title);	
			content.setOutputMarkupId(true);
			contents.add(content);
			
			if (content instanceof PageTabContainerPanel) {
				/**
				 * generate <li><a href="url">title</a></li>
				 * page will be loaded instead of panel
				 */
				final PageTabContainerPanel panel = (PageTabContainerPanel)content;
				AbstractLink link  = new AbstractLink("link") {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onComponentTag(ComponentTag tag) {
						super.onComponentTag(tag);
						tag.put("href", urlFor (panel.getPageClass(), panel.getParameters()));
					}
				};
				link.add(new Label("title", tabs.get(i).getTitle()));
				title.add(link);
			} else {
				/**
				 * generate <li><a href="#markupId">title</a></li>
				 * panel will be loaded
				 */
				AbstractLink link  = new AbstractLink("link") {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onComponentTag(ComponentTag tag) {
						super.onComponentTag(tag);
						tag.put("href", "#"+content.getMarkupId());
					}
				};
				link.add(new Label("title", tabs.get(i).getTitle()));
				title.add(link);
			}
		}
	}
	
	public JQueryUITabBehaviorItem getQueryUITabBehaviorItem() {
		return queryUITabBehaviorItem;
	}

	public void setSelectedTab(int i) {
		queryUITabBehaviorItem.setSelectedTab(i);
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getContentContainerId() {
		return contentContainer.getMarkupId();
	}

	public void addOnShowFunction(int i) {
		getQueryUITabBehaviorItem().addOrReplace("show", getJQueryOnShowFunction (getContentContainerId (), i), false);
	}
	
	private String getJQueryOnShowFunction(String containerMarkupId, int i) {
		return "function(event, ui) {var elem = '#' + ui.panel.id; var link = $(this).find(elem).find('a:contains(load)');  var loaded = link.hasClass('selected').toString(); if (loaded == 'false') { $(link).click();} $('#" + containerMarkupId + "').children().hide();$('#" + containerMarkupId + "').find(elem).show(); $('#" + titleContainer.getMarkupId() + "').find('li').removeClass('ui-tabs-selected ui-state-active').addClass('ui-state-default'); $('#" + titleContainer.getMarkupId() + "').find('li.ui-state-hover').removeClass('ui-state-hover').addClass('ui-tabs-selected ui-state-active'); if ($('#" + titleContainer.getMarkupId() + "').find('li.ui-state-active').hasClass('ui-state-active').toString() == 'false') {$($('#" + titleContainer.getMarkupId() + "').find('li')[" + i + "]).addClass('ui-tabs-selected ui-state-active');}}";
	}

	public String getReloadTabsFunction(int index) {
		return "$('#" + getQueryUITabBehaviorItem().getMarkupId() + "').tabs({selected: '" +  index + "', show: " + getJQueryOnShowFunction (getContentContainerId (), index) + "});";
	}
}
