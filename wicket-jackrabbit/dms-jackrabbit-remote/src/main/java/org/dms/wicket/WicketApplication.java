package org.dms.wicket;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadWebRequest;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.dms.wicket.component.ContentWebRequestCycle;
import org.dms.wicket.component.ThreadLocalSessionFactory;
import org.dms.wicket.page.HomePage;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see org.xaloon.StartJcrRemote#main(String[])
 */
public class WicketApplication extends WebApplication
{
    public static final String DMS_REPO_PATH ="sxi/dms/";
    /**
     * Constructor
     */
    
    public WicketApplication()
    {
	
    }

    @Override
    protected void init()
    {
	super.init();
	
	getResourceSettings().setThrowExceptionOnMissingResource(false);

	getMarkupSettings().setCompressWhitespace(true);
	getMarkupSettings().setStripComments(true);
	getMarkupSettings().setStripWicketTags(true);
	getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
	getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
	addComponentInstantiationListener(new SpringComponentInjector(this));

    }


    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class<HomePage> getHomePage()
    {
	return HomePage.class;
    }

    @Override
    protected WebRequest newWebRequest(HttpServletRequest servletRequest)
    {
	return new UploadWebRequest(servletRequest);
    }
    
    public static WicketApplication get()
    {
	return (WicketApplication) WebApplication.get();
    }
    
    
    @Override
    public RequestCycle newRequestCycle(Request request, Response response)
    {
	return new ContentWebRequestCycle(this, (WebRequest) request, response)
	{
	    @Override
	    protected ThreadLocalSessionFactory getContentSessionFactory()
	    {
		return getThreadLocalSessionFactory();
	    }
	};
    }

    
    public final ThreadLocalSessionFactory getThreadLocalSessionFactory()
    {
	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	return (ThreadLocalSessionFactory) ctx.getBean("jcrThreadLocalSessionFactory");
    }

    public String getDmsRepoPath()
    {
	return DMS_REPO_PATH;
    }
}
