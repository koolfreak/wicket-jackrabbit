/**
 * 
 */
package org.dms.wicket.repository.db.service;

import java.util.List;

import org.dms.wicket.repository.db.model.FileDescription;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 19:49:09
 */
public interface JcrFileDescription
{

    void save(FileDescription file);
    
    void delete(FileDescription file);
    
    void update(FileDescription file);
    
    FileDescription loadByUUID(String uuid);
    
    String getFilePath(String uuid);
    
    int countAll();
    
    List<FileDescription> loadAll();
    
    List<FileDescription> loadAll(int first,int max);
    
    List<FileDescription> loadByLuceneQuery(String query,int max);
    
    int countByLuceneQuery(String query);
    
}
