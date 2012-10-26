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

import de.inovex.jmorm.util.ReflectionUtil;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
class ArrayConverter implements Converter {
	
	private ObjectConverter objectConverter;
	
	ArrayConverter(ObjectConverter objectConverter) {
		this.objectConverter = objectConverter;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object decode(Object dbval, Class<?> objectType, Field field) {
		
		if(!objectType.isArray() || dbval == null)
			return null;
		
		// Every array will be returned by the MongoDB driver as a list...
		// ... except byte arrays, they will be returned as byte[]. So we
		// don't need to cast them to an array, but return them unmodified.
		if(dbval instanceof byte[]) {
			return dbval;
		}
		
		// If object in database is not a collection, just create a single element
		// collection, containing it.
		if(!(dbval instanceof Collection<?>)) {
			List<Object> tmp = new ArrayList<Object>(1);
			tmp.add(dbval);
			dbval = tmp;
		}
		
		// Object in database was a collection so cast it to that value
		Collection<?> dblist = (Collection<?>)dbval;
		int arraySize = dblist.size();
		
		// Component type readsts the type of the components of the array field
		Class<?> componentType = objectType.getComponentType();
		
		// Create a new array with the given size for the given type.
		Object array = Array.newInstance(componentType, arraySize);
		
		int i = 0;
		for(Object ob : dblist) {

			// Decode the array object
			ob = objectConverter.decode(ob, componentType, null);
			
			// Check if object matches component object of array, if not skip this object
			if(!ReflectionUtil.getBoxedType(componentType).isAssignableFrom(ob.getClass())) 
				continue;
			
			// Set the specific array element
			Array.set(array, i, ob);
			i++;
		
		}
		
		// If array is smaller then expected, some elements had to be skipped,
		// so shrink the array to its actual size. This should happen only rarely
		// so we won't use a List above, since most surly we won't need to shrink.
		if(i < arraySize) {
			Object newArray = Array.newInstance(componentType, i);
			System.arraycopy(array, 0, newArray, 0, i);
			return newArray;
		}
		
		return array;		
	
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object encode(Object fieldval, Class<?> fieldType, Field field) {
		
		if(fieldval == null)
			return null;
		
		// If value is not an array or is a primitive array, return unmodified.
		if(!fieldType.isArray() || ReflectionUtil.isPseudoPrimitive(fieldType.getComponentType()))
			return fieldval;
				
		List<Object> dbList = new ArrayList<Object>();
		
		for(Object obj : (Object[])fieldval) {
			Object encode = objectConverter.encode(obj, fieldType.getComponentType(), null);
			dbList.add(encode);
		}
		
		return dbList;
				
	}
		
}