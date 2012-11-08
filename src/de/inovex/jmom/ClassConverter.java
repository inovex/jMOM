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

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import de.inovex.jmom.exception.DeserializationException;
import de.inovex.jmom.exception.SerializationException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for the translation of Java objects to {@link DBObject DBObjects}.
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
class ClassConverter {

	private ObjectConverter fieldConverter;
	
	ClassConverter(Storage storage) {
		this.fieldConverter = new ObjectConverter(storage);
	}
	
	/**
	 * Encode a Java object to a {@link DBObject}, that can be written to database
	 * or serialized to BSON with help of the {@link org.bson.BSONEncoder BSONEncoder}.
	 * 
	 * @param obj A Java object.
	 * @return The java object transformed to a {@link DBObject}.
	 * 
	 * @throws SerializationException In case the object could not be converted
	 *		to a {@link DBObject}.
	 */
	DBObject encode(Object obj) {
		
		// Find all fields in object
		FieldList fields = FieldList.valueOf(obj.getClass());
		
		DBObject dbobj = new BasicDBObject(fields.size());
		
		for(Field field : fields) {
			
			Object fieldval = null;
			
			try {
				
				field.setAccessible(true);
				fieldval = field.get(obj);
				
			} catch (IllegalArgumentException ex) {
				throw new SerializationException(String.format("Cannot get value from "
						+ "field %s in class %s.", field.getName(), field.getDeclaringClass().getName()), ex);
			} catch (IllegalAccessException ex) {
				// Cannot happen, we just made the field accessible
				throw new SerializationException(String.format("Cannot access field %s in class %s. "
						+ "This should never happen!", field.getName(), 
						field.getDeclaringClass().getName()), ex);
			}
			
			Object encodedVal = fieldConverter.encode(fieldval, field.getType(), field);
			if(encodedVal != null)
				dbobj.put(field.getName(), encodedVal);
			
		}
		
		return dbobj;
		
	}
	
	/**
	 * Transforms an {@link DBObject} back to a java {@link Object} of the 
	 * given type.
	 * 
	 * @param dbobj The {@link DBObject} to transform to an object.
	 * @param clazz The {@link Class} of the java object to transform it to.
	 * @return The object created from the given {@link DBObject}.
	 * 
	 * @throws DeserializationException WIll be thrown, if the {@link DBObject}
	 *		cannot successfully be transformed to an object of the given type.
	 */
	<T> T decode(DBObject dbobj, Class<T> clazz) {
		
		FieldList fields = FieldList.valueOf(clazz);	
		
		T obj = newInstance(clazz);

		for(Field field : fields) {
			
			Object dbval = dbobj.get(field.getName());
			Object val = fieldConverter.decode(dbval, field.getType(), field);
			
			if(val != null) {
				field.setAccessible(true);
				
				try {
					field.set(obj, val);
				} catch (IllegalArgumentException ex) {
					throw new DeserializationException(String.format("Cannot write %s [%s] to field %s in class %s. ",
						val, val.getClass().getName(), field.getName(), field.getDeclaringClass().getName()), ex);
				} catch (IllegalAccessException ex) {
					throw new DeserializationException(String.format("Cannot write to field %s in class %s. "
							+ "This should never happen!", field.getName(), 
							field.getDeclaringClass().getName()), ex);
				}
			}
			
		}
		
		return obj;
	}
	
	/**
	 * Create a new instance of the given {@link Class}.
	 * 
	 * @param clazz The {@link Class} from which a new instance should be created.
	 * @return An instance of the given class.
	 */
	private static <T> T newInstance(Class<T> clazz) {
		
		T obj = null;
	
		try {
			// TODO: Guess the best matching constructor
			obj = clazz.newInstance();
		} catch (InstantiationException ex) {
			throw new DeserializationException(String.format("Cannot instantiate an "
					+ "object of class %s. Might the default contructor be missing?", 
					clazz.getName()), ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(ClassConverter.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return obj;
	}
	
}