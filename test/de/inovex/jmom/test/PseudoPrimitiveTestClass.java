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

import de.inovex.jmom.annotations.Transient;
import java.util.Date;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class PseudoPrimitiveTestClass {
	
	private int intVal;
	private long longVal;
	private byte byteVal;
	private short shortVal;
	private float floatVal;
	private double doubleVal;
	private char charVal;
	private boolean boolVal;
	
	private Integer intClassVal;
	private Long longClassVal;
	private Byte byteClassVal;
	private Short shortClassVal;
	private Float floatClassVal;
	private Double doubleClassVal;
	private Character charClassVal;
	private Boolean boolClassVal;
	
	private String stringVal;
	private Date dateVal;

	public Boolean getBoolClassVal() {
		return boolClassVal;
	}

	public void setBoolClassVal(Boolean boolClassVal) {
		this.boolClassVal = boolClassVal;
	}

	public boolean isBoolVal() {
		return boolVal;
	}

	public void setBoolVal(boolean boolVal) {
		this.boolVal = boolVal;
	}

	public Byte getByteClassVal() {
		return byteClassVal;
	}

	public void setByteClassVal(Byte byteClassVal) {
		this.byteClassVal = byteClassVal;
	}

	public byte getByteVal() {
		return byteVal;
	}

	public void setByteVal(byte byteVal) {
		this.byteVal = byteVal;
	}

	public Character getCharClassVal() {
		return charClassVal;
	}

	public void setCharClassVal(Character charClassVal) {
		this.charClassVal = charClassVal;
	}

	public char getCharVal() {
		return charVal;
	}

	public void setCharVal(char charVal) {
		this.charVal = charVal;
	}

	public Date getDateVal() {
		return dateVal;
	}

	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}

	public Double getDoubleClassVal() {
		return doubleClassVal;
	}

	public void setDoubleClassVal(Double doubleClassVal) {
		this.doubleClassVal = doubleClassVal;
	}

	public double getDoubleVal() {
		return doubleVal;
	}

	public void setDoubleVal(double doubleVal) {
		this.doubleVal = doubleVal;
	}

	public Float getFloatClassVal() {
		return floatClassVal;
	}

	public void setFloatClassVal(Float floatClassVal) {
		this.floatClassVal = floatClassVal;
	}

	public float getFloatVal() {
		return floatVal;
	}

	public void setFloatVal(float floatVal) {
		this.floatVal = floatVal;
	}

	public Integer getIntClassVal() {
		return intClassVal;
	}

	public void setIntClassVal(Integer intClassVal) {
		this.intClassVal = intClassVal;
	}

	public int getIntVal() {
		return intVal;
	}

	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	public Long getLongClassVal() {
		return longClassVal;
	}

	public void setLongClassVal(Long longClassVal) {
		this.longClassVal = longClassVal;
	}

	public long getLongVal() {
		return longVal;
	}

	public void setLongVal(long longVal) {
		this.longVal = longVal;
	}

	public Short getShortClassVal() {
		return shortClassVal;
	}

	public void setShortClassVal(Short shortClassVal) {
		this.shortClassVal = shortClassVal;
	}

	public short getShortVal() {
		return shortVal;
	}

	public void setShortVal(short shortVal) {
		this.shortVal = shortVal;
	}

	public String getStringVal() {
		return stringVal;
	}

	public void setStringVal(String stringval) {
		this.stringVal = stringval;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PseudoPrimitiveTestClass other = (PseudoPrimitiveTestClass) obj;
		if (this.intVal != other.intVal) {
			return false;
		}
		if (this.longVal != other.longVal) {
			return false;
		}
		if (this.byteVal != other.byteVal) {
			return false;
		}
		if (this.shortVal != other.shortVal) {
			return false;
		}
		if (Float.floatToIntBits(this.floatVal) != Float.floatToIntBits(other.floatVal)) {
			return false;
		}
		if (Double.doubleToLongBits(this.doubleVal) != Double.doubleToLongBits(other.doubleVal)) {
			return false;
		}
		if (this.charVal != other.charVal) {
			return false;
		}
		if (this.boolVal != other.boolVal) {
			return false;
		}
		if (this.intClassVal != other.intClassVal && (this.intClassVal == null || !this.intClassVal.equals(other.intClassVal))) {
			return false;
		}
		if (this.longClassVal != other.longClassVal && (this.longClassVal == null || !this.longClassVal.equals(other.longClassVal))) {
			return false;
		}
		if (this.byteClassVal != other.byteClassVal && (this.byteClassVal == null || !this.byteClassVal.equals(other.byteClassVal))) {
			return false;
		}
		if (this.shortClassVal != other.shortClassVal && (this.shortClassVal == null || !this.shortClassVal.equals(other.shortClassVal))) {
			return false;
		}
		if (this.floatClassVal != other.floatClassVal && (this.floatClassVal == null || !this.floatClassVal.equals(other.floatClassVal))) {
			return false;
		}
		if (this.doubleClassVal != other.doubleClassVal && (this.doubleClassVal == null || !this.doubleClassVal.equals(other.doubleClassVal))) {
			return false;
		}
		if (this.charClassVal != other.charClassVal && (this.charClassVal == null || !this.charClassVal.equals(other.charClassVal))) {
			return false;
		}
		if (this.boolClassVal != other.boolClassVal && (this.boolClassVal == null || !this.boolClassVal.equals(other.boolClassVal))) {
			return false;
		}
		if ((this.stringVal == null) ? (other.stringVal != null) : !this.stringVal.equals(other.stringVal)) {
			return false;
		}
		if (this.dateVal != other.dateVal && (this.dateVal == null || !this.dateVal.equals(other.dateVal))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + this.intVal;
		hash = 97 * hash + (int) (this.longVal ^ (this.longVal >>> 32));
		hash = 97 * hash + this.byteVal;
		hash = 97 * hash + this.shortVal;
		hash = 97 * hash + Float.floatToIntBits(this.floatVal);
		hash = 97 * hash + (int) (Double.doubleToLongBits(this.doubleVal) ^ (Double.doubleToLongBits(this.doubleVal) >>> 32));
		hash = 97 * hash + this.charVal;
		hash = 97 * hash + (this.boolVal ? 1 : 0);
		hash = 97 * hash + (this.intClassVal != null ? this.intClassVal.hashCode() : 0);
		hash = 97 * hash + (this.longClassVal != null ? this.longClassVal.hashCode() : 0);
		hash = 97 * hash + (this.byteClassVal != null ? this.byteClassVal.hashCode() : 0);
		hash = 97 * hash + (this.shortClassVal != null ? this.shortClassVal.hashCode() : 0);
		hash = 97 * hash + (this.floatClassVal != null ? this.floatClassVal.hashCode() : 0);
		hash = 97 * hash + (this.doubleClassVal != null ? this.doubleClassVal.hashCode() : 0);
		hash = 97 * hash + (this.charClassVal != null ? this.charClassVal.hashCode() : 0);
		hash = 97 * hash + (this.boolClassVal != null ? this.boolClassVal.hashCode() : 0);
		hash = 97 * hash + (this.stringVal != null ? this.stringVal.hashCode() : 0);
		hash = 97 * hash + (this.dateVal != null ? this.dateVal.hashCode() : 0);
		return hash;
	}
	
	

}