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
import de.inovex.jmorm.exception.NumericException;
import de.inovex.jmorm.test.PseudoPrimitiveTestClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests the behavior of numeric overflows. If the database holds a numeric
 * value, that is too large for the data type of the field, the {@link Config#getNumericBehavior()}
 * defines, how the program should react. This class tests the different options
 * for their correct working.
 * 
 * @see Config.NumericBehavior
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageNumericOverflowTest extends AbstractStorageTest {
	
	private Config config;
	
	@Before
	public void createConfig() {
		config = new Config();
	}
	
	private PseudoPrimitiveTestClass testOverflowBehavior(String dataType, Config.NumericBehavior numericBehavior, Object valueToSave) {
		
		config.setNumericBehavior(numericBehavior);
		storage.setConfig(config);
		
		DBObject dbobj = new BasicDBObject(dataType + "Val", valueToSave);
		dbobj.put(dataType + "ClassVal", valueToSave);
		
		storage.saveDBObject(dbobj, PseudoPrimitiveTestClass.class.getCanonicalName());
	
		return storage.findOne(PseudoPrimitiveTestClass.class);
		
	}

	/**
	 * If {@link Config#getNumericBehavior()} is set to {@link Config.NumericBehavior#EXCEPTION}
	 * a {@link NumericException} should be thrown, when the value is too large for the field.
	 */
	@Test(expected=NumericException.class)
	public void testByteException() {
		testOverflowBehavior("byte", Config.NumericBehavior.EXCEPTION, Byte.MAX_VALUE + 1);
		fail("byte overflow didn't throw an exception.");
	}

	/**
	 * If {@link Config#getNumericBehavior()} is set to {@link Config.NumericBehavior#EXCEPTION}
	 * a {@link NumericException} should be thrown, when the value is too large for the field.
	 */
	@Test(expected=NumericException.class)
	public void testShortException() {
		testOverflowBehavior("short", Config.NumericBehavior.EXCEPTION, Short.MAX_VALUE + 1);
		fail("short overflow didn't throw an exception.");
	}
	
	/**
	 * If {@link Config#getNumericBehavior()} is set to {@link Config.NumericBehavior#EXCEPTION}
	 * a {@link NumericException} should be thrown, when the value is too large for the field.
	 */
	@Test(expected=NumericException.class)
	public void testIntException() {
		testOverflowBehavior("int", Config.NumericBehavior.EXCEPTION, ((long)Integer.MAX_VALUE) + 1);
		fail("int overflow didn't throw an exception.");
	}
	
	/**
	 * If {@link Config#getNumericBehavior()} is set to {@link Config.NumericBehavior#OVERFLOW}
	 * the value should be casted regardless of the overflow.
	 */
	@Test
	public void testByteOverflow() {
		PseudoPrimitiveTestClass res = testOverflowBehavior("byte", Config.NumericBehavior.OVERFLOW, 
				Byte.MAX_VALUE + 1);
		assertEquals("byte overflow failed.", (byte)(Byte.MAX_VALUE + 1), res.getByteVal());
		assertEquals("Byte overflow failed.", (byte)(Byte.MAX_VALUE + 1), (Object)res.getByteClassVal());
	}
	
	/**
	 * If {@link Config#getNumericBehavior()} is set to {@link Config.NumericBehavior#OVERFLOW}
	 * the value should be casted regardless of the overflow.
	 */
	@Test
	public void testShortOverflow() {
		PseudoPrimitiveTestClass res = testOverflowBehavior("short", Config.NumericBehavior.OVERFLOW, 
				Short.MAX_VALUE + 1);
		assertEquals("short overflow failed.", (short)(Short.MAX_VALUE + 1), res.getShortVal());
		assertEquals("Short overflow failed.", (short)(Short.MAX_VALUE + 1), (Object)res.getShortClassVal());
	}
	
	/**
	 * If {@link Config#getNumericBehavior()} is set to {@link Config.NumericBehavior#OVERFLOW}
	 * the value should be casted regardless of the overflow.
	 */
	@Test
	public void testIntOverflow() {
		PseudoPrimitiveTestClass res = testOverflowBehavior("int", Config.NumericBehavior.OVERFLOW, 
				(long)Integer.MAX_VALUE + 1);
		assertEquals("int overflow failed.", (int)((long)Integer.MAX_VALUE) + 1, res.getIntVal());
		assertEquals("Integer overflow failed.", (int)((long)Integer.MAX_VALUE) + 1, (Object)res.getIntClassVal());
	}
	
	/**
	 * If {@link Config#getNumericBehavior()} is set to {@link Config.NumericBehavior#ZERO_VALUE}
	 * the field should get its default value (0).
	 */
	@Test
	public void testByteZero() {
		PseudoPrimitiveTestClass res = testOverflowBehavior("byte", Config.NumericBehavior.ZERO_VALUE, Byte.MAX_VALUE + 1);
		assertEquals("byte overflow wasn't zero", (byte)0, res.getByteVal());
		assertEquals("Byte overflow wasn't zero", (byte)0, (Object)res.getByteClassVal());
	}
	
	/**
	 * If {@link Config#getNumericBehavior()} is set to {@link Config.NumericBehavior#ZERO_VALUE}
	 * the field should get its default value (0).
	 */
	@Test
	public void testShortZero() {
		PseudoPrimitiveTestClass res = testOverflowBehavior("short", Config.NumericBehavior.ZERO_VALUE, Short.MAX_VALUE + 1);
		assertEquals("short overflow wasn't zero", (short)0, res.getShortVal());
		assertEquals("Short overflow wasn't zero", (short)0, (Object)res.getShortClassVal());
	}
	
	/**
	 * If {@link Config#getNumericBehavior()} is set to {@link Config.NumericBehavior#ZERO_VALUE}
	 * the field should get its default value (0).
	 */
	@Test
	public void testIntZero() {
		PseudoPrimitiveTestClass res = testOverflowBehavior("int", Config.NumericBehavior.ZERO_VALUE, (long)Integer.MAX_VALUE + 1);
		assertEquals("int overflow wasn't zero", 0, res.getIntVal());
		assertEquals("Integer overflow wasn't zero", 0, (Object)res.getIntClassVal());
	}
	
}
