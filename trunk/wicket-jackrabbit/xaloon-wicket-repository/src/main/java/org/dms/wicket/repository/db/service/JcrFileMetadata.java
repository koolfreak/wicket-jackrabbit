/**
 * 
 */
package org.dms.wicket.repository.db.service;

import java.io.InputStream;
import java.util.List;

import org.dms.wicket.repository.db.model.FileDescription;
import org.dms.wicket.repository.db.model.FileVersion;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 01:09:05
 */
public interface JcrFileMetadata
{
    void storeFileVersion(String path, String name, String mimeType, InputStream input) throws FileStorageException;
    
    void storeNextVersion(FileDescription file,InputStream fileStream) throws FileStorageException;

    List<FileDescription> loadAll();
    
    List<FileVersion> getFileVersions(String path) throws FileStorageException;
}
