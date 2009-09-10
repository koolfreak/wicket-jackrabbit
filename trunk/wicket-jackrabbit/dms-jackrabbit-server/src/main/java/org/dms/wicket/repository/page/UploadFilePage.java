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
package org.dms.wicket.repository.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.upload.FileItem;
import org.dms.wicket.repository.panel.RepositoryPanel;
import org.xaloon.wicket.component.mounting.MountPage;
import org.xaloon.wicket.component.repository.FileRepository;
import org.xaloon.wicket.component.uploadify.UploadifyFileProcessPage;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
@MountPage(path="/process")
public class UploadFilePage extends UploadifyFileProcessPage {
	
	@SpringBean
	private FileRepository fileRepository;
	
	public UploadFilePage (PageParameters params) {
		super (params);
	}

	@Override
	protected void processFileItem(FileItem fi) throws Exception {
		String name = fi.getName();
		fileRepository.storeFile(RepositoryPanel.PARENT_NAME + name.substring(0, name.indexOf(".")), name, "images/jpeg", fi.getInputStream());
	}	
}
