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

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bson.BSONEncoder;
import org.bson.BasicBSONEncoder;
import org.bson.types.ObjectId;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class Storage {

	private static Map<DB, Storage> storages = new HashMap<DB, Storage>();
	
	/**
	 * Get a storage instance for a {@link StorageInfo}.
	 * 
	 * @param info The {@link StorageInfo} to create a Storage for.
	 * @return The {@link Storage} for the given {@code StorageInfo}.
	 */
	public static Storage getInstance(StorageInfo info) {
			
		if(info == null) {
			throw new IllegalArgumentException("StorageInfo is not allowed to be null.");
		}
		
		Mongo mongo = new Mongo(info.getServerAddress());
		DB database = mongo.getDB(info.getDbName());
		
		return getInstance(database);
		
	}
	
	/**
	 * Get a storage instance for a specific {@link DB MongoDB}.
	 * 
	 * @param db The {@link DB} object.
	 * @return The {@link Storage} for the given {@code DB}.
	 */
	public static Storage getInstance(DB db) {
		if(db == null) {
			throw new IllegalArgumentException("DB is not allowed to be null.");
		}
		
		Storage storage = storages.get(db);
		if(storage == null) {
			storage = new Storage(db);
			storages.put(db, storage);
		}
		return storage;
	}
	
	// ------------------
	// Instance
	// ------------------
	
	private DB db;
	private Config config;
	
	private CollectionResolver collectionResolver = new CanonicalNameResolver();
	
	private ClassConverter classConverter = new ClassConverter(this);
	private HashMap<ObjectId, Object> objectIds = new HashMap<ObjectId, Object>();

	private Storage(DB db) {
		this.db = db;
		this.config = new Config();
	}
	
	/**
	 * Get the {@link Config} of the {@link Storage}.
	 * 
	 * @see Storage#setConfig(de.inovex.jmorm.Config) 
	 * 
	 * @return The {@link Config} of the storage object.
	 */
	public Config getConfig() {
		return config;
	}
	
	/**
	 * Sets the {@link Config} of the {@link Storage}.
	 * 
	 * @param config The new {@link Config}.
	 */
	public void setConfig(Config config) {
		this.config = config;
	}
	
	/**
	 * Stores an object in the database.
	 * 
	 * @param obj The object to save inside the database.
	 */
	public void save(Object obj) {
		saveObject(obj);		
	}
	
	DBObject saveObject(Object obj) {
		
		DBObject dbobj = classConverter.encode(obj);
		ObjectId id = getId(obj);
		if(id != null) {
			dbobj.put("_id", id);
		}
		
		db.getCollection(collectionResolver.getCollectionForClass(obj.getClass())).save(dbobj);
		if(id == null) {
			id = (ObjectId)dbobj.get("_id");
			objectIds.put(id, obj);
		}
		
		return dbobj;
	}
	
	DBObject saveDBObject(DBObject dbobj, String collection) {
		
		db.getCollection(collection).save(dbobj);
		return dbobj;
	}
	
	DBRef createRef(DBObject obj, Class<?> clazz) {
		
		DBRef ref = new DBRef(db, collectionResolver.getCollectionForClass(clazz), obj.get("_id"));
		return ref;
		
	}
	
	private ObjectId getId(Object obj) {
		for(Entry<ObjectId, Object> entry : objectIds.entrySet()) {
			if(entry.getValue() == obj) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public <T> T findOne(Class<T> clazz) {
		
		DBCollection col = db.getCollection(collectionResolver.getCollectionForClass(clazz));
		DBObject dbobj = col.findOne();
		ObjectId id = (ObjectId)dbobj.get("_id");
		T obj = classConverter.decode(dbobj, clazz);
		objectIds.put(id, obj);
		return obj;
		
	}
	
	public <T> Collection<T> findAll(Class<T> clazz) {
		
		Collection<T> objects = new ArrayList<T>();
		
		DBCollection col = db.getCollection(collectionResolver.getCollectionForClass(clazz));
		DBCursor cursor = col.find();
		
		try {
			while(cursor.hasNext()) {
				
				DBObject dbobj = cursor.next();
				ObjectId id = (ObjectId)dbobj.get("_id");
				T obj = classConverter.decode(dbobj, clazz);
				objects.add(obj);
				objectIds.put(id, obj);
				
			}
		} finally {
			cursor.close();
		}
		
		return objects;

	}
	
	public byte[] getBSON(Object obj) {
		BSONEncoder encoder = new BasicBSONEncoder();
		DBObject dbobj = classConverter.encode(obj);
		return encoder.encode(dbobj);
	}
	
	<T> T convertObject(DBObject dbobj, Class<T> clazz) {
		Object get = objectIds.get(dbobj.get("_id"));
		if(get != null && (get.getClass() == clazz)) {
			return (T)get;
		}
		return classConverter.decode(dbobj, clazz);
	}
	
	/**
	 * Changes the {@link CollectionResolver} that is used to look up the collection
	 * for a specific class. This should normally never be changed from the default
	 * {@link CanonicalNameResolver}. So this method is only needed for unit testing.
	 * 
	 * @param resolver The {@link CollectionResolver} to use.
	 */
	void setCollectionResolver(CollectionResolver resolver) {
		this.collectionResolver = resolver;
	}
	
	/**
	 * A class implementing this interface can be passed to
	 * {@link Storage#setCollectionResolver(de.inovex.jmorm.Storage.CollectionResolver) setCollectionResolver}.
	 * It will be queried for the collection name, for a specific class.
	 */
	static interface CollectionResolver {
		/**
		 * Must return the collection name, that any object of a specific class
		 * should be stored in.
		 * 
		 * @param clazz The {@link Class} that should be stored.
		 * @return The collection name to store objects of that class.
		 */
		String getCollectionForClass(Class<?> clazz);
	}
	
	/**
	 * This is the default {@link CollectionResolver}, that should be used.
	 */
	class CanonicalNameResolver implements CollectionResolver {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getCollectionForClass(Class<?> clazz) {
			return clazz.getCanonicalName();
		}
		
	}
	
	/**
	 * Holds information about a specific MongoDB storage. Can be used to 
	 * get an instance of the storage, for the specific MongoDB.
	 * 
	 * @see Storage#getInstance(de.inovex.jmorm.Storage.StorageInfo) 
	 */
	public static class StorageInfo {
		
		private InetAddress address;
		private int port;
		private String dbName;

		/**
		 * Create {@link StorageInfo storage informations} for a local MongoDB
		 * server listening on the default port, with the given database name.
		 * 
		 * @param dbName Database name
		 * @throws UnknownHostException Will be thrown, when the host is unknown.
		 *		Should never be thrown for the localhost.
		 */
		public StorageInfo(String dbName) throws UnknownHostException {
			this("127.0.0.1", dbName);
		}
		
		/**
		 * Create {@link StorageInfo storage informations} for a MongoDB server 
		 * under the given address, listening on the default port, with the given
		 * database name.
		 * 
		 * @param host The host of the server. Can be an host name or an IP address.
		 * @param dbName The database name.
		 * @throws UnknownHostException Will be thrown, when the host is unknown.
		 */
		public StorageInfo(String host, String dbName) throws UnknownHostException {
			this(host, 27017, dbName);
		}
		
		/**
		 * Create {@link StorageInfo storage information} for a MongoDB server
		 * under the given address, listening on the given port, with the given
		 * database name.
		 * 
		 * @param host The host of the server. Can be an host name or an IP address.
		 * @param port The port to connect to.
		 * @param dbName The database name.
		 * @throws UnknownHostException Will be thrown, when the host is unknown.
		 */
		public StorageInfo(String host, int port, String dbName) throws UnknownHostException {
			this.port = port;
			this.dbName = dbName;
			this.address = InetAddress.getByName(host);
		}
		
		private ServerAddress getServerAddress() {
			return new ServerAddress(address, port);
		}
		
		private String getDbName() {
			return dbName;
		}
				
	}
	
}
