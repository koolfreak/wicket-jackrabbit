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
package org.dms.wicket.perf.test;

import junit.framework.Test;

import org.dms.wicket.perf.DMSParentTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.xaloon.wicket.component.repository.FileRepository;
import org.xaloon.wicket.component.repository.RepositoryManager;

import com.clarkware.junitperf.LoadTest;
import com.clarkware.junitperf.TimedTest;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class RepositoryTestCase extends DMSParentTestCase
{
    @Autowired
    private RepositoryManager repositoryManager;

    @Autowired
    private FileRepository fileRepository;

    @Override
    protected synchronized void onSetUp() throws Exception
    {
	super.onSetUp();
	repositoryManager.init();
    }

    @Override
    protected void onTearDown() throws Exception
    {
	super.onTearDown();
	repositoryManager.shutdown();
    }

    public Test items()
    {
	int maxUsers = 50;
	long maxElapsedTime = 35000;

	Test testCase = new SingleFileUploadTestCase("testFileUpload",
		repositoryManager, fileRepository);
	Test timedTest = new TimedTest(testCase, maxElapsedTime);
	Test loadTest = new LoadTest(timedTest, maxUsers);

	return loadTest;
    }

    public void testFileUpload() throws Exception
    {
	junit.textui.TestRunner.run(items());
    }
}
