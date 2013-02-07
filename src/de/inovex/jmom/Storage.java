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
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class Storage {

	private static Map<DBHandler, Storage> storages = new HashMap<DBHandler, Storage>();
	
	private static final String ID_FIELD = "_id";
	
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
	
	private Cache cache = new DefaultCache();

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
	 * Sets a cache to be used by that storage to store the links between {@link Object Objects}
	 * and {@link ObjectId ObjectIds} from the database. For proper function this method must
	 * be called before any action (saving/deleting/...) is done with the {@link Storage}.
	 * If it's called afterwards the behavior is undefined. If this method is never called, the
	 * {@link DefaultCache} will be used. Under normal circumstances you don't need to change the
	 * {@code Cache}.
	 * 
	 * @param cache The new {@code Cache} to use for this {@code Storage}.
	 */
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	/**
	 * Returns the cache, used by this {@link Storage} to map between {@link ObjectId} and {@link Object}.
	 * 
	 * @return The currently used {@code Cache}.
	 */
	public Cache getCache() {
		return this.cache;
	}
	
	/**
	 * Stores an object in the database.
	 * 
	 * @param obj The object to onSave inside the database.
	 */
	public void save(Object obj) {
		saveObject(obj);		
	}
	
	public void saveMultiple(Iterable<Object> objects) {
		for(Object o : objects) {
			saveObject(o);
		}
	}
	
	public void delete(Object obj) {
		ObjectId id = cache.getId(obj);
		if(id != null) {
			dbhandler.onDelete(collectionResolver.getCollectionForClass(obj.getClass()), id);
		}
	}
	
	DBObject saveObject(Object obj) {
		
		DBObject dbobj = classConverter.encode(obj);
		ObjectId id = cache.getId(obj);
		if(id != null) {
			dbobj.put(ID_FIELD, id);
		}
		
		dbhandler.onSave(collectionResolver.getCollectionForClass(obj.getClass()), dbobj);
		
		if(id == null) {
			id = (ObjectId)dbobj.get(ID_FIELD);
			cache.put(id, obj);
		}
		
		return dbobj;
	}
	
	DBObject saveDBObject(DBObject dbobj, String collection) {
		
		dbhandler.onSave(collection, dbobj);
		return dbobj;
	}
	
	DBRef createRef(DBObject obj, Class<?> clazz) {
		return dbhandler.onCreateRef(collectionResolver.getCollectionForClass(clazz), obj);
	}
	
	DBObject fetchRef(DBRef dbref) {
		return dbhandler.onFetchRef(dbref);
	}
		
	public <T> T findFirst(Class<T> clazz) {
		
		DBObject dbobj = dbhandler.onGetFirst(collectionResolver.getCollectionForClass(clazz),
				FieldList.valueOf(clazz));
		if(dbobj == null)
			return null;
		ObjectId id = (ObjectId)dbobj.get(ID_FIELD);
		T obj = classConverter.decode(dbobj, clazz);
		cache.put(id, obj);
		return obj;
		
	}
	
	public <T> List<T> findAll(Class<T> clazz) {
		
		List<T> objects = new ArrayList<T>();
		
		Collection<DBObject> dbobjects = dbhandler.onGet(collectionResolver.getCollectionForClass(clazz),
				FieldList.valueOf(clazz));
		
		List<DecodeThread<T>> threads = new LinkedList<DecodeThread<T>>();
				
		for(DBObject dbobj : dbobjects) {
			
			DecodeThread<T> t = new DecodeThread<T>(dbobj, clazz);
			
			// If multithreading is enabled, start in new thread.
			if(config.getMultithreadingEnabled()) {
				t.start();
			} else {
				t.run();
			}
			
			// Add thread to list
			threads.add(t);
			
		}
		
		// Wait for every thread in list to finish and add its result to the object list.
		for(DecodeThread<T> t : threads) {
			
			try {
				t.join();
				T obj = t.getResult();
				objects.add(obj);
				cache.put(t.getObjectId(), obj);
			} catch (InterruptedException ex) {
				Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
			}
			
		}
		
		return objects;

	}
	
	<T> T convertObject(DBObject dbobj, Class<T> clazz) {
		
		if(dbobj == null)
			return null;
		
		Object get = cache.getObject((ObjectId)dbobj.get(ID_FIELD));
		if(get != null && (get.getClass() == clazz)) {
			return (T)get;
		}
		
		return classConverter.decode(dbobj, clazz);
	}
	
	public static interface Cache {
		
		/**
		 * Gets the {@link Object} for a specific {@link ObjectId}. This will 
		 * return either the last {@code Object}, that has been created for
		 * that {@code ObjectId} or {@code null}, if that object already isn't used
		 * anymore and has been cleared by the GC.
		 * 
		 * @param id The {@link ObjectId} of the object.
		 * @return The {@link Object} for the given {@code ObjectId} or {@code null}.
		 */
		public Object getObject(ObjectId id);
		
		/**
		 * Gets the {@link ObjectId} of a given {@link Object}. This will return
		 * either the object's id or {@code null}, if the object has no 
		 * {@code ObjectId}, because it hasn't been stored to database yet.
		 *
		 * @param obj The {@link Object} to get the {@link ObjectId id} for.
		 * @return The {@link ObjectId} of the given object or {@code null}.
		 */
		public ObjectId getId(Object object);
		
		/**
		 * Stores a new {@link Object} and its {@link ObjectId} in the cache.
		 * That object will now be returned querying for its {@link ObjectId}
		 * until a new object will be put to database.
		 * 
		 * @param id The id of the object.
		 * @param obj The object itself. 
		 */
		public void put(ObjectId id, Object object);
		
	}
	
	/**
	 * Since the mapper cannot add an {@code _id} field to an object, once it is
	 * saved, it need to store a mapping between {@link ObjectId ObjectIds} and
	 * {@link Object Objects}. This cache handles this mapping.
	 */
	public static class DefaultCache implements Cache {
		
		/**
		 * This stores the ObjectId of each Object. It uses a WeakHashMap, so
		 * the entry will be deleted, whenever the object isn't used anymore.
		 */
		private WeakHashMap<Object, ObjectId> objectIds = new WeakHashMap<Object, ObjectId>();
		
		/**
		 * This stores the last object in memory, that was created for a specific 
		 * ObjectId. This is needed to be stored, to have a unique ObjectId to 
		 * Object mapping, even when the user holds several objects with the same
		 * ObjectId in memory. This stores only a WeakReference to the object,
		 * so that this map isn't preventing the object to be cleared by the
		 * GC, if the user doesn't use it anymore.
		 */
		private HashMap<ObjectId, WeakReference<Object>> lastObject 
				= new HashMap<ObjectId, WeakReference<Object>>();
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getObject(ObjectId id) {
			WeakReference<Object> ref = lastObject.get(id);
			if(ref == null) return null;
			Object obj = ref.get(); 
			if(obj == null) {
				lastObject.remove(id);
			}
			return obj;
		}
		
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public ObjectId getId(Object obj) {
			return objectIds.get(obj);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void put(ObjectId id, Object obj) {	
			objectIds.put(obj, id);
			lastObject.put(id, new WeakReference<Object>(obj));		
		}
		
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
		DBObject onGetFirst(String collection, FieldList fieldList);
		
		/**
		 * This method must return a collection of {@link DBObject DBObjects} from
		 * the given collection.
		 * 
		 * @param collection The collection to catch all objects from.
		 * @return A collection of all {@link DBObject} from this collection.
		 */
		Collection<DBObject> onGet(String collection, FieldList fieldList);
		
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
		
		/**
		 * This method is called, whenever an object should be deleted from
		 * database. The object is identified by its collection and its {@link ObjectId}.
		 * 
		 * @param collection The name of the collection.
		 * @param id The {@link ObjectId} of the object.
		 */
		void onDelete(String collection, ObjectId id);
		
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
		public DBObject onGetFirst(String collection, FieldList fieldList) {
			DBCollection col = db.getCollection(collection);
			return col.findOne();
		}

		@Override
		public Collection<DBObject> onGet(String collection, FieldList fieldlist) {
			
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
			return new DBRef(db, collection, refTo.get(ID_FIELD));
		}

		@Override
		public DBObject onFetchRef(DBRef ref) {
			return ref.fetch();
		}

		@Override
		public void onDelete(String collection, ObjectId id) {
			db.getCollection(collection).remove(new BasicDBObject(ID_FIELD, id));
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
	
	private class DecodeThread<T> extends Thread {
		
		private DBObject dbo;
		private Class<T> clazz;
		private ObjectId id;
		private T result;
		
		public DecodeThread(DBObject dbo, Class<T> clazz) {
			this.dbo = dbo;
			this.clazz = clazz;
			this.id = (ObjectId)dbo.get(ID_FIELD);
		}

		@Override
		public synchronized void run() {
			result = classConverter.decode(dbo, clazz);
		}
		
		public synchronized T getResult() {
			return result;
		}
		
		public ObjectId getObjectId() {
			return id;
		}
		
		
	}
	
}