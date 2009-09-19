/**
 * 
 */
package org.dms.wicket.repository.db.dao;

import org.dms.wicket.repository.db.model.FileVersion;
import org.springframework.dao.DataAccessException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 13:00:36
 */
public interface JcrFileVersionDao
{

    void save(FileVersion file) throws DataAccessException;
    
    void update(FileVersion file) throws DataAccessException;
    
    void delete(FileVersion file) throws DataAccessException;
}
