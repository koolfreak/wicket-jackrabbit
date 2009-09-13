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

import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.xaloon.wicket.component.jquery.JQueryTabPanel;

/**
 * PageTabContainerPanel is used to load page into tab
 * 
 * @author vytautas racelis
 */
public class PageTabContainerPanel extends FormContainerPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Class<? extends WebPage> pageClass;
	private PageParameters params;
	
	public PageTabContainerPanel(String id, Class<? extends WebPage> clz, PageParameters params) {
		super(id);
		this.pageClass = clz;
		this.params = params;
	}


	public Class<? extends Page> getPageClass() {
		return pageClass;
	}

	public PageParameters getParameters () {
		return params;
	}


	@Override
	public ValidatedForm<?> getForm() {
		return null;
	}


	@Override
	public JQueryTabPanel getQueryTabPanel() {
		return null;
	}
}
