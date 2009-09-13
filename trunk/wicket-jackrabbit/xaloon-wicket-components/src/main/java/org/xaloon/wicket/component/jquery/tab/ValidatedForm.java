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

import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormVisitorParticipant;
import org.apache.wicket.model.IModel;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public class ValidatedForm<T> extends Form<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidatedForm(String id, IModel<T> model) {
		super(id, model);
	}
	
	public void validateForm () {
		validate();
	}

	public boolean hasRequiredFields() {
		final BooleanHolder result = new BooleanHolder();
		visitFormComponents(new FormComponent.IVisitor() {

			@SuppressWarnings("unchecked")
			public Object formComponent(IFormVisitorParticipant formComponent) {
				if (result.value) {
					return null;
				}
				if (formComponent instanceof FormComponent) {
					FormComponent c = (FormComponent)formComponent;
					if (c.isRequired()) {
						result.value = true;
					}
				}
				return null;
			}});
		return result.value;
	}
	
	class BooleanHolder implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private boolean value;

		public boolean isValue() {
			return value;
		}

		public void setValue(boolean value) {
			this.value = value;
		}
		
	}
}
