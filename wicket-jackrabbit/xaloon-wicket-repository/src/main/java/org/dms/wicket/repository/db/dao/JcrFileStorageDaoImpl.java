/**
 * 
 */
package org.dms.wicket.repository.db.dao;

import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.dms.wicket.repository.db.model.FileDescription;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 9 - 16:18:29
 */

public class JcrFileStorageDaoImpl extends HibernateDaoSupport implements
	JcrFileStorageDao
{

    /* (non-Javadoc)
     * @see org.dms.wicket.repository.db.dao.JcrFileStorageDao#delete(org.dms.wicket.repository.db.model.JcrFileRepository)
     */
    public void delete(FileDescription file) throws DataAccessException
    {
	getHibernateTemplate().delete(file);
    }

    /* (non-Javadoc)
     * @see org.dms.wicket.repository.db.dao.JcrFileStorageDao#loadAll()
     */
    @SuppressWarnings("unchecked")
    public List<FileDescription> loadAll() throws DataAccessException
    {
	return this.getSession().createCriteria(FileDescription.class).list();
    }

    /* (non-Javadoc)
     * @see org.dms.wicket.repository.db.dao.JcrFileStorageDao#save(org.dms.wicket.repository.db.model.JcrFileRepository)
     */
    public void save(FileDescription file) throws DataAccessException
    {
	getHibernateTemplate().save(file);
    }

    public FileDescription loadByUUID(String uuid) throws DataAccessException
    {
	return (FileDescription) this.getSession().createCriteria(FileDescription.class).add(Restrictions.eq("UUID", uuid)).uniqueResult();
    }

    public void update(FileDescription file) throws DataAccessException
    {
	getHibernateTemplate().merge(file);
    }

    @SuppressWarnings("unchecked")
    public List<FileDescription> loadAll(int first, int max)
	    throws DataAccessException
    {
	return this.getSession().createCriteria(FileDescription.class).setFirstResult(first).setMaxResults(max).list();
    }

    @SuppressWarnings("unchecked")
    public List<FileDescription> search(String searchCriteria, int max)
	    throws DataAccessException
    {
	QueryParser qparser = new QueryParser("name", new StandardAnalyzer());
	
	org.apache.lucene.search.Query luceneQuery;
	try {
	    luceneQuery = qparser.parse(searchCriteria);  //build Lucene query
	}
	catch (ParseException e) {
	    throw new RuntimeException("Unable to parse query: " + searchCriteria, e);
	}
	
	FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(this.getSession());
	Query query = ftSession.createFullTextQuery(luceneQuery, FileDescription.class);
	query.setMaxResults(max); // just to get the top result and for fast display
	
	return query.list();
    }

    public int countAll() throws DataAccessException
    {
	
	return (Integer) this.getSession()
		.createCriteria(FileDescription.class).setProjection(
			Projections.rowCount()).uniqueResult();
    }

}
