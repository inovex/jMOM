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

import de.inovex.jmom.Config;
import org.junit.Before;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import de.inovex.jmom.exception.CharacterCastException;
import de.inovex.jmom.test.PseudoPrimitiveTestClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageCharTest extends AbstractStorageTest {
	
	private Config config;
	
	@Before
	public void createConfig() {
		this.config = new Config();
	}
	
	private void testConvertBehavior(Config.CharacterBehavior characterBehavior, Object valueToSave, char expectedValue) {
		
		config.setCharacterBehavior(characterBehavior);
		storage.setConfig(config);
		
		DBObject dbobj = new BasicDBObject("charVal", valueToSave);
		dbobj.put("charClassVal", valueToSave);
		
		storage.saveDBObject(dbobj, PseudoPrimitiveTestClass.class.getCanonicalName());
	
		PseudoPrimitiveTestClass res = getSingleResult(storage.findAll(PseudoPrimitiveTestClass.class));
		assertEquals("char field doesn't match.", expectedValue, res.getCharVal());
		assertEquals("Character field doesn't match.", expectedValue, res.getCharClassVal().charValue());
		
	}
	
	@Test
	public void testStringToCharFirst() {
		testConvertBehavior(Config.CharacterBehavior.FIRST_CHAR, "string", 's');
	}
	
	@Test(expected=CharacterCastException.class)
	public void testStringToCharException() {
		testConvertBehavior(Config.CharacterBehavior.EXCEPTION, "string", '\0');
		fail("No exception has been thrown.");
	}
	
	@Test
	public void testStringToCharZero() {
		testConvertBehavior(Config.CharacterBehavior.ZERO_VALUE, "string", (char)0);
	}
	
	@Test
	public void testIntToCharFirst() {
		testConvertBehavior(Config.CharacterBehavior.FIRST_CHAR, 42, '4');
	}
	
	@Test(expected=CharacterCastException.class)
	public void testIntToCharException() {
		testConvertBehavior(Config.CharacterBehavior.EXCEPTION, 42, '\0');
		fail("No exception has been thrown.");
	}
	
	@Test
	public void testIntToCharZero() {
		testConvertBehavior(Config.CharacterBehavior.ZERO_VALUE, 42, (char)0);
	}
	
	private void testChar(char ch) {
		
		DBObject dbobj = new BasicDBObject("charVal", ch);
		dbobj.put("charClassVal", ch);
		
		storage.saveDBObject(dbobj, PseudoPrimitiveTestClass.class.getCanonicalName());
		
		PseudoPrimitiveTestClass res = getSingleResult(storage.findAll(PseudoPrimitiveTestClass.class));
		assertEquals("char field doesn't match.", ch, res.getCharVal());
		assertEquals("Character field doesn't match.", ch, res.getCharClassVal().charValue());
		
	}
	
	@Test
	public void testCharNull() {
		testChar('\u0000');
	}
	
	@Test
	public void testCharHigh() {		
		testChar('\uFFFF');
	}
	
}
