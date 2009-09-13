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
package org.xaloon.wicket.component.comment;

import java.io.Serializable;
import java.util.Date;

/**
 * Interface is used to identify comment persitable entity. 
 * 
 * e.g., public class Comment implements Commentable {...}
 *
 * @author vytautas racelis
 */
public interface AbstractComment extends Serializable {
	
	Long getId ();
	
	/**
	 * 
	 * @return object create date
	 */
	Date getCreateDate();
	void setCreateDate(Date createDate);	
	
	/**
	 * Who wrote the comment
	 * 
	 * @return autohor's id
	 */
	Long getAuthorId ();
	void setAuthorId (Long authorId);
	
	/**
	 * This is object id which comment was written on
	 * 
	 * @return object id
	 */
	Long getObjectId ();
	void setObjectId (Long objectId);
	
	/**
	 * Several systems may have the same object id's. This is to identify unique system
	 * 
	 * @return component enumeration
	 */
	Long getComponent ();
	void setComponent (Long component);
	
	/**
	 * 
	 * @return who wrote comment - full name
	 */
	String getName ();
	void setName (String name);
	
	/**
	 * 
	 * @return message
	 */
	String getMessage ();
	void setMessage (String message);
	
	/**
	 * 
	 * @return web site of comment owner
	 */
	String getWebsite ();
	void setWebsite (String website);
	
	/**
	 * 
	 * @return email of comment owner
	 */
	String getEmail ();
	void setEmail (String email);
}
