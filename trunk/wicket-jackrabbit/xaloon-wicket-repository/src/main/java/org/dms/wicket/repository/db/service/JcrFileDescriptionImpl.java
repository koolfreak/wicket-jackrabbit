/**
 * 
 */
package org.dms.wicket.repository.db.service;

import java.util.List;

import javax.jws.WebService;

import org.dms.wicket.repository.cxf.service.JcrWebServiceAccess;
import org.dms.wicket.repository.db.dao.JcrFileStorageDao;
import org.dms.wicket.repository.db.model.FileDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 19:49:44
 */
@WebService(endpointInterface="org.dms.wicket.repository.cxf.service.JcrWebServiceAccess")
@Component("jcrFileDescription")
public class JcrFileDescriptionImpl implements JcrFileDescription, JcrWebServiceAccess
{

    @Autowired private JcrFileStorageDao jcrFileStorageDao;
    
    public List<FileDescription> loadAll() throws FileStorageException
    {
	return jcrFileStorageDao.loadAll();
    }

    public int countAll()
    {
	return jcrFileStorageDao.countAll();
    }

    public int countByLuceneQuery(String query)
    {
	return 0;
    }

    public List<FileDescription> loadAll(int first, int max)
    {
	return jcrFileStorageDao.loadAll(first, max);
    }

    public List<FileDescription> loadByLuceneQuery(String query, int max)
    {
	return jcrFileStorageDao.search(query, max);
    }

    public FileDescription loadByUUID(String uuid)
    {
	return jcrFileStorageDao.loadByUUID(uuid);
    }

    public String getFilePath(String uuid) throws NullPointerException
    {
	return loadByUUID(uuid).getFilePath();
    }

    public void delete(FileDescription file)
    {
	jcrFileStorageDao.delete(file);
    }

    public void save(FileDescription file)
    {
	jcrFileStorageDao.save(file);
    }

    public void update(FileDescription file)
    {
	jcrFileStorageDao.update(file);
    }

    public List<FileDescription> findDocumentByBranch(String branch,int max)
    {
	// TODO - implement flexible lucene query here
	return jcrFileStorageDao.loadAll();
    }

    
}
