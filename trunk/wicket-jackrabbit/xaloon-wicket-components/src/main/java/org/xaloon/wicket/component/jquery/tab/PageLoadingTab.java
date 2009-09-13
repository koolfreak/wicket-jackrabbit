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

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Use this class if you want to get external or other page information. This is useful for dynamic help tab.
 * Not useful if Form is in that page. It will not be validated!
 * 
 * @author vytautas racelis
 */
public class PageLoadingTab extends AbstractTab implements XTab {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Class<? extends WebPage> pageClass;
	private PageParameters params;
	
	public PageLoadingTab(IModel<String> title) {
		super(title);
	}
	
	public PageLoadingTab(IModel<String> title, Class<? extends WebPage> clz, PageParameters params) {
		super(title);
		this.pageClass = clz;
		this.params = params;
	}

	public boolean hasRequiredFields() {
		return false;
	}

	public boolean isValidTab() {
		return true;
	}

	public boolean isVisibleTab() {
		return true;
	}

	public void validateTab() {}

	@Override
	public Panel getPanel(String panelId) {
		return new PageTabContainerPanel(panelId, pageClass, params);
	}

	public void processNestedTabs(AjaxRequestTarget target) {		
	}
}
