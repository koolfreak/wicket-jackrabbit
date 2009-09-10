/**
 * 
 */
package org.dms.component.file;

import java.io.InputStream;
import java.util.List;


/**
 * @author Emmanuel Nollase - emanux
 * created 2009 8 24 - 11:35:29
 */
public interface StoreFileRepository
{

    /**
	 * Store file into repository
	 * 
	 * @param session - content repository session
	 * @param path - full path of image location
	 * @param input - file input stream
	 */
	String storeFile(String path, String name, String mimeType, InputStream input);

	/**
	 * Retrieve file from repository
	 * 
	 * @param session
	 * @param original
	 */
	InputStream retrieveFile(String pathToFile);

	/**
	 * Retrieve file by UUID
	 * 
	 * @param session
	 * @param uuid
	 * @return
	 */
	InputStream retrieveFileByUUID(String uuid);
	
	/**
	 * Retrieve information of files
	 * 
	 * @param searchPath
	 * @return
	 */
	List<FileDescription> searchFiles (String searchPath);

	/**
	 * Delete file/folder by path
	 * 
	 * @param path
	 */
	void delete(String path);

	/**
	 * Check if file exists in repository
	 * 
	 * @param name
	 * @return
	 */
	boolean existsFile(String name);

	/**
	 * Return list of folder for selected path
	 * 
	 * @param path
	 * @return
	 */
	List<String> searchFolders(String path);
}
