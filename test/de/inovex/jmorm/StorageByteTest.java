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
public class StorageByteTest extends AbstractStorageTest {
	
	private Config config;
	
	@Before
	public void createConfig() {
		config = new Config();
	}
	
	@Test
	public void testByteMinimum() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setByteVal(Byte.MIN_VALUE);
		obj.setByteClassVal(Byte.MIN_VALUE);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("byte field doesn't match.", Byte.MIN_VALUE, res.getByteVal());
		assertEquals("Byte field doesn't match.", Byte.MIN_VALUE, (Object)res.getByteClassVal());
		
	}
	
	@Test
	public void testByteMaximum() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setByteVal(Byte.MAX_VALUE);
		obj.setByteClassVal(Byte.MAX_VALUE);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("byte field doesn't match.", Byte.MAX_VALUE, res.getByteVal());
		assertEquals("Byte field doesn't match.", Byte.MAX_VALUE, (Object)res.getByteClassVal());
		
	}
	
	@Test(expected=NumericException.class)
	public void testByteAboveMaximumException() {	
		testOverflowBehavior(Config.NumericBehavior.EXCEPTION, Byte.MAX_VALUE + 1, null);		
		fail("Expected NumericException didn't occur.");
	}
	
	@Test
	public void testByteAboveMaximumOverflow() {
		testOverflowBehavior(Config.NumericBehavior.OVERFLOW, Byte.MAX_VALUE + 1, 
				Byte.valueOf((byte)(Byte.MAX_VALUE + 1)));
	}
	
	@Test
	public void testByteAboveMaximumZero() {
		testOverflowBehavior(Config.NumericBehavior.ZERO_VALUE, Byte.MAX_VALUE + 1, 
				Byte.valueOf((byte)0));
	}
	
	private void testOverflowBehavior(Config.NumericBehavior numericBehavior, Object valueToSave, Object expectedValue) {
		
		config.setNumericBehavior(numericBehavior);
		storage.setConfig(config);
		
		DBObject dbobj = new BasicDBObject("byteVal", valueToSave);
		dbobj.put("byteClassVal", valueToSave);
		
		storage.saveDBObject(dbobj, PseudoPrimitiveTestClass.class.getCanonicalName());
	
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("byte field doesn't match.", expectedValue, res.getByteVal());
		assertEquals("Byte field doesn't match.", expectedValue, res.getByteClassVal());
		
	}
	
}
