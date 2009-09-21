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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.ItemExistsException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.dms.wicket.repository.db.model.FileDescription;
import org.dms.wicket.repository.db.model.FileVersion;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public interface FileRepository
{

    /**
     * Store file into repository
     * 
     * @param session
     *            - content repository session
     * @param path
     *            - full path of image location
     * @param input
     *            - file input stream
     */
    String storeFile(String path, String name, String mimeType,
	    InputStream fileStream) throws PathNotFoundException, RepositoryException, Exception;

    /**
     * Store file into repository as versionable
     * 
     * @param path
     * @param name
     * @param mimeType
     * @param fileStream
     * @throws FileStorageException
     */
    FileDescription storeFileVersion(String path, String name, String mimeType,
	    InputStream input) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException,Exception;

    /**
     * Store file into repository the next version
     * 
     * @param path
     * @param fileStream
     * @throws FileStorageException
     */
    void storeNextVersion(FileDescription fileDesc, InputStream fileStream)
    throws ValueFormatException, VersionException, LockException, ConstraintViolationException, PathNotFoundException, RepositoryException;

    /**
     * 
     * @param file
     * @param verName
     * @throws FileStorageException
     */
    void restoreVersion(FileDescription file, String verName)
    throws RepositoryException;

    /**
     * Retrieve file from repository
     * 
     * @param session
     * @param original
     */
    InputStream retrieveFile(String pathToFile) throws PathNotFoundException, ValueFormatException, RepositoryException;

    /**
     * Retrieve file by UUID
     * 
     * @param session
     * @param uuid
     * @return
     */
    InputStream retrieveFileByUUID(String uuid) throws ValueFormatException, PathNotFoundException, RepositoryException;

    /**
     * This use for servlet access & output
     * 
     * @param uuid
     *            - a uuid of a file in JR
     * @param attr
     *            - file attributes such mimetype and filename only
     * @return
     */
    InputStream retrieveFile(String uuid, Map<String, String> attr)
	    throws ValueFormatException, PathNotFoundException, RepositoryException;

    /**
     * Retrieve information of files
     * 
     * @param searchPath
     * @return
     */
    List<FileDescription> searchFiles(String searchPath)
	    throws FileStorageException;

    /**
     * 
     * @param path
     * @return
     * @throws FileStorageException
     */
    List<FileVersion> getFileVersions(String path) throws RepositoryException;

    /**
     * Delete file/folder by path
     * 
     * @param path
     */
    void delete(String path) throws PathNotFoundException, RepositoryException;

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

    void crateJcrWorkspace(final String wsname) throws AccessDeniedException, UnsupportedRepositoryOperationException, RepositoryException;

    void renameNode(String path, String newName) throws RepositoryException;

    void exportDocumentView(String path, String exportFile,boolean skipBinary) throws PathNotFoundException, IOException, RepositoryException;

    void importDocumentView(String path, String exportFile)
    throws PathNotFoundException, ItemExistsException, ConstraintViolationException, VersionException, InvalidSerializedDataException, LockException, IOException, RepositoryException,Exception;

    void lockFileNode(String path) throws PathNotFoundException, RepositoryException;
    
    void unlockFileNode(String path) throws PathNotFoundException, RepositoryException;
    
    void createRepositoryNodes(String paths) throws Exception;

    void performGC() throws FileStorageException;
}
