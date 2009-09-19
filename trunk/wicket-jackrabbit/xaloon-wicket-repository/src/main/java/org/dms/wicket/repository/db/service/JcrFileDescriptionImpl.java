/**
 * 
 */
package org.dms.wicket.repository.db.service;

import java.util.List;

import org.dms.wicket.repository.db.dao.JcrFileStorageDao;
import org.dms.wicket.repository.db.model.FileDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 19:49:44
 */
@Component
public class JcrFileDescriptionImpl implements JcrFileDescription
{

    @Autowired private JcrFileStorageDao jcrFileStorageDao;
    
    public List<FileDescription> loadAll() throws FileStorageException
    {
	return jcrFileStorageDao.loadAll();
    }

}