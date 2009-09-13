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
package org.xaloon.wicket.component.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * http://www.xaloon.org
 *
 * @author vytautas racelis
 */
public final class Formatter {
	public static final String DELIMITER = "/";
	
	private Formatter () {}
	
	public static String formatDate(TimeZone timezone, Date dt, String dateFormatType, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatType, locale);
		if (timezone != null) {
			sdf.setTimeZone(timezone);
		}
		return sdf.format(dt);
	}
	
	public static String formatDateTime(String tzStr, Date date, String dateFormat, Locale locale) {
		TimeZone timezone = TimeZone.getDefault();
		if (!StringUtils.isEmpty(tzStr)) {
			timezone = TimeZone.getTimeZone(tzStr);
		}
		return Formatter.formatDate(timezone, date, dateFormat, locale);
	}

	public static String prepareStringForHTML(String value) {
		if (!StringUtils.isEmpty(value)) {
			return value.replaceAll("\n", "<br/>");
		}
		return value;
	}

	public static int formatInteger(Object object) {
		if (object != null) {
			Long result = (Long)object;
			if (result != null) {
				return result.intValue();
			}
		}
		return 0;
	}

	public static String fixUrl(String context, String path) {
		if (context.endsWith(DELIMITER) && path.startsWith(DELIMITER)) {
			return context + path.substring(1);
		} else if (!context.endsWith(DELIMITER) && !path.startsWith(DELIMITER)) {
			return context + DELIMITER + path;
		} else {
			return context + path;
		}
	}
}
