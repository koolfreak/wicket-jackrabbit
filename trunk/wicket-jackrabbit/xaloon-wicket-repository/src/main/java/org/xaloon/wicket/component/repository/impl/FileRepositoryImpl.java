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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.ImportUUIDBehavior;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.WorkspaceImpl;
import org.apache.jackrabbit.core.data.GarbageCollector;
import org.apache.jackrabbit.core.state.ItemStateException;
import org.dms.wicket.component.ContentSessionFacade;
import org.dms.wicket.repository.db.model.FileDescription;
import org.dms.wicket.repository.db.model.FileVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xaloon.wicket.component.exception.FileStorageException;
import org.xaloon.wicket.component.repository.FileRepository;
import org.xaloon.wicket.component.repository.util.RepositoryHelper;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
@Repository("fileRepository")
public class FileRepositoryImpl implements FileRepository
{
    @Autowired
    private ContentSessionFacade contentSessionFacade;

    public String storeFile(String path, String name, String mimeType, InputStream fileStream) throws PathNotFoundException, RepositoryException, Exception
    {
	String uuid = null;
	
	    Session session = contentSessionFacade.getDefaultSession();
	    Node rootNode = session.getRootNode();

	    Node folder = (rootNode.hasNode(path)) ? rootNode.getNode(path)
		    : RepositoryHelper.createFolder(session, path, rootNode);
	    Node file = folder.addNode(name, JcrConstants.NT_FILE);
	    file.addMixin(JcrConstants.MIX_REFERENCEABLE);
	    Node fileContent = file.addNode(JcrConstants.JCR_CONTENT,
		    JcrConstants.NT_RESOURCE);
	    fileContent.setProperty(JcrConstants.JCR_MIMETYPE, mimeType);
	    fileContent.setProperty(JcrConstants.JCR_LASTMODIFIED, Calendar
		    .getInstance());
	    fileContent.setProperty(JcrConstants.JCR_DATA, fileStream);
	    session.save();
	    uuid = fileContent.getUUID();
	
	return uuid;
    }

    public FileDescription storeFileVersion(String path, String name,
	    String mimeType, InputStream fileStream)
	    throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException, Exception
    {
	FileDescription filedesc = null;
	
	    Session session = contentSessionFacade.getDefaultSession();
	    Node rootNode = session.getRootNode();

	    Node folder = (rootNode.hasNode(path)) ? rootNode.getNode(path)
		    : RepositoryHelper.createFolder(session, path, rootNode);
	    Node file = folder.addNode(name, JcrConstants.NT_FILE);
	    file.addMixin(JcrConstants.MIX_VERSIONABLE);

	    Node fileContent = file.addNode(JcrConstants.JCR_CONTENT,
		    JcrConstants.NT_RESOURCE);
	    fileContent.setProperty(JcrConstants.JCR_MIMETYPE, mimeType);
	    final Calendar lastModified = Calendar.getInstance();
	    lastModified.setTimeInMillis(System.currentTimeMillis());
	    fileContent
		    .setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
	    fileContent.setProperty(JcrConstants.JCR_DATA, fileStream);
	    session.save();
	    final Version version = file.checkin();

	    filedesc = new FileDescription();
	    filedesc.setLastModified(lastModified.getTime());
	    filedesc.setMimeType(mimeType);
	    filedesc.setPath(path);
	    filedesc.setName(name);
	    filedesc.setUUID(fileContent.getUUID());
	    filedesc.setFileVersion(version.getName());
	    // filedesc.setSize(file.getNode("jcr:content").getProperty("jcr:data").getLength());
	
	return filedesc;
    }

    public void storeNextVersion(FileDescription latestVersion,
	    InputStream fileStream) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, PathNotFoundException, RepositoryException 
    {
	    final String pathLatestVersion = latestVersion.getFilePath();
	    Node rootNode = contentSessionFacade.getDefaultSession()
		    .getRootNode();
	    if (rootNode.hasNode(pathLatestVersion))
	    {
		final Node childNode = rootNode.getNode(pathLatestVersion);
		childNode.checkout();
		childNode.getNode(JcrConstants.JCR_CONTENT).setProperty(JcrConstants.JCR_DATA, fileStream);
		final Calendar lastModified = Calendar.getInstance();
		lastModified.setTimeInMillis(System.currentTimeMillis());
		childNode.getNode(JcrConstants.JCR_CONTENT).setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
		childNode.save();
		final Version version = childNode.checkin();

		latestVersion.setFileVersion(version.getName());
		latestVersion.setLastModified(lastModified.getTime());
	    }
    }

