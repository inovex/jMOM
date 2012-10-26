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
package de.inovex.jmorm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets a {@link Class} for this field, that is used to instantiate an object.
 * Usually the field type is used for instantiation. If the field type is abstract
 * or an interface, you need to specify another type. Some abstract types and
 * interfaces have a default instantiation type (e.g. {@link List} will be
 * instantiated as {@link ArrayList}), but you can change the type with this annotation.
 * 
 * @author Tim Roes <tim.roes@inovex.de>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseType {
	public Class<?> value();
}
