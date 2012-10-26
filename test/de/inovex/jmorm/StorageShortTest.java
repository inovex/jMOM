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

import org.junit.Before;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import de.inovex.jmorm.exception.NumericException;
import de.inovex.jmorm.test.PseudoPrimitiveTestClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageShortTest extends AbstractStorageTest {
	
	private Config config;
	
	@Before
	public void createConfig() {
		config = new Config();
	}
	
	@Test
	public void testShortMinimum() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setShortVal(Short.MIN_VALUE);
		obj.setShortClassVal(Short.MIN_VALUE);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("short field doesn't match.", Short.MIN_VALUE, res.getShortVal());
		assertEquals("Short field doesn't match.", Short.MIN_VALUE, (Object)res.getShortClassVal());
		
	}
	
	@Test
	public void testShortMaximum() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setShortVal(Short.MAX_VALUE);
		obj.setShortClassVal(Short.MAX_VALUE);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("short field doesn't match.", Short.MAX_VALUE, res.getShortVal());
		assertEquals("Short field doesn't match.", Short.MAX_VALUE, (Object)res.getShortClassVal());
		
	}
	
	@Test(expected=NumericException.class)
	public void testShortAboveMaximumException() {	
		testOverflowBehavior(Config.NumericBehavior.EXCEPTION, Short.MAX_VALUE + 1, null);		
		fail("Expected NumericException didn't occur.");
	}
	
	@Test
	public void testShortAboveMaximumOverflow() {
		testOverflowBehavior(Config.NumericBehavior.OVERFLOW, Short.MAX_VALUE + 1, 
				Short.valueOf((short)(Short.MAX_VALUE + 1)));
	}
	
	@Test
	public void testShortAboveMaximumZero() {
		testOverflowBehavior(Config.NumericBehavior.ZERO_VALUE, Short.MAX_VALUE + 1, 
				Short.valueOf((short)0));
	}
	
	private void testOverflowBehavior(Config.NumericBehavior numericBehavior, Object valueToSave, Object expectedValue) {
		
		config.setNumericBehavior(numericBehavior);
		storage.setConfig(config);
		
		DBObject dbobj = new BasicDBObject("shortVal", valueToSave);
		dbobj.put("shortClassVal", valueToSave);
		
		storage.saveDBObject(dbobj, PseudoPrimitiveTestClass.class.getCanonicalName());
	
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("short field doesn't match.", expectedValue, res.getShortVal());
		assertEquals("Short field doesn't match.", expectedValue, res.getShortClassVal());
		
	}
	
}
