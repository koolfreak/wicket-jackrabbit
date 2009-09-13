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
package org.xaloon.wicket.component.captcha;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * http://www.xaloon.org
 * 
 * @author vytautas racelis
 */
public class ReCaptchaValidator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(ReCaptchaValidator.class);

	public static boolean validate(String privateKey, String remoteAddress, String response, String challenge) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://api-verify.recaptcha.net/verify");
		post.addParameter("privatekey", privateKey);

		post.addParameter("remoteip", remoteAddress);
		post.addParameter("challenge", challenge);
		post.addParameter("response", response);
		try {
			int code = client.executeMethod(post);
			if (code != HttpStatus.SC_OK) {
				throw new RuntimeException("Could not send request: " + post.getStatusLine());
			}
			String resp = readString(post.getResponseBodyAsStream());
			if (resp.startsWith("false")) {
				return false;
			}
		} catch (Exception e) {
			log.error(e);
		}

		return true;
	}

	private static String readString(InputStream is) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
		} catch (Exception e) {
			log.error(e);
		}
		return sb.toString();
	}
}
