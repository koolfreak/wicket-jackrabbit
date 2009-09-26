/**
 * 
 */
package org.dms.wicket.repository.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.ItemExistsException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.dms.wicket.repository.db.dao.JcrFileStorageDao;
import org.dms.wicket.repository.db.model.FileDescription;
import org.dms.wicket.repository.db.model.FileVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xaloon.wicket.component.exception.FileStorageException;
import org.xaloon.wicket.component.repository.FileRepository;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 01:11:07
 */
@Component("fileMetaData")
public class JcrFileMetadataImpl implements JcrFileMetadata
{

    @Autowired private FileRepository fileRepository;
    @Autowired private JcrFileStorageDao jcrFileStorageDao;
    
    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#storeFile(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream)
     */
    public void storeFile(String path, String name, String mimeType, InputStream fileStream) throws FileStorageException
    {
	try
	{
	    FileDescription file = fileRepository.storeFile(path, name, mimeType, fileStream);
	    jcrFileStorageDao.save(file);
	    
	} catch (PathNotFoundException e)
	{
	    throw new FileStorageException(e);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException(e);
	} catch (Exception e)
	{
	    throw new FileStorageException(e);
	}
    }
    
    /* (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileMetadata#storeFileVersion(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream)
     */
    public void storeFileVersion(String path, String name, String mimeType,
	    InputStream input) throws FileStorageException
    {
	try
	{
	    FileDescription file = fileRepository.storeFileVersion(path, name, mimeType, input);
	    jcrFileStorageDao.save(file);
	    
	} catch (NoSuchNodeTypeException e)
	{
	   throw new FileStorageException(e);
	} catch (VersionException e)
	{
	    throw new FileStorageException(e);
	} catch (ConstraintViolationException e)
	{
	    throw new FileStorageException(e);
	} catch (LockException e)
	{
	    throw new FileStorageException(e);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException(e);
	} catch (Exception e)
	{
	    throw new FileStorageException(e);
	}

    }

