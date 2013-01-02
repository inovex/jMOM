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

import de.inovex.jmom.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class FieldList implements Iterable<Field> {
	
	private static Map<Class<?>, FieldList> cachedFieldLists = new HashMap<Class<?>, FieldList>();
	
	static FieldList valueOf(Class<?> clazz) {
		FieldList fl = cachedFieldLists.get(clazz);
		if(fl == null) {
			fl = new FieldList(clazz);
			cachedFieldLists.put(clazz, fl);
		}
		return fl;
	}
	
	private final Collection<Field> fields;
	
	private FieldList(Class<?> clazz) {
		this.fields = ReflectionUtil.getAllFields(clazz);
	}
	
	public int size() {
		return fields.size();
	}
	
	@Override
	public Iterator<Field> iterator() {
		return fields.iterator();
	}
	
}
