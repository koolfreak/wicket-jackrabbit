/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xaloon.wicket.component.repository.util;

import java.util.StringTokenizer;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class RepositoryHelper
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
}
