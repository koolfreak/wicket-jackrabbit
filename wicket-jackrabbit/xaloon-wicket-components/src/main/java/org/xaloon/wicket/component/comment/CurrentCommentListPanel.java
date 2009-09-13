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
package org.xaloon.wicket.component.comment;

import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.xaloon.wicket.component.util.Formatter;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public class CurrentCommentListPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_PAGE_SIZE = 10;
	
	private WebMarkupContainer commentContainer;
	
	private String timezone;
	
	private boolean providerAll = false;
	
	private String dateFormat = "yy/MM/dd";
	private Locale locale = Locale.getDefault();
	private IDataProvider<AbstractComment> provider;
	private int itemsPerPage = DEFAULT_PAGE_SIZE;
	
	public CurrentCommentListPanel(String id, IModel<Commentable> model, String timezone, IDataProvider<AbstractComment> provider) {
		this (id, model, timezone, provider, DEFAULT_PAGE_SIZE);
	}
	
	public CurrentCommentListPanel(String id, IModel<Commentable> model, String timezone, IDataProvider<AbstractComment> provider, int itemsPerPage) {
		super(id, model);
		this.timezone = timezone;
		this.provider = provider;
		this.itemsPerPage = itemsPerPage;
	}

	public void init() {
		commentContainer = new WebMarkupContainer("commentContainer");
		commentContainer.setOutputMarkupId(true);
		add (commentContainer);
		
		load ();
	}

	public void load() {
		commentContainer.removeAll();
		if (!providerAll) {
			// usual case with pages
			DataView<AbstractComment> dv = new DataView<AbstractComment>("rowContainer", provider, itemsPerPage) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(Item<AbstractComment> item) {
					updateItem (item, item.getModelObject());
				}
				
			};
			commentContainer.add (dv);
			commentContainer.add(new AjaxPagingNavigator("navigator", dv)); 
		} else {
			// google app engine - special case - issues with DataView, ListView
			RepeatingView repeating = new RepeatingView("rowContainer");
			repeating.setOutputMarkupId(true);
			Iterator<? extends AbstractComment> result = provider.iterator(0, -1);
			while (result.hasNext()) {
				AbstractComment comment = result.next();
				WebMarkupContainer wmc = new WebMarkupContainer(repeating.newChildId());
				
				repeating.add(wmc);
				updateItem (wmc, comment);
			}
			commentContainer.add (repeating);
			commentContainer.add(new WebMarkupContainer("navigator").setVisible(false));
		}
	}

	protected void updateItem(WebMarkupContainer item, AbstractComment comment) {
		Label name = new Label("name", comment.getName());
		if (!StringUtils.isEmpty(comment.getWebsite())) {
			name.add(new SimpleAttributeModifier("href", (comment.getWebsite().startsWith("http://")?comment.getWebsite():"http://"+comment.getWebsite())));
		}
		item.add(name);
		item.add(new Label("createDate", Formatter.formatDateTime(timezone, comment.getCreateDate(), dateFormat, locale)));
		item.add(new Label("message", Formatter.prepareStringForHTML(comment.getMessage())).setEscapeModelStrings(false));
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setProviderAll(boolean providerAll) {
		this.providerAll = providerAll;
	}

	public boolean isProviderAll() {
		return providerAll;
	}
}
