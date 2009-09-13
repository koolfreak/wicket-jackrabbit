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
package org.xaloon.wicket.component.google;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public class GoogleAdsenseBehavior extends AbstractDefaultAjaxBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_WIDTH = 120, DEFAULT_HEIGHT = 240;
	private String adClient;
	private String slot;
	
	private int adWidth = DEFAULT_WIDTH;
	private int adHeight = DEFAULT_HEIGHT;
	
	private String createDate = "12/22/08";
	
	public GoogleAdsenseBehavior (String adClient, String slot) {
		this.adClient = adClient;
		this.slot = slot;
	}
	
	@Override
	protected void respond(AjaxRequestTarget target) {
		throw new UnsupportedOperationException("nothing to do");
	}
	
	@Override
	protected void onComponentRendered() {
		Response response = RequestCycle.get().getResponse();
		response.write(getScript());
	}
	
	public CharSequence getScript() {
		return "<script type=\"text/javascript\"><!--\n" +
		"google_ad_client = \"" + adClient + "\";\n" +
		"/* " +  adWidth + "x" +  adHeight + ", created " + createDate + " */\n" +
		"google_ad_slot = \"" + slot + "\";\n" +
		"google_ad_width = " +  adWidth + ";\n" +
		"google_ad_height = " +  adHeight + ";\n" +
		"//-->\n" +
		"</script>\n" +
		"<script type=\"text/javascript\"\n" +
		"src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
		"</script>";
	}

	public int getAdWidth() {
		return adWidth;
	}

	public void setAdWidth(int adWidth) {
		this.adWidth = adWidth;
	}

	public int getAdHeight() {
		return adHeight;
	}

	public void setAdHeight(int adHeight) {
		this.adHeight = adHeight;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
