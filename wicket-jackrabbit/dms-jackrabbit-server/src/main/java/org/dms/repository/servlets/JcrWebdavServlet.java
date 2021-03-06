/**
 * 
 */
package org.dms.repository.servlets;

import javax.jcr.Repository;

import org.apache.jackrabbit.server.CredentialsProvider;
import org.apache.jackrabbit.webdav.simple.SimpleWebdavServlet;
import org.dms.repository.servlets.creds.JcrWebdavCredentials;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xaloon.wicket.component.repository.RepositoryManager;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 26 - 18:53:43
 */
public class JcrWebdavServlet extends SimpleWebdavServlet
{

    /* (non-Javadoc)
     * @see org.apache.jackrabbit.webdav.simple.SimpleWebdavServlet#getRepository()
     */
    @Override
    public Repository getRepository()
    {
	final ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	return ((RepositoryManager) ctx.getBean("repositoryManager")).getRespository();
    }
    
    /*
     * Authenticate user for webdav access
     * (non-Javadoc)
     * @see org.apache.jackrabbit.webdav.simple.SimpleWebdavServlet#getCredentialsProvider()
     */
    /*@Override
    protected CredentialsProvider getCredentialsProvider()
    {
	return new JcrWebdavCredentials();
    }*/

    
}
