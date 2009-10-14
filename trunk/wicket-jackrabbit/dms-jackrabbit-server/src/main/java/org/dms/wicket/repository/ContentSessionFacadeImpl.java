package org.dms.wicket.repository;

import javax.jcr.Session;

import org.dms.wicket.component.ContentSessionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xaloon.wicket.component.repository.RepositoryManager;

@Component
public class ContentSessionFacadeImpl implements ContentSessionFacade {

    	@Autowired RepositoryManager repositoryManager;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Session getDefaultSession() {
		return repositoryManager.getContentSessionFactory().getDefaultSession();
	}

}
