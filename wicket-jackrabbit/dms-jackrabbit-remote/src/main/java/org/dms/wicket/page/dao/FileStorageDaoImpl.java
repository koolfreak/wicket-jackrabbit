/**
 * 
 */
package org.dms.wicket.page.dao;

import java.util.List;

import org.dms.wicket.page.model.CustomFileDescription;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 14:13:24
 */
public class FileStorageDaoImpl extends HibernateDaoSupport implements
	FileStorageDao
{

    public void delete(CustomFileDescription fileDescription)
	    throws DataAccessException
    {
	getHibernateTemplate().delete(fileDescription);
    }

    public void save(CustomFileDescription fileDescription)
	    throws DataAccessException
    {
	getHibernateTemplate().save(fileDescription);
    }

    @SuppressWarnings("unchecked")
    public List<CustomFileDescription> loadAll() throws DataAccessException
    {
	return this.getSession().createCriteria(CustomFileDescription.class).list();
    }

}
