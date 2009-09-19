/**
 * 
 */
package org.dms.wicket.repository.admin;

import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 14 - 12:31:44
 */
public interface JcrAdminService
{

    boolean crateJcrWorkspace(final String wsname) throws FileStorageException;
    
    boolean renameNode(String path,String newName) throws FileStorageException;
    
    void exportDocumentView(String path,String exportFile) throws FileStorageException;
    
    void createRepositoryNodes(String paths) throws FileStorageException;
}
