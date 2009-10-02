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
import javax.jcr.query.InvalidQueryException;
import javax.jcr.version.VersionException;

import org.dms.wicket.repository.db.model.FileDescription;
import org.dms.wicket.repository.db.model.FileVersion;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 * @author emmanuel nollase
 */
public interface FileRepository
{

    /**
     * Store file into repository.Not versionable
     * @param session
     *            - content repository session
     * @param path
     *            - full path of image location
     * @param input
     *            - file input stream
     */
    FileDescription storeFile(String path, String name, String mimeType,
	    InputStream fileStream) throws PathNotFoundException, RepositoryException, Exception;

    /**
     * Store file into repository as versionable
     * 
     * @param path - full path of image location
     * @param name - filename
     * @param mimeType - content type
     * @param fileStream - file data input stream format
     * @throws FileStorageException
     */
    FileDescription storeFileVersion(String path, String name, String mimeType,
	    InputStream input) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException,Exception;

    /**
     * Store file into repository the next version
     * 
     * @param path - full path of image location
     * @param fileStream - file data input stream format
     * @throws FileStorageException
     */
    void storeNextVersion(FileDescription fileDesc, InputStream fileStream)
    throws ValueFormatException, VersionException, LockException, ConstraintViolationException, PathNotFoundException, RepositoryException;

    /**
     * 
     * @param file - FileDescription
     * @param verName - the version to restore
     * @throws FileStorageException
     */
    void restoreVersion(FileDescription file, String verName)
    throws PathNotFoundException, RepositoryException;

    /**
     * 
     * @param path - full path of image location
     * @param versionName - version to remove
     * @throws PathNotFoundException
     * @throws RepositoryException
     */
    void removeFileVersion(String path,String versionName) throws PathNotFoundException, RepositoryException;
   
    /**
     * 
     * @param pathToFile
     * @return
     * @throws PathNotFoundException
     * @throws ValueFormatException
     * @throws RepositoryException
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
    
    /**
     * 
     * @param path
     * @param keyword
     * @return
     */
    List<FileDescription> searchFileByKeyword(String path,String keyword) throws InvalidQueryException, RepositoryException;
    
    /**
     * 
     * @param wsname
     * @throws AccessDeniedException
     * @throws UnsupportedRepositoryOperationException
     * @throws RepositoryException
     */
    void crateJcrWorkspace(final String wsname) throws AccessDeniedException, UnsupportedRepositoryOperationException, RepositoryException;

    /**
     * 
     * @param path
     * @param newName
     * @throws RepositoryException
     */
    void renameNode(String path, String newName) throws RepositoryException;

    /**
     * 
     * @param path
     * @param exportFile
     * @param skipBinary
     * @throws PathNotFoundException
     * @throws IOException
     * @throws RepositoryException
     */
    void exportSystemView(String path, String exportFile) throws PathNotFoundException, IOException, RepositoryException;

    /**
     * 
     * @param path
     * @param exportFile
     * @throws PathNotFoundException
     * @throws ItemExistsException
     * @throws ConstraintViolationException
     * @throws VersionException
     * @throws InvalidSerializedDataException
     * @throws LockException
     * @throws IOException
     * @throws RepositoryException
     * @throws Exception
     */
    void importXML(String exportFile)
    throws PathNotFoundException, ItemExistsException, ConstraintViolationException, VersionException, InvalidSerializedDataException, LockException, IOException, RepositoryException,Exception;

    
    void importXML(InputStream fileStream) 
    throws PathNotFoundException, ItemExistsException, ConstraintViolationException, VersionException, InvalidSerializedDataException, LockException, IOException, RepositoryException,Exception;
    /**
     * 
     * @param path
     * @throws PathNotFoundException
     * @throws RepositoryException
     */
    void lockFileNode(String path) throws PathNotFoundException, RepositoryException;
    
    /**
     * 
     * @param path
     * @throws PathNotFoundException
     * @throws RepositoryException
     */
    void unlockFileNode(String path) throws PathNotFoundException, RepositoryException;
    
    /**
     * 
     * @param paths
     * @throws Exception
     */
    void createRepositoryNodes(String paths) throws Exception;
    
    /**
     * 
     * @throws FileStorageException
     */
    void performGC() throws FileStorageException;
}
