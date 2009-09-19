package org.dms.wicket.repository.page.admin;

import java.io.IOException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.dms.wicket.repository.db.model.FileDescription;
import org.dms.wicket.repository.db.model.FileVersion;
import org.dms.wicket.repository.db.service.JcrFileMetadata;
import org.dms.wicket.repository.page.JcrMainPage;
import org.xaloon.wicket.component.exception.FileStorageException;
import org.xaloon.wicket.component.mounting.MountPage;

@MountPage(path="/version")
public class JcrVersionPage extends JcrMainPage
{

    @SpringBean private JcrFileMetadata jcrFileMetadata;
    
    public JcrVersionPage(FileDescription file)
    {
	final Form<Void> form = new FileUploadForm<Void>("form",file);
	add(form);
	
	final ListView<FileVersion> versions = new ListView<FileVersion>("versions",jcrFileMetadata.getFileVersions(file.getFilePath()))
	{
	    
	    @Override
	    protected void populateItem(ListItem<FileVersion> item)
	    {
		final FileVersion vers = item.getModelObject();
		item.setModel(new CompoundPropertyModel<FileVersion>(vers));
		item.add(new Label("fileVersion"));
		item.add(new Label("lastModified"));
	    }
	};
	add(versions);
    }
    
    private class FileUploadForm<Void> extends Form<Void>
    {
	private FileUploadField fileUploadField;
	private FileDescription file;

	public FileUploadForm(String id, FileDescription file)
	{
	    super(id);
	    this.file = file;
	    setMultiPart(true);

	    // Add one file input field
	    add(fileUploadField = new FileUploadField("fileInput"));

	    setMaxSize(Bytes.megabytes(10));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void onSubmit()
	{
	    final FileUpload upload = fileUploadField.getFileUpload();
	    try
	    {
		if (upload != null)
		{
		    // TODO [eman] - check the fileName if EQUAL ???
		   jcrFileMetadata.storeNextVersion(file, upload.getInputStream());
		}
	    } catch (IOException e)
	    {
		e.printStackTrace();
	    } catch (FileStorageException e)
	    {
		e.printStackTrace();
	    }

	    setResponsePage(new JcrVersionPage(file));
	}
    }
}

