/**
 * 
 */
package org.dms.repository.servlets;

import javax.jcr.Repository;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xaloon.wicket.component.repository.RepositoryManager;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 26 - 23:04:00
 */
public class JcrRemotingServlet extends
	org.apache.jackrabbit.server.remoting.davex.JcrRemotingServlet
{

    /* (non-Javadoc)
     * @see org.apache.jackrabbit.webdav.jcr.JCRWebdavServerServlet#getRepository()
     */
    @Override
    protected Repository getRepository()
    {
	final ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	return ((RepositoryManager) ctx.getBean("RepositoryManager")).getRespository();
    }

}
