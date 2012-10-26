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
import com.mongodb.DBRef;
import de.inovex.jmorm.test.ArrayTestClass;
import de.inovex.jmorm.test.PlainTestClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageToArrayTest extends AbstractStorageTest {
	
	@Test
	public void testByteArray() {
		
		DBObject dbobj = new BasicDBObject("byteArray", 42);
		storage.saveDBObject(dbobj, ArrayTestClass.class.getCanonicalName());
		
		ArrayTestClass atc = storage.findOne(ArrayTestClass.class);
		assertArrayEquals(new byte[]{ 42 }, atc.getByteArray());
		
	}
	
	@Test
	public void testShortArray() {
		
		DBObject dbobj = new BasicDBObject("shortArray", 42);
		storage.saveDBObject(dbobj, ArrayTestClass.class.getCanonicalName());
		
		ArrayTestClass atc = storage.findOne(ArrayTestClass.class);
		assertArrayEquals(new short[]{ 42 }, atc.getShortArray());
		
	}	
	
	@Test
	public void testIntArray() {
	
		DBObject dbobj = new BasicDBObject("intArray", 42);
		
		storage.saveDBObject(dbobj, ArrayTestClass.class.getCanonicalName());
		
		ArrayTestClass atc = storage.findOne(ArrayTestClass.class);
		assertArrayEquals(new int[]{ 42 }, atc.getIntArray());
		
	}
	
	@Test
	public void testLongArray() {
	
		DBObject dbobj = new BasicDBObject("longArray", 42);
		
		storage.saveDBObject(dbobj, ArrayTestClass.class.getCanonicalName());
		
		ArrayTestClass atc = storage.findOne(ArrayTestClass.class);
		assertArrayEquals(new long[]{ 42 }, atc.getLongArray());
		
	}
	
	@Test
	public void testFloatArray() {
	
		DBObject dbobj = new BasicDBObject("floatArray", 42.12f);
		
		storage.saveDBObject(dbobj, ArrayTestClass.class.getCanonicalName());
		
		ArrayTestClass atc = storage.findOne(ArrayTestClass.class);
		assertArrayEquals(new float[]{ 42.12f }, atc.getFloatArray(), 1E-10f);
		
	}
	
	@Test
	public void testDoubleArray() {
	
		DBObject dbobj = new BasicDBObject("doubleArray", 42.12);
		
		storage.saveDBObject(dbobj, ArrayTestClass.class.getCanonicalName());
		
		ArrayTestClass atc = storage.findOne(ArrayTestClass.class);
		assertArrayEquals(new double[]{ 42.12 }, atc.getDoubleArray(), 1E-10);
		
	}
	
	@Test
	public void testCharArray() {
	
		DBObject dbobj = new BasicDBObject("charArray", 'x');
		
		storage.saveDBObject(dbobj, ArrayTestClass.class.getCanonicalName());
		
		ArrayTestClass atc = storage.findOne(ArrayTestClass.class);
		assertArrayEquals(new char[]{ 'x' }, atc.getCharArray());
		
	}
	
	@Test
	public void testStringArray() {
	
		DBObject dbobj = new BasicDBObject("stringArray", "test");
		
		storage.saveDBObject(dbobj, ArrayTestClass.class.getCanonicalName());
		
		ArrayTestClass atc = storage.findOne(ArrayTestClass.class);
		assertArrayEquals(new String[]{ "test" }, atc.getStringArray());
		
	}
	
	@Test
	public void testReferenceArray() {
	
		PlainTestClass ptc = new PlainTestClass(42);
		
		DBObject dbobj = storage.saveObject(ptc);
		
		DBRef ref = storage.createRef(dbobj, PlainTestClass.class);
		
		DBObject dbobj2 = new BasicDBObject("referenceArray", ref);
		
		storage.saveDBObject(dbobj2, ArrayTestClass.class.getCanonicalName());
		
		ArrayTestClass atc = storage.findOne(ArrayTestClass.class);
		assertArrayEquals(new PlainTestClass[]{ ptc }, atc.getReferenceArray());
		
	}
	
	
}
