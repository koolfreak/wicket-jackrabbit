/**
 * 
 */
package org.dms.wicket.repository.db.dao;

import java.util.List;

import org.dms.wicket.repository.db.model.FileDescription;
import org.springframework.dao.DataAccessException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 9 - 16:17:03
 */
public interface JcrFileStorageDao
{
    void save(FileDescription file) throws DataAccessException;
    
    void delete(FileDescription file) throws DataAccessException;
    
    FileDescription loadByUUID(String uuid) throws DataAccessException;
    
    List<FileDescription> loadAll() throws DataAccessException;
}
