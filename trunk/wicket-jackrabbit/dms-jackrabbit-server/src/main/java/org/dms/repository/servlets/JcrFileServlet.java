/**
 * 
 */
package org.dms.repository.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.xaloon.wicket.component.repository.FileRepository;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 9 7 - 16:47:52
 */
public class JcrFileServlet extends JcrRemoteServlet
{

    private static final int DEFAULT_CONTENT_LENGTH_SIZE = 4096;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException
    {
	final Map<String, String> fileAttr = new HashMap<String, String>();
	
	final ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream(DEFAULT_CONTENT_LENGTH_SIZE);
	InputStream imageInputStream = null;
	try {
        	imageInputStream = getImageAsStream(req,fileAttr); 
        	IOUtils.copy(imageInputStream, imageOutputStream);
        	
        	 // flush it in the response
        	resp.setHeader("Cache-Control", "no-store");
        	resp.setHeader("Pragma", "no-cache");
        	resp.setDateHeader("Expires", 0);
        	resp.setContentType(fileAttr.get("mimetype"));
        	//resp.setHeader("Content-Disposition", "attachment; filename=\""+ fileAttr.get("filename") + "\"");
        	
                final ServletOutputStream responseOutputStream =  resp.getOutputStream();
                responseOutputStream.write(imageOutputStream.toByteArray());
                responseOutputStream.flush();
                responseOutputStream.close();
                
	}catch (Exception e) {
	    e.printStackTrace();
	    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
	} finally {
	    IOUtils.closeQuietly(imageInputStream);
	    IOUtils.closeQuietly(imageOutputStream);
	}
	
    }

    public InputStream getImageAsStream(HttpServletRequest req,Map<String, String> fileAttr)
    {
	final String uuid = req.getParameter("uuid");
	if(StringUtils.isEmpty(uuid))
	{
	    throw new IllegalArgumentException("file id must not be empty");
	}
	
	final FileRepository fileRepository = (FileRepository) getApplicationContext().getBean("fileRepository");
	
	return fileRepository.retrieveFileByUUID(uuid,fileAttr);
    }
}
