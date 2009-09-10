package org.dms.wicket.repository.page;

import org.apache.wicket.PageParameters;
import org.dms.wicket.repository.panel.RepositoryPanel;
import org.xaloon.wicket.component.mounting.MountPage;

@MountPage(path="/index")
public class IndexPage extends JcrMainPage {

	public IndexPage(PageParameters params) {
		super(params);
		add (new RepositoryPanel("repository", params));
	}
}
