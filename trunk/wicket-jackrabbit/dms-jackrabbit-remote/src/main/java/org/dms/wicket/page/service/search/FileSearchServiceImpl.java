/**
 * 
 */
package org.dms.wicket.page.service.search;

import java.util.List;

import org.dms.wicket.page.dao.search.FileSearchDao;
import org.dms.wicket.page.model.CustomFileDescription;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 22:09:24
 */
public class FileSearchServiceImpl implements FileSearchService
{
    private FileSearchDao fileSearchDao;
    
    public void setFileSearchDao(FileSearchDao fileSearchDao)
    {
        this.fileSearchDao = fileSearchDao;
    }

    /* (non-Javadoc)
     * @see org.dms.wicket.page.service.search.FileSearchService#search(java.lang.String)
     */
    public List<CustomFileDescription> search(String query)
    {
	final String squery = "name:"+query;
	return fileSearchDao.search(squery);
    }

}
