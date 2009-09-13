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

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.xaloon.wicket.component.jquery.JQueryBehavior;
import org.xaloon.wicket.component.jquery.JQueryTabPanel;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public abstract class JQueryTabForm<T> extends Form<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JQueryTabForm(String id, final IModel<T> model, Panel parent) {
		super(id, model);
		JQueryBehavior queryBehavior = new JQueryBehavior();
		add(queryBehavior);

		final JQueryTabPanel queryTabPanel = new JQueryTabPanel("tbPanel");
		queryTabPanel.setOutputMarkupId(true);
		queryTabPanel.init(getTabList(), queryBehavior);
		queryTabPanel.setSelectedTab(0);
		queryTabPanel.addOnShowFunction(0);
		add(queryTabPanel);

		final ComponentFeedbackPanel feedback = new ComponentFeedbackPanel("fp", this);
		feedback.setOutputMarkupId(true);
		parent.add(feedback);
		class JqueryAjaxButton extends AjaxButton {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public JqueryAjaxButton(String id) {
				super(id);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				int index = 0, firstFailure = -1;
				for (XTab tab : getTabList()) {
					if (!tab.isValidTab()) {
						if (firstFailure == -1) {
							firstFailure = index;
						}
						form.error("Tab '" + tab.getTitle().getObject() + "' has errors.");
					} else if (!tab.isVisibleTab() && tab.hasRequiredFields()) {
						form.warn("Tab '" + tab.getTitle().getObject() + "' may be not initialized.");
					}
					index++;
					tab.processNestedTabs(target);
				}

				target.addComponent(form);

				target.appendJavascript(queryTabPanel.getReloadTabsFunction(firstFailure));
				target.addComponent(feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.addComponent(form);
				target.addComponent(feedback);
				target.appendJavascript(queryTabPanel.getReloadTabsFunction(0));
				onFormSubmit(model, form);
				for (XTab tab : getTabList()) {
					tab.processNestedTabs(target);
				}
			}
		}
		add(new JqueryAjaxButton("check"));
	}

	@Override
	protected void onValidate() {
		for (XTab tab : getTabList()) {
			tab.validateTab();
		}
	}

	protected abstract List<XTab> getTabList();

	protected abstract void onFormSubmit(IModel<T> model, Form<?> form);
}