    public void restoreVersion(FileDescription file, String verName) throws RepositoryException 
	    
    {
	    final String pathLatestVersion = file.getFilePath();
	    Node rootNode = contentSessionFacade.getDefaultSession().getRootNode();
	    if (rootNode.hasNode(pathLatestVersion))
	    {
		Node node = rootNode.getNode(pathLatestVersion);
		node.restore(verName, true);
		Version restoreVersion = node.checkin();
		
		file.setFileVersion(restoreVersion.getName());
		file.setLastModified(restoreVersion.getCreated().getTime());
		
	    }
    }

    public List<FileVersion> getFileVersions(String path) throws RepositoryException
    {
	final List<FileVersion> versions = new ArrayList<FileVersion>();
	    Node rootNode = contentSessionFacade.getDefaultSession()
		    .getRootNode();
	    Node file = rootNode.getNode(path);

	    VersionHistory history = file.getVersionHistory();
	    VersionIterator ite = history.getAllVersions();
	    while (ite.hasNext())
	    {
		
		Version ver = (Version) ite.next();
		if (!ver.getName().equalsIgnoreCase("jcr:rootVersion"))
		{
		    versions.add(new FileVersion(ver.getCreated().getTime(), ver.getName()));
		}
	    }

	return versions;
    }

    public InputStream retrieveFile(String pathToFile) throws PathNotFoundException, ValueFormatException, RepositoryException
	    
    {
	    Node rootNode = contentSessionFacade.getDefaultSession()
		    .getRootNode();
	    if (rootNode.hasNode(pathToFile))
	    {
		Node file = rootNode.getNode(pathToFile);
		return file.getNode("jcr:content").getProperty("jcr:data")
			.getStream();
	    }
	return null;
    }

    public InputStream retrieveFileByUUID(String uuid) throws ValueFormatException, PathNotFoundException, RepositoryException
    {
	if (!org.apache.commons.lang.StringUtils.isEmpty(uuid))
	{
		Node fileContent = contentSessionFacade.getDefaultSession()
			.getNodeByUUID(uuid);
		return fileContent.getProperty("jcr:data").getStream();
	}
	return null;
    }

    public InputStream retrieveFileByUUID(String uuid, Map<String, String> attr)
	    throws ValueFormatException, PathNotFoundException, RepositoryException
    {
	if (!org.apache.commons.lang.StringUtils.isEmpty(uuid))
	{
		final Node fileContent = contentSessionFacade
			.getDefaultSession().getNodeByUUID(uuid);

		attr.put("mimetype", fileContent.getProperty("jcr:mimeType")
			.getString());
		attr.put("filename", RepositoryHelper.getFileName(fileContent
			.getPath()));

		return fileContent.getProperty("jcr:data").getStream();
	}
	return null;
    }

    public List<FileDescription> searchFiles(String searchPath)
	    throws FileStorageException
    {
	List<FileDescription> result = new ArrayList<FileDescription>();
	try
	{
	    Session session = contentSessionFacade.getDefaultSession();
	    if (!session.getRootNode().hasNode(searchPath))
	    {
		return result;
	    }
	    Node node = session.getRootNode().getNode(searchPath);
	    NodeIterator ni = node.getNodes();
	    while (ni.hasNext())
	    {
		Node file = ni.nextNode();
		if (isFile(file))
		{
		    FileDescription desc = new FileDescription();
		    desc.setPath(file.getPath());
		    desc.setName(file.getName());
		    desc.setLastModified(file.getNode("jcr:content")
			    .getProperty("jcr:lastModified").getDate()
			    .getTime());
		    desc.setMimeType(file.getNode("jcr:content").getProperty(
			    "jcr:mimeType").getString());
		    desc.setSize(file.getNode("jcr:content").getProperty(
			    "jcr:data").getLength());
		    result.add(desc);
		}
	    }
	} catch (Exception e)
	{
	    throw new FileStorageException("Error while searching files", e);
	}
	return result;
    }

    private boolean isFile(Node file)
    {
	try
	{
	    return "nt:file".equals(file.getPrimaryNodeType().getName());
	} catch (RepositoryException e)
	{
	    throw new FileStorageException("Error while checking for a file", e);
	}
    }

    public void delete(String path) throws PathNotFoundException, RepositoryException 
    {
	if (!org.apache.commons.lang.StringUtils.isEmpty(path))
	{
		Session session = contentSessionFacade.getDefaultSession();
		Node nodeToRemove = session.getRootNode().getNode(path);

		if (nodeToRemove != null)
		{
		    nodeToRemove.remove();
		    session.save();
		}

	}
    }

