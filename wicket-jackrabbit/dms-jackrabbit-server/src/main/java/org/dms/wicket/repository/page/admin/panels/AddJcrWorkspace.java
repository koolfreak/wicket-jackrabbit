package org.dms.wicket.repository.page.admin.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.dms.wicket.repository.admin.JcrAdminService;

/**
 * 
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 17:20:53
 */
public class AddJcrWorkspace extends Panel
{

    @SpringBean private JcrAdminService jcrAdminService;
    
    private String wsName;
    
    public AddJcrWorkspace(String id)
    {
	super(id);
	
	final FeedbackPanel feed = new FeedbackPanel("feedback");
	add(feed.setOutputMarkupId(true));
	
	final Form<Void> form = new Form<Void>("form");
	add(form);

	final RequiredTextField<String> wscomp = 
	    new RequiredTextField<String>("wsname", new PropertyModel<String>(this, "wsName"));
	form.add(wscomp);

	final IndicatingAjaxButton submit = new IndicatingAjaxButton("submit", form)
	{
	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form)
	    {
		boolean ws = jcrAdminService.crateJcrWorkspace(getWsName());
		if(ws)
		{
		    feed.info("Workspace: "+getWsName()+" was successfully created.");
		}
		else
		{
		    feed.error("Workspace: "+getWsName()+" creation failed.");
		}
		target.addComponent(feed);
		//jcrAdminService.exportSystemView();
	    }
	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form)
	    {
		target.addComponent(feed);
		super.onError(target, form);
	    }
	};
	form.add(submit);
    }
    
    public String getWsName()
    {
	return wsName;
    }

    public void setWsName(String wsName)
    {
	this.wsName = wsName;
    }
}

