package org.dms.wicket.repository.page.admin;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.dms.wicket.repository.db.model.FileDescription;
import org.dms.wicket.repository.db.service.JcrFileDescription;
import org.dms.wicket.repository.file.service.JcrFileMetadata;
import org.dms.wicket.repository.page.IndexPage;
import org.dms.wicket.repository.page.JcrMainPage;
import org.dms.wicket.repository.page.admin.forms.UploadVersionForm;
import org.dms.wicket.repository.page.admin.panels.CreateNodesPanel;
import org.dms.wicket.repository.page.admin.panels.ExportDocumentPanel;
import org.dms.wicket.repository.page.admin.panels.ImportDocumentPanel;
import org.xaloon.wicket.component.mounting.MountPage;
import org.xaloon.wicket.component.resource.FileResource;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 9 14 - 12:59:35
 * 
 */
@MountPage(path="/admin")
public class JcrAdminPage extends JcrMainPage
{
    
    @SpringBean private JcrFileMetadata jcrFileMetadata;
    @SpringBean private JcrFileDescription jcrFileDescription;
    
    public JcrAdminPage()
    {
	
	
	final ExportDocumentPanel exportDocs = new ExportDocumentPanel("exportDocs");
	add(exportDocs);
	
	final ImportDocumentPanel importDocs = new ImportDocumentPanel("importDocs");
	add(importDocs);
	
	
	final FeedbackPanel feed = new FeedbackPanel("feedback");
	final UploadVersionForm form = new UploadVersionForm("upversion", "photo/upload/test/");
	add(form);
	form.add(feed);
	
	final WebMarkupContainer imageContainer = new WebMarkupContainer("imageContainer");
	imageContainer.setOutputMarkupId(true);
	add(imageContainer);
	imageContainer.add(new NonCachingImage("img", new ResourceReference(IndexPage.class, "images/testimonial-bg.gif")));
	
	final ListView<FileDescription> lists = new ListView<FileDescription>("files",jcrFileDescription.loadAll(0, 10))
	{
	    @Override
	    protected void populateItem(ListItem<FileDescription> item)
	    {
		final FileDescription fileDesc = item.getModelObject();
		item.setModel(new CompoundPropertyModel<FileDescription>(fileDesc));
		item.add(new Label("name"));
		item.add(new  Label("lastModified"));
		item.add(new Link<FileDescription>("addVersion", item.getModel() )
		{
		    @Override
		    public void onClick()
		    {
			setResponsePage(new JcrVersionPage(getModelObject()));
		    }
		});
		item.add(new Link<FileDescription>("delete",item.getModel())
		{
		    @Override
		    public void onClick()
		    {
			jcrFileMetadata.deleteFile(getModelObject());
			setRedirect(true);
			setResponsePage(JcrAdminPage.class);
		    }
		});
		final AjaxFallbackLink<Void> preview = new AjaxFallbackLink<Void>("show")
		{
		    @Override
		    public void onClick(AjaxRequestTarget target)
		    {
			imageContainer.removeAll();
			imageContainer.add(new NonCachingImage("img", new FileResource(fileDesc.getFilePath())));
			target.addComponent(imageContainer);
		    }
		};
		item.add(preview);
	    }
	};
	add(lists);
    }

   

}
