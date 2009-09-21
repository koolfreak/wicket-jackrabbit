/**
 * 
 */
package org.dms.wicket.repository.file.service;

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

    void restoreVersion(FileDescription file, String verName)  throws FileStorageException;
    
    void deleteFile(FileDescription file) throws FileStorageException;
    
    boolean crateJcrWorkspace(final String wsname) throws FileStorageException;
    
    boolean renameNode(String path,String newName) throws FileStorageException;
    
    void exportDocumentView(String path,String exportFile,boolean skipBinary) throws FileStorageException;
    
    void importDocumentView(String path,String exportFile) throws FileStorageException;
    
    void createRepositoryNodes(String paths) throws FileStorageException;
    
    void unlockFileNode(String path) throws FileStorageException;
    
    void lockFileNode(String path) throws FileStorageException;
    
    void performGC() throws FileStorageException;
    
    List<FileVersion> getFileVersions(String path) throws FileStorageException;
}
