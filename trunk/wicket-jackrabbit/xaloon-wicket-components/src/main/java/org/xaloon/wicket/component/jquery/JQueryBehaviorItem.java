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
package org.xaloon.wicket.component.jquery;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehavior;

/**
*
* @author vytautas racelis
*/
public abstract class JQueryBehaviorItem extends AbstractBehavior implements IBehavior, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, JQueryOptionValue> options = new HashMap<String, JQueryOptionValue>();
	private String markupId;
	
	public abstract ResourceReference getJs();

	public abstract ResourceReference getCss();

	public abstract String getFunctionName();

	public String getMarkupId() {
		if (!StringUtils.isEmpty(markupId)) {
			return markupId;
		}
		return getFunctionName();
	}
	
	public void setMarkupId(String markupId) {
		this.markupId = markupId;
	}
	
	public String getTheme () {
		return null;
	}
	
	public String getParameters() {
		return "";
	}

	public CharSequence getOnReadyScript() {
		String functionName = getFunctionName ();
		String markupId = getMarkupId ();
		String parameters = getParameters ();
		if (!StringUtils.isEmpty(parameters)) {
			parameters += ",";
		}
		String optionsStr = "";
		for (Map.Entry<String, JQueryOptionValue> entry : options.entrySet()) {
			JQueryOptionValue queryOptionValue = entry.getValue();
			if (!StringUtils.isEmpty(optionsStr)) {
				optionsStr += ",\n";
			}
			optionsStr += "\t\t" + entry.getKey() + ": ";
			if (queryOptionValue.isWrapWithQuatation()) {
				optionsStr += "'"+ queryOptionValue.getValue() + "'";
			} else {
				optionsStr += queryOptionValue.getValue();
			}
		}
		if (!StringUtils.isEmpty(optionsStr)) {
			optionsStr = "{\n" + optionsStr + "\n\t}";
		}
		return "\t$('#" + markupId + "')." + functionName + "(" + parameters + optionsStr + ");\n";
	}
	
	public void addIfNotExists (String key, String value) {
		if (!options.containsKey(key)) {
			options.put(key, new JQueryOptionValue(value));
		}
	}
	
	public void addOrReplace (String key, String value) {
		addOrReplace (key, value, true);
	}
	
	public void addOrReplace (String key, String value, boolean wrapWithQuatation) {
		options.put(key, new JQueryOptionValue(value, wrapWithQuatation));
	}
	
	public void add (String key, String value) {
		addOrReplace (key, value);
	}
	
	public void remove (String key) {
		options.remove(key);
	}
}
