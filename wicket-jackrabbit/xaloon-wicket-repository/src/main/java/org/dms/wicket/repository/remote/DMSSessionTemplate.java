/**
 * 
 */
package org.dms.wicket.repository.remote;

import javax.jcr.Session;


/**
 * @author Emmanuel Nollase - emanux
 * created 2009 8 29 - 19:24:45
 */
public interface DMSSessionTemplate
{

    Session getDefaultSession();
    
}
