package org.dms.wicket.repository;

import javax.jcr.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xaloon.wicket.component.repository.ContentSessionFacade;

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
