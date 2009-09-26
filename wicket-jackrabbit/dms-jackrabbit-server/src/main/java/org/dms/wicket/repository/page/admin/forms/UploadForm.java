/**
 * 
 */
package org.dms.wicket.repository.page.admin.forms;

import java.io.IOException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.dms.wicket.repository.file.service.JcrFileMetadata;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 25 - 14:55:28
 */
public class UploadForm extends Form<Void>
{
    @SpringBean private JcrFileMetadata jcrFileMetadata;
    
    private FileUploadField fileUploadField;
    private String path;

    public UploadForm(String id,String path)
    {
	super(id);
	this.path = path;
	setMultiPart(true);
	// Add one file input field
	add(fileUploadField = new FileUploadField("fileInput"));
	setMaxSize(Bytes.megabytes(10));
    }

    @Override
    protected void onSubmit()
    {
	final FileUpload upload = fileUploadField.getFileUpload();
	try {
		if (upload != null) {
		    
		    jcrFileMetadata.storeFile(path, upload.getClientFileName(), upload.getContentType(), upload.getInputStream());
		}
	} catch (IOException e) {
	    error(e.getMessage());
	    e.printStackTrace();
	} catch(FileStorageException e) {
	    error(e.getMessage());
	    e.printStackTrace();
	}
    }
}
