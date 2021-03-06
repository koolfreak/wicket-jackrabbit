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

/**
 * Every commentable object should implement this interface
 * 
 * @author vytautas racelis
 */
public interface Commentable extends Serializable {
	/**
	 * 
	 * @return unique inner id of commentable object
	 */
	Long getId ();
	
	/**
	 * Commentable object may have the same id if there are many implementations. Component + id should ensure uniqueness 
	 * 
	 * @return
	 */
	Long getComponent ();
}
