/**
 * 
 */
package org.dms.wicket.page.service;

import java.io.InputStream;
import java.util.List;

import org.dms.wicket.repository.db.model.FileDescription;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 14:15:42
 */
public interface FileStorageService
{
   
    void save(String path,String name,String mimeType,InputStream fileStream) throws FileStorageException;
    
    void delete(FileDescription fileDescription) throws FileStorageException;
    
    void update(FileDescription fileDescription);
    
    List<FileDescription> loadAll() throws FileStorageException;
}
