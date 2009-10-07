/**
 * 
 */
package org.dms.wicket.page.service;

import java.io.InputStream;
import java.util.List;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.dms.wicket.page.dao.FileStorageDao;
import org.dms.wicket.repository.cxf.service.JcrWebServiceAccess;
import org.dms.wicket.repository.db.model.FileDescription;
import org.xaloon.wicket.component.exception.DMSException;
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
    public void storeFileVersion(String path,String name,String mimeType,InputStream fileStream) throws DMSException
    {
	try
	{
	   final FileDescription file = fileRepository.storeFileVersion(path, name, mimeType, fileStream);
	   if(null != file)	
	       jcrWebServiceAccess.save(file);
	} catch (PathNotFoundException e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	} catch (RepositoryException e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	} catch (Exception e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	}
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.page.service.FileStorageService#storeNextVersion(org.dms.wicket.repository.db.model.FileDescription, java.io.InputStream)
     */
    public void storeNextVersion(FileDescription fileDesc,
	    InputStream fileStream) throws DMSException
    {
	try
	{
	    fileRepository.storeNextVersion(fileDesc, fileStream);
	    jcrWebServiceAccess.update(fileDesc);
	    
	} catch (ValueFormatException e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	} catch (VersionException e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	} catch (LockException e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	} catch (ConstraintViolationException e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	} catch (PathNotFoundException e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	} catch (RepositoryException e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	} catch(Exception e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	}
	
    }
    
    /*
     * (non-Javadoc)
     * @see org.dms.wicket.page.service.FileStorageService#delete(org.dms.wicket.repository.db.model.FileDescription)
     */
    public void delete(FileDescription file)
    	throws DMSException
    {
	
	try
	{
	    fileRepository.delete(file.getFilePath());
	    jcrWebServiceAccess.delete(file);
	    
	} catch (PathNotFoundException e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	} catch (RepositoryException e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	} catch (Exception e)
	{
	    e.printStackTrace();
	    throw new DMSException(e.getMessage());
	}
	
    }

    public void update(FileDescription fileDescription) throws DMSException
    {
	// TODO Auto-generated method stub
	
    }
    
    public List<FileDescription> loadAll(String path) throws DMSException
    {
	//
	return jcrWebServiceAccess.findDocumentByBranch(path, 10);
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
