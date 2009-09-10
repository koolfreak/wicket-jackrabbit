/**
 * 
 */
package org.dms.wicket.page.service;

import java.io.InputStream;
import java.util.List;

import org.dms.wicket.page.model.CustomFileDescription;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 14:15:42
 */
public interface FileStorageService
{
    void save(CustomFileDescription fileDescription,InputStream fileStream);
    
    void delete(CustomFileDescription fileDescription);
    
    List<CustomFileDescription> loadAll();
}
