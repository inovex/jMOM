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

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import de.inovex.jmom.test.CollectionTestClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageToCollectionTest extends AbstractStorageTest {
	
	@Test
	public void testByteList() {
		
		DBObject dbobj = new BasicDBObject("byteList", 42);
		storage.saveDBObject(dbobj, CollectionTestClass.class.getCanonicalName());
		
		CollectionTestClass ctc = getSingleResult(storage.findAll(CollectionTestClass.class));
		List<Byte> list = new ArrayList<Byte>();
		list.add((byte)42);
		assertEquals(list, ctc.getByteList());
		
	}
	
	@Test
	public void testShortList() {
		
		DBObject dbobj = new BasicDBObject("shortList", 42);
		storage.saveDBObject(dbobj, CollectionTestClass.class.getCanonicalName());
		
		CollectionTestClass ctc = getSingleResult(storage.findAll(CollectionTestClass.class));
		List<Short> list = new ArrayList<Short>();
		list.add((short)42);
		assertEquals(list, ctc.getShortList());
		
	}
	
	@Test
	public void testIntegerList() {
		
		DBObject dbobj = new BasicDBObject("intList", 42);
		storage.saveDBObject(dbobj, CollectionTestClass.class.getCanonicalName());
		
		CollectionTestClass ctc = getSingleResult(storage.findAll(CollectionTestClass.class));
		List<Integer> list = new ArrayList<Integer>();
		list.add(42);
		assertEquals(list, ctc.getIntList());
		
	}
	
	@Test
	public void testLongList() {
		
		DBObject dbobj = new BasicDBObject("longList", 42);
		storage.saveDBObject(dbobj, CollectionTestClass.class.getCanonicalName());
		
		CollectionTestClass ctc = getSingleResult(storage.findAll(CollectionTestClass.class));
		List<Long> list = new ArrayList<Long>();
		list.add(42l);
		assertEquals(list, ctc.getLongList());
		
	}
	
	@Test
	public void testFloatList() {
		
		DBObject dbobj = new BasicDBObject("floatList", 42.21);
		storage.saveDBObject(dbobj, CollectionTestClass.class.getCanonicalName());
		
		CollectionTestClass ctc = getSingleResult(storage.findAll(CollectionTestClass.class));
		List<Float> list = new ArrayList<Float>();
		list.add(42.21f);
		assertEquals(list, ctc.getFloatList());
		
	}
	
	@Test
	public void testDoubleList() {
		
		DBObject dbobj = new BasicDBObject("doubleList", 42.21);
		storage.saveDBObject(dbobj, CollectionTestClass.class.getCanonicalName());
		
		CollectionTestClass ctc = getSingleResult(storage.findAll(CollectionTestClass.class));
		List<Double> list = new ArrayList<Double>();
		list.add(42.21);
		assertEquals(list, ctc.getDoubleList());
		
	}
	
	@Test
	public void testCharacterList() {
		
		DBObject dbobj = new BasicDBObject("charList", 'x');
		storage.saveDBObject(dbobj, CollectionTestClass.class.getCanonicalName());
		
		CollectionTestClass ctc = getSingleResult(storage.findAll(CollectionTestClass.class));
		List<Character> list = new ArrayList<Character>();
		list.add('x');
		assertEquals(list, ctc.getCharList());
		
	}
	
	@Test
	public void testStringList() {
		
		DBObject dbobj = new BasicDBObject("stringList", "test");
		storage.saveDBObject(dbobj, CollectionTestClass.class.getCanonicalName());
		
		CollectionTestClass ctc = getSingleResult(storage.findAll(CollectionTestClass.class));
		List<String> list = new ArrayList<String>();
		list.add("test");
		assertEquals(list, ctc.getStringList());
		
	}
	
	@Test
	public void testStringSet() {
		
		DBObject dbobj = new BasicDBObject("stringSet", "test");
		storage.saveDBObject(dbobj, CollectionTestClass.class.getCanonicalName());
		
		CollectionTestClass ctc = getSingleResult(storage.findAll(CollectionTestClass.class));
		Set<String> set = new HashSet<String>();
		set.add("test");
		assertEquals(set, ctc.getStringSet());
		
	}
	
}
