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
package org.xaloon.wicket.component.resource;

import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.Resource;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.xaloon.wicket.component.repository.FileRepository;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class FileResource extends Resource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String pathToFile;
	
	@SpringBean
	private FileRepository fileRepository;
	
	public FileResource(String pathToFile) {
		InjectorHolder.getInjector().inject(this);
		this.pathToFile = pathToFile;
	}

	@Override
	public IResourceStream getResourceStream() {
		return new AbstractResourceStream() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void close() throws IOException {
			}
			
			public InputStream getInputStream() throws ResourceStreamNotFoundException {
				return fileRepository.retrieveFile(pathToFile);
			}			
		};
	}
}
