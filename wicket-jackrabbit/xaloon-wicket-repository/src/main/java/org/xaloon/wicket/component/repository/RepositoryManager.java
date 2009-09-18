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

import javax.jcr.Repository;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.dms.wicket.component.ThreadLocalSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.xaloon.wicket.component.repository.util.ContentHelper;
import org.xaloon.wicket.component.repository.util.ContentProperties;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
@org.springframework.stereotype.Repository("RepositoryManager")
public class RepositoryManager {
	@Autowired
	private ContentProperties contentProperties;
	
	private Repository repository;
	private ThreadLocalSessionFactory threadLocalSessionFactory;
	
	public ThreadLocalSessionFactory getContentSessionFactory() {
		return threadLocalSessionFactory;
	}

	public void shutdown() {
		if ((repository != null) && (repository instanceof RepositoryImpl)) {
			((RepositoryImpl) repository).shutdown();
		}
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public Repository getRespository() {
		return repository;
	}

	public void setContentSessionFactory(ThreadLocalSessionFactory threadLocalSessionFactory) {
		this.threadLocalSessionFactory = threadLocalSessionFactory;
	}

	public void init() {
		try {
			setRepository (ContentHelper.createRepository(contentProperties.getJcrRepository()));
			setContentSessionFactory (ThreadLocalSessionFactory.createSessionFactory (getRespository (), contentProperties.getJcrUsername(), contentProperties.getJcrPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
