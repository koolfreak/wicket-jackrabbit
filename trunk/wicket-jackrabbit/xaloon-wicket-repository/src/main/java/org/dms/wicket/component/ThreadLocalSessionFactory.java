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
package org.dms.wicket.component;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.xaloon.wicket.component.exception.JcrSessionException;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class ThreadLocalSessionFactory {
	private final Repository repository;
	private final Credentials credentials;

	ThreadLocal<Map<String, Session>> container = new ThreadLocal<Map<String, Session>>() {
		@Override
		protected Map<String, Session> initialValue() {
			return new HashMap<String, Session>();
		}
	};

	public Session getDefaultSession() {
		return getSession ("default");
	}
	
	public Session getSession(String workspace) {
		final Map<String, Session> map = container.get();
		Session session = map.get(workspace);
		if (session != null && !session.isLive()) {
			session = null;
		}
		if (session == null) {
			try {
				final Credentials credentials = getCredentials();
				session = getRepository().login(credentials, workspace);
			} catch (Exception e) {
				throw new JcrSessionException(workspace, e);
			}
			map.put(workspace, session);
			container.set(map);
		}
		return session;
	}

	public void cleanup() {
		for (Session session : container.get().values()) {			
			if (session.isLive()) {
				session.logout();
			}
		}
	}

	public ThreadLocalSessionFactory(Repository repository, Credentials credentials) {
		this.credentials = credentials;
		this.repository = repository;
	}

	public Repository getRepository() {
		return repository;
	}

	public Credentials getCredentials() {
		return credentials;
	}
	
	public static ThreadLocalSessionFactory createSessionFactory(Repository repository, String username, String password) {
		return new ThreadLocalSessionFactory(repository, new SimpleCredentials(username, password.toCharArray()));
	}
}
