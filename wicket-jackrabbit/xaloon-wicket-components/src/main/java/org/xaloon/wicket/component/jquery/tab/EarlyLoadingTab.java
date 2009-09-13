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
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Use this class in order to load panel content on page load. Panel content will be loaded with all page information.
 * 
 * @author vytautas racelis
 */
public abstract class EarlyLoadingTab extends AbstractTab implements XTab {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormContainerPanel panel;
	
	public EarlyLoadingTab(IModel<String> title) {
		super(title);
	}
	
	public void setPanel(FormContainerPanel panel) {
		this.panel = panel;
	}

	public FormContainerPanel getPanel() {
		return panel;
	}

	public boolean hasRequiredFields() {
		if (panel.getForm() != null) {
			return panel.getForm().hasRequiredFields ();
		}
		return false;
	}

	public boolean isValidTab() {
		if (panel.getForm() != null) {
			return !panel.getForm().hasError();
		}
		return true;
	}

	public boolean isVisibleTab() {
		return panel.isVisible();
	}

	public void validateTab() {
		if (panel.getForm() != null) {
			panel.getForm().validateForm();
		}
	}
	
	@Override
	public Panel getPanel(String panelId) {
		panel = createPanel (panelId);
		return panel;
	}
	
	public void processNestedTabs(AjaxRequestTarget target) {		
	}
	
	public abstract FormContainerPanel createPanel(String panelId);
}
