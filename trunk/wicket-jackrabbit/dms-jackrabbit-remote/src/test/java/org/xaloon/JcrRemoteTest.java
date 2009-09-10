/**
 * 
 */
package org.xaloon;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 8 29 - 16:42:15
 */
public class JcrRemoteTest
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
	    try
	    {
		Repository repository = new URLRemoteRepository("http://localhost:8282/jcrRemote");
		//Repository repository = new RMIRemoteRepository("//localhost:1100/jackrabbit");
		Session session = repository.login(new SimpleCredentials("username", "password".toCharArray()));
		Node rootNode = session.getRootNode();
		String filex = "photo/upload/test/test_signature.png";
		
		if (rootNode.hasNode(filex)) {
			Node file = rootNode.getNode(filex);
			InputStream stream = file.getNode("jcr:content").getProperty("jcr:data").getStream();
			try
			{
			    System.out.println("available:"+stream.available());
			    stream.close();
			} catch (IOException e)
			{
			    e.printStackTrace();
			}
		}else
		{
		    System.out.println("No node");
		}
		
	    } 
	    catch (ClassCastException e)
	    {
		e.printStackTrace();
	    } catch (LoginException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (RepositoryException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (MalformedURLException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

    }

}
