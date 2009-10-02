package org.dms.wicket.repository.page.admin.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.dms.wicket.repository.file.service.JcrFileMetadata;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 30 - 16:28:54
 */
public class CreateNodesPanel extends Panel
{

    @SpringBean private JcrFileMetadata jcrFileMetadata;
    private String nodes;
    
    public CreateNodesPanel(String id)
    {
	super(id);
	
	final Form<Void> form = new Form<Void>("nodeform");
	add(form);
	
	final RequiredTextField<String> nodecomp = new RequiredTextField<String>("node", new PropertyModel<String>(this, "nodes"));
	form.add(nodecomp);
	
	final IndicatingAjaxButton createNode = new IndicatingAjaxButton("nodebutt", form)
	{
	    
	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form)
	    {
		try
		{
		    jcrFileMetadata.createRepositoryNodes(getNodes());
		} catch (FileStorageException e)
		{
		    e.printStackTrace();
		}
	    }
	};
	form.add(createNode);
    }

    public String getNodes()
    {
        return nodes;
    }

    public void setNodes(String nodes)
    {
        this.nodes = nodes;
    }

    
}

