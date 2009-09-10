package org.dms.wicket.page;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.dms.wicket.page.model.CustomFileDescription;
import org.dms.wicket.page.service.search.FileSearchService;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 5 - 22:07:45
 */
public class SearchPage extends WebPage
{
    @SpringBean private FileSearchService fileSearchService;
    
    private String search;
    
    public SearchPage()
    {
	
	final Form<Void> form = new Form<Void>("search");
	add(form);
	
	final TextField<String> query = new TextField<String>("query", new PropertyModel<String>(this, "search") );
	form.add(query);
	
	List<CustomFileDescription> results = null;
	final WebMarkupContainer resultcontainer = new WebMarkupContainer("resultcontainer");
	add(resultcontainer.setOutputMarkupId(true));
	
	final ListView<CustomFileDescription> files = new ListView<CustomFileDescription>("files", results)
	{
	    @Override
	    protected void populateItem(ListItem<CustomFileDescription> item)
	    {
		final CustomFileDescription fileDesc = item.getModelObject();
		item.setModel(new CompoundPropertyModel<CustomFileDescription>(fileDesc));
		item.add(new Label("name"));
		item.add(new Label("lastModified"));
	    }
	};
	//files.setReuseItems(true);
	resultcontainer.add(files);
	
	final IndicatingAjaxButton buttquery = new IndicatingAjaxButton("submit", form)
	{
	    
	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form)
	    {
		final List<CustomFileDescription> results = fileSearchService.search(query.getDefaultModelObjectAsString());
		files.setDefaultModelObject(results);
		target.addComponent(resultcontainer);
	    }
	};
	form.add(buttquery);
    }

    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }

    
}

