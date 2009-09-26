/**
 * 
 */
package org.dms.repository.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dms.wicket.repository.file.service.JcrFileMetadata;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xaloon.wicket.component.exception.FileStorageException;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 26 - 11:01:55
 */
public class JcrFileUploadServlet extends HttpServlet
{

    private static final String UPLOAD_PATH = "photo/upload/test/";
    
   
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException
    {
	// add connection closed header
        resp.setHeader("Connection","close");
        PrintWriter out = resp.getWriter();
        String jcrDir = req.getParameter("jcrDir");
	if(StringUtils.isBlank(jcrDir))
	{
	    jcrDir = UPLOAD_PATH;
	}
        
	final JcrFileMetadata jcrFile = (JcrFileMetadata) getApplicationContext().getBean("fileMetaData");
	final FileItemFactory factory = new DiskFileItemFactory();
	final ServletFileUpload upload = new ServletFileUpload(factory);
	//upload.setFileSizeMax(10400000); // 10MB
	
	InputStream ins = null;
	try
	{
	    List<FileItem> items = upload.parseRequest(req);
	    
	    for (FileItem item : items)
	    {
		DiskFileItem diskitem = (DiskFileItem) item;
		if( !item.isFormField() )
		{
		    final String mimeType = item.getContentType();
		    final String name = item.getName();
		    
		    ins = diskitem.getInputStream();
		    
		    jcrFile.storeFileVersion(jcrDir, name, mimeType, ins);
		    
		    out.write("RESP.100");
		    out.flush();
		}
	    }
	    
	} 
	catch (FileUploadException e)
	{
	    out.print("RESP.200");
	    out.flush();
	    e.printStackTrace();
	}
	catch(FileStorageException e)
	{
	    out.print("RESP.200");
	    out.flush();
	    e.printStackTrace();
	}
	finally
	{
	    IOUtils.closeQuietly(ins);
	}
    }

    public WebApplicationContext getApplicationContext()
    {
	return WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
    }

    
    
}
