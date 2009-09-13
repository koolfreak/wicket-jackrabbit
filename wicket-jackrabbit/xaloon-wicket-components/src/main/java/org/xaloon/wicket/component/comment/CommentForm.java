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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.xaloon.wicket.component.captcha.ReCaptchaPanel;
import org.xaloon.wicket.component.email.EmailFacade;
import org.xaloon.wicket.component.email.EmailTemplate;
import org.xaloon.wicket.component.util.ComponentContainer;
import org.xaloon.wicket.component.util.UIHelper;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public abstract class CommentForm extends Form<AbstractComment>{
	private static final Log log = LogFactory.getLog(CommentForm.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ComponentContainer<String> emailContainer;
	private TextField<String> websiteField;	
	
	private ComponentContainer<String> usernameContainer;
	private ComponentContainer<String> textContainer;
	private ReCaptchaPanel reCaptchaPanel;
	private ComponentFeedbackPanel formFeedbackPanel;
	
	private boolean anonymous;
	private boolean emailVisible;
	private boolean websiteVisible;
	private boolean sendEmail;
	private boolean useCaptcha;
	
	private Map<String, String> options = new HashMap<String, String> ();
	
	
	@SpringBean
	private EmailFacade emailFacade;
	
	@SpringBean (name="commentEmailTemplate")
	private EmailTemplate emailTemplate;
	
	public CommentForm(String id, IModel<AbstractComment> model) {
		super(id, model);
	}

	protected void init() {
		IModel<AbstractComment> model = getModel();
		
		//START refactor
		WebMarkupContainer wc = new WebMarkupContainer ("email");
		wc.setVisible(emailVisible);
		add(wc);
		emailContainer = UIHelper.addInputFieldContainer(wc, "email", new PropertyModel<String>(model, "email"), anonymous, false);
		emailContainer.getComponent().add(EmailAddressValidator.getInstance());		
		//END refactor
		
		websiteField = UIHelper.addTextFieldInsideContainer(this, "website", new PropertyModel<String>(model, "website"), websiteVisible);
		
		textContainer = UIHelper.addRequiredTextAreaFieldContainer(this, "message", new PropertyModel<String>(model, "message"));
		usernameContainer = UIHelper.addInputFieldContainer(this, "username_in", new PropertyModel<String>(model, "name"), anonymous,true);
		
		add (new Label("username", new Model<String> (model.getObject().getName())).setVisible(!anonymous));
		
		add(new CommentAjaxFormSubmitBehavior(this, "onsubmit"));
		add (formFeedbackPanel = new ComponentFeedbackPanel("formFeedback", this));
		formFeedbackPanel.setOutputMarkupId(true);
		
		add(reCaptchaPanel = new ReCaptchaPanel("rc", this, options.get(ReCaptchaPanel.RECAPTCHA_PRIVATE_KEY), options.get(ReCaptchaPanel.RECAPTCHA_PUBLIC_KEY)));
		reCaptchaPanel.setOutputMarkupId(true);
		reCaptchaPanel.setVisible(useCaptcha);
	}

	@Override
	protected void onSubmit() {
		if (sendEmail && !StringUtils.isEmpty(getModelObject().getEmail())) {
			sendEmail (getModelObject());
		}
		setDefaultModel(saveComment (getModelObject()));
		
		cleanup();
	}

	private void sendEmail(AbstractComment ac) {
		try {
			emailFacade.sendEmail (emailTemplate, ac.getMessage(), ac.getEmail(), ac.getName());
		} catch (Exception e) {
			log.error (e);
		}
	}

	private void cleanup() {
		getModelObject().setMessage("");
		textContainer.getComponent().setModel(new PropertyModel<String> (getModelObject(), "message"));
		if (anonymous) {
			getModelObject().setName("");
			usernameContainer.getComponent().setModel(new PropertyModel<String> (getModelObject(), "name"));
			
			getModelObject().setEmail("");
			emailContainer.getComponent().setModel(new PropertyModel<String> (getModelObject(), "email"));
			getModelObject().setWebsite("");
			websiteField.setModel(new PropertyModel<String> (getModelObject(), "website"));
		}
	}
	
	abstract CompoundPropertyModel<AbstractComment> saveComment(AbstractComment modelObject);	
	abstract CurrentCommentListPanel getComments ();

	public void setEmailVisible(boolean emailVisible) {
		this.emailVisible = emailVisible;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public void setWebsiteVisible(boolean websiteVisible) {
		this.websiteVisible = websiteVisible;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
	
	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	public boolean isUseCaptcha() {
		return useCaptcha;
	}

	class CommentAjaxFormSubmitBehavior extends AjaxFormSubmitBehavior {
		
		public CommentAjaxFormSubmitBehavior(Form<?> form, String event) {
			super(form, event);
		}

		private static final long serialVersionUID = 1L;

		
		@Override
		protected IAjaxCallDecorator getAjaxCallDecorator() {
			return new AjaxCallDecorator() {
				private static final long serialVersionUID = 1L;

				@Override
				public CharSequence decorateScript(CharSequence script) {
					return script + "return false;";
				}
			};
		}

		@Override
		protected void onError(AjaxRequestTarget target) {
			reCaptchaPanel.onError (target);
			target.addComponent(textContainer.getPanel());
			target.addComponent(formFeedbackPanel);
			
			if (usernameContainer.getComponent().isVisible()) {
				target.addComponent(usernameContainer.getPanel());
			}
			if (emailContainer.getComponent().isVisible()) {
				target.addComponent(emailContainer.getPanel());
			}
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target) {
			reCaptchaPanel.onSubmit (target);
			CurrentCommentListPanel comments = getComments ();
			comments.load();
			target.addComponent(comments);
			target.addComponent(textContainer.getComponent());
			target.addComponent(formFeedbackPanel);
			if (usernameContainer.getComponent().isVisible()) {
				target.addComponent(usernameContainer.getComponent());
			}
			if (emailContainer.getComponent().isVisible()) {
				emailContainer.getComponent().add(new SimpleAttributeModifier("class", "rounded"));
				target.addComponent(emailContainer.getComponent());
			}
			if (websiteField.isVisible()) {
				target.addComponent(websiteField);
			}
			//target.appendJavascript("document.getElementById('" + usernameContainer.getComponent().getMarkupId() + "').focus();");
		}
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}	
}
