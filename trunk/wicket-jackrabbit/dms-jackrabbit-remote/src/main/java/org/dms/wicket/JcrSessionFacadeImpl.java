/**
 * 
 */
package org.dms.wicket;

import javax.jcr.Session;

import org.dms.wicket.component.ContentSessionFacade;
import org.dms.wicket.component.ThreadLocalSessionFactory;
import org.dms.wicket.repository.remote.DMSSessionFactory;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 10 3 - 21:25:40
 */
public class JcrSessionFacadeImpl implements ContentSessionFacade
{

   // private Session session;
   
    private ThreadLocalSessionFactory threadLocalSessionFactory;
    
    /* (non-Javadoc)
     * @see org.dms.wicket.component.ContentSessionFacade#getDefaultSession()
     */
    public Session getDefaultSession()
    {
	return threadLocalSessionFactory.getDefaultSession();
    }
    
    public void setThreadLocalSessionFactory(
    	ThreadLocalSessionFactory threadLocalSessionFactory)
    {
        this.threadLocalSessionFactory = threadLocalSessionFactory;
    }
    
}
