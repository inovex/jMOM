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

import de.inovex.jmom.test.PseudoPrimitiveTestClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests the regular numeric saving. It tries to save the maximum,
 * minimum value of each datatype and zero.
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageNumericTest extends AbstractStorageTest {
	
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
	
	@Test
	public void testByteZero() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setByteVal((byte)0);
		obj.setByteClassVal((byte)0);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("byte field doesn't match.", (byte)0, res.getByteVal());
		assertEquals("Byte field doesn't match.", (byte)0, (Object)res.getByteClassVal());
		
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
	
	@Test
	public void testShortZero() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setShortVal((short)0);
		obj.setShortClassVal((short)0);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("short field doesn't match.", (short)0, res.getShortVal());
		assertEquals("Short field doesn't match.", (short)0, (Object)res.getShortClassVal());
		
	}
	
	@Test
	public void testIntMinimum() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setIntVal(Integer.MIN_VALUE);
		obj.setIntClassVal(Integer.MIN_VALUE);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("int field doesn't match.", Integer.MIN_VALUE, res.getIntVal());
		assertEquals("Int field doesn't match.", Integer.MIN_VALUE, (Object)res.getIntClassVal());
		
	}
	
	@Test
	public void testIntMaximum() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setIntVal(Integer.MAX_VALUE);
		obj.setIntClassVal(Integer.MAX_VALUE);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("int field doesn't match.", Integer.MAX_VALUE, res.getIntVal());
		assertEquals("Int field doesn't match.", Integer.MAX_VALUE, (Object)res.getIntClassVal());
		
	}
	
	@Test
	public void testIntZero() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setIntVal(0);
		obj.setIntClassVal(0);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("int field doesn't match.", 0, res.getIntVal());
		assertEquals("Int field doesn't match.", 0, (Object)res.getIntClassVal());
		
	}
	
	@Test
	public void testLongMinimum() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setLongVal(Long.MIN_VALUE);
		obj.setLongClassVal(Long.MIN_VALUE);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("long field doesn't match.", Long.MIN_VALUE, res.getLongVal());
		assertEquals("Long field doesn't match.", Long.MIN_VALUE, (Object)res.getLongClassVal());
		
	}
	
	@Test
	public void testLongMaximum() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setLongVal(Long.MAX_VALUE);
		obj.setLongClassVal(Long.MAX_VALUE);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("long field doesn't match.", Long.MAX_VALUE, res.getLongVal());
		assertEquals("Long field doesn't match.", Long.MAX_VALUE, (Object)res.getLongClassVal());
		
	}
	
	@Test
	public void testLongZero() {
		
		PseudoPrimitiveTestClass obj = new PseudoPrimitiveTestClass();
		
		obj.setLongVal((long)0);
		obj.setLongClassVal((long)0);
		
		storage.save(obj);
		
		PseudoPrimitiveTestClass res = storage.findOne(PseudoPrimitiveTestClass.class);
		assertEquals("long field doesn't match.", (long)0, res.getLongVal());
		assertEquals("Long field doesn't match.", (long)0, (Object)res.getLongClassVal());
		
	}
	
	
}
