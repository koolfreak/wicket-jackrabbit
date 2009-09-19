/**
 * 
 */
package org.dms.wicket.repository.admin;

import java.io.File;
import java.io.FileOutputStream;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.core.WorkspaceImpl;
import org.dms.wicket.component.ContentSessionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xaloon.wicket.component.exception.FileStorageException;
import org.xaloon.wicket.component.repository.util.RepositoryHelper;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 9 14 - 12:33:15
 */
@Component
public class JcrAdminServiceImpl implements JcrAdminService
{
    private static final Log log = LogFactory.getLog(JcrAdminServiceImpl.class);

    @Autowired
    private ContentSessionFacade contentSessionFacade;

    /*
     * (non-Javadoc)
     * 
     * @see org.dms.wicket.jcr.admin.JcrAdminService#crateJcrWorkspace()
     */
    public boolean crateJcrWorkspace(String wsname) throws FileStorageException
    {
	boolean _wscreated = false;
	final WorkspaceImpl ws = (WorkspaceImpl) contentSessionFacade.getDefaultSession().getWorkspace();
	try
	{
	    ws.createWorkspace(wsname);
	    log.debug("worspace is created with name " + wsname);
	    _wscreated = true;
	} 
	catch (Exception e)
	{
	    throw new FileStorageException("Error creating workspace", e);
	}
	return _wscreated;
    }

    public boolean renameNode(String path,String newName) throws FileStorageException
    {
	boolean _renamed = false;
	try
	{
	    Node rootnode = contentSessionFacade.getDefaultSession().getRootNode();
	    if(rootnode.hasNode(path)) {
		return false;    
	    }
	    Node node = rootnode.getNode(path);
	    renameNode(node, newName);
	    _renamed = true;
	} 
	catch (RepositoryException e)
	{
	    throw new FileStorageException("Error renaming the node", e);
	}
	return _renamed;
    }

    public void renameNode(Node node, String newName) throws RepositoryException
    {
	node.getSession().move(node.getPath(),node.getParent().getPath() + "/" + newName);
    }

    public void exportDocumentView(String path,String exportFile) throws FileStorageException
    {
	try
	{
	    final File file = new File(exportFile);
	    final FileOutputStream out = new FileOutputStream(file);
	    contentSessionFacade.getDefaultSession().exportDocumentView(path, out, true, false);
	} 
	catch (Exception e)
	{
	    throw new FileStorageException("Error exporting node with path:"+path, e);
	} 
    }
    
    public void createRepositoryNodes(String path) throws FileStorageException
    {
	try
	{
	    final Session session = contentSessionFacade.getDefaultSession();
	    final Node rootNode = contentSessionFacade.getDefaultSession().getRootNode();
	    
	    if( !rootNode.hasNode(path) )
	    {
		Node createNode = RepositoryHelper.createFolder(session, path, rootNode);
		createNode.save();
	    }
	} 
	catch (Exception e)
	{
	    throw new FileStorageException("Error getting repository node", e);
	}
    }
}
