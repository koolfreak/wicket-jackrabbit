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
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Google Analytics shows you how people found your site, how they explored it, 
 * and how you can enhance their visitor experience. With this information, 
 * you can improve your website return on investment, increase conversions, and make more money on the web. 
 * 
 * (Taken from google support page) 
 * 
 * @author vytautas racelis
 *
 */
public class GoogleAnalyticsPanel extends Panel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String webPropertyId;
	
	public GoogleAnalyticsPanel (String id, String webPropertyId) {
		super(id);
		this.webPropertyId = webPropertyId;
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		Response response = RequestCycle.get().getResponse();
		response.write(getScript());
	}	
	
	public CharSequence getScript() {
		return "<script type=\"text/javascript\">\n" +
				"var gaJsHost = ((\"https:\" == document.location.protocol) ? \"https://ssl.\" : \"http://www.\");\n" +
				"document.write(unescape(\"%3Cscript src='\" + gaJsHost + \"google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E\"));\n" +
				"</script>\n" +
				"<script type=\"text/javascript\">\n" +
				"try {\n" +
				"var pageTracker = _gat._getTracker(\"" + webPropertyId + "\");\n" +
				"pageTracker._trackPageview();\n" +
				"} catch(err) {}</script>\n";
	}
}
