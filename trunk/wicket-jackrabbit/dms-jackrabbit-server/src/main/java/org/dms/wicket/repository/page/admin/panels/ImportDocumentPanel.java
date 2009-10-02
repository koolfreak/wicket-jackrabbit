package org.dms.wicket.repository.page.admin.panels;

import java.io.IOException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.dms.wicket.repository.file.service.JcrFileMetadata;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * 
 * @author Emmanuel Nollase - emanux
 * created 2009 9 30 - 11:12:39
 */
public class ImportDocumentPanel extends Panel
{

    @SpringBean private JcrFileMetadata jcrFileMetadata;
    
    private String filePath;
    
    public ImportDocumentPanel(String id)
    {
	super(id);
	
	final FeedbackPanel feedback = new FeedbackPanel("feedlabel");
	add(feedback.setOutputMarkupId(true));
	
	final Form<Void> form = new ImportForm("importform");
	add(form);
	

	/*final IndicatingAjaxButton importDocs = new IndicatingAjaxButton("import",form)
	{
	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form)
	    {
		try
		{
		    jcrFileMetadata.importXML("");
		    feedback.info("Successfully imported");
		    
		} catch (FileStorageException e)
		{
		    e.printStackTrace();
		   // feedback.error(e.getMessage());
		}
		target.addComponent(feedback);
	    }
	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form)
	    {
		target.addComponent(feedback);
		super.onError(target, form);
	    }
	};
	form.add(importDocs);*/
    }

    public class ImportForm extends Form<Void>
    {
	private FileUploadField fileUploadField;

	public ImportForm(String id)
	{
	    super(id);
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
			jcrFileMetadata.importXML(upload.getInputStream());
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
}

