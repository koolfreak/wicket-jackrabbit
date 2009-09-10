/**
 * 
 */
package org.dms.component.service;

import javax.jcr.Session;


/**
 * @author Emmanuel Nollase - emanux
 * created 2009 8 29 - 19:26:09
 */
public class DMSTemplateImpl implements DMSSessionTemplate
{

    private Session session;
    
    public DMSTemplateImpl(DMSSessionFactory sessionFactory)
    {
	this.session = sessionFactory.getJcrSession();
    }

    public Session getDefaultSession()
    {
        return session;
    }

}
