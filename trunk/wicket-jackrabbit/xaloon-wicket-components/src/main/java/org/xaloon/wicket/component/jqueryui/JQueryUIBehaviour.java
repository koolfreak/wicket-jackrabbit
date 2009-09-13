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

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.xaloon.wicket.component.jquery.JQueryBehaviorItem;

/**
*
* @author vytautas racelis
*/
public abstract class JQueryUIBehaviour extends JQueryBehaviorItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String defaultTheme = "start";
	
	private static final ResourceReference JS = new CompressedResourceReference(JQueryUIBehaviour.class, "jquery-ui-1.7.1.custom.min.js");
	private static ResourceReference cssReference = new CompressedResourceReference(JQueryUIBehaviour.class, "css/" + defaultTheme + "/jquery-ui-1.7.1.custom.css");
	
	public ResourceReference getCss() {
		if (!getTheme().equals(defaultTheme)) {
			defaultTheme = getTheme();
			cssReference = new CompressedResourceReference(JQueryUIBehaviour.class, "css/" + getTheme() + "/jquery-ui-1.7.1.custom.css");
		}
		return cssReference;
	}

	public ResourceReference getJs() {
		return JS;
	}
	
	public String getTheme () {
		return defaultTheme;
	}
}
