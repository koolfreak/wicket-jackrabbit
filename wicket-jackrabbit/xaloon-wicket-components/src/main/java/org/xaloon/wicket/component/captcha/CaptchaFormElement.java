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

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.xaloon.wicket.component.util.ComponentContainer;
import org.xaloon.wicket.component.util.UIHelper;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public class CaptchaFormElement extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String captcha, captchaText;
	private ComponentContainer<String> captchaContainer;
	private CaptchaPanel captchaPanel;
	
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getCaptchaText() {
		return captchaText;
	}

	public void setCaptchaText(String captchaText) {
		this.captchaText = captchaText;
	}
	
	public CaptchaFormElement(String id) {
		super(id);
		final PropertyModel<String> captchaModel = new PropertyModel<String> (this, "captcha");
		captchaContainer = UIHelper.addInputFieldContainer(this, "captchaText", new PropertyModel<String>(this, "captchaText"), true,true);
		
		captchaPanel = new CaptchaPanel("captcha", captchaModel);
		captchaPanel.setupCaptcha(null, captchaModel) ;
		add (captchaPanel);		
		captchaContainer.getComponent().add(new CaptchaValidator (captchaModel));
		AjaxFallbackLink<Void> reload;
		add(reload = new AjaxFallbackLink<Void>("refreshCaptcha") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				captchaPanel.setupCaptcha(target, captchaModel);
			}
		});
		reload.add (new Image("reload", new ResourceReference(CaptchaFormElement.class, "reload.jpg")));
	}

	public void reset(AjaxRequestTarget target) {
		captchaText = null;
		captchaPanel.setupCaptcha(target, new PropertyModel<String> (this, "captcha"));
		target.addComponent(getCaptchaContainer().getComponent());
	}

	public ComponentContainer<String> getCaptchaContainer() {
		return captchaContainer;
	}
}
