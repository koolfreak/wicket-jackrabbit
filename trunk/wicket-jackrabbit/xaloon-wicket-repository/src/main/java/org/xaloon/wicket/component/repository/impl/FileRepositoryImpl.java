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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.JcrConstants;
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

    public String storeFile(String path, String name, String mimeType,
	    InputStream fileStream) throws FileStorageException
    {
	String uuid = null;
	try
	{
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

	} catch (Exception e)
	{
	    throw new FileStorageException("Error while storing file", e);
	}
	return uuid;
    }

    public FileDescription storeFileVersion(String path, String name,
	    String mimeType, InputStream fileStream)
	    throws FileStorageException
    {
	FileDescription filedesc = null;
	try
	{
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

	} catch (Exception e)
	{
	    throw new FileStorageException("Error while storing file", e);
	} finally
	{
	    IOUtils.closeQuietly(fileStream);
	}
	return filedesc;
    }

    public void storeNextVersion(FileDescription latestVersion,
	    InputStream fileStream) throws FileStorageException
    {

	try
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

	} catch (RepositoryException e)
	{
	    throw new FileStorageException("Error while cheking-in version", e);
	} finally
	{
	    IOUtils.closeQuietly(fileStream);
	}

    }

    public void restoreVersion(FileDescription file, String verName) 
	    throws FileStorageException
    {
	
	try
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
	catch (RepositoryException e)
	{
	    throw new FileStorageException("Erro restoring version: "+ verName, e);
	}

    }

    public List<FileVersion> getFileVersions(String path) throws FileStorageException
    {
	final List<FileVersion> versions = new ArrayList<FileVersion>();
	try
	{
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

	} catch (RepositoryException e)
	{
	    throw new FileStorageException("Erro accessing versions ", e);
	} catch (Exception e)
	{
	    throw new FileStorageException("Erro accessing versions ", e);
	}
	return versions;
    }

    public InputStream retrieveFile(String pathToFile)
	    throws FileStorageException
    {
	try
	{
	    Node rootNode = contentSessionFacade.getDefaultSession()
		    .getRootNode();
	    if (rootNode.hasNode(pathToFile))
	    {
		Node file = rootNode.getNode(pathToFile);
		return file.getNode("jcr:content").getProperty("jcr:data")
			.getStream();
	    }
	} catch (Exception e)
	{
	    throw new FileStorageException("Error while storing file", e);
	}
	return null;
    }

    public InputStream retrieveFileByUUID(String uuid)
    {
	if (!org.apache.commons.lang.StringUtils.isEmpty(uuid))
	{
	    try
	    {
		Node fileContent = contentSessionFacade.getDefaultSession()
			.getNodeByUUID(uuid);
		return fileContent.getProperty("jcr:data").getStream();
	    } catch (Exception e)
	    {
		throw new FileStorageException("Error while storing file", e);
	    }
	}
	return null;
    }

    public InputStream retrieveFileByUUID(String uuid, Map<String, String> attr)
	    throws FileStorageException
    {
	if (!org.apache.commons.lang.StringUtils.isEmpty(uuid))
	{
	    try
	    {
		final Node fileContent = contentSessionFacade
			.getDefaultSession().getNodeByUUID(uuid);

		attr.put("mimetype", fileContent.getProperty("jcr:mimeType")
			.getString());
		attr.put("filename", RepositoryHelper.getFileName(fileContent
			.getPath()));

		return fileContent.getProperty("jcr:data").getStream();
	    } catch (Exception e)
	    {
		throw new FileStorageException("Error while storing file", e);
	    }
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

    public void delete(String path) throws FileStorageException
    {
	if (!org.apache.commons.lang.StringUtils.isEmpty(path))
	{
	    try
	    {
		Session session = contentSessionFacade.getDefaultSession();
		Node nodeToRemove = session.getRootNode().getNode(path);

		if (nodeToRemove != null)
		{
		    nodeToRemove.remove();
		    session.save();
		}

	    } catch (Exception e)
	    {
		throw new FileStorageException("Error while deleting file", e);
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
}
