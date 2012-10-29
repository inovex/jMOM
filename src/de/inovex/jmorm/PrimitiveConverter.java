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

import de.inovex.jmorm.exception.CharacterCastException;
import de.inovex.jmorm.exception.DeserializationException;
import de.inovex.jmorm.exception.NumericException;
import de.inovex.jmorm.util.DateUtil;
import de.inovex.jmorm.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * This {@link Converter} is responsible for converting primitive and
 * pseudo primitive (like {@link Date} data types. If a data type is pseudo
 * primitive is determined by the {@link ReflectionUtil#isPseudoPrimitive(java.lang.Class)}
 * utility method.
 * 
 * Since all numbers are either {@link Integer}, {@link Double} or {@link Long}
 * coming from database, the main purpose of this converter is to check if,
 * the number is in the range of the requested datatype. If the value is outside
 * the range of the requested datatype, the {@link Config} defines the behavior 
 * for that value.
 * 
 * @see Config#getNumericBehavior()
 * @see Config#getCharacterBehavior() 
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
class PrimitiveConverter implements Converter {
	
	private Storage storage;

	PrimitiveConverter(Storage storage) {
		this.storage = storage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object decode(Object dbval, Class<?> objectType, Field field) {
		
		if(!ReflectionUtil.isPseudoPrimitive(objectType))
			return null;
		
		Object val = dbval;
		Long longVal;
		
		if(objectType == Byte.TYPE || objectType == Byte.class) {
			longVal = checkRange(Long.valueOf(dbval.toString()), Byte.class, Byte.MIN_VALUE, Byte.MAX_VALUE);
			val = longVal.byteValue();
		} else if(objectType == Short.TYPE || objectType == Short.class) {
			longVal = checkRange(Long.valueOf(dbval.toString()), Short.class, Short.MIN_VALUE, Short.MAX_VALUE);
			val = longVal.shortValue();
		} else if(objectType == Integer.TYPE || objectType == Integer.class) {
			longVal = checkRange(Long.valueOf(dbval.toString()), Integer.class, Integer.MIN_VALUE, Integer.MAX_VALUE);
			val = longVal.intValue();
		} else if(objectType == Long.TYPE || objectType == Long.class) {
			val = Long.valueOf(dbval.toString());
		} else if(objectType == Float.TYPE || objectType == Float.class) {
			val = Float.parseFloat(dbval.toString());
		} else if(objectType == Double.TYPE || objectType == Double.class) {
			val = Double.valueOf(dbval.toString());
		} else if(objectType == Character.TYPE || objectType == Character.class) {
			val = getCharacter(dbval.toString());
		} else if(Date.class.isAssignableFrom(objectType)) {
			val = dbval;
			if(dbval instanceof String) {
				val = DateUtil.parseString((String)dbval);
			}
		} else if(String.class.isAssignableFrom(objectType)) {
			val = String.valueOf(dbval);
		}
		
		return val;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object encode(Object fieldval, Class<?> fieldType, Field field) {
		return fieldval;
	}
	
	private Long checkRange(Long dbval, Class<?> downcastType, long minValue, long maxValue) {
		
		if(dbval > maxValue || dbval < minValue) {
			switch(storage.getConfig().getNumericBehavior()) {
				case EXCEPTION:
					throw new NumericException(String.format("The value %s is to large or small to fit into a %s field.", dbval, downcastType.getName()));
				case ZERO_VALUE:
					return Long.valueOf(0);
				case OVERFLOW:
					return dbval;
				default:
					throw new DeserializationException("Unknown NumericBehavior in deserialization.");
			}
		} else {
			return dbval;
		}
		
	}

	private Character getCharacter(String dbval) {
		
		if(dbval.length() <= 1) {
			return dbval.charAt(0);
		}
		
		switch(storage.getConfig().getCharacterBehavior()) {
			case EXCEPTION:
				throw new CharacterCastException("String has more than one character.");
			case FIRST_CHAR:
				return dbval.charAt(0);
			case ZERO_VALUE:
				return '\u0000';
		}
		
		return null;
		
	}
	
}