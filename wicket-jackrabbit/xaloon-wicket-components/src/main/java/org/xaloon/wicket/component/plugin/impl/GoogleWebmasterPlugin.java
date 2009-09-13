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
package org.xaloon.wicket.component.plugin.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.model.Model;
import org.xaloon.wicket.component.basic.MetaTagWebContainer;
import org.xaloon.wicket.component.plugin.AbstractPlugin;
import org.xaloon.wicket.component.plugin.MetaTagPlugin;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class GoogleWebmasterPlugin extends AbstractPlugin implements MetaTagPlugin {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PROPERTY_VERIFY = "verify-v1";

	public GoogleWebmasterPlugin () {
		setId("GWP");
		setPluginName("Google webmaster plugin");
	}
	
	public void processMetaTag(List<Component> metaTagList) {
		String value = getProperty (PROPERTY_VERIFY);
		if (!StringUtils.isEmpty(value)) {
			metaTagList.add(new MetaTagWebContainer("meta-tag", PROPERTY_VERIFY, new Model<String>(value)));
		}
	}

	public List<String> getPropertyKeys() {
		return Arrays.asList(PROPERTY_VERIFY);
	}
}
