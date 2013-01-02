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

import java.util.Collection;
import java.util.Date;
import de.inovex.jmom.test.PseudoPrimitiveTestClass;
import de.inovex.jmom.test.PlainTestClass;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageTest extends AbstractStorageTest {
	
	/**
	 * Test if storing several objects and then reloading them, will result
	 * in the same (equals == true) objects.
	 */
	@Test
	public void testFindAll() {
		
		PlainTestClass tc1_1 = new PlainTestClass(42);
		PlainTestClass tc1_2 = new PlainTestClass(66);
		storage.save(tc1_1);
		storage.save(tc1_2);
		
		List<PlainTestClass> expectedList = new ArrayList<PlainTestClass>(2);
		expectedList.add(tc1_1);
		expectedList.add(tc1_2);
		
		assertEquals(expectedList, storage.findAll(PlainTestClass.class));
		
	}
	
	public void testReference() {
		
		PlainTestClass ptc = new PlainTestClass(42);
		
		storage.save(ptc);
		
		assertEquals(ptc, storage.findFirst(PlainTestClass.class));
		
	}
	
	/**
	 * Tests if writing two times the same object, will also result in the
	 * database only having one object, what should be the expected behavior.
	 * That object must just be equal, to the one saved into the database,
	 * but not be the same.
	 */
	@Test
	public void testMultipleWriteIdentity() {
		
		PlainTestClass ptc = new PlainTestClass(42);
		
		storage.save(ptc);
		storage.save(ptc);
		
		Collection<PlainTestClass> list = storage.findAll(PlainTestClass.class);
		
		Collection<PlainTestClass> expectedResult = new ArrayList<PlainTestClass>(1);
		expectedResult.add(ptc);
		
		assertEquals("Saving two times the same object, didn't result in one database object.",
				expectedResult, list);
		
	}
	
	/**
	 * Test saving and reading back a filled {@link PseudoPrimitiveTestClass}.
	 */
	@Test
	public void testPseudoPrimitiveFullClassInsertRead() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		Date d = Calendar.getInstance().getTime();
		
		obj.setBoolClassVal(Boolean.TRUE);
		obj.setBoolVal(true);
		obj.setByteClassVal((byte)50);
		obj.setByteVal((byte)50);
		obj.setCharClassVal('1');
		obj.setCharVal('2');
		obj.setDateVal(d);
		obj.setDoubleClassVal(42.42);
		obj.setDoubleVal(42.42);
		obj.setFloatClassVal(42.42f);
		obj.setFloatVal(42.42f);
		obj.setIntClassVal(42);
		obj.setIntVal(42);
		obj.setLongClassVal(1287342123123L);
		obj.setLongVal(42);
		obj.setShortClassVal((short)100);
		obj.setShortVal((short)100);
		obj.setStringVal("Douglas Adams");
		
		storage.save(obj);
		
		Collection<PseudoPrimitiveTestClass> findAll = storage.findAll(PseudoPrimitiveTestClass.class);
		assertEquals("findAll didn't return the right amount of objects", 1, findAll.size());
		
		assertTrue(findAll.contains(obj));
		
	}
	
}
