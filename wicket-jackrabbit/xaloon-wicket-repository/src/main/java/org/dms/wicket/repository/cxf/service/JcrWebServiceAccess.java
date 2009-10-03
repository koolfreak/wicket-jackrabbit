/**
 * 
 */
package org.dms.wicket.repository.cxf.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.dms.wicket.repository.db.model.FileDescription;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 10 4 - 01:03:22
 */
@WebService
public interface JcrWebServiceAccess
{
    @WebMethod
    void save(FileDescription file);
    
    @WebMethod
    void delete(FileDescription file);
    
    @WebMethod
    void update(FileDescription file);

    @WebMethod
    FileDescription loadByUUID(String uuid);
}
