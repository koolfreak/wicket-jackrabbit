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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.jcr.Repository;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.io.Streams;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class ContentHelper {
	
	public static Repository createRepository(String url) {
		try {
			final File home = new File(url);
			mkdirs(home);

			File cfg = new File(home, "repository.xml");
			if (!cfg.exists()) {
				copyClassResourceToFile("/org/dms/wicket/repository/config/repository.xml", cfg);
			}

			InputStream configStream = new FileInputStream(cfg);
			RepositoryConfig config = RepositoryConfig.create(configStream, home.getAbsolutePath());
			return RepositoryImpl.create(config);
		} catch (Exception e) {
			throw new RuntimeException("Could not create repository: " + url, e);
		}
	}

	private static void mkdirs(File file) {
		if (!file.exists()) {
			if (!file.mkdirs()) {
				throw new RuntimeException("Could not create directory: " + file.getAbsolutePath());
			}
		}
	}

	private static void copyClassResourceToFile(String source, File destination) {
		final InputStream in = ContentHelper.class.getResourceAsStream(source);
		if (in == null) {
			throw new RuntimeException("Class resource: " + source + " does not exist");
		}

		try {
			final FileOutputStream fos = new FileOutputStream(destination);
			Streams.copy(in, fos);
			fos.close();
			in.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not copy class resource: " + source + " to destination: " + destination.getAbsolutePath());
		}
	}
}
