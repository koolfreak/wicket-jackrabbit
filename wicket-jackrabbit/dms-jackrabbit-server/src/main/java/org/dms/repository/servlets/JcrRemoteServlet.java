/**
 * 
 */
package org.dms.repository.servlets;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServlet;

import org.apache.jackrabbit.rmi.jackrabbit.JackrabbitServerAdapterFactory;
import org.apache.jackrabbit.rmi.remote.RemoteRepository;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xaloon.wicket.component.repository.RepositoryManager;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 7 - 16:28:16
 */
public abstract class JcrRemoteServlet extends HttpServlet
{

    public RemoteRepository getRemote()
    {
        RepositoryManager repositoryManager = (RepositoryManager) getApplicationContext().getBean("RepositoryManager");
        JackrabbitServerAdapterFactory factory = new JackrabbitServerAdapterFactory();
        try
     {
 	return factory.getRemoteRepository(repositoryManager.getRespository());
     } 
        catch (RemoteException e)
     {
 	e.printStackTrace();
     }
     throw new IllegalArgumentException("Error getting remote repository");
    }
    
    public WebApplicationContext getApplicationContext()
    {
	return WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
    }
}
