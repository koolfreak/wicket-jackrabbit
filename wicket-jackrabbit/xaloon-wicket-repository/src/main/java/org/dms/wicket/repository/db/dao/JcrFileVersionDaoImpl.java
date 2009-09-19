/**
 * 
 */
package org.dms.wicket.repository.db.dao;

import org.dms.wicket.repository.db.model.FileVersion;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 13:03:41
 */
public class JcrFileVersionDaoImpl extends HibernateDaoSupport implements
	JcrFileVersionDao
{

    /* (non-Javadoc)
     * @see org.dms.wicket.repository.db.dao.JcrFileVersionDao#delete(org.dms.wicket.repository.db.model.FileVersion)
     */
    public void delete(FileVersion file) throws DataAccessException
    {
	getHibernateTemplate().delete(file);
    }

    /* (non-Javadoc)
     * @see org.dms.wicket.repository.db.dao.JcrFileVersionDao#save(org.dms.wicket.repository.db.model.FileVersion)
     */
    public void save(FileVersion file) throws DataAccessException
    {
	getHibernateTemplate().save(file);
    }

    /* (non-Javadoc)
     * @see org.dms.wicket.repository.db.dao.JcrFileVersionDao#update(org.dms.wicket.repository.db.model.FileVersion)
     */
    public void update(FileVersion file) throws DataAccessException
    {
	getHibernateTemplate().merge(file);
    }

}
