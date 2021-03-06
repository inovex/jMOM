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

import de.inovex.jmom.Storage;
import org.junit.Ignore;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
@Ignore
public class StaticResolver implements Storage.CollectionResolver {

	private Class<?> clazz;
	
	public StaticResolver(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public String getCollectionForClass(Class<?> clazz) {
		return this.clazz.getName();
	}
	
}
