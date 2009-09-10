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
package org.dms.component.file;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.dms.component.exception.FileStorageException;
import org.dms.component.service.DMSSessionTemplate;
import org.dms.component.utils.RepositoryUtils;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 * @author Emmanuel Nollase
 * 
 */

public class FileRepositoryImpl implements StoreFileRepository {
	
    	private DMSSessionTemplate sessionTemplate;
    	
	public void setSessionTemplate(DMSSessionTemplate sessionTemplate)
	{
	    this.sessionTemplate = sessionTemplate;
	}

	public String storeFile(String path, String name, String mimeType, InputStream fileStream) {
		String uuid = null;
		try {
			Session session = sessionTemplate.getDefaultSession();
			Node rootNode = session.getRootNode();
			
			Node folder = (rootNode.hasNode(path))?rootNode.getNode(path):RepositoryUtils.createFolder(session, path, rootNode);
			Node file = folder.addNode(name, "nt:file");
        	    	Node fileContent = file.addNode("jcr:content", "nt:resource"); 
        	    	fileContent.addMixin("mix:versionable");
        	    	fileContent.addMixin("mix:referenceable");
        	    	fileContent.setProperty ("jcr:mimeType", mimeType); 
        	    	fileContent.setProperty ("jcr:lastModified", Calendar.getInstance()); 
        	        fileContent.setProperty("jcr:data", fileStream);
        	        session.save();
        	        uuid = fileContent.getUUID();
        	        
		} catch (Exception e) {
			throw new FileStorageException ("Error while storing file", e);
		}
		return uuid;
	}

	public InputStream retrieveFile(String pathToFile) {
		try {
			Node rootNode = sessionTemplate.getDefaultSession().getRootNode();
			if (rootNode.hasNode(pathToFile)) {
				Node file = rootNode.getNode(pathToFile);
				return file.getNode("jcr:content").getProperty("jcr:data").getStream();
			}
		} catch (Exception e) {
			throw new FileStorageException ("Error while storing file", e);
		}
		return null;
	}

	public InputStream retrieveFileByUUID(String uuid) {
		if (!org.apache.commons.lang.StringUtils.isEmpty(uuid)) {
			try {
				Node fileContent = sessionTemplate.getDefaultSession().getNodeByUUID(uuid);
				return fileContent.getProperty("jcr:data").getStream();
			} catch (Exception e) {
				throw new FileStorageException ("Error while storing file", e);
			}
		}
		return null;
	}
	
	public List<FileDescription> searchFiles(String searchPath) {
		List<FileDescription> result = new ArrayList<FileDescription>();
		try {
			Session session = sessionTemplate.getDefaultSession();
			if (!session.getRootNode().hasNode(searchPath)) {
				return result;
			}
			Node node = session.getRootNode().getNode(searchPath);
			NodeIterator ni = node.getNodes();
			while (ni.hasNext()) {
				Node file = ni.nextNode();
				if (isFile (file)) {
					final FileDescription desc = new FileDescription();
					desc.setPath(file.getPath());
					desc.setName(file.getName());
					desc.setLastModified(file.getNode("jcr:content").getProperty("jcr:lastModified").getDate().getTime());
					desc.setMimeType(file.getNode("jcr:content").getProperty("jcr:mimeType").getString());
					desc.setSize(file.getNode("jcr:content").getProperty("jcr:data").getLength());
					result.add (desc);
				}
			}
		} catch (Exception e) {
			throw new FileStorageException ("Error while searching files", e);
		}
		return result;
	}

	public List<FileDescription> searchFiles(String path,String keyword) {
		List<FileDescription> result = new ArrayList<FileDescription>();
		try {
			Session session = sessionTemplate.getDefaultSession();
			if (!session.getRootNode().hasNode(path)) {
				return result;
			}
			QueryManager qmanager =  session.getWorkspace().getQueryManager();
			Query query = qmanager.createQuery("//*[jcr:contains(.,'"+keyword+"')]", Query.XPATH);
			QueryResult qresult = query.execute();
			
			NodeIterator ni = qresult.getNodes();
			while (ni.hasNext()) {
				Node file = ni.nextNode();
				if (isFile (file)) {
					final FileDescription desc = new FileDescription();
					desc.setPath(file.getPath());
					desc.setName(file.getName());
					desc.setLastModified(file.getNode("jcr:content").getProperty("jcr:lastModified").getDate().getTime());
					desc.setMimeType(file.getNode("jcr:content").getProperty("jcr:mimeType").getString());
					desc.setSize(file.getNode("jcr:content").getProperty("jcr:data").getLength());
					result.add (desc);
				}
			}
		} catch (Exception e) {
			throw new FileStorageException ("Error while searching files", e);
		}
		return result;
	}
	private boolean isFile(Node file) {
		try {
			return "nt:file".equals(file.getPrimaryNodeType().getName());
		} catch (RepositoryException e) {
			throw new FileStorageException ("Error while checking for a file", e);
		}
	}

	public void delete(String path) {
		if (!org.apache.commons.lang.StringUtils.isEmpty(path)) {
			try {
				Session session = sessionTemplate.getDefaultSession();
				Node nodeToRemove = session.getRootNode().getNode(path);
				if (nodeToRemove != null) {
					nodeToRemove.remove();
					session.save();					
				}
			} catch (Exception e) {
				throw new FileStorageException ("Error while deleting file", e);
			}
		}
	}

	public boolean existsFile(String name) {
		try {
			Node root = sessionTemplate.getDefaultSession().getRootNode();
			return root.hasNode(name) && "nt:file".equalsIgnoreCase(root.getNode(name).getPrimaryNodeType().getName()) ;
		} catch (Exception e) {
			throw new FileStorageException ("Error while checking file", e);
		}
	}

	public List<String> searchFolders(String searchPath) {
		List<String> result = new ArrayList<String>();
		try {
			Session session = sessionTemplate.getDefaultSession();
			if (!session.getRootNode().hasNode(searchPath)) {
				return result;
			}
			Node node = session.getRootNode().getNode(searchPath);
			NodeIterator ni = node.getNodes();
			while (ni.hasNext()) {
				Node item = ni.nextNode();
				if (!isFile(item)) {
					result.add(item.getName());
				}
			}
		} catch (Exception e) {
			throw new FileStorageException ("Error while searching files", e);
		}
		return result;
	}
}
