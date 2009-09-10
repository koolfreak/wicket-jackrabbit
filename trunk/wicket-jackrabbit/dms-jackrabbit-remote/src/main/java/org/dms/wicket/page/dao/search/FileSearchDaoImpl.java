/**
 * 
 */
package org.dms.wicket.page.dao.search;

import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.dms.wicket.page.model.CustomFileDescription;
import org.hibernate.Query;
import org.hibernate.search.FullTextSession;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 21:56:29
 */
public class FileSearchDaoImpl extends HibernateDaoSupport implements
	FileSearchDao
{

    /* (non-Javadoc)
     * @see org.dms.wicket.page.dao.search.FileSearchDao#search(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<CustomFileDescription> search(String searchQuery)
	    throws DataAccessException
    {
	
	QueryParser qparser = new QueryParser("name", new StandardAnalyzer());
	
	org.apache.lucene.search.Query luceneQuery;
	try {
	    luceneQuery = qparser.parse(searchQuery);  //build Lucene query
	}
	catch (ParseException e) {
	    throw new RuntimeException("Unable to parse query: " + searchQuery, e);
	}
	
	FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(this.getSession());
	Query query = ftSession.createFullTextQuery(luceneQuery, CustomFileDescription.class);
	
	return query.list();
    }

}
