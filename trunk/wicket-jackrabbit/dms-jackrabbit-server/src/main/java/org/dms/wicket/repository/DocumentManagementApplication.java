package org.dms.wicket.repository;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.http.HttpServletRequest;

import org.apache.jackrabbit.rmi.remote.RemoteRepository;
import org.apache.jackrabbit.rmi.server.ServerAdapterFactory;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadWebRequest;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.dms.wicket.component.ContentWebRequestCycle;
import org.dms.wicket.component.ThreadLocalSessionFactory;
import org.dms.wicket.repository.page.IndexPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xaloon.wicket.component.mounting.PageAnnotationScannerContainer;
import org.xaloon.wicket.component.repository.RepositoryManager;

public class DocumentManagementApplication extends WebApplication
{

    @Autowired
    private RepositoryManager repositoryManager;

    @Autowired
    private PageAnnotationScannerContainer annotationScannerContainer;

    @Override
    public Class<? extends Page> getHomePage()
    {
	return IndexPage.class;
    }

    public DocumentManagementApplication()
    {
    }

    @Override
    protected void init()
    {
	super.init();
	repositoryManager.init();
	initRMI();
	getResourceSettings().setThrowExceptionOnMissingResource(false);

	getMarkupSettings().setCompressWhitespace(true);
	getMarkupSettings().setStripComments(true);
	getMarkupSettings().setStripWicketTags(true);
	getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
	getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
	addComponentInstantiationListener(new SpringComponentInjector(this));

	WebApplicationContext context = WebApplicationContextUtils
		.getRequiredWebApplicationContext(this.getServletContext());
	annotationScannerContainer.scan(this, context);
    }

    @Override
    protected void onDestroy()
    {
	repositoryManager.shutdown();
	super.onDestroy();
    }

    @Override
    public RequestCycle newRequestCycle(Request request, Response response)
    {
	return new ContentWebRequestCycle(this, (WebRequest) request, response)
	{
	    @Override
	    protected ThreadLocalSessionFactory getContentSessionFactory()
	    {
		return repositoryManager.getContentSessionFactory();
	    }
	};
    }

    @Override
    protected WebRequest newWebRequest(HttpServletRequest servletRequest)
    {
	return new UploadWebRequest(servletRequest);
    }

    public ThreadLocalSessionFactory getContentSessionFactory()
    {
	return repositoryManager.getContentSessionFactory();
    }

    public static DocumentManagementApplication get()
    {
	return (DocumentManagementApplication) WebApplication.get();
    }

    private void initRMI()
    {
	try
	{
	    ServerAdapterFactory factory = new ServerAdapterFactory();
	    RemoteRepository remote = factory.getRemoteRepository(repositoryManager.getRespository());
	    Registry reg = LocateRegistry.createRegistry(1100);
	    reg.rebind("jackrabbit", remote);
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
}