    public boolean existsFile(String name) throws FileStorageException
    {
	try
	{
	    Node root = contentSessionFacade.getDefaultSession().getRootNode();
	    return root.hasNode(name)
		    && "nt:file".equalsIgnoreCase(root.getNode(name)
			    .getPrimaryNodeType().getName());
	} catch (Exception e)
	{
	    throw new FileStorageException("Error while checking file", e);
	}
    }

    public List<String> searchFolders(String searchPath)
	    throws FileStorageException
    {
	List<String> result = new ArrayList<String>();
	try
	{
	    Session session = contentSessionFacade.getDefaultSession();
	    if (!session.getRootNode().hasNode(searchPath))
	    {
		return result;
	    }
	    Node node = session.getRootNode().getNode(searchPath);
	    NodeIterator ni = node.getNodes();
	    while (ni.hasNext())
	    {
		Node item = ni.nextNode();
		if (!isFile(item))
		{
		    result.add(item.getName());
		}
	    }
	} catch (Exception e)
	{
	    throw new FileStorageException("Error while searching files", e);
	}
	return result;
    }

    public void crateJcrWorkspace(String wsname) throws AccessDeniedException, UnsupportedRepositoryOperationException, RepositoryException 
    {
	
	final WorkspaceImpl ws = (WorkspaceImpl) contentSessionFacade.getDefaultSession().getWorkspace();
	ws.createWorkspace(wsname);
    }

    public void createRepositoryNodes(String paths) throws Exception 
    {
	    final Session session = contentSessionFacade.getDefaultSession();
	    final Node rootNode = contentSessionFacade.getDefaultSession().getRootNode();
	    
	    if( !rootNode.hasNode(paths) )
	    {
		Node createNode = RepositoryHelper.createFolder(session, paths, rootNode);
		createNode.save();
	    }
    }

    public void exportDocumentView(String path, String exportFile,boolean skipBinary)
	    throws PathNotFoundException, IOException, RepositoryException
    {
	    final File file = new File(exportFile);
	    final FileOutputStream out = new FileOutputStream(file);
	    contentSessionFacade.getDefaultSession().exportDocumentView(path, out, skipBinary, false);
    }

    public void importDocumentView(String path, String importFile) 
    throws PathNotFoundException, ItemExistsException, ConstraintViolationException, VersionException, InvalidSerializedDataException, LockException, IOException, RepositoryException,Exception
	    
    {
	FileInputStream file = new FileInputStream(importFile);
	contentSessionFacade.getDefaultSession().importXML("/", file, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW);
    }

    public void performGC() throws FileStorageException
    {
	try
	{
	    SessionImpl si = (SessionImpl)contentSessionFacade.getDefaultSession();
	    GarbageCollector gc = si.createDataStoreGarbageCollector();
	    	// optional (if you want to implement a progress bar / output):
		//gc.setScanEventListener(this);
		gc.scan();
		gc.stopScan();
		// delete old data
		gc.deleteUnused();
	} catch (RepositoryException e)
	{
	    throw new FileStorageException("Error performing GC",e);
	} catch (IllegalStateException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	} catch (ItemStateException e)
	{
	    e.printStackTrace();
	}
    }

    public void renameNode(String path, String newName)
	    throws FileStorageException, RepositoryException
    {
	    Node rootnode = contentSessionFacade.getDefaultSession().getRootNode();
	    if(rootnode.hasNode(path)) {
		throw new FileStorageException("Cannot find file path");    
	    }
	    Node node = rootnode.getNode(path);
	    renameNode(node, newName);
    }
    
    public void lockFileNode(String path) throws PathNotFoundException, RepositoryException 
    {
	Node rootnode = contentSessionFacade.getDefaultSession().getRootNode();
	Node nodeToLock = rootnode.getNode(path);
	nodeToLock.checkout();
	nodeToLock.lock(true, true);
	nodeToLock.checkin();
    }
    
    public void unlockFileNode(String path) throws PathNotFoundException, RepositoryException
    {
	Node rootnode = contentSessionFacade.getDefaultSession().getRootNode();
	Node nodeToLock = rootnode.getNode(path);
	nodeToLock.checkout();
	nodeToLock.unlock();
	nodeToLock.checkin();
    }
    
    private void renameNode(Node node, String newName) throws RepositoryException
    {
	node.getSession().move(node.getPath(),node.getParent().getPath() + "/" + newName);
    }
}
