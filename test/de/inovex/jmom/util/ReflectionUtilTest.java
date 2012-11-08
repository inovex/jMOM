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
package de.inovex.jmom.util;

import de.inovex.jmom.util.ReflectionUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class ReflectionUtilTest {
	
	public ReflectionUtilTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testGetBoxedTypeInteger() {
		assertEquals(Integer.class, ReflectionUtil.getBoxedType(Integer.TYPE));
	}
	
	@Test
	public void testGetBoxedTypeBoolean() {
		assertEquals(Boolean.class, ReflectionUtil.getBoxedType(Boolean.TYPE));
	}
	
	@Test
	public void testGetBoxedTypeDouble() {
		assertEquals(Double.class, ReflectionUtil.getBoxedType(Double.TYPE));
	}	

	@Test
	public void testGetBoxedTypeFloat() {
		assertEquals(Float.class, ReflectionUtil.getBoxedType(Float.TYPE));
	}
	
	@Test
	public void testGetBoxedTypeShort() {
		assertEquals(Short.class, ReflectionUtil.getBoxedType(Short.TYPE));
	}	
	
	@Test
	public void testGetBoxedTypeByte() {
		assertEquals(Byte.class, ReflectionUtil.getBoxedType(Byte.TYPE));
	}
	
	@Test
	public void testGetBoxedTypeChar() {
		assertEquals(Character.class, ReflectionUtil.getBoxedType(Character.TYPE));
	}
	
	@Test
	public void testGetBoxedTypeLong() {
		assertEquals(Long.class, ReflectionUtil.getBoxedType(Long.TYPE));
	}
	
	/**
	 * Any non primitive datatype should be returned as is
	 */
	@Test
	public void testGetBoxedTypeNonPrimitive() {
		assertEquals(String.class, ReflectionUtil.getBoxedType(String.class));
	}
		
}
