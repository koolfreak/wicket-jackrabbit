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
package org.xaloon.wicket.component.uploadify;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.upload.FileItem;

/**
 * File processing abstract class. Should be implemented to move file item to original folder
 * 
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public abstract class UploadifyFileProcessPage extends WebPage {
	public UploadifyFileProcessPage (PageParameters params) {
		HttpServletRequest r = ((WebRequest)RequestCycle.get().getRequest()).getHttpServletRequest();
		try {
			MultipartServletWebRequest r2 = new MultipartServletWebRequest(r, Bytes.MAX);
			for (FileItem fi : r2.getFiles().values()) {
				processFileItem (fi);
				fi.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void processFileItem (FileItem fileItem)  throws Exception ;
}
