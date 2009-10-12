/**
 * 
 */
package org.dms.wicket.perf;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * @author Emmanuel Nollase - emanux 
 * created 2009 10 10 - 08:30:37
 */
public class DMSParentTestCase extends
	AbstractDependencyInjectionSpringContextTests
{

    protected String[] getConfigLocations()
    {
	return new String[]
	    { "classpath*:META-INF/application-test-context.xm"};
    }

}
