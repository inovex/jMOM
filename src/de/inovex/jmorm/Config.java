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

import de.inovex.jmorm.exception.NumericException;

/**
 *
 * @author Tim Roes <tim.roes@inovex.de>
 */
public class Config {
	
	public enum NumericBehavior { OVERFLOW, EXCEPTION, ZERO_VALUE };
	
	public enum CharacterBehavior { FIRST_CHAR, EXCEPTION, ZERO_VALUE };
	
	private NumericBehavior numericBehavior = NumericBehavior.EXCEPTION;
	
	private CharacterBehavior characterBehavior = CharacterBehavior.EXCEPTION;
	
	/**
	 * The {@link NumericBehavior} of the Storage sets the behavior, that should
	 * be done, when the value in the numeric value in the database is too large
	 * to fit into the field. It can be one of the following values:
	 * 
	 * {@link NumericBehavior#OVERFLOW} - Just cast the value, and accept the numeric overflow.
	 * {@link NumericBehavior#EXCEPTION} - Throw an {@link NumericException}.
	 * {@link NumericBehavior#ZERO_VALUE} - Leave the field to it's default value (0 or null).
	 * 
	 * @return The {@link NumericBehavior}, that should be used.
	 */
	public NumericBehavior getNumericBehavior() {
		return numericBehavior;
	}
	
	public CharacterBehavior getCharacterBehavior() {
		return characterBehavior;
	}

	public void setCharacterBehavior(CharacterBehavior characterBehavior) {
		this.characterBehavior = characterBehavior;
	}

	public void setNumericBehavior(NumericBehavior numericBehavior) {
		this.numericBehavior = numericBehavior;
	}
	
}
