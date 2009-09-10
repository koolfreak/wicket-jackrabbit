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
package org.xaloon.wicket.component.repository.util;

import java.io.Serializable;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class ContentProperties implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jcrRepository;
	private String jcrUsername;
	private String jcrPassword;
	public String getJcrRepository() {
		return jcrRepository;
	}
	public void setJcrRepository(String jcrRepository) {
		this.jcrRepository = jcrRepository;
	}
	public String getJcrUsername() {
		return jcrUsername;
	}
	public void setJcrUsername(String jcrUsername) {
		this.jcrUsername = jcrUsername;
	}
	public String getJcrPassword() {
		return jcrPassword;
	}
	public void setJcrPassword(String jcrPassword) {
		this.jcrPassword = jcrPassword;
	}
	
}
