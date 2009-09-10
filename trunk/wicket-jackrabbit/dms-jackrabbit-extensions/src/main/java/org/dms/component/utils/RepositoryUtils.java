/**
 * 
 */
package org.dms.component.utils;

import java.util.StringTokenizer;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 9 4 - 17:10:56
 */
public final class RepositoryUtils
{

    public static synchronized Node createFolder(Session session, String path,
	    Node rootNode) throws Exception
    {
	StringTokenizer stringTokenizer = new StringTokenizer(path, "/");
	Node result = rootNode;
	while (stringTokenizer.hasMoreTokens())
	{
	    String nodeStr = stringTokenizer.nextToken();
	    if (result.hasNode(nodeStr))
	    {
		result = result.getNode(nodeStr);
	    } else
	    {
		result = result.addNode(nodeStr);
	    }
	}
	session.save();
	return result;
    }
    
    public static String getFileName(String nodePath)
    {
	final String nstr = StringUtils.left(nodePath, nodePath.lastIndexOf("/"));
	return StringUtils.substring(nstr, nstr.lastIndexOf("/") + 1);
    }

    private RepositoryUtils()
    {
    }
}
