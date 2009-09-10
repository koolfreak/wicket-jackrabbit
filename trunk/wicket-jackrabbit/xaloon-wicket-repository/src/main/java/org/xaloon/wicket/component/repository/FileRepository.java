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

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public interface FileRepository {

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
	 * This use for servlet access & output
	 * @param uuid - a uuid of a file in JR
	 * @param attr - file attributes such mimetype and filename only
	 * @return
	 */
	public InputStream retrieveFileByUUID(String uuid,Map<String, String> attr);
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
