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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;

/**
*
* @author vytautas racelis
*/
public class JQueryBehavior extends AbstractDefaultAjaxBehavior {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Pattern JQUERY_REGEXP = Pattern.compile(".*\\<.*script.*src=\".*jquery.*\\.js\"\\>.*", Pattern.DOTALL);
    
	public static final CompressedResourceReference JQUERY_JS = new CompressedResourceReference(JQueryBehavior.class, "jquery-1.3.2.min.js");

	private List<JQueryBehaviorItem> children = new ArrayList<JQueryBehaviorItem> ();
	private boolean createdFromComponent;
	
	public JQueryBehavior () {}
	
	public JQueryBehavior(boolean createdFromComponent) {
		this.createdFromComponent = createdFromComponent;
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		try {
            super.renderHead(response);
            if(getIncludeJQueryJS(response)) {
	            response.renderJavascriptReference(JQUERY_JS);
            }
            
            StringBuilder script = new StringBuilder();
            for (JQueryBehaviorItem child : children) {
            	script.append(child.getOnReadyScript ());
            	response.renderJavascriptReference(child.getJs());
            	response.renderCSSReference(child.getCss());            	
            }
            if ((script != null) && (script.length() > 0)) {
                StringBuilder builder = new StringBuilder();
                builder.append("<script type=\"text/javascript\">\n$(document).ready(function(){\n");
                builder.append(script);
                builder.append("\n});</script>");
                response.renderString(builder.toString());
            }
        } catch (Exception exc) {
            throw new JQueryRenderException("wrap: " + exc.getMessage(), exc);
        }

	}

	public boolean getIncludeJQueryJS(IHeaderResponse response) {
        return !JQUERY_REGEXP.matcher(response.getResponse().toString()).matches();
    }

	
	@Override
	protected void respond(AjaxRequestTarget target) {
		throw new UnsupportedOperationException("nothing to do");
	}
	
	public void addChild (JQueryBehaviorItem child) {
		children.add(child);
	}

	public boolean isCreatedFromComponent() {
		return createdFromComponent;
	}
}
