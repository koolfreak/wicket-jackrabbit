/**
 * 
 */
package org.dms.wicket.repository.remote;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 8 29 - 19:20:18
 */
public class DMSSessionFactory
{

    /** the remote repository object **/
    private Repository repository;
    
    /** the remote repository credentials **/
    private Credentials credentials;

    public void setRepository(Repository repository)
    {
        this.repository = repository;
    }

    public void setCredentials(Credentials credentials)
    {
        this.credentials = credentials;
    }

    public Repository getRepository()
    {
        return repository;
    }

    public Credentials getCredentials()
    {
        return credentials;
    }
    
    /**
     * @return - remote repository session
     */
    public Session getJcrSession()
    {
	try
	{
	    return repository.login(getCredentials());
	} 
	catch (LoginException e)
	{
	    e.printStackTrace();
	} catch (RepositoryException e)
	{
	    e.printStackTrace();
	}
	throw new IllegalArgumentException("JcrSession must not be null");
    }
}
