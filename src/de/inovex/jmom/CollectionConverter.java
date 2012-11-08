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

import de.inovex.jmom.annotations.UseType;
import de.inovex.jmom.exception.DeserializationException;
import de.inovex.jmom.exception.SerializationException;
import de.inovex.jmom.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The {@code CollectionConverter} is responsible for transforming {@link Collection Collections}
 * from or to database.
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
class CollectionConverter implements Converter {
	
	private ObjectConverter objectConverter;

	CollectionConverter(ObjectConverter objectConverter) {
		this.objectConverter = objectConverter;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object decode(Object dbval, Class<?> objectType, Field field) {
		
		if(!Collection.class.isAssignableFrom(objectType))
			return null;
		
		Class<?> componentType = ReflectionUtil.getCollectionType(field.getGenericType());
		
		if(componentType == null) {
			throw new DeserializationException("Cannot handle raw collection types. Use generics instead.");
		}
		
		// If object in database is not a collection, just create a single element
		// collection, containing it.
		if(!(dbval instanceof Iterable<?>)) {
			List<Object> tmp = new ArrayList<Object>(1);
			tmp.add(dbval);
			dbval = tmp;
		}
		
		Iterable<?> dblist = (Iterable<?>)dbval;
		Collection list = null;
			
		try {

			// If UseType is given, use this type instead 
			// field type.
			UseType useType = field.getAnnotation(UseType.class);
			Class<?> instType = (useType != null && useType.value() != null)
					? useType.value() : objectType;

			// If cannot instantiate type (because it's abstract or an interface)
			// guess the best matching type for it.
			if(Modifier.isAbstract(instType.getModifiers()) 
					|| instType.isInterface()) {
				list = getInstantiableType(objectType).newInstance();
			} else {
				list = (Collection<?>)instType.newInstance();
			}


			for(Object ob : dblist) {

				ob = objectConverter.decode(ob, componentType, null);

				// Do a typecheck on the object to the type of the collection,
				// if a collection type existed (no raw collection was used).
				if(!ReflectionUtil.getBoxedType(componentType).isAssignableFrom(ob.getClass())) {
					return null;
				}

				list.add(ob);
			}

			return list;

		} catch(InstantiationException ex) {
			throw new DeserializationException("Cannot instantiate collection.", ex);
		} catch(IllegalAccessException ex) {
			// Should not occur
			throw new DeserializationException("Cannot instantiate collection.", ex);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object encode(Object fieldval, Class<?> fieldType, Field field) {
				
		if(!(fieldval instanceof Iterable<?>))
			return fieldval;
				
		Class<?> innerType = ReflectionUtil.getCollectionType(field.getGenericType());
		
		if(innerType == null) {
			throw new SerializationException("Cannot serialize nested generic types.");
		}
		
		// Inner type is pseudo primitive so leave it to the MongoDB driver to serialize it.
		if(ReflectionUtil.isPseudoPrimitive(innerType)) {
			return fieldval;
		}
		
		List<Object> dbList = new ArrayList<Object>();
		
		for(Object obj : (Iterable<?>)fieldval) {
			Object encode = objectConverter.encode(obj, innerType, null);
			dbList.add(encode);
		}

		return dbList;
		
	}
	

	/**
	 * Choose an instantiable type for a given interface or abstract collection type.
	 * This will choose a reasonable default value, for every field. To use another
	 * type, use the {@link UseType} annotation.
	 * 
	 * @param clazz An abstract class or interface to find an assignable instantiable type for.
	 * @return The instantiable type that can be assigned to the given class type.
	 * 
	 * @throws DeserializationException Will be thrown, when no type can be found.
	 */
	private static Class<? extends Collection> getInstantiableType(Class<?> clazz) {
		
		if (clazz == List.class) {
			return ArrayList.class;
		} else if(clazz == SortedSet.class) {
			return TreeSet.class;
		} else if(clazz == Set.class) {
			return HashSet.class;
		} else if(clazz == Collection.class) {
			return ArrayList.class;
		} else if(clazz == NavigableSet.class) {
			return TreeSet.class;
		} else if(clazz == AbstractSet.class) {
			return HashSet.class;
		}
		
		throw new DeserializationException(String.format("No suitable instantiable "
				+ "collection type for field type %s found. Use the @UseType(Class<?>) "
				+ "annotation, to chose a specific instatntiation type.", clazz.getName()));
		
	}
	
}
