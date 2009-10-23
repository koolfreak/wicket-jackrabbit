/**
 * 
 */
package org.dms.wicket.repository.db.service;

import java.util.List;

import org.dms.wicket.repository.db.dao.JcrFileStorageDao;
import org.dms.wicket.repository.db.model.FileDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 19:49:44
 */
@Component("jcrFileDescription")
public class JcrFileDescriptionImpl implements JcrFileDescription
{

    @Autowired private JcrFileStorageDao jcrFileStorageDao;
    
    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileDescription#countAll(java.lang.String)
     */
    public int countAllByBranch(String branch)
    {
	return jcrFileStorageDao.countAllByBranch(branch);
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileDescription#loadAll(java.lang.String, int, int)
     */
    public List<FileDescription> loadAllByBranch(String branch,int first, int max)
    {
	return jcrFileStorageDao.loadAllByBranch(branch,first, max);
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileDescription#searchByLuceneQuery(java.lang.String, int)
     */
    public List<FileDescription> searchByLuceneQuery(String query, int max)
    {
	return jcrFileStorageDao.search(query, max);
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileDescription#loadByUUID(java.lang.String)
     */
    public FileDescription loadByUUID(String uuid)
    {
	return jcrFileStorageDao.loadByUUID(uuid);
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileDescription#getFilePath(java.lang.String)
     */
    public String getFilePath(String uuid) throws NullPointerException
    {
	return loadByUUID(uuid).getFilePath();
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileDescription#delete(org.dms.wicket.repository.db.model.FileDescription)
     */
    public void delete(FileDescription file)
    {
	jcrFileStorageDao.delete(file);
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileDescription#save(org.dms.wicket.repository.db.model.FileDescription)
     */
    public void save(FileDescription file)
    {
	jcrFileStorageDao.save(file);
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileDescription#update(org.dms.wicket.repository.db.model.FileDescription)
     */
    public void update(FileDescription file)
    {
	jcrFileStorageDao.update(file);
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.cxf.service.JcrWebServiceAccess#findDocumentByBranch(java.lang.String, int)
     */
    public List<FileDescription> findDocumentByBranch(String branch,int max)
    {
	return jcrFileStorageDao.loadAllByBranch(branch, 0, max);
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileDescription#countAll()
     */
    public int countAll()
    {
	return jcrFileStorageDao.countAll();
    }

    /*
     * (non-Javadoc)
     * @see org.dms.wicket.repository.db.service.JcrFileDescription#loadAll(int, int)
     */
    public List<FileDescription> loadAll(int first, int max)
    {
	return jcrFileStorageDao.loadAll(first, max);
    }

}
