/**
 * 
 */
package org.dms.wicket.page.service;

import java.io.InputStream;
import java.util.List;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.dms.wicket.page.dao.FileStorageDao;
import org.dms.wicket.page.model.CustomFileDescription;
import org.dms.wicket.repository.cxf.service.JcrWebServiceAccess;
import org.dms.wicket.repository.db.model.FileDescription;
import org.xaloon.wicket.component.exception.FileStorageException;
import org.xaloon.wicket.component.repository.FileRepository;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 14:17:10
 */
public class FileStorageServiceImpl implements FileStorageService
{

    private FileStorageDao fileStorageDao;
    
    private FileRepository fileRepository;
    
    private JcrWebServiceAccess jcrWebServiceAccess;
    
    public void setJcrWebServiceAccess(JcrWebServiceAccess jcrWebServiceAccess)
    {
        this.jcrWebServiceAccess = jcrWebServiceAccess;
    }

    public void setFileStorageDao(FileStorageDao fileStorageDao)
    {
        this.fileStorageDao = fileStorageDao;
    }
    
    public void setFileRepository(FileRepository fileRepository)
    {
        this.fileRepository = fileRepository;
    }

    /* (non-Javadoc)
     * @see org.dms.wicket.page.service.FileStrorageService#delete(org.dms.wicket.page.model.CustomFileDescription)
     */
    public void delete(CustomFileDescription fileDescription) throws FileStorageException
    {
	fileStorageDao.delete(fileDescription);
    }

    /* (non-Javadoc)
     * @see org.dms.wicket.page.service.FileStrorageService#save(org.dms.wicket.page.model.CustomFileDescription)
     */
    public void save(CustomFileDescription filedesc,InputStream fileStream) throws FileStorageException
    {
	try
	{
	   FileDescription file = fileRepository.storeFileVersion(filedesc.getPath(), filedesc.getName(), filedesc.getMimeType(), fileStream);
	   jcrWebServiceAccess.save(file);
	} catch (PathNotFoundException e)
	{
	    e.printStackTrace();
	} catch (RepositoryException e)
	{
	    e.printStackTrace();
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    }

    public List<CustomFileDescription> loadAll() throws FileStorageException
    {
	return fileStorageDao.loadAll();
    }

}
