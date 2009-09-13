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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public class CaptchaPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_MIN = 4, DEFAULT_MAX= 6;
	public CaptchaPanel(String id, final IModel<String> model) {
		super(id, model);
	}

	public void setupCaptcha(AjaxRequestTarget target, IModel<String> model) {
		model.setObject(randomString(DEFAULT_MIN, DEFAULT_MAX));
		CaptchaImageResource captchaImageResource = new CaptchaImageResource(model.getObject());
		NonCachingImage imgCaptcha = new NonCachingImage("captchaImage", captchaImageResource) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);

				String url = tag.getAttributes().getString("src");
				url = url.replaceAll("&", "&amp;");
				tag.put("src", url);
				tag.put("alt", "captcha");
			}
		};
		imgCaptcha.setOutputMarkupId(true); // required for AjaxFallbackLink
		addOrReplace(imgCaptcha);
		if (target != null) {
			target.addComponent(imgCaptcha);
		}
	}

	private static String randomString(int min, int max) {
		int num = randomInt(min, max);
		byte b[] = new byte[num];
		for (int i = 0; i < num; i++) {
			b[i] = (byte) randomInt('a', 'z');
		}
		return new String(b);
	}

	private static int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}
}
