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

import de.inovex.jmom.annotations.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class contains several utilities for reflection.
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class ReflectionUtil {
	
	/**
	 * Returns a collection containing all fields of a class, that should be 
	 * persisted into the database. This will be all fields (also private) 
	 * except {@code transient} fields. 
	 * 
	 * @param clazz The class, that should be analysed.
	 * @return A collection of all fields, that should be persisted.
	 */
	public static Collection<Field> getAllFields(Class<?> clazz) {
		
		Collection<Field> fields = new LinkedList<Field>();
		
		Class<?> c = clazz;
		
		while(c != null && c != Object.class) {
		
			Field[] allFields = c.getDeclaredFields();

			// Add all non transient fields
			for(Field field : allFields) {

				int modifiers = field.getModifiers();

				if((modifiers & Modifier.TRANSIENT) == Modifier.TRANSIENT
						|| field.isAnnotationPresent(Transient.class))
					continue;

				fields.add(field);

			}
			
			c = c.getSuperclass();
			
		}
		
		return fields;
		
	}
	
	/**
	 * This method return the boxed type of a given {@link Class}. Meaning it will
	 * return {@code Class<Integer>}, if passed {@code Class<int>} and likewise
	 * for the other primitive datatypes. If the passed class isn't a primitive
	 * datatype, it will be returned unmodified.
	 * 
	 * @param clazz A {@link Class} to check, if it can be boxed.
	 * @return The boxed {@link Class} or the unmodified class, if it can't be boxed.
	 */
	public static Class<?> getBoxedType(Class<?> clazz)	 {
		
		if(clazz == null) return null;
		
		// TOTO use XXX.TYPE
		if(clazz.isPrimitive()) {
			String name = clazz.getName();
			if("int".equals(name)) {
				return Integer.class;
			} else if("double".equals(name)) {
				return Double.class;
			} else if("boolean".equals(name)) {
				return Boolean.class;
			} else if("char".equals(name)) {
				return Character.class;
			} else if("float".equals(name)) {
				return Float.class;
			} else if("long".equals(name)) {
				return Long.class;
			} else if("byte".equals(name)) {
				return Byte.class;
			} else if("short".equals(name)) {
				return Short.class;
			}
		}
		
		return clazz;
		
	}
	
		/**
	 * Checks whether a type is a pseudo primitive datatype. Pseudo primitive
	 * datatypes should be saved in the database object itself, instead of
	 * saving a reference to another object.
	 * 
	 * @param clazz Type to check.
	 * @return Whether type is pseudo primitive.
	 */
	public static boolean isPseudoPrimitive(Class<?> clazz) {
		return (clazz == Integer.TYPE || clazz == Integer.class
				|| clazz == Double.TYPE || clazz == Double.class
				|| clazz == Boolean.TYPE || clazz == Boolean.class
				|| clazz == Character.TYPE || clazz == Character.class
				|| clazz == Byte.TYPE || clazz == Byte.class
				|| clazz == Short.TYPE || clazz == Short.class
				|| clazz == Long.TYPE || clazz == Long.class
				|| clazz == Float.TYPE || clazz == Float.class
				|| clazz == String.class
				|| Date.class.isAssignableFrom(clazz)
				|| Map.class.isAssignableFrom(clazz));
	}
	
	/**
	 * Get the generic type of a collection. This will return null, if
	 * used on a raw collection type (e.g. List, not List<Object>).
	 * 
	 * @param collectionType The type of the collection.
	 * @return The generic type for that collection or null if raw type.
	 */
	public static Class<?> getCollectionType(Type collectionType) {
		
		Class<?> componentType;
		
		if(collectionType instanceof ParameterizedType) {
			// Read the first type of the generic types, if more t
			componentType = (Class<?>)((ParameterizedType)collectionType).getActualTypeArguments()[0];
		} else {
			// Raw type was used
			componentType = null;
		}
		
		return componentType;
		
	}
		
	/**
	 * Permit to initialize or subclass this class.
	 */
	private ReflectionUtil() { }
	
}
