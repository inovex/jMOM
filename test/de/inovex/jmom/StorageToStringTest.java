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
package de.inovex.jmom;

import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import de.inovex.jmom.test.PseudoPrimitiveTestClass;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class test the parsing of non {@link String} values into a {@code String}
 * field.
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageToStringTest extends AbstractStorageTest {
	
	private PseudoPrimitiveTestClass saveObject(Object value) {
		
		DBObject dbobj = new BasicDBObject();
		dbobj.put("stringVal", value);
		storage.saveDBObject(dbobj, PseudoPrimitiveTestClass.class.getCanonicalName());
		
		return getSingleResult(storage.findAll(PseudoPrimitiveTestClass.class));
		
	}
	
	@Test
	public void testIntToString() {
		PseudoPrimitiveTestClass obj = saveObject(42);
		assertEquals("42", obj.getStringVal());
	}
	
	@Test
	public void testDoubleToString() {
		PseudoPrimitiveTestClass obj = saveObject(4.213);
		assertEquals("4.213", obj.getStringVal());
	}
	
	@Test
	public void testCharToString() {
		PseudoPrimitiveTestClass obj = saveObject('x');
		assertEquals("x", obj.getStringVal());
	}
	
	@Test
	public void testDateToString() {
		Date now = Calendar.getInstance().getTime();
		PseudoPrimitiveTestClass obj = saveObject(now);
		assertEquals(now.toString(), obj.getStringVal());
	}
	
}
