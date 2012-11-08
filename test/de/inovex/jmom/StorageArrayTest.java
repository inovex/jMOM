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

import de.inovex.jmom.test.ArrayTestClass;
import de.inovex.jmom.test.PlainTestClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class test saving arrays. It test primitive arrays and boxed primitive arrays.
 * It test only reading arrays that has been written the same way to the database.
 *
 * See {@link StorageToArrayTest} for tests, about reading non array types into arrays.
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageArrayTest extends AbstractStorageTest {
	
	@Test
	public void testByteArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setByteArray(new byte[]{ 123, 12 });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testByteClassArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setByteClassArray(new Byte[]{ 123, 12 });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testShortArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setShortArray(new short[]{ 123, 12, 42 });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testShortClassArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setShortClassArray(new Short[]{ 123, 12 });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testIntArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setIntArray(new int[]{ 123, 12, 42 });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testIntClassArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setIntClassArray(new Integer[]{ 123, 12 });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testLongArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setLongArray(new long[]{ 123L, 12L });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}

	@Test
	public void testLongClassArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setLongClassArray(new Long[]{ 123L, 12L });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testFloatArray() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setFloatArray(new float[]{ 42.123f, 123.12f });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);	
		
	}

	@Test
	public void testFloatClassArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setFloatClassArray(new Float[]{ 42.123f, 123.12f });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testDoubleArray() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setDoubleArray(new double[]{ 42.123, 123.12 });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);	
		
	}
	
	@Test
	public void testDoubleClassArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setDoubleClassArray(new Double[]{ 123.123, 12.12 });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testCharArray() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setCharArray(new char[]{ 'x', 'y', 'z' });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testCharClassArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setCharClassArray(new Character[]{ 'x', 'y', 'z' });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}

	@Test
	public void testStringArrays() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setStringArray(new String[]{ "Test1", "Test2" });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testReferenceArray() {
		
		ArrayTestClass atc = new ArrayTestClass();
		atc.setReferenceArray(new PlainTestClass[]{ 
			new PlainTestClass(42),
			new PlainTestClass(66)
		});
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertEquals(atc, readAtc);
		
	}
	
	@Test
	public void testReferenceArrayIdentity() {
		
		ArrayTestClass atc = new ArrayTestClass();
		PlainTestClass ptc = new PlainTestClass(42);
		atc.setReferenceArray(new PlainTestClass[]{ ptc, ptc });
		
		storage.save(atc);
		
		ArrayTestClass readAtc = storage.findOne(ArrayTestClass.class);
		assertSame(readAtc.getReferenceArray()[0], readAtc.getReferenceArray()[1]);
		
	}
	
	
}
