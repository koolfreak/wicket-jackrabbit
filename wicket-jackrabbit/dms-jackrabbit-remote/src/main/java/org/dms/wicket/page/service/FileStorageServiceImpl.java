/**
 * 
 */
package org.dms.wicket.page.service;

import java.io.InputStream;
import java.util.List;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.dms.wicket.page.dao.FileStorageDao;
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
    
    

    /* (non-Javadoc)
     * @see org.dms.wicket.page.service.FileStrorageService#save(org.dms.wicket.page.model.CustomFileDescription)
     */
    public void save(String path,String name,String mimeType,InputStream fileStream) throws FileStorageException
    {
	try
	{
	   FileDescription file = fileRepository.storeFileVersion(path, name, mimeType, fileStream);
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

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.page.service.FileStorageService#delete(org.dms.wicket.repository.db.model.FileDescription)
     */
    public void delete(FileDescription file)
	    throws FileStorageException
    {
	
	try
	{
	    fileRepository.delete(file.getFilePath());
	    jcrWebServiceAccess.delete(file);
	    
	} catch (PathNotFoundException e)
	{
	    e.printStackTrace();
	} catch (RepositoryException e)
	{
	    e.printStackTrace();
	}
	
    }

    public void update(FileDescription fileDescription)
    {
	// TODO Auto-generated method stub
	
    }
    
    public List<FileDescription> loadAll() throws FileStorageException
    {
	return jcrWebServiceAccess.findDocumentByBranch("", 0);
    }
    
    /************* setter injection ******************************/
    
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

}
