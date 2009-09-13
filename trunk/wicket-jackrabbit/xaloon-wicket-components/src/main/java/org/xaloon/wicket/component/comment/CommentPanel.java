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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public abstract class CommentPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DEFAUL_PAGE_SIZE = 10;

	private String dateFormat = "yy/MM/dd";
	private Locale locale = Locale.getDefault();
	private int pageSize = DEFAUL_PAGE_SIZE;
	private boolean providerAll;
	private boolean anonymous;
	private boolean emailVisible = true;
	private boolean websiteVisible = true;
	private boolean useCaptcha = false;
	
	private boolean sendEmail = true;

	private Map<String, String> options = new HashMap<String, String>();
	private List<AbstractBehavior> formValidators = new ArrayList<AbstractBehavior> ();
	
	public CommentPanel(String id, final IModel<Commentable> model, boolean anonymous) {
		super(id, model);
		this.anonymous = anonymous;
	}

	protected void initialize(final IModel<Commentable> model, String username, boolean readVisible, boolean writeVisible,
			IDataProvider<AbstractComment> provider) {
		add(CSSPackageResource.getHeaderContribution(new ResourceReference(CommentPanel.class, "comment.css")));
		WebMarkupContainer wmc = new WebMarkupContainer("commentContainer");
		add(wmc);
		CompoundPropertyModel<AbstractComment> commentModel = createDefaultComment(model.getObject());
		commentModel.getObject().setName(username);
		
		final CurrentCommentListPanel comments = new CurrentCommentListPanel("cclp", model, null, provider, pageSize);
		comments.setVisible(readVisible);
		comments.setDateFormat(dateFormat);
		comments.setLocale(locale);
		comments.setProviderAll(providerAll);
		comments.init();
		add(comments.setOutputMarkupId(true));	
		
		final CommentForm commentForm = new CommentForm("commentForm", commentModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			CompoundPropertyModel<AbstractComment> saveComment(AbstractComment comment) {
				executeSaveComment(comment);
				return createDefaultComment(model.getObject());
			}

			@Override
			CurrentCommentListPanel getComments() {
				return comments;
			}
		};
		processValidators (commentForm);
		commentForm.setWebsiteVisible(websiteVisible);
		commentForm.setEmailVisible(emailVisible);
		commentForm.setVisible(writeVisible);
		commentForm.setAnonymous(anonymous);
		commentForm.setSendEmail(sendEmail);
		commentForm.setUseCaptcha (useCaptcha);
		commentForm.setOptions (options);
		commentForm.init();
		
		wmc.add(commentForm);	
	}

	private void processValidators(CommentForm form) {
		for (AbstractBehavior validator : formValidators) {
			form.add(validator);
		}
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setProviderAll(boolean providerAll) {
		this.providerAll = providerAll;
	}
	
	public void setEmailVisible(boolean emailVisible) {
		this.emailVisible = emailVisible;
	}

	public void setWebsiteVisible(boolean websiteVisible) {
		this.websiteVisible = websiteVisible;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	/**
	 * execute persist new comment
	 * 
	 * @param comment
	 */
	protected abstract void executeSaveComment(AbstractComment comment);

	/**
	 * create default comment
	 * 
	 * @param model
	 * @return
	 */
	protected abstract CompoundPropertyModel<AbstractComment> createDefaultComment(Commentable model);

	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	public boolean isUseCaptcha() {
		return useCaptcha;
	}
	
	public Map<String, String> getOptions () {
		return options;
	}

	public List<AbstractBehavior> getFormValidators() {
		return formValidators;
	}

}