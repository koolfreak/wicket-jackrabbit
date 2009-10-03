/**
 * 
 */
package org.dms.wicket;

import javax.jcr.Session;

import org.dms.wicket.component.ContentSessionFacade;
import org.dms.wicket.repository.remote.DMSSessionFactory;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 10 3 - 21:25:40
 */
public class JcrSessionFacadeImpl implements ContentSessionFacade
{

    private Session session;
    
    public JcrSessionFacadeImpl(DMSSessionFactory sessionFactory)
    {
	this.session = sessionFactory.getJcrSession();
    }
    /* (non-Javadoc)
     * @see org.dms.wicket.component.ContentSessionFacade#getDefaultSession()
     */
    public Session getDefaultSession()
    {
	return session;
    }

}