    /* (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileMetadata#storeNextVersion(java.lang.String, java.io.InputStream)
     */
    public void storeNextVersion(FileDescription file, InputStream fileStream)
	    throws FileStorageException
    {
	try
	{
	    fileRepository.storeNextVersion(file, fileStream);
	    jcrFileStorageDao.update(file);
	    
	} catch (ValueFormatException e)
	{
	    throw new FileStorageException(e);
	} catch (VersionException e)
	{
	    throw new FileStorageException(e);
	} catch (LockException e)
	{
	    throw new FileStorageException(e);
	} catch (ConstraintViolationException e)
	{
	    throw new FileStorageException(e);
	} catch (PathNotFoundException e)
	{
	    throw new FileStorageException(e);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException(e);
	}
	
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#getFileVersions(java.lang.String)
     */
    public List<FileVersion> getFileVersions(String path)
	    throws FileStorageException
    {
	try
	{
	    return fileRepository.getFileVersions(path);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException("Error getting file versions",e);
	}
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#restoreVersion(org.dms.wicket.repository.db.model.FileDescription, java.lang.String)
     */
    public void restoreVersion(FileDescription file, String verName)
	    throws FileStorageException
    {
	try
	{
	    fileRepository.restoreVersion(file, verName);
	    jcrFileStorageDao.update(file);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException("Error restoring version: "+verName,e);
	}
	
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#deleteFile(org.dms.wicket.repository.db.model.FileDescription)
     */
    public void deleteFile(FileDescription file) throws FileStorageException
    {
	try
	{
	    fileRepository.delete(file.getFilePath());
	    jcrFileStorageDao.delete(file);
	    
	} catch (PathNotFoundException e)
	{
	    throw new FileStorageException("Error retrieving file "+file.getFilePath(),e);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException("Error deleting file ",e);
	}
	
	
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#crateJcrWorkspace(java.lang.String)
     */
    public boolean crateJcrWorkspace(String wsname) throws FileStorageException
    {
	boolean _wscreated = false;
	try
	{
	    fileRepository.crateJcrWorkspace(wsname);
	    _wscreated = true;
	    
	} catch (AccessDeniedException e)
	{
	    throw new FileStorageException(e);
	} catch (UnsupportedRepositoryOperationException e)
	{
	    throw new FileStorageException(e);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException(e);
	}
	return _wscreated;
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#exportDocumentView(java.lang.String, java.lang.String, boolean)
     */
    public void exportDocumentView(String path, String exportFile,boolean skipBinary)
	    throws FileStorageException
    {
	try
	{
	    fileRepository.exportDocumentView(path, exportFile,skipBinary );
	} catch (PathNotFoundException e)
	{
	    throw new FileStorageException(e);
	} catch (IOException e)
	{
	    throw new FileStorageException(e);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException(e);
	}
	
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#renameNode(java.lang.String, java.lang.String)
     */
    public boolean renameNode(String path, String newName)
	    throws FileStorageException
    {
	boolean _renamed = false;
	try
	{
	    fileRepository.renameNode(path, newName);
	    _renamed = true;
	} catch (RepositoryException e)
	{
	    throw new FileStorageException("Repository error occured",e);
	}
	return _renamed;
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#createRepositoryNodes(java.lang.String)
     */
    public void createRepositoryNodes(String paths) throws FileStorageException
    {
	try
	{
	    fileRepository.createRepositoryNodes(paths);
	} catch (Exception e)
	{
	    throw new FileStorageException("Error creating nodes", e);
	}
	
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#importDocumentView(java.lang.String, java.lang.String)
     */
    public void importDocumentView(String path, String exportFile)
	    throws FileStorageException
    {
	try
	{
	    fileRepository.importDocumentView(path, exportFile);
	} catch (PathNotFoundException e)
	{
	    throw new FileStorageException(e);
	} catch (ItemExistsException e)
	{
	    throw new FileStorageException(e);
	} catch (ConstraintViolationException e)
	{
	    throw new FileStorageException(e);
	} catch (VersionException e)
	{
	    throw new FileStorageException(e);
	} catch (InvalidSerializedDataException e)
	{
	    throw new FileStorageException(e);
	} catch (LockException e)
	{
	    throw new FileStorageException(e);
	} catch (IOException e)
	{
	    throw new FileStorageException(e);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException(e);
	} catch (Exception e)
	{
	    throw new FileStorageException(e);
	}
	
    }
    
    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#lockFileNode(java.lang.String)
     */
    public void lockFileNode(String path) throws FileStorageException
    {
	try
	{
	    fileRepository.lockFileNode(path);
	} catch (PathNotFoundException e)
	{
	    throw new FileStorageException("Cannot find file path",e);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException(e);
	}
	
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#unlockFileNode(java.lang.String)
     */
    public void unlockFileNode(String path) throws FileStorageException
    {
	try
	{
	    fileRepository.unlockFileNode(path);
	} catch (PathNotFoundException e)
	{
	    throw new FileStorageException("Cannot find file path",e);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException(e);
	}
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#performGC()
     */
    public void performGC() throws FileStorageException
    {
	fileRepository.performGC();
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#existFile(java.lang.String)
     */
    public boolean existFile(String path)
    {
	boolean _exist = false;
	
	try
	{
	    fileRepository.existsFile(path);
	    _exist = true;
	} catch (FileStorageException e)
	{
	    e.printStackTrace();
	}
	
	return _exist;
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.file.service.JcrFileMetadata#removeFileVersion(java.lang.String, java.lang.String)
     */
    public void removeFileVersion(String path, String versionName)
	    throws FileStorageException
    {
	try
	{
	    fileRepository.removeFileVersion(path, versionName);
	} catch (PathNotFoundException e)
	{
	    throw new FileStorageException("File path not found",e);
	} catch (RepositoryException e)
	{
	    throw new FileStorageException(e);
	}
    }
 }
