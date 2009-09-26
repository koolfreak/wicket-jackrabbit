/**
 * 
 */
package org.dms.repository.servlets;

import javax.jcr.Repository;

import org.apache.jackrabbit.webdav.simple.SimpleWebdavServlet;
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
	return ((RepositoryManager) ctx.getBean("RepositoryManager")).getRespository();
    }

}
