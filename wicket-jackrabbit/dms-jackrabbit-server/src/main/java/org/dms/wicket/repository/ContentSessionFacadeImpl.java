package org.dms.wicket.repository;

import javax.jcr.Session;

import org.dms.wicket.component.ContentSessionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContentSessionFacadeImpl implements ContentSessionFacade {

    	@Autowired DocumentManagementApplication application;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Session getDefaultSession() {
		return application.getContentSessionFactory().getDefaultSession();
	}

}
