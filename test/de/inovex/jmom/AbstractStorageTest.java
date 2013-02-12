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

import java.util.List;
import com.mongodb.DB;
import com.mongodb.Mongo;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 * This class does server as a super class for all tests, against the storage.
 * It will initiate a connection to the database and instantiate a Storage.
 * Each subclass should use the storage field, to access the {@link Storage}.
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
@Ignore
public abstract class AbstractStorageTest {
	
	private static DB db;
	static Storage storage;
	
	/**
	 * Initialize database connection and fail, if we cannot connect to database.
	 */
	@BeforeClass
	public static void setUpConnection() {
		try {
			//Mongo mongo = new Mongo(new ServerAddress(InetAddress.getByName("10.10.20.118")));
			Mongo mongo = new Mongo();
			db = mongo.getDB("junit");
			db.dropDatabase();
			storage = Storage.getInstance(db);
		} catch (Exception ex) {
			fail("Could not create connection to test database.");
		}
	}
	
	/**
	 * Drop database after each test.
	 */
	@After
	public void after() {
		db.dropDatabase();
	}
	
	public <T> T getSingleResult(List<T> list) {
		if(list.size() != 1) {
			fail(String.format("Result list contained %d elements. Only one element expected.", list.size()));
		}
		return list.get(0);
	}
	
}
