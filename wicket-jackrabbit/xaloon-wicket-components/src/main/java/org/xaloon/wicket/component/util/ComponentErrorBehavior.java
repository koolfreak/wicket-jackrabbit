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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.xaloon.wicket.component.feedback.XComponentFeedbackPanel;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public class ComponentErrorBehavior extends AjaxFormComponentUpdatingBehavior {

	private Component updateComponent = null;
	private XComponentFeedbackPanel cfp = null;
	
	public ComponentErrorBehavior(String event, Component updateComponent, XComponentFeedbackPanel cfp) {
		super(event);
		this.updateComponent=updateComponent;
		this.cfp = cfp;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void onError(AjaxRequestTarget ajaxRequestTarget, RuntimeException e) {
        changeCssClass(ajaxRequestTarget, false, "invalid");
    }

	
	@Override
	protected void onUpdate(AjaxRequestTarget target) {
		changeCssClass(target, true, "valid");
	}

	@SuppressWarnings("unchecked")
	private void changeCssClass(AjaxRequestTarget ajaxRequestTarget, boolean valid, String cssClass) {
        FormComponent formComponent = getFormComponent();

        if(formComponent.isValid() == valid){
            formComponent.add(new AttributeModifier("class", true, new Model<String>(cssClass)));
            ajaxRequestTarget.addComponent(formComponent);
            ajaxRequestTarget.addComponent(cfp);
        }

        if(updateComponent!=null){
            ajaxRequestTarget.addComponent(updateComponent);
        }
    }

}
