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

import java.util.AbstractSet;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import de.inovex.jmorm.test.CollectionTestClass;
import de.inovex.jmorm.test.PlainTestClass;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests saving different collection types.
 * It tests only reading collections, that has been saved as collections to the database.
 * 
 * // TODO SEE
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageCollectionTest extends AbstractStorageTest {
	
	@Test
	public void testByteList() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		List<Byte> list = new ArrayList<Byte>();
		list.add(Byte.MIN_VALUE);
		list.add(Byte.MAX_VALUE);
		ctc.setByteList(list);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testShortList() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		ArrayList<Short> list = new ArrayList<Short>();
		list.add(Short.MAX_VALUE);
		list.add(Short.MIN_VALUE);
		ctc.setShortList(list);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testIntegerList() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(Integer.MIN_VALUE);
		list.add(Integer.MAX_VALUE);
		ctc.setIntList(list);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testLongList() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		List<Long> list = new ArrayList<Long>();
		list.add(Long.MIN_VALUE);
		list.add(Long.MAX_VALUE);
		ctc.setLongList(list);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testFloatList() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		ArrayList<Float> list = new ArrayList<Float>();
		list.add(Float.MIN_VALUE);
		list.add(Float.MAX_VALUE);
		ctc.setFloatList(list);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testDoubleList() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		LinkedList<Double> list = new LinkedList<Double>();
		list.add(Double.MIN_VALUE);
		list.add(Double.MAX_VALUE);
		ctc.setDoubleList(list);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testCharList() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		List<Character> list = new ArrayList<Character>();
		list.add(Character.MIN_VALUE);
		list.add(Character.MAX_VALUE);
		ctc.setCharList(list);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testStringList() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		ArrayList<String> list = new ArrayList<String>();
		list.add("TestString");
		list.add("Another String");
		ctc.setStringList(list);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testReferenceList() {
		
		CollectionTestClass ctc = new CollectionTestClass();
		List<PlainTestClass> list = new ArrayList<PlainTestClass>();
		list.add(new PlainTestClass(42));
		list.add(new PlainTestClass(66));
		ctc.setReferenceList(list);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testReferenceListIdentity() {
		
		CollectionTestClass ctc = new CollectionTestClass();
		List<PlainTestClass> list = new ArrayList<PlainTestClass>();
		PlainTestClass ptc = new PlainTestClass(42);
		list.add(ptc);
		list.add(ptc);
		ctc.setReferenceList(list);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertSame(readCtc.getReferenceList().get(0), readCtc.getReferenceList().get(1));
			
	}

	@Test
	public void testByteSet() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		Set<Byte> set = new HashSet<Byte>();
		set.add(Byte.MIN_VALUE);
		set.add(Byte.MAX_VALUE);
		ctc.setByteSet(set);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testShortSet() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		SortedSet<Short> set = new TreeSet<Short>();
		set.add(Short.MIN_VALUE);
		set.add(Short.MAX_VALUE);
		ctc.setShortSet(set);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testIntegerSet() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		HashSet<Integer> set = new HashSet<Integer>();
		set.add(Integer.MIN_VALUE);
		set.add(Integer.MAX_VALUE);
		ctc.setIntSet(set);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testLongSet() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		TreeSet<Long> set = new TreeSet<Long>();
		set.add(Long.MIN_VALUE);
		set.add(Long.MAX_VALUE);
		ctc.setLongSet(set);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testFloatSet() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		NavigableSet<Float> set = new TreeSet<Float>();
		set.add(Float.MIN_VALUE);
		set.add(Float.MAX_VALUE);
		ctc.setFloatSet(set);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testDoubleSet() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		AbstractSet<Double> set = new HashSet<Double>();
		set.add(Double.MIN_VALUE);
		set.add(Double.MAX_VALUE);
		ctc.setDoubleSet(set);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testCharacterSet() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		Set<Character> set = new HashSet<Character>();
		set.add(Character.MIN_VALUE);
		set.add(Character.MAX_VALUE);
		ctc.setCharSet(set);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testStringSet() {
				
		CollectionTestClass ctc = new CollectionTestClass();
		SortedSet<String> set = new TreeSet<String>();
		set.add("TestString");
		set.add("AnotherString");
		ctc.setStringSet(set);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
	@Test
	public void testReferenceSet() {

		CollectionTestClass ctc = new CollectionTestClass();
		Set<PlainTestClass> set = new HashSet<PlainTestClass>();
		set.add(new PlainTestClass(42));
		set.add(new PlainTestClass(66));
		set.add(new PlainTestClass(42));
		ctc.setReferenceSet(set);
		
		storage.save(ctc);
		
		CollectionTestClass readCtc = storage.findOne(CollectionTestClass.class);
		assertEquals(ctc, readCtc);
		
	}
	
}
