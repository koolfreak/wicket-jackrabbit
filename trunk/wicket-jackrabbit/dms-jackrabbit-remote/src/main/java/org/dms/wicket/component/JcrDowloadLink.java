/**
 * 
 */
package org.dms.wicket.component;

import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.dms.component.file.FileDescription;
import org.dms.component.file.StoreFileRepository;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 8 29 - 00:35:36
 */
public class JcrDowloadLink extends Link<Void>
{

    @SpringBean  private StoreFileRepository fileRepository;
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
		in = fileRepository.retrieveFile(pathToFile);
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
