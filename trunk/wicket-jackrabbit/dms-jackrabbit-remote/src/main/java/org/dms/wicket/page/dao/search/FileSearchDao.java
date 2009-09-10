/**
 * 
 */
package org.dms.wicket.page.dao.search;

import java.util.List;

import org.dms.wicket.page.model.CustomFileDescription;
import org.springframework.dao.DataAccessException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 21:54:52
 */
public interface FileSearchDao
{

    List<CustomFileDescription> search(String query) throws DataAccessException;
}
