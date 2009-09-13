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
package org.xaloon.wicket.component.captcha;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.xaloon.wicket.component.util.ComponentContainer;
import org.xaloon.wicket.component.util.UIHelper;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class ReCaptchaPanel extends Panel {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RECAPTCHA_PRIVATE_KEY = "RECAPTCHA_PRIVATE_KEY";

	public static final String RECAPTCHA_PUBLIC_KEY = "RECAPTCHA_PUBLIC_KEY";
	
	private ComponentContainer<String> recaptcha_response_fieldContainer;
	private String recaptcha;
	
	public ReCaptchaPanel(String id, Form<?> form, final String privateKey, String publicKey) {
		super(id);
        WebMarkupContainer reJS = new WebMarkupContainer("reJS");
        reJS.add(new SimpleAttributeModifier("src", "http://api.recaptcha.net/challenge?k=" + publicKey));
        add(reJS);
        
        WebMarkupContainer iframe = new WebMarkupContainer("iframe");
        iframe.add(new SimpleAttributeModifier("src", "http://api.recaptcha.net/noscript?k=" + publicKey));
        add(iframe);
	        
		add(CSSPackageResource.getHeaderContribution(new ResourceReference(ReCaptchaPanel.class, "recaptcha.css")));
		recaptcha_response_fieldContainer = UIHelper.addInputFieldContainer(this, "recaptcha_response_field", new PropertyModel<String>(this, "recaptcha"), true,true);
		recaptcha_response_fieldContainer.getComponent().setMarkupId("recaptcha_response_field");
		form.add(new IFormValidator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public FormComponent<?>[] getDependentFormComponents() {
				return null;
			}

			public void validate(Form<?> form) {
				Request request = RequestCycle.get().getRequest();
				String recaptcha_challenge_field = request.getParameter("recaptcha_challenge_field");
				if ((recaptcha!= null) && !ReCaptchaValidator.validate(privateKey, ((WebRequestCycle)RequestCycle.get()).getWebRequest().getHttpServletRequest().getRemoteAddr(), recaptcha, recaptcha_challenge_field)) {
					recaptcha_response_fieldContainer.getComponent().error("Incorrect, please try again");
				}
			}});
	}

	public void setRecaptcha(String recaptcha) {
		this.recaptcha = recaptcha;
	}

	public String _getRecaptcha() {
		return recaptcha;
	}

	public void onError(AjaxRequestTarget target) {
		target.addComponent(recaptcha_response_fieldContainer.getPanel());
		target.appendJavascript("Recaptcha.reload()");
	}

	public void onSubmit(AjaxRequestTarget target) {
		setRecaptcha("");
		target.addComponent(recaptcha_response_fieldContainer.getComponent());
		target.appendJavascript("Recaptcha.reload()");
		
	}
}
