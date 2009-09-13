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
package org.xaloon.wicket.component.feedback;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.xaloon.wicket.component.util.ComponentErrorBehavior;
import org.xaloon.wicket.component.util.ErrorHighlighter;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public class XComponentFeedbackPanel extends ComponentFeedbackPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public XComponentFeedbackPanel(String id, Component filter) {
		this (id, filter, true);
	}

	public XComponentFeedbackPanel(String id, Component filter, boolean required) {
		super(id, filter);
		setOutputMarkupId(true);
		filter.add(new ComponentErrorBehavior("onblur", filter, this));
		if (required) {
			filter.add(new SimpleAttributeModifier("class", "required"));
		}
		filter.add(new ErrorHighlighter());
	}
}
