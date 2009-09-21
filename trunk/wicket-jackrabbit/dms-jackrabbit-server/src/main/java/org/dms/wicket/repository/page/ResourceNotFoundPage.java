package org.dms.wicket.repository.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.xaloon.wicket.component.mounting.MountPage;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 21 - 18:59:34
 */
@MountPage(path="/resourceNotFound")
public class ResourceNotFoundPage extends WebPage
{
    public ResourceNotFoundPage()
    {
	add(new Label("msg","The resource that you are trying to view was not found"));
    }

}

