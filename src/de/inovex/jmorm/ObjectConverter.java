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

import com.mongodb.DBObject;
import com.mongodb.DBRef;
import de.inovex.jmorm.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 *
 * // TODO Need Map serializer/deserializer
 * @author Tim Roes <tim.roes@inovex.de>
 */
class ObjectConverter implements Converter {
	
	private Storage storage;
	
	private ArrayConverter arrayConverter;
	private CollectionConverter collectionConverter;
	private PrimitiveConverter primitiveConverter;
	
	ObjectConverter(Storage storage) {
		this.storage = storage;
		arrayConverter = new ArrayConverter(this);
		collectionConverter = new CollectionConverter(this);
		primitiveConverter = new PrimitiveConverter(storage);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object encode(Object fieldval, Class<?> fieldType, Field field) {
		
		Object val = null;
	
		if(fieldval == null) {
			return null;
		} else if(ReflectionUtil.isPseudoPrimitive(fieldType))  {
			val = primitiveConverter.encode(fieldval, fieldType, field);
		} else if(fieldType.isArray()) {
			val = arrayConverter.encode(fieldval, fieldType, field);
		} else if(Collection.class.isAssignableFrom(fieldType)) {
			val = collectionConverter.encode(fieldval, fieldType, field);
		} else {
			// Save referenced object to database
			DBObject refObj = storage.saveObject(fieldval);
			// Create DBRef object to referenced object
			val = storage.createRef(refObj, fieldval.getClass());
		}
		
		return val;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object decode(Object dbval, Class<?> objectType, Field field) {
	
		Object obj;
		
		if(ReflectionUtil.isPseudoPrimitive(objectType)) {
			obj = primitiveConverter.decode(dbval, objectType, field);
		} else if(objectType.isArray()) {
			obj = arrayConverter.decode(dbval, objectType, field);
		} else if(Collection.class.isAssignableFrom(objectType)) {
			obj = collectionConverter.decode(dbval, objectType, field);
		} else {
			if(dbval instanceof DBRef) {
				// Dereference database object and convert it to field type
				obj = storage.convertObject(((DBRef)dbval).fetch(), objectType);
			} else {
				obj = dbval;
			}
		}
		
		return obj;

	}
	
}