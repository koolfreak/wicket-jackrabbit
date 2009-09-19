/**
 * 
 */
package org.dms.wicket.repository.db.service;

import java.util.List;

import org.dms.wicket.repository.db.model.FileDescription;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 19:49:09
 */
public interface JcrFileDescription
{

    List<FileDescription> loadAll() throws FileStorageException;
}
