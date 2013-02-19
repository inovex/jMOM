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
package de.inovex.jmom;

import de.inovex.jmom.test.SimpleReferenceClass;
import de.inovex.jmom.test.SimpleReferencedClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class StorageReferenceTest extends AbstractStorageTest {
	
	@Test
	public void testReferenceCount() {
		SimpleReferenceClass ref = new SimpleReferenceClass(42);
		ref.setRef(new SimpleReferencedClass(23));
		storage.save(ref);
		assertEquals("SimpleReferenceClass count missmatch", 1, storage.findAll(SimpleReferenceClass.class).size());
		assertEquals("SimpleReferencedClass count missmatch", 1, storage.findAll(SimpleReferencedClass.class).size());
	}
	
	@Test
	public void testReferenceEquals() {
		SimpleReferenceClass ref = new SimpleReferenceClass(42);
		ref.setRef(new SimpleReferencedClass(23));
		storage.save(ref);
		assertEquals("SimpleReferenceClass not equal", ref, getSingleResult(storage.findAll(SimpleReferenceClass.class)));
		assertEquals("SimpleReferencedClass not equal", ref.getRef(), getSingleResult(storage.findAll(SimpleReferencedClass.class)));
	}
	
	@Test
	public void testReferenceUpdateCount() {
		SimpleReferenceClass ref = new SimpleReferenceClass(42);
		ref.setRef(new SimpleReferencedClass(23));
		storage.save(ref);
		storage.save(ref);
		assertEquals("SimpleReferenceClass count missmatch", 1, storage.findAll(SimpleReferenceClass.class).size());
		assertEquals("SimpleReferencedClass count missmatch", 1, storage.findAll(SimpleReferencedClass.class).size());
	}
	
	@Test
	public void testReferenceUpdateEquals() {
		SimpleReferenceClass ref = new SimpleReferenceClass(42);
		ref.setRef(new SimpleReferencedClass(23));
		storage.save(ref);
		storage.save(ref);
		assertEquals("SimpleReferenceClass not equal", ref, getSingleResult(storage.findAll(SimpleReferenceClass.class)));
		assertEquals("SimpleReferencedClass not equal", ref.getRef(), getSingleResult(storage.findAll(SimpleReferencedClass.class)));
	}
	
}
