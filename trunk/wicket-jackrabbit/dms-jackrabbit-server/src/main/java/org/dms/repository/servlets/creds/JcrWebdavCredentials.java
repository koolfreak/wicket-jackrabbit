package org.dms.repository.servlets.creds;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.jackrabbit.server.CredentialsProvider;
import org.apache.jackrabbit.util.Base64;
import org.apache.jackrabbit.webdav.DavConstants;

/**
 * Implementation of {@link CredentialsProvider} for authentication in webdav
 * repository server.
 * @author Emmanuel Nollase - emanux
 * created 2009 9 29 - 12:50:58
 */
public class JcrWebdavCredentials implements CredentialsProvider
{
    public JcrWebdavCredentials()
    {
	
    }

    /*
     * (non-Javadoc)
     * @see org.apache.jackrabbit.server.CredentialsProvider#getCredentials(javax.servlet.http.HttpServletRequest)
     */
    public Credentials getCredentials(HttpServletRequest request)
	    throws LoginException, ServletException
    {
	try
	{
	String authHeader = request.getHeader(DavConstants.HEADER_AUTHORIZATION);
        if (authHeader != null) 
        {
            String[] authStr = authHeader.split(" ");
            if (authStr.length >= 2 && authStr[0].equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) 
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
		Base64.decode(authStr[1].toCharArray(), out);
                String decAuthStr = out.toString("ISO-8859-1");
                int pos = decAuthStr.indexOf(':');
                String userid = decAuthStr.substring(0, pos);
                String passwd = decAuthStr.substring(pos + 1);
                
                // FIXME - authenticate here in DB or other authentication method
                
                return new SimpleCredentials(userid, passwd.toCharArray());
            }
           throw new ServletException("Unable to decode authorization.");
        }
        
	} catch (IOException e)
	{
	    throw new ServletException("Unable to decode authorization: " + e.toString());
	}
	throw new ServletException("Unable to authorize");
    }

}
