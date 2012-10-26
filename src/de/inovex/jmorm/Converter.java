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

import java.lang.reflect.Field;

/**
 * This interface should be implemented by every possible Converter.
 * A Converter can decode values coming from database and encode them
 * for the use in a {@link DBObject} (so that they can be serialized there).
 * @author Tim Roes <tim.roes@inovex.de>
 */
interface Converter {
	
	/**
	 * Decode a value read from the database for a specific {@link Field}.
	 * This method will get a {@code Field} of an object and the value from
	 * an {@link DBObject}. It must then convert this value to an {@link Object},
	 * that can be assigned to the specified {@code Field}.
	 * 
	 * @param dbval The value read from the specific field from the database.
	 * @param fieldType The {@link Class} of the object, that should be deserialized.
	 * @param field The Field, that this value should be assigned to. This might be
	 *		{@code null}, if the method is called recursive (e.g. for each element 
	 *		of an array).
	 * @return An object, that can be assigned to the given field.
	 */
	Object decode(Object dbval, Class<?> fieldType, Field field);
	
	
	/**
	 * Encode a value from a field in an object, to save it in the database.
	 * The returning {@link Object} must be serializable by the MongoDB driver.
	 * Meaning it should either be a simple type, that can be serialized, or
	 * some kind of {@link DBObject} for more complex types.
	 * 
	 * @param fieldval The value of the field.
	 * @param fieldType The {@link Class} of the object, that should be serialized.
	 * @param field The {@link Field} in the object, that this value came from.
	 *		This might be {@code null}, if the method is called recursive (e.g. for
	 *		each element of an array).
	 * @return An object, suitable for serialization with the MongoDB driver.
	 */
	Object encode(Object fieldval, Class<?> fieldType, Field field);
}
