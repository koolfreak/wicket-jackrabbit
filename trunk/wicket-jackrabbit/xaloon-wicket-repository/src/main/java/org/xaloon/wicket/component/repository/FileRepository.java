/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xaloon.wicket.component.repository;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.dms.wicket.repository.db.model.FileDescription;
import org.dms.wicket.repository.db.model.FileVersion;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public interface FileRepository {
    	
    	/**
	 * Store file into repository
	 * @param session - content repository session
	 * @param path - full path of image location
	 * @param input - file input stream
	 */
    	String storeFile(String path, String name, String mimeType,InputStream fileStream) throws FileStorageException;
	
    	/**
	 * Store file into repository as versionable
	 * @param path
	 * @param name
	 * @param mimeType
	 * @param fileStream
	 * @throws FileStorageException
	 */
    	FileDescription storeFileVersion(String path, String name, String mimeType, InputStream input) throws FileStorageException;

	/**
	 * Store file into repository the next version
	 * @param path
	 * @param fileStream
	 * @throws FileStorageException
	 */
    	void storeNextVersion(FileDescription fileDesc,InputStream fileStream) throws FileStorageException;
	
	/**
	 * Retrieve file from repository
	 * @param session
	 * @param original
	 */
	InputStream retrieveFile(String pathToFile) throws FileStorageException;

	/**
	 * Retrieve file by UUID
	 * 
	 * @param session
	 * @param uuid
	 * @return
	 */
	InputStream retrieveFileByUUID(String uuid) throws FileStorageException;
	
	/**
	 * This use for servlet access & output
	 * @param uuid - a uuid of a file in JR
	 * @param attr - file attributes such mimetype and filename only
	 * @return
	 */
	InputStream retrieveFileByUUID(String uuid,Map<String, String> attr) throws FileStorageException;
	/**
	 * Retrieve information of files
	 * 
	 * @param searchPath
	 * @return
	 */
	List<FileDescription> searchFiles (String searchPath) throws FileStorageException;
	
	List<FileVersion> getFileVersions(String path) throws FileStorageException;

	/**
	 * Delete file/folder by path
	 * 
	 * @param path
	 */
	void delete(String path) throws FileStorageException;

	/**
	 * Check if file exists in repository
	 * 
	 * @param name
	 * @return
	 */
	boolean existsFile(String name) throws FileStorageException;

	/**
	 * Return list of folder for selected path
	 * 
	 * @param path
	 * @return
	 */
	List<String> searchFolders(String path) throws FileStorageException;
	
	
}
