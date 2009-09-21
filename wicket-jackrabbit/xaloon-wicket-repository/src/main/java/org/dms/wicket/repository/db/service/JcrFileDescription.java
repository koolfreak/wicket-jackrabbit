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

    FileDescription loadByUUID(String uuid);
    
    String getFilePath(String uuid);
    
    List<FileDescription> loadAll();
    
    List<FileDescription> loadAll(int first,int max);
    
    List<FileDescription> loadByLuceneQuery(String query,int first,int max);
    
    int countAll();
    
    int countByLuceneQuery(String query);
}
