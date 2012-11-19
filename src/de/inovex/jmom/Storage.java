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

import com.mongodb.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.Map.Entry;
import org.bson.types.ObjectId;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class Storage {

	private static Map<DBHandler, Storage> storages = new HashMap<DBHandler, Storage>();
	
	/**
	 * Get a storage instance with a given {@link DBHandler}.
	 * 
	 * @param dbhandler The {@link DBHandler}, that should handle database connection.
	 * @return The {@link Storage} for the given {@link DBHandler}.
	 */
	public static Storage getInstance(DBHandler dbhandler) {
		
		if(dbhandler == null) {
			throw new IllegalArgumentException("dbhandler is not allowed to be null.");
		}
		
		Storage storage = storages.get(dbhandler);
		if(storage == null) {
			storage = new Storage(dbhandler);
			storages.put(dbhandler, storage);
		}
		return storage;
		
	}
	
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
		
		return getInstance(new MongoDBHandler(db));
		
	}
	
	// ------------------
	// Instance
	// ------------------
	
	private DBHandler dbhandler;
	//private DB db;
	private Config config;
	
	private CollectionResolver collectionResolver = new CanonicalNameResolver();
	
	private ClassConverter classConverter = new ClassConverter(this);
	private HashMap<ObjectId, Object> objectIds = new HashMap<ObjectId, Object>();

	private Storage(DBHandler dbhandler) {
		this.dbhandler = dbhandler;
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
	 * @param obj The object to onSave inside the database.
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
		
		dbhandler.onSave(collectionResolver.getCollectionForClass(obj.getClass()), dbobj);
		//db.getCollection(collectionResolver.getCollectionForClass(obj.getClass())).onSave(dbobj);
		if(id == null) {
			id = (ObjectId)dbobj.get("_id");
			objectIds.put(id, obj);
		}
		
		return dbobj;
	}
	
	DBObject saveDBObject(DBObject dbobj, String collection) {
		
		//db.getCollection(collection).onSave(dbobj);
		dbhandler.onSave(collection, dbobj);
		return dbobj;
	}
	
	DBRef createRef(DBObject obj, Class<?> clazz) {
		return dbhandler.onCreateRef(collectionResolver.getCollectionForClass(clazz), obj);
	}
	
	DBObject fetchRef(DBRef dbref) {
		return dbhandler.onFetchRef(dbref);
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
		
		DBObject dbobj = dbhandler.onGetFirst(collectionResolver.getCollectionForClass(clazz));
		//DBCollection col = db.getCollection(collectionResolver.getCollectionForClass(clazz));
		//DBObject dbobj = col.findOne();
		ObjectId id = (ObjectId)dbobj.get("_id");
		T obj = classConverter.decode(dbobj, clazz);
		objectIds.put(id, obj);
		return obj;
		
	}
	
	public <T> List<T> findAll(Class<T> clazz) {
		
		List<T> objects = new ArrayList<T>();
		
		Collection<DBObject> dbobjects = dbhandler.onGet(collectionResolver.getCollectionForClass(clazz));
				
		for(DBObject dbobj : dbobjects) {

			ObjectId id = (ObjectId)dbobj.get("_id");
			T obj = classConverter.decode(dbobj, clazz);
			objects.add(obj);
			objectIds.put(id, obj);
	
		}
		
		return objects;

	}
	
	<T> T convertObject(DBObject dbobj, Class<T> clazz) {
		
		if(dbobj == null)
			return null;
		
		Object get = objectIds.get((ObjectId)dbobj.get("_id"));
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
	private class CanonicalNameResolver implements CollectionResolver {

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
	 * onGet an instance of the storage, for the specific MongoDB.
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
	
	/**
	 * A DBHandler interface can be used to intercept connection to MongoDB.
	 * An implementation of this interface need to do all the database writing
	 * and reading. This mapper has a default implementation ({@link MongoDBHandler})
	 * of this interface, that is used for the connection with a MongoDB.
	 * 
	 * Under normal circumstances you won't need to overwrite this class, only
	 * if you want to intercept or modify into the writing in reading process.
	 * Use {@link Storage#getInstance(de.inovex.jmom.Storage.DBHandler)} to onGet
	 * a {@link Storage} for a specific {@code DBHandler}.
	 */
	public static interface DBHandler {
		
		/**
		 * This method is called, whenever an {@link DBObject} should be stored
		 * into database. The implementation of this method should store the 
		 * object into database. If the {@code DBObject} has an {@code _id} field,
		 * the value isn't allowed to be changed by that method. If it hasn't such
		 * a field, this method must make sure, the {@code DBObject} contains a
		 * {@code _if} field after this methods ends, with a valid {@link ObjectId}.
		 * 
		 * @param collection The name of the collection to save that object too.
		 * @param dbobj The object to store to database.
		 */
		void onSave(String collection, DBObject dbobj);
		
		/**
		 * This method must return the first object from the given collection.
		 * 
		 * @param collection The name of the collection to get the object from.
		 * @return The first {@link DBObject} of the given collection.
		 */
		DBObject onGetFirst(String collection);
		
		/**
		 * This method must return a collection of {@link DBObject DBObjects} from
		 * the given collection.
		 * 
		 * @param collection The collection to catch all objects from.
		 * @return A collection of all {@link DBObject} from this collection.
		 */
		Collection<DBObject> onGet(String collection);
		
		/**
		 * This method is called, whenever a {@link DBRef} to another object needs to 
		 * be created. The returning reference must point to the given {@link DBObject}
		 * in the collection with the given name.
		 * 
		 * @param collection The name of the collection the referenced object is in.
		 * @param refTo The object to create a reference for.
		 * @return A reference to that object.
		 */
		DBRef onCreateRef(String collection, DBObject refTo);
		
		/**
		 * This method is called, whenever the mapper tries to fetch the object
		 * a {@link DBRef} is referencing to. The implementation must fetch
		 * this object and return it.
		 * 
		 * @param ref A reference to another object.
		 * @return The object, that was referenced.
		 */
		DBObject onFetchRef(DBRef ref);
		
	}
	
	/**
	 * The default implementation of the {@link DBHandler} interface, that does
	 * all the action on a {@link DB}. This will be used when the user creates
	 * a storage and don't pass an own implementation of {@code DBHandler} to it.
	 */
	private static class MongoDBHandler implements DBHandler {

		private DB db;
		
		public MongoDBHandler(DB db) {
			this.db = db;
		}
		
		@Override
		public void onSave(String collection, DBObject dbobj) {
			db.getCollection(collection).save(dbobj);
		}

		@Override
		public DBObject onGetFirst(String collection) {
			DBCollection col = db.getCollection(collection);
			return col.findOne();
		}

		@Override
		public Collection<DBObject> onGet(String collection) {
			
			Collection<DBObject> objects = new ArrayList<DBObject>();
			
			DBCursor cur = null;
			try {
				cur = db.getCollection(collection).find();
			
				for(DBObject dbobj : cur) {
					objects.add(dbobj);
				}
			} finally {
				if(cur != null)
					cur.close();
			}
			
			return objects;
			
		}
		
		@Override
		public DBRef onCreateRef(String collection, DBObject refTo) {
			return new DBRef(db, collection, refTo.get("_id"));
		}

		@Override
		public DBObject onFetchRef(DBRef ref) {
			return ref.fetch();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final MongoDBHandler other = (MongoDBHandler) obj;
			if (this.db != other.db && (this.db == null || !this.db.equals(other.db))) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 79 * hash + (this.db != null ? this.db.hashCode() : 0);
			return hash;
		}
		
	}
	
}
