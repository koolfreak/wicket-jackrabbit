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
package org.xaloon.wicket.component.jqueryui;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;

/**
*
* @author vytautas racelis
*/
public class JQueryUIDatePickerBehaviorItem extends JQueryUIBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dateFormat = "mm/dd/yy";
	private String markupId;
	
	public JQueryUIDatePickerBehaviorItem () {
	}

	public void init() {
		add ("showOn", "button");
		add ("buttonImage", RequestCycle.get().urlFor (new CompressedResourceReference(JQueryUIDatePickerBehaviorItem.class, "images/calendar.gif")).toString());
		add ("buttonImageOnly", "true");
		add ("changeMonth", "true");
		add ("changeYear", "true");
		add ("yearRange", "-20:+10");
		add ("minDate", "-100Y");
		add ("maxDate", "-3Y");
		add ("dateFormat", getDateFormat());
		add ("firstDay", "1");
	}
	
	public JQueryUIDatePickerBehaviorItem (WebMarkupContainer container) {
		container.setOutputMarkupId(true);
		this.markupId = container.getMarkupId();
	}
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getFunctionName() {
		return "datepicker";
	}
	
	@Override
	public String getMarkupId() {
		if (markupId != null) {
			return markupId;
		} else {
			return getFunctionName();
		}
	}
}
