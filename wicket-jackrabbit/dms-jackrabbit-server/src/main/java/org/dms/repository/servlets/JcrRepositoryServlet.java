/**
 * 
 */
package org.dms.repository.servlets;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.rmi.jackrabbit.JackrabbitServerAdapterFactory;
import org.apache.jackrabbit.rmi.remote.RemoteRepository;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xaloon.wicket.component.repository.RepositoryManager;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 9 7 - 14:26:57
 */
public class JcrRepositoryServlet extends JcrRemoteServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException
    {
	 //ServerAdapterFactory factory = new ServerAdapterFactory();
	
	resp.setContentType("application/octet-stream");
	ObjectOutputStream output = new ObjectOutputStream(resp.getOutputStream());
	output.writeObject(RemoteObject.toStub(getRemote()));
	output.flush();
    }

}
