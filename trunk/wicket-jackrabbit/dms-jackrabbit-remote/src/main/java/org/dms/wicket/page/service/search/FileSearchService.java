/**
 * 
 */
package org.dms.wicket.page.service.search;

import java.util.List;

import org.dms.wicket.page.model.CustomFileDescription;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 22:08:26
 */
public interface FileSearchService
{

    List<CustomFileDescription> search(String query);
}
