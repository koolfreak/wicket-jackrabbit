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
    /**
     * 
     * @param file
     * @throws DataAccessException
     */
    void save(FileDescription file) throws DataAccessException;
    
    /**
     * 
     * @param file
     * @throws DataAccessException
     */
    void delete(FileDescription file) throws DataAccessException;
    
    /**
     * 
     * @param file
     * @throws DataAccessException
     */
    void update(FileDescription file) throws DataAccessException;
    
    /**
     * 
     * @param uuid
     * @return
     * @throws DataAccessException
     */
    FileDescription loadByUUID(String uuid) throws DataAccessException;
    
    /**
     * 
     * @param branch
     * @return
     * @throws DataAccessException
     */
    int countAll(String branch) throws DataAccessException;
    
    /**
     * 
     * @return
     * @throws DataAccessException
     */
    List<FileDescription> loadAll() throws DataAccessException;
    
    /**
     * 
     * @param branch
     * @param first
     * @param max
     * @return
     * @throws DataAccessException
     */
    List<FileDescription> loadAllByBranch(String branch,int first,int max) throws DataAccessException;
    
    /**
     * 
     * @param searchCriteria
     * @param maxResult
     * @return
     * @throws DataAccessException
     */
    List<FileDescription> search(String searchCriteria, int maxResult) throws DataAccessException;
}
