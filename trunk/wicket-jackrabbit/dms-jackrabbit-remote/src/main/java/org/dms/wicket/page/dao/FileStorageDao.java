/**
 * 
 */
package org.dms.wicket.page.dao;

import java.util.List;

import org.dms.wicket.page.model.CustomFileDescription;
import org.springframework.dao.DataAccessException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 14:12:50
 */
public interface FileStorageDao
{
    void save(CustomFileDescription fileDescription) throws DataAccessException;
    
    void delete(CustomFileDescription fileDescription) throws DataAccessException;
    
    List<CustomFileDescription> loadAll() throws DataAccessException;
    
}
