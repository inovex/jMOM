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
package de.inovex.jmorm.test;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class CollectionTestClass {
	
	private List<Byte> byteList;
	private ArrayList<Short> shortList;
	private LinkedList<Integer> intList;
	private List<Long> longList;
	private ArrayList<Float> floatList;
	private LinkedList<Double> doubleList;
	private List<Character> charList;
	private ArrayList<String> stringList;
	
	private Set<Byte> byteSet;
	private SortedSet<Short> shortSet;
	private HashSet<Integer> intSet;
	private TreeSet<Long> longSet;
	private NavigableSet<Float> floatSet;
	private AbstractSet<Double> doubleSet;
	private Set<Character> charSet;
	private SortedSet<String> stringSet;
	
	private List<PlainTestClass> referenceList;
	private Set<PlainTestClass> referenceSet;

	public Set<PlainTestClass> getReferenceSet() {
		return referenceSet;
	}

	public void setReferenceSet(Set<PlainTestClass> referenceSet) {
		this.referenceSet = referenceSet;
	}

	public List<PlainTestClass> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<PlainTestClass> referenceList) {
		this.referenceList = referenceList;
	}

	public List<Byte> getByteList() {
		return byteList;
	}

	public void setByteList(List<Byte> byteList) {
		this.byteList = byteList;
	}

	public List<Character> getCharList() {
		return charList;
	}

	public void setCharList(List<Character> charList) {
		this.charList = charList;
	}

	public LinkedList<Double> getDoubleList() {
		return doubleList;
	}

	public void setDoubleList(LinkedList<Double> doubleList) {
		this.doubleList = doubleList;
	}

	public ArrayList<Float> getFloatList() {
		return floatList;
	}

	public void setFloatList(ArrayList<Float> floatList) {
		this.floatList = floatList;
	}

	public LinkedList<Integer> getIntList() {
		return intList;
	}

	public void setIntList(LinkedList<Integer> intList) {
		this.intList = intList;
	}

	public List<Long> getLongList() {
		return longList;
	}

	public void setLongList(List<Long> longList) {
		this.longList = longList;
	}

	public ArrayList<Short> getShortList() {
		return shortList;
	}

	public void setShortList(ArrayList<Short> shortList) {
		this.shortList = shortList;
	}

	public ArrayList<String> getStringList() {
		return stringList;
	}

	public void setStringList(ArrayList<String> stringList) {
		this.stringList = stringList;
	}

	public Set<Byte> getByteSet() {
		return byteSet;
	}

	public void setByteSet(Set<Byte> byteSet) {
		this.byteSet = byteSet;
	}

	public Set<Character> getCharSet() {
		return charSet;
	}

	public void setCharSet(Set<Character> charSet) {
		this.charSet = charSet;
	}

	public AbstractSet<Double> getDoubleSet() {
		return doubleSet;
	}

	public void setDoubleSet(AbstractSet<Double> doubleSet) {
		this.doubleSet = doubleSet;
	}

	public NavigableSet<Float> getFloatSet() {
		return floatSet;
	}

	public void setFloatSet(NavigableSet<Float> floatSet) {
		this.floatSet = floatSet;
	}

	public HashSet<Integer> getIntSet() {
		return intSet;
	}

	public void setIntSet(HashSet<Integer> intSet) {
		this.intSet = intSet;
	}

	public TreeSet<Long> getLongSet() {
		return longSet;
	}

	public void setLongSet(TreeSet<Long> longSet) {
		this.longSet = longSet;
	}

	public SortedSet<Short> getShortSet() {
		return shortSet;
	}

	public void setShortSet(SortedSet<Short> shortSet) {
		this.shortSet = shortSet;
	}

	public SortedSet<String> getStringSet() {
		return stringSet;
	}

	public void setStringSet(SortedSet<String> stringSet) {
		this.stringSet = stringSet;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CollectionTestClass other = (CollectionTestClass) obj;
		if (this.byteList != other.byteList && (this.byteList == null || !this.byteList.equals(other.byteList))) {
			return false;
		}
		if (this.shortList != other.shortList && (this.shortList == null || !this.shortList.equals(other.shortList))) {
			return false;
		}
		if (this.intList != other.intList && (this.intList == null || !this.intList.equals(other.intList))) {
			return false;
		}
		if (this.longList != other.longList && (this.longList == null || !this.longList.equals(other.longList))) {
			return false;
		}
		if (this.floatList != other.floatList && (this.floatList == null || !this.floatList.equals(other.floatList))) {
			return false;
		}
		if (this.doubleList != other.doubleList && (this.doubleList == null || !this.doubleList.equals(other.doubleList))) {
			return false;
		}
		if (this.charList != other.charList && (this.charList == null || !this.charList.equals(other.charList))) {
			return false;
		}
		if (this.stringList != other.stringList && (this.stringList == null || !this.stringList.equals(other.stringList))) {
			return false;
		}
		if (this.byteSet != other.byteSet && (this.byteSet == null || !this.byteSet.equals(other.byteSet))) {
			return false;
		}
		if (this.shortSet != other.shortSet && (this.shortSet == null || !this.shortSet.equals(other.shortSet))) {
			return false;
		}
		if (this.intSet != other.intSet && (this.intSet == null || !this.intSet.equals(other.intSet))) {
			return false;
		}
		if (this.longSet != other.longSet && (this.longSet == null || !this.longSet.equals(other.longSet))) {
			return false;
		}
		if (this.floatSet != other.floatSet && (this.floatSet == null || !this.floatSet.equals(other.floatSet))) {
			return false;
		}
		if (this.doubleSet != other.doubleSet && (this.doubleSet == null || !this.doubleSet.equals(other.doubleSet))) {
			return false;
		}
		if (this.charSet != other.charSet && (this.charSet == null || !this.charSet.equals(other.charSet))) {
			return false;
		}
		if (this.stringSet != other.stringSet && (this.stringSet == null || !this.stringSet.equals(other.stringSet))) {
			return false;
		}
		if (this.referenceList != other.referenceList && (this.referenceList == null || !this.referenceList.equals(other.referenceList))) {
			return false;
		}
		if (this.referenceSet != other.referenceSet && (this.referenceSet == null || !this.referenceSet.equals(other.referenceSet))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + (this.byteList != null ? this.byteList.hashCode() : 0);
		hash = 67 * hash + (this.shortList != null ? this.shortList.hashCode() : 0);
		hash = 67 * hash + (this.intList != null ? this.intList.hashCode() : 0);
		hash = 67 * hash + (this.longList != null ? this.longList.hashCode() : 0);
		hash = 67 * hash + (this.floatList != null ? this.floatList.hashCode() : 0);
		hash = 67 * hash + (this.doubleList != null ? this.doubleList.hashCode() : 0);
		hash = 67 * hash + (this.charList != null ? this.charList.hashCode() : 0);
		hash = 67 * hash + (this.stringList != null ? this.stringList.hashCode() : 0);
		hash = 67 * hash + (this.byteSet != null ? this.byteSet.hashCode() : 0);
		hash = 67 * hash + (this.shortSet != null ? this.shortSet.hashCode() : 0);
		hash = 67 * hash + (this.intSet != null ? this.intSet.hashCode() : 0);
		hash = 67 * hash + (this.longSet != null ? this.longSet.hashCode() : 0);
		hash = 67 * hash + (this.floatSet != null ? this.floatSet.hashCode() : 0);
		hash = 67 * hash + (this.doubleSet != null ? this.doubleSet.hashCode() : 0);
		hash = 67 * hash + (this.charSet != null ? this.charSet.hashCode() : 0);
		hash = 67 * hash + (this.stringSet != null ? this.stringSet.hashCode() : 0);
		hash = 67 * hash + (this.referenceList != null ? this.referenceList.hashCode() : 0);
		hash = 67 * hash + (this.referenceSet != null ? this.referenceSet.hashCode() : 0);
		return hash;
	}

}
