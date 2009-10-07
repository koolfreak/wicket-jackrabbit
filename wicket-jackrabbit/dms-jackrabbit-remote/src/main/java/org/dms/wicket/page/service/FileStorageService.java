/**
 * 
 */
package org.dms.wicket.page.service;

import java.io.InputStream;
import java.util.List;

import org.dms.wicket.repository.db.model.FileDescription;
import org.xaloon.wicket.component.exception.DMSException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 14:15:42
 */
public interface FileStorageService
{
   
    void storeFileVersion(String path,String name,String mimeType,InputStream fileStream) throws DMSException;
    
    void storeNextVersion(FileDescription fileDesc,InputStream fileStream) throws DMSException;
    
    void delete(FileDescription fileDescription) throws DMSException;
    
    void update(FileDescription fileDescription) throws DMSException;
    
    List<FileDescription> loadAll(String path) throws DMSException;
}
