/**
 * 
 */
package org.dms.wicket.repository.db.dao;

import java.util.List;

import org.dms.wicket.repository.db.model.FileDescription;
import org.hibernate.criterion.Restrictions;
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
    public List<FileDescription> loadAll() throws DataAccessException
    {
	// TODO Auto-generated method stub
	return null;
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

}
