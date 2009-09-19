/**
 * 
 */
package org.xaloon.wicket.component.repository.util;

import javax.jcr.RepositoryException;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Emmanuel Nollase - emanux
 * created 2009 9 19 - 15:53:22
 */
public class JcrNodeEventListener implements EventListener
{

   // @Autowired private ContentSessionFacade facade;
    
    private static final Log log = LogFactory.getLog(JcrNodeEventListener.class);
    
    /* (non-Javadoc)
     * @see javax.jcr.observation.EventListener#onEvent(javax.jcr.observation.EventIterator)
     */
    public void onEvent(EventIterator events)
    {
	while (events.hasNext())
	{
	    
	    try
	    {
		Event event =  events.nextEvent();
		String	eventPath = event.getPath();
		int eventType = event.getType();
		if (eventType == Event.NODE_ADDED) {
			String nodePath = eventPath
					.substring(1, eventPath.length());
			log.info(nodePath);
		}
	    } 
	    catch (RepositoryException e)
	    {
		e.printStackTrace();
	    }
		
	    
	}

    }

}
