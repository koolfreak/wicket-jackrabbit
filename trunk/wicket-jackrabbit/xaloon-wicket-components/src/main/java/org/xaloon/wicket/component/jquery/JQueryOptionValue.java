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

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public class JQueryOptionValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;
	private boolean wrapWithQuatation = true;
	
	public JQueryOptionValue (String value) {
		this (value, true);
	}
	
	public JQueryOptionValue (String value, boolean wrapWithQuatation) {
		this.value = value;
		this.wrapWithQuatation = wrapWithQuatation;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isWrapWithQuatation() {
		return wrapWithQuatation;
	}
	public void setWrapWithQuatation(boolean wrapWithQuatation) {
		this.wrapWithQuatation = wrapWithQuatation;
	}
}
