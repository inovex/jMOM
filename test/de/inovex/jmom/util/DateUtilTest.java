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
package de.inovex.jmom.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.junit.Before;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class DateUtilTest {
	
	private Date testDate;
	
	@Before
	public void setupDate() {
		Calendar c = Calendar.getInstance();
		c.set(2012, 11, 21, 11, 42, 6);
		c.set(Calendar.MILLISECOND, 0);
		testDate = c.getTime();
	}
	
	private void testDateFormat(String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(testDate);
		Date parsedDate = DateUtil.parseString(dateString);
		if(parsedDate == null) {
			fail("Date in format " + format + " could not be parsed.");
		}
		// We need to compare the string representations against each others, since
		// the date hold informations, that might not have been in the parsed string 
		// representation. Thats why the parsed date might now be equal to our
		// testDate, but to test, if it will hold all the information, that has been
		// in the string representation, we check the string representation.
		assertEquals("Test format: " + format, dateString, formatter.format(parsedDate));
	}
	
	@Test
	public void testToStringFormat() {
		String dateString = testDate.toString();
		assertEquals("Java default Date.toString() format.", testDate, DateUtil.parseString(dateString));
	}
	
	@Test
	public void testMMDDYYYY() {
		testDateFormat("mm/dd/yyyy");
	}
	
	@Test
	public void testDDMMYYYY() {
		testDateFormat("dd.mm.yyyy");
	}
	
	
}
