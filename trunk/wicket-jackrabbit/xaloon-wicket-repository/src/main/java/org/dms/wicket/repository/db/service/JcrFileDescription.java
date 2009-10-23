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

    /**
     * Save the metadata of images to DB
     * @param file
     */
    void save(FileDescription file);
    
    /**
     * Delete the metadata from DB
     * @param file
     */
    void delete(FileDescription file);
    
    /**
     * Updates the metadata
     * @param file
     */
    void update(FileDescription file);
    
    /**
     * Get the file description base from uuid of document in JCR
     * @param uuid
     * @return
     */
    FileDescription loadByUUID(String uuid);
    
    /**
     * Get the document path in JCR
     * @param uuid
     * @return
     */
    String getFilePath(String uuid);
    
    /**
     * Count all document for a particular branch
     * @param branch
     * @return
     */
    int countAllByBranch(String branch);
    
    /**
     * @return - 
     */
    int countAll();
    
    /**
     * Load all document of branch and paged it
     * @param branch
     * @param first
     * @param max
     * @return
     */
    List<FileDescription> loadAllByBranch(String branch, int first,int max);
    
    /**
     * Load files for paging
     * @param first
     * @param max
     * @return
     */
    List<FileDescription> loadAll(int first,int max);
    
    /**
     * Search document using lucene index
     * @param query
     * @param max
     * @return
     */
    List<FileDescription> searchByLuceneQuery(String query, int max);
    
}
