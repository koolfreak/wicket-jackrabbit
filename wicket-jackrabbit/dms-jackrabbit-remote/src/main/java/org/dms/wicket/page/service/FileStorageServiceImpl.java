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
import org.springframework.beans.factory.annotation.Autowired;
import org.xaloon.wicket.component.repository.FileRepository;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 14:17:10
 */
public class FileStorageServiceImpl implements FileStorageService
{

    private FileStorageDao fileStorageDao;
    
    private FileRepository fileRepository;

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
    public void delete(CustomFileDescription fileDescription)
    {
	fileStorageDao.delete(fileDescription);
    }

    /* (non-Javadoc)
     * @see org.dms.wicket.page.service.FileStrorageService#save(org.dms.wicket.page.model.CustomFileDescription)
     */
    public void save(CustomFileDescription filedesc,InputStream fileStream)
    {
	try
	{
	    fileRepository.storeFileVersion(filedesc.getPath(), filedesc.getName(), filedesc.getMimeType(), fileStream);
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

    public List<CustomFileDescription> loadAll()
    {
	return fileStorageDao.loadAll();
    }

}
