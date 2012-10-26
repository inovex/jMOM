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
package de.inovex.jmorm;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import de.inovex.jmorm.test.PseudoPrimitiveTestClass;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageDateTest extends AbstractStorageTest {
	
	@Test
	public void testStringToDate() {
	
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		
		DBObject dbobj = new BasicDBObject("dateVal", now.toString());
		
		storage.saveDBObject(dbobj, PseudoPrimitiveTestClass.class.getCanonicalName());
	
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		Date newDate = res.getDateVal();
		// Both dates should be equal, if they are within 1 second to each other,
		// since the tostring method won't write the milliseconds, so the object 
		// read from database, won't have any milliseconds.
		assertTrue((newDate.getTime() - now.getTime()) < 1000);
	}
	
}
