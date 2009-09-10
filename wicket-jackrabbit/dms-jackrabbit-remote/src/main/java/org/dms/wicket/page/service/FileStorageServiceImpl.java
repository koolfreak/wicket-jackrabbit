/**
 * 
 */
package org.dms.wicket.page.service;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import org.dms.component.file.StoreFileRepository;
import org.dms.wicket.page.dao.FileStorageDao;
import org.dms.wicket.page.model.CustomFileDescription;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 14:17:10
 */
public class FileStorageServiceImpl implements FileStorageService
{

    private FileStorageDao fileStorageDao;
    
    private StoreFileRepository storeFileRepository;
    
    public void setStoreFileRepository(StoreFileRepository storeFileRepository)
    {
        this.storeFileRepository = storeFileRepository;
    }

    public void setFileStorageDao(FileStorageDao fileStorageDao)
    {
        this.fileStorageDao = fileStorageDao;
    }
    
    /* (non-Javadoc)
     * @see org.dms.wicket.page.service.FileStrorageService#delete(org.dms.wicket.page.model.CustomFileDescription)
     */
    public void delete(CustomFileDescription fileDescription)
    {
	storeFileRepository.delete(fileDescription.getFilePath());
	fileStorageDao.delete(fileDescription);
    }

    /* (non-Javadoc)
     * @see org.dms.wicket.page.service.FileStrorageService#save(org.dms.wicket.page.model.CustomFileDescription)
     */
    public void save(CustomFileDescription filedesc,InputStream fileStream)
    {
	final String uuid = storeFileRepository.storeFile(filedesc.getPath(), filedesc.getName(), filedesc.getMimeType(), fileStream);
	filedesc.setUUID(uuid);
	filedesc.setLastModified(Calendar.getInstance().getTime());
	
	fileStorageDao.save(filedesc);
    }

    public List<CustomFileDescription> loadAll()
    {
	return fileStorageDao.loadAll();
    }

}
