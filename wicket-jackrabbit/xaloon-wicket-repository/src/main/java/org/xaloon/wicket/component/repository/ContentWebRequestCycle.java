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
package org.xaloon.wicket.component.repository;

import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public abstract class ContentWebRequestCycle extends WebRequestCycle {

	public ContentWebRequestCycle(WebApplication application, WebRequest request, Response response) {
		super(application, request, response);
	}
	
	@Override
	protected void onEndRequest() {
		if (getContentSessionFactory () != null) {
			getContentSessionFactory ().cleanup();
		}
	}
	
	protected abstract ThreadLocalSessionFactory getContentSessionFactory ();

}
