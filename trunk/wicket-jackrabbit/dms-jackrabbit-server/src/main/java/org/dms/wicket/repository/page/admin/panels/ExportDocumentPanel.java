package org.dms.wicket.repository.page.admin.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.dms.wicket.repository.file.service.JcrFileMetadata;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 30 - 10:15:43
 */
public class ExportDocumentPanel extends Panel
{
    
    @SpringBean private JcrFileMetadata jcrFileMetadata;
    
    private String exportPath;
    private String filePath;
    
    public ExportDocumentPanel(String id)
    {
	super(id);
	
	final FeedbackPanel feedback = new FeedbackPanel("feedlabel");
	add(feedback.setOutputMarkupId(true));
	
	final Form<Void> form = new Form<Void>("exportform");
	add(form);
	
	final RequiredTextField<String> jcrpath = new RequiredTextField<String>("exportPath", new PropertyModel<String>(this, "exportPath"));
	form.add(jcrpath);
	
	final RequiredTextField<String> pathTofile = new RequiredTextField<String>("pathTofile", new PropertyModel<String>(this, "filePath"));
	form.add(pathTofile);
	
	final IndicatingAjaxButton export = new IndicatingAjaxButton("export",form)
	{
	    
	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form)
	    {
		
		try
		{
		    jcrFileMetadata.exportSystemView(getExportPath(), getFilePath());
		    feedback.info("Successfully exported");
		    
		} catch (FileStorageException e)
		{
		    feedback.error(e.getMessage());
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
	form.add(export);
    }

    public String getExportPath()
    {
        return exportPath;
    }

    public void setExportPath(String exportPath)
    {
        this.exportPath = exportPath;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    
}

