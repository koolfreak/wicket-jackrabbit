/**
 * 
 */
package org.dms.wicket.component;

import java.io.IOException;
import java.io.InputStream;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.dms.wicket.repository.db.model.FileDescription;
import org.xaloon.wicket.component.repository.FileRepository;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 8 29 - 00:35:36
 */
public class JcrDowloadLink extends Link<Void>
{

    @SpringBean private FileRepository fileRepository;
    private FileDescription fileDescription;

    public JcrDowloadLink(String id, FileDescription fileDescription)
    {
	super(id);
	this.fileDescription = fileDescription;
    }

    @Override
    public void onClick()
    {
	IResourceStream stream = new AbstractResourceStream()
	{
	    InputStream in;

	    public InputStream getInputStream()
		    throws ResourceStreamNotFoundException
	    {
		final String pathToFile = fileDescription.getFilePath();
		try
		{
		    in = fileRepository.retrieveFile(pathToFile);
		} catch (PathNotFoundException e)
		{
		    e.printStackTrace();
		} catch (ValueFormatException e)
		{
		    e.printStackTrace();
		} catch (RepositoryException e)
		{
		    e.printStackTrace();
		}
		return in;
	    }

	    public void close() throws IOException
	    {
		in.close();
	    }

	    public String getContentType()
	    {
		return fileDescription.getMimeType();
	    }
	};
	
	getRequestCycle().setRequestTarget(
		new ResourceStreamRequestTarget(stream).setFileName(fileDescription.getName()));

    }
}
