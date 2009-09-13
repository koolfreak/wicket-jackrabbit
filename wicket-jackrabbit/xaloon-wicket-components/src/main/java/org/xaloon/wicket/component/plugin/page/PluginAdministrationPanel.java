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
package org.xaloon.wicket.component.plugin.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.xaloon.wicket.component.plugin.Plugin;
import org.xaloon.wicket.component.plugin.PluginManager;
import org.xaloon.wicket.component.plugin.PluginRepository;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class PluginAdministrationPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private transient PluginManager pluginManager;
	
	@SpringBean
	private transient PluginRepository pluginRepository;
	
	public PluginAdministrationPanel(String id) {
		super(id);
	}
	
	@Override
	protected void onBeforeRender() {
		init ();
		super.onBeforeRender();
	}
	
	private void init () {
		RepeatingView pluginBox = new RepeatingView("pluginBox");
		add (pluginBox);
		for (Map.Entry<Class<?>, List<Plugin>> pluginListEntry : pluginManager.retrieveAllPlugins ().entrySet()) {
			WebMarkupContainer wmc = new WebMarkupContainer(pluginBox.newChildId());
			pluginBox.add(wmc);
			initPluginsByType (wmc, pluginListEntry.getKey(), pluginListEntry.getValue());
		}
	}
	
	private void initPluginsByType(WebMarkupContainer pluginBox, Class<?> key, List<Plugin> pluginList) {
		pluginBox.add(new Label("title", getString(key.getName())));
		RepeatingView repeating = new RepeatingView("plugins");
		for (final Plugin plugin : pluginList) {
			WebMarkupContainer wmc = new WebMarkupContainer(repeating.newChildId());
			repeating.add(wmc);
			
			wmc.add(new Label("name", plugin.getPluginName()));
			final DetailsContainer details = new DetailsContainer ("detailsContainer", plugin);
			final AjaxCheckBox c = new AjaxCheckBox("enabled", new Model<Boolean>(plugin.isEnabled())) {
				private static final long serialVersionUID = 1L;
				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					pluginRepository.setEnabled (plugin, getModelObject());
					details.init();
					target.addComponent(details);
				}
			};
			wmc.add(c);
			wmc.add(details);
		}
		pluginBox.add (repeating);
	}

	class DetailsContainer extends WebMarkupContainer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DetailsContainer (String id, Plugin plugin) {
			super (id, new Model<Plugin> (plugin));
			setOutputMarkupId(true);
			
			init ();
		}
		
		public void init () {
			removeAll();
			WebMarkupContainer detailsHeaderContainer = new WebMarkupContainer("detailsHeaderContainer");
			add (detailsHeaderContainer);
			final Plugin plugin = (Plugin)getDefaultModelObject();
			List<String> propertyKeys = new ArrayList<String>();
			propertyKeys.add("info");
			
			RepeatingView repeating = new RepeatingView("properties");
			detailsHeaderContainer.setVisible(plugin.isEnabled() && (plugin.getPropertyKeys ().size() > 0));
			for (final String key : plugin.getPropertyKeys ()) {
				WebMarkupContainer wmc = new WebMarkupContainer(repeating.newChildId());
				repeating.add(wmc);
				wmc.add(new Label("name", key));
				Model<String> modelValue = new Model<String> (pluginRepository.getPluginPropertyValue(plugin, key));
				Form<String> form = new Form<String>("changeProperty", modelValue) {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					@Override
					protected void onSubmit() {
						pluginRepository.setPluginPropertyValue (plugin, key, getModelObject());
						setRedirect(true);
						setResponsePage(PluginAdministrationPage.class);
					}
				};
				wmc.add(form);
				form.add(new TextField<String> ("value", modelValue));
			}
			detailsHeaderContainer.add (repeating);
		}
	}
}
