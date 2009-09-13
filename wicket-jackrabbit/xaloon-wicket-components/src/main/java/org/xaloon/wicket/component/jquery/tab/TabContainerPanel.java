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
package org.xaloon.wicket.component.jquery.tab;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * TabContainerPanel is container for dynamic load of panel with jQuery.
 * Hidden clickable field is added into panel. When tab is opened jQuery clicks on link 
 * in order to open content of selected tab.
 *
 * @author vytautas racelis
 */
public class TabContainerPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TabContainerPanel(String id, final FormContainerPanel panel) {
		super(id);
		panel.setVisible(false);
		add (panel);
		final IndicatingAjaxLink<String> link = new IndicatingAjaxLink<String>("id"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (!panel.isVisible()) {
					panel.setVisible(true);
					target.addComponent(TabContainerPanel.this);
					add(new SimpleAttributeModifier("class", "selected"));
					target.addComponent(this);
					if (panel.getQueryTabPanel () != null) {
						target.appendJavascript(panel.getQueryTabPanel().getReloadTabsFunction(0));
					}
				}
			}			
		};
		link.setOutputMarkupId(true);
		add (link);
	}
}
