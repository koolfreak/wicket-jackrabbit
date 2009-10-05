/**
 * 
 */
package org.dms.wicket.page.service;

import java.io.InputStream;
import java.util.List;

import org.dms.wicket.page.model.CustomFileDescription;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 14:15:42
 */
public interface FileStorageService
{
    void save(CustomFileDescription fileDescription,InputStream fileStream) throws FileStorageException;
    
    void delete(CustomFileDescription fileDescription) throws FileStorageException;
    
    List<CustomFileDescription> loadAll() throws FileStorageException;
}
