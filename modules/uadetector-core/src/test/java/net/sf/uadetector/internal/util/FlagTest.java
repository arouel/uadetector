/*******************************************************************************
 * Copyright 2013 André Rouél
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.uadetector.internal.util;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.internal.util.RegularExpressionConverter.Flag;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class FlagTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void convertToBitmask_null() {
		Flag.convertToBitmask(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void convertToModifiers_null() {
		Flag.convertToModifiers(null);
	}

	@Test
	public void evaluateByCharacter_i() {
		assertThat(Flag.evaluateByCharacter('i')).isEqualTo(Flag.CASE_INSENSITIVE);
	}

	@Test
	public void evaluateByCharacter_s() {
		assertThat(Flag.evaluateByCharacter('s')).isEqualTo(Flag.DOTALL);
	}

	@Test
	public void evaluateByCharacter_unknown() {
		assertThat(Flag.evaluateByCharacter('1')).isEqualTo(null);
	}

	@Test
	public void evaluateByNumber_maxInteger() {
		assertThat(Flag.evaluateByNumber(Integer.MAX_VALUE)).isEqualTo(null);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void evaluateByNumber_negativeNumber() {
		Flag.evaluateByNumber(-1);
	}

	@Test
	public void evaluateByNumber_UNIX_LINES() {
		assertThat(Flag.evaluateByNumber(1)).isEqualTo(Flag.UNIX_LINES);
	}

	@Test
	public void parse_bitmaskThree() {
		assertThat(Flag.parse(3)).isEqualTo(EnumSet.of(Flag.CASE_INSENSITIVE, Flag.UNIX_LINES));
	}

	@Test
	public void parse_imsx() {
		final Set<Flag> flags = Flag.parse("imsx");
		final EnumSet<Flag> expected = EnumSet.of(Flag.CASE_INSENSITIVE, Flag.DOTALL, Flag.MULTILINE, Flag.COMMENTS);
		assertThat(flags.containsAll(expected)).isTrue();
		assertThat(expected.containsAll(flags)).isTrue();
	}

	@Test
	public void parse_is() {
		final Set<Flag> flags = Flag.parse("is");
		final EnumSet<Flag> expected = EnumSet.of(Flag.CASE_INSENSITIVE, Flag.DOTALL);
		assertThat(flags.containsAll(expected)).isTrue();
		assertThat(expected.containsAll(flags)).isTrue();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void parse_negativeNumber() {
		Flag.parse(-1);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void parse_null() {
		Flag.parse((String) null);
	}

	@Test
	public void parse_si() {
		final Set<Flag> flags = Flag.parse("si");
		final EnumSet<Flag> expected = EnumSet.of(Flag.CASE_INSENSITIVE, Flag.DOTALL);
		assertThat(flags.containsAll(expected)).isTrue();
		assertThat(expected.containsAll(flags)).isTrue();
	}

	@Test
	public void parse_Si() {
		final Set<Flag> flags = Flag.parse("Si");
		final EnumSet<Flag> expected = EnumSet.of(Flag.CASE_INSENSITIVE);
		assertThat(flags.containsAll(expected)).isTrue();
		assertThat(expected.containsAll(flags)).isTrue();
	}

	@Test
	public void parse_unknownBigBitmask() {
		assertThat(Flag.parse(Integer.MAX_VALUE)).isEqualTo(EnumSet.allOf(Flag.class));
	}

	@Test
	public void parse_zero() {
		assertThat(Flag.parse(0)).isEqualTo(new HashSet<Flag>(0));
	}

	@Test
	public void parseAndConvert() {
		final String modifiers = "imsx";
		final Set<Flag> convertedOnce = Flag.parse("imsx");
		final EnumSet<Flag> expected = EnumSet.of(Flag.CASE_INSENSITIVE, Flag.DOTALL, Flag.MULTILINE, Flag.COMMENTS);
		final Set<Flag> convertedTwice = Flag.parse(Flag.convertToModifiers(convertedOnce));

		// testing completeness of all flags
		assertThat(convertedTwice.containsAll(expected)).isTrue();
		assertThat(expected.containsAll(convertedTwice)).isTrue();

		// testing character ordering
		assertThat(Flag.convertToModifiers(convertedOnce)).isEqualTo(new StringBuffer(modifiers).reverse().toString());
		assertThat(Flag.convertToModifiers(convertedTwice)).isEqualTo(new StringBuffer(modifiers).reverse().toString());
	}

}
