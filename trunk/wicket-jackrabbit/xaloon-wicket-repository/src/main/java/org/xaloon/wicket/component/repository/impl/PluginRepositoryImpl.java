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
package org.xaloon.wicket.component.repository.impl;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.dms.wicket.component.ContentSessionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.xaloon.wicket.component.plugin.Plugin;
import org.xaloon.wicket.component.plugin.PluginRepository;
import org.xaloon.wicket.component.repository.util.RepositoryHelper;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class PluginRepositoryImpl implements PluginRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PLUGIN_ROOT_PATH = "application/plugins";
	private static final String IS_ENABLED = "enabled";
	
	@Autowired
	private ContentSessionFacade contentSessionFacade;

	public String getPluginPropertyValue(Plugin plugin, String key) {
		Property property = getPluginProperty (plugin, key);
		if (property != null) {
			try {
				return property.getString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean isEnabled(Plugin plugin) {
		Property property = getPluginProperty (plugin, IS_ENABLED);
		if (property != null) {
			try {
				return property.getBoolean();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	
	private Property getPluginProperty(Plugin plugin, String key) {
		try {
			Session session = contentSessionFacade.getDefaultSession();
			Node node = getNode(plugin, session);
			if (node.hasProperty(key)) {
				return node.getProperty(key);
			}						
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Node getNode(Plugin plugin, Session session)
			throws RepositoryException, PathNotFoundException, Exception {
		Node rootNode = session.getRootNode();
		
		Node folder = (rootNode.hasNode(PLUGIN_ROOT_PATH))?rootNode.getNode(PLUGIN_ROOT_PATH):RepositoryHelper.createFolder (session, PLUGIN_ROOT_PATH, rootNode);
		Node node = null;
		String nodeId = plugin.getId().replaceAll("/", "-");
		if (folder.hasNode(nodeId)) {
			node = folder.getNode(nodeId);
		} else {
			node = folder.addNode(nodeId);
		}
		return node;
	}

	public void setEnabled(Plugin plugin, Boolean enabled) {
		Session session = contentSessionFacade.getDefaultSession();
		try {
			Node node = getNode(plugin, session);
			node.setProperty(IS_ENABLED, enabled);
			session.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPluginPropertyValue(Plugin plugin, String key, String value) {
		Session session = contentSessionFacade.getDefaultSession();
		try {
			Node node = getNode(plugin, session);
			node.setProperty(key, value);
			session.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
