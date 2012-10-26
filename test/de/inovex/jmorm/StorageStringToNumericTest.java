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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests filling non String fields with String values from the 
 * database. So the actual value is coded inside a string value in the database.
 * This class only tests successful transformation for valid strings.
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageStringToNumericTest extends AbstractStorageTest {
	
	private PseudoPrimitiveTestClass saveStringNumber(String datatype, Object value) {
		
		DBObject dbobj = new BasicDBObject();
		
		dbobj.put(datatype + "Val", String.valueOf(value));
		dbobj.put(datatype + "ClassVal", String.valueOf(value));
		
		storage.saveDBObject(dbobj, PseudoPrimitiveTestClass.class.getCanonicalName());
		
		return storage.findOne(PseudoPrimitiveTestClass.class);
		
	}
	
	@Test
	public void testStringToByte() {
		PseudoPrimitiveTestClass obj = saveStringNumber("byte", Byte.MIN_VALUE);
		
		assertEquals("byte field doesn't match.", Byte.MIN_VALUE, obj.getByteVal());
		assertEquals("Byte field doesn't match.", Byte.MIN_VALUE, (Object)obj.getByteClassVal());
	}
	
	@Test
	public void testStringToShort() {
		PseudoPrimitiveTestClass obj = saveStringNumber("short", Short.MIN_VALUE);
		
		assertEquals("short field doesn't match.", Short.MIN_VALUE, obj.getShortVal());
		assertEquals("Short field doesn't match.", Short.MIN_VALUE, (Object)obj.getShortClassVal());
	}
	
	@Test
	public void testStringToInt() {
		PseudoPrimitiveTestClass obj = saveStringNumber("int", Integer.MIN_VALUE);
		
		assertEquals("int field doesn't match.", Integer.MIN_VALUE, obj.getIntVal());
		assertEquals("Integer field doesn't match.", Integer.MIN_VALUE, (Object)obj.getIntClassVal());
	}
	
	@Test
	public void testStringToLong() {
		PseudoPrimitiveTestClass obj = saveStringNumber("long", Long.MIN_VALUE);
		
		assertEquals("long field doesn't match.", Long.MIN_VALUE, obj.getLongVal());
		assertEquals("Long field doesn't match.", Long.MIN_VALUE, (Object)obj.getLongClassVal());
	}
	
	@Test
	public void testStringToFloatMaxValue() {
		PseudoPrimitiveTestClass obj = saveStringNumber("float", Float.MAX_VALUE);
		
		assertEquals("float field doesn't match.", Float.MAX_VALUE, obj.getFloatVal(), 1E-10);
		assertEquals("Float field doesn't match.", Float.MAX_VALUE, obj.getFloatClassVal(), 1E-10);
	}
	
	@Test
	public void testStringToFloatNaN() {
		PseudoPrimitiveTestClass obj = saveStringNumber("float", Float.NaN);
		
		assertEquals("float field doesn't match.", Float.NaN, obj.getFloatVal(), 1E-10);
		assertEquals("Float field doesn't match.", Float.NaN, obj.getFloatClassVal(), 1E-10);
	}
	
	@Test
	public void testStringToFloatInf() {
		PseudoPrimitiveTestClass obj = saveStringNumber("float", Float.POSITIVE_INFINITY);
		
		assertEquals("float field doesn't match.", Float.POSITIVE_INFINITY, obj.getFloatVal(), 1E-10);
		assertEquals("Float field doesn't match.", Float.POSITIVE_INFINITY, obj.getFloatClassVal(), 1E-10);
	}
	
	@Test
	public void testStringToFloatNegInf() {
		PseudoPrimitiveTestClass obj = saveStringNumber("float", Float.NEGATIVE_INFINITY);
		
		assertEquals("float field doesn't match.", Float.NEGATIVE_INFINITY, obj.getFloatVal(), 1E-10);
		assertEquals("Float field doesn't match.", Float.NEGATIVE_INFINITY, obj.getFloatClassVal(), 1E-10);
	}
	
	@Test
	public void testStringToDoubleMaxValue() {
		PseudoPrimitiveTestClass obj = saveStringNumber("double", Double.MAX_VALUE);
		
		assertEquals("double field doesn't match.", Double.MAX_VALUE, obj.getDoubleVal(), 1E-10);
		assertEquals("Double field doesn't match.", Double.MAX_VALUE, obj.getDoubleClassVal(), 1E-10);
	}
	
	@Test
	public void testStringToDoubleNaN() {
		PseudoPrimitiveTestClass obj = saveStringNumber("double", Double.NaN);
		
		assertEquals("double field doesn't match.", Double.NaN, obj.getDoubleVal(), 1E-10);
		assertEquals("Double field doesn't match.", Double.NaN, obj.getDoubleClassVal(), 1E-10);
	}
	
	@Test
	public void testStringToDoubleInf() {
		PseudoPrimitiveTestClass obj = saveStringNumber("double", Double.POSITIVE_INFINITY);
		
		assertEquals("double field doesn't match.", Double.POSITIVE_INFINITY, obj.getDoubleVal(), 1E-10);
		assertEquals("Double field doesn't match.", Double.POSITIVE_INFINITY, obj.getDoubleClassVal(), 1E-10);
	}
	
	@Test
	public void testStringToDoubleNegInf() {
		PseudoPrimitiveTestClass obj = saveStringNumber("double", Double.NEGATIVE_INFINITY);
		
		assertEquals("double field doesn't match.", Double.NEGATIVE_INFINITY, obj.getDoubleVal(), 1E-10);
		assertEquals("Double field doesn't match.", Double.NEGATIVE_INFINITY, obj.getDoubleClassVal(), 1E-10);
	}
	
}
