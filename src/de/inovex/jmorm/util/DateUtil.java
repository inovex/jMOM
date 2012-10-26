/*
 * Copyright 2012 Tim Roes <tim.roes@inovex.de>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.inovex.jmorm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class DateUtil {
	
	private static final String[] DATE_FORMATS = new String[] {
		"EEE MMM dd yyyy HH:mm:ss zzz",
		"EEE MMM dd HH:mm:ss zzz yyyy",
		"mm/dd/yyyy",
		"dd.mm.yyyy"
	};
	
	/**
	 * Forbid instantiation and subclassing of this class.
	 */
	private DateUtil() { }
	
	public static Date parseString(String dateString) {
		
		Date date = null;
		DateFormat formater;
		
		for(String format : DATE_FORMATS) {
			formater = new SimpleDateFormat(format);
			
			try {
				date = formater.parse(dateString);
			} catch (ParseException ex) {
				continue;
			}
			
		}
			
		return date;
		
	}
	
}
