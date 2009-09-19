/**
 * 
 */
package org.dms.wicket.repository.db.service;

import java.io.InputStream;
import java.util.List;

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
@Component
public class JcrFileMetadataImpl implements JcrFileMetadata
{

    @Autowired private FileRepository fileRepository;
    @Autowired private JcrFileStorageDao jcrFileStorageDao;
    /* (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileMetadata#storeFileVersion(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream)
     */
    public void storeFileVersion(String path, String name, String mimeType,
	    InputStream input) throws FileStorageException
    {
	final FileDescription file = fileRepository.storeFileVersion(path, name, mimeType, input);
	if(file != null)
	{
	    jcrFileStorageDao.save(file);
	}

    }

    /* (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileMetadata#storeNextVersion(java.lang.String, java.io.InputStream)
     */
    public void storeNextVersion(FileDescription file, InputStream fileStream)
	    throws FileStorageException
    {
	fileRepository.storeNextVersion(file, fileStream);
	jcrFileStorageDao.update(file);
    }

    public List<FileDescription> loadAll()
    {
	return jcrFileStorageDao.loadAll();
    }

    public List<FileVersion> getFileVersions(String path)
	    throws FileStorageException
    {
	return fileRepository.getFileVersions(path);
    }
 }
