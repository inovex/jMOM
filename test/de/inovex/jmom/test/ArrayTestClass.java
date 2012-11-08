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
package de.inovex.jmom.test;

import java.util.Arrays;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class ArrayTestClass {
	
	private byte[] byteArray;
	private short[] shortArray;
	private int[] intArray;
	private long[] longArray;
	private float[] floatArray;
	private double[] doubleArray;
	private char[] charArray;
	private boolean[] booleanArray;
	
	private Byte[] byteClassArray;
	private Short[] shortClassArray;
	private Integer[] intClassArray;
	private Long[] longClassArray;
	private Float[] floatClassArray;
	private Double[] doubleClassArray;
	private Character[] charClassArray;
	private Boolean[] booleanClassArray;
	
	private String[] stringArray;
	
	private PlainTestClass[] referenceArray;

	public PlainTestClass[] getReferenceArray() {
		return referenceArray;
	}

	public void setReferenceArray(PlainTestClass[] referenceArray) {
		this.referenceArray = referenceArray;
	}

	public Boolean[] getBooleanClassArray() {
		return booleanClassArray;
	}

	public void setBooleanClassArray(Boolean[] booleanClassArray) {
		this.booleanClassArray = booleanClassArray;
	}

	public Byte[] getByteClassArray() {
		return byteClassArray;
	}

	public void setByteClassArray(Byte[] byteClassArray) {
		this.byteClassArray = byteClassArray;
	}

	public Character[] getCharClassArray() {
		return charClassArray;
	}

	public void setCharClassArray(Character[] charClassArray) {
		this.charClassArray = charClassArray;
	}

	public Double[] getDoubleClassArray() {
		return doubleClassArray;
	}

	public void setDoubleClassArray(Double[] doubleClassArray) {
		this.doubleClassArray = doubleClassArray;
	}

	public Float[] getFloatClassArray() {
		return floatClassArray;
	}

	public void setFloatClassArray(Float[] floatClassArray) {
		this.floatClassArray = floatClassArray;
	}

	public Integer[] getIntClassArray() {
		return intClassArray;
	}

	public void setIntClassArray(Integer[] intClassArray) {
		this.intClassArray = intClassArray;
	}

	public Long[] getLongClassArray() {
		return longClassArray;
	}

	public void setLongClassArray(Long[] longClassArray) {
		this.longClassArray = longClassArray;
	}

	public Short[] getShortClassArray() {
		return shortClassArray;
	}

	public void setShortClassArray(Short[] shortClassArray) {
		this.shortClassArray = shortClassArray;
	}
	
	public boolean[] getBooleanArray() {
		return booleanArray;
	}

	public void setBooleanArray(boolean[] booleanArray) {
		this.booleanArray = booleanArray;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public char[] getCharArray() {
		return charArray;
	}

	public void setCharArray(char[] charArray) {
		this.charArray = charArray;
	}

	public double[] getDoubleArray() {
		return doubleArray;
	}

	public void setDoubleArray(double[] doubleArray) {
		this.doubleArray = doubleArray;
	}

	public float[] getFloatArray() {
		return floatArray;
	}

	public void setFloatArray(float[] floatArray) {
		this.floatArray = floatArray;
	}

	public int[] getIntArray() {
		return intArray;
	}

	public void setIntArray(int[] intArray) {
		this.intArray = intArray;
	}

	public long[] getLongArray() {
		return longArray;
	}

	public void setLongArray(long[] longArray) {
		this.longArray = longArray;
	}

	public short[] getShortArray() {
		return shortArray;
	}

	public void setShortArray(short[] shortArray) {
		this.shortArray = shortArray;
	}

	public String[] getStringArray() {
		return stringArray;
	}

	public void setStringArray(String[] stringArray) {
		this.stringArray = stringArray;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ArrayTestClass other = (ArrayTestClass) obj;
		if (!Arrays.equals(this.byteArray, other.byteArray)) {
			return false;
		}
		if (!Arrays.equals(this.shortArray, other.shortArray)) {
			return false;
		}
		if (!Arrays.equals(this.intArray, other.intArray)) {
			return false;
		}
		if (!Arrays.equals(this.longArray, other.longArray)) {
			return false;
		}
		if (!Arrays.equals(this.floatArray, other.floatArray)) {
			return false;
		}
		if (!Arrays.equals(this.doubleArray, other.doubleArray)) {
			return false;
		}
		if (!Arrays.equals(this.charArray, other.charArray)) {
			return false;
		}
		if (!Arrays.equals(this.booleanArray, other.booleanArray)) {
			return false;
		}
		if (!Arrays.deepEquals(this.byteClassArray, other.byteClassArray)) {
			return false;
		}
		if (!Arrays.deepEquals(this.shortClassArray, other.shortClassArray)) {
			return false;
		}
		if (!Arrays.deepEquals(this.intClassArray, other.intClassArray)) {
			return false;
		}
		if (!Arrays.deepEquals(this.longClassArray, other.longClassArray)) {
			return false;
		}
		if (!Arrays.deepEquals(this.floatClassArray, other.floatClassArray)) {
			return false;
		}
		if (!Arrays.deepEquals(this.doubleClassArray, other.doubleClassArray)) {
			return false;
		}
		if (!Arrays.deepEquals(this.charClassArray, other.charClassArray)) {
			return false;
		}
		if (!Arrays.deepEquals(this.booleanClassArray, other.booleanClassArray)) {
			return false;
		}
		if (!Arrays.deepEquals(this.stringArray, other.stringArray)) {
			return false;
		}
		if (!Arrays.deepEquals(this.referenceArray, other.referenceArray)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Arrays.hashCode(this.byteArray);
		hash = 67 * hash + Arrays.hashCode(this.shortArray);
		hash = 67 * hash + Arrays.hashCode(this.intArray);
		hash = 67 * hash + Arrays.hashCode(this.longArray);
		hash = 67 * hash + Arrays.hashCode(this.floatArray);
		hash = 67 * hash + Arrays.hashCode(this.doubleArray);
		hash = 67 * hash + Arrays.hashCode(this.charArray);
		hash = 67 * hash + Arrays.hashCode(this.booleanArray);
		hash = 67 * hash + Arrays.deepHashCode(this.byteClassArray);
		hash = 67 * hash + Arrays.deepHashCode(this.shortClassArray);
		hash = 67 * hash + Arrays.deepHashCode(this.intClassArray);
		hash = 67 * hash + Arrays.deepHashCode(this.longClassArray);
		hash = 67 * hash + Arrays.deepHashCode(this.floatClassArray);
		hash = 67 * hash + Arrays.deepHashCode(this.doubleClassArray);
		hash = 67 * hash + Arrays.deepHashCode(this.charClassArray);
		hash = 67 * hash + Arrays.deepHashCode(this.booleanClassArray);
		hash = 67 * hash + Arrays.deepHashCode(this.stringArray);
		hash = 67 * hash + Arrays.deepHashCode(this.referenceArray);
		return hash;
	}

}
