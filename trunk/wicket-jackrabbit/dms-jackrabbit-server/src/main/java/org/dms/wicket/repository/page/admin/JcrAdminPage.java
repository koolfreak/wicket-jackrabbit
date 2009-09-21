package org.dms.wicket.repository.page.admin;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.dms.wicket.repository.admin.JcrAdminService;
import org.dms.wicket.repository.db.model.FileDescription;
import org.dms.wicket.repository.db.service.JcrFileDescription;
import org.dms.wicket.repository.file.service.JcrFileMetadata;
import org.dms.wicket.repository.page.JcrMainPage;
import org.xaloon.wicket.component.mounting.MountPage;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 9 14 - 12:59:35
 * 
 */
@MountPage(path="/admin")
public class JcrAdminPage extends JcrMainPage
{
    
    @SpringBean private JcrAdminService jcrAdminService;
    @SpringBean private JcrFileMetadata jcrFileMetadata;
    @SpringBean private JcrFileDescription jcrFileDescription;
    
    public JcrAdminPage()
    {
	
	add(new Link<Void>("runGC")
	{
	    @Override
	    public void onClick()
	    {
		jcrAdminService.runGC();
	    }
	});
	
	final ListView<FileDescription> lists = new ListView<FileDescription>("files",jcrFileDescription.loadAll())
	{
	    @Override
	    protected void populateItem(ListItem<FileDescription> item)
	    {
		final FileDescription fileDesc = item.getModelObject();
		item.setModel(new CompoundPropertyModel<FileDescription>(fileDesc));
		item.add(new Label("name"));
		item.add(new Label("lastModified"));
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
		    }
		});
	    }
	};
	add(lists);
    }

   

}
