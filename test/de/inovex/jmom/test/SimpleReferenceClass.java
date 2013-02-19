/*
 * Copyright 2013 Tim Roes <tim.roes@inovex.de>.
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
package de.inovex.jmom.test;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class SimpleReferenceClass {
	
	private SimpleReferencedClass ref;
	private int key;
	
	public SimpleReferenceClass() {
		
	}
	
	public SimpleReferenceClass(int key) {
		this.key = key;
	}

	public void setRef(SimpleReferencedClass ref) {
		this.ref = ref;
	}

	public SimpleReferencedClass getRef() {
		return ref;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SimpleReferenceClass other = (SimpleReferenceClass) obj;
		if (this.ref != other.ref && (this.ref == null || !this.ref.equals(other.ref))) {
			return false;
		}
		if (this.key != other.key) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 47 * hash + (this.ref != null ? this.ref.hashCode() : 0);
		hash = 47 * hash + this.key;
		return hash;
	}
	
}
