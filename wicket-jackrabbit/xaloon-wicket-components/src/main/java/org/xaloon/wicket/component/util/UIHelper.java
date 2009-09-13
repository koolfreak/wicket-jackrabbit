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
package org.xaloon.wicket.component.util;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.xaloon.wicket.component.feedback.XComponentFeedbackPanel;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public final class UIHelper {
	private UIHelper () {}
	
	public static TextField<String> addTextField(Form<?>  form, String name, PropertyModel<String> model) {
		final TextField<String> field = new TextField<String>(name, model);
	
		form.add (field);
		return field;
	}
	
	public static TextField<String> addTextFieldInsideContainer(Form<?>  form, String name, PropertyModel<String> model, boolean visible) {
		WebMarkupContainer wc = new WebMarkupContainer (name);
		wc.setVisible(visible);
		form.add(wc);
		final TextField<String> field = new TextField<String>(name, model);
		field.setOutputMarkupId(true);
		wc.add(field);
		return field;
	}
	
	public static void addTextAreaField(Form<?>  form, String name, PropertyModel<String> model) {
		final TextArea<String> field = new TextArea<String>(name, model);
		form.add (field);
	}
	
	public static TextArea<String> addRequiredTextAreaField(Form<?>  form, String name, PropertyModel<String> model) {
		final TextArea<String> field = new TextArea<String>(name, model);
		field.setRequired(true);
		field.setOutputMarkupId(true);
		form.add (field);
		XComponentFeedbackPanel cfp = new XComponentFeedbackPanel(name + "_fp", field);
		form.add (cfp);
		return field;
	}

	public static RequiredTextField<String> addRequiredInputField(Form<?>  form, String name, PropertyModel<String> model) {
		final RequiredTextField<String> field = new RequiredTextField<String>(name, model);
		form.add (field);
		XComponentFeedbackPanel cfp = new XComponentFeedbackPanel(name + "_fp", field);
		form.add (cfp);
		return field;
	}

	public static ComponentContainer<String> addInputFieldContainer(WebMarkupContainer  container, String name, 
			PropertyModel<String> model, boolean visible, boolean required) {
		
		ComponentContainer<String> componentContainer = new ComponentContainer<String>();
		final TextField<String> field = new TextField<String>(name, model);
		field.setRequired(required);
		field.setVisible(visible);
		container.add (field);
		XComponentFeedbackPanel cfp = new XComponentFeedbackPanel(name + "_fp", field, required);
		container.add (cfp);
		componentContainer.setComponent (field);
		componentContainer.setPanel (cfp);
		return componentContainer;
	}

	public static ComponentContainer<String> addRequiredTextAreaFieldContainer(Form<?>  form, String name, PropertyModel<String> model) {
		ComponentContainer<String> componentContainer = new ComponentContainer<String>();
		final TextArea<String> field = new TextArea<String>(name, model);
		field.setRequired(true);
		field.setOutputMarkupId(true);
		form.add (field);
		XComponentFeedbackPanel cfp = new XComponentFeedbackPanel(name + "_fp", field);
		form.add (cfp);
		
		componentContainer.setComponent (field);
		componentContainer.setPanel (cfp);
		return componentContainer;
	}
}
