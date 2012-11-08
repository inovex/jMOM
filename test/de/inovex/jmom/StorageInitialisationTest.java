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

import de.inovex.jmom.Storage.StorageInfo;
import com.mongodb.DB;
import com.mongodb.Mongo;
import java.net.UnknownHostException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageInitialisationTest {
	
	private DB db;
	
	public StorageInitialisationTest() {
	}
	
	@Before
	public void setUp() throws UnknownHostException {
		Mongo mongo = new Mongo();
		db = mongo.getDB("junit");
	}

	@Test
	public void testGetInstanceNotNull() {
		Storage st = Storage.getInstance(db);
		assertNotNull(st);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testgetInstanceNull() {
		StorageInfo info = null;
		Storage.getInstance(info);
		fail("Getting a storage for a null value should throw an exception.");
	}

	@Test
	public void testGetInstanceUnique() {
		Storage st = Storage.getInstance(db);
		Storage st2 = Storage.getInstance(db);
		assertSame(st, st2);
	}
	
}
