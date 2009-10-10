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
package org.dms.wicket.perf.test;

import junit.framework.TestCase;

import org.xaloon.wicket.component.repository.FileRepository;
import org.xaloon.wicket.component.repository.RepositoryManager;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class SingleFileUploadTestCase extends TestCase {
	private RepositoryManager repositoryManager;
	private FileRepository fileRepository;
	
	public SingleFileUploadTestCase (String name, RepositoryManager repositoryManager, FileRepository fileRepository) {
		super (name);
		this.repositoryManager = repositoryManager;
		this.fileRepository = fileRepository;
	}
	
	public void testFileUpload () throws Exception {
		String path =  "photo/upload/vicento.ramos/spain-2008/barcelona/img1/" + Thread.currentThread().getName();
		String name = path + "/test.jpg";
		fileRepository.storeFile(path, "test.jpg", "images/jpeg", this.getClass().getResourceAsStream("/dziungles.jpg"));
		assertTrue (fileRepository.existsFile(name));
		fileRepository.delete("/" + name);
		repositoryManager.getContentSessionFactory().cleanup();
	}
}
