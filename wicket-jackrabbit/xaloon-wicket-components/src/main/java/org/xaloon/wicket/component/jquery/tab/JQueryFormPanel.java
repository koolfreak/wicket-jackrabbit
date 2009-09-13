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

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public abstract class JQueryFormPanel<T> extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JQueryFormPanel(String id, IModel<T> model) {
		super(id, model);
		JQueryTabForm<T> form = new JQueryTabForm<T>("form", model, this) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected List<XTab> getTabList() {
				return JQueryFormPanel.this.getTabList ();
			}

			@Override
			protected void onFormSubmit(IModel<T> model, Form<?> form) {
				JQueryFormPanel.this.onSubmit(model, form);
			}
			
		};
		add(form);
	}

	protected abstract List<XTab> getTabList();
	protected abstract void onSubmit(IModel<T> model, Form<?> form);

}
