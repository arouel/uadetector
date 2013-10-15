/*******************************************************************************
 * Copyright 2012 André Rouél
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
package net.sf.uadetector.internal.data.domain;

import java.util.regex.Pattern;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class OperatingSystemPatternTest {

	@Test
	public void compare_differentPatternFlags() {
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+", Pattern.MULTILINE), 1);
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE), 1);
		assertThat(pattern1.equals(pattern2)).isFalse();
		assertThat(pattern2.equals(pattern1)).isFalse();
		assertThat(pattern1.compareTo(pattern2)).isEqualTo(1);
		assertThat(pattern2.compareTo(pattern1)).isEqualTo(-1);
	}

	@Test
	public void compare_identicalPatternFlags() {
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+", Pattern.MULTILINE
				| Pattern.CASE_INSENSITIVE), 1);
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE
				| Pattern.MULTILINE), 1);
		assertThat(pattern1.equals(pattern2)).isTrue();
		assertThat(pattern1.compareTo(pattern2)).isEqualTo(0);
	}

	@Test
	public void compareTo_differentPosition() {
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 2);
		assertThat(pattern1.compareTo(pattern2)).isEqualTo(-1);
		assertThat(pattern2.compareTo(pattern1)).isEqualTo(1);
	}

	@Test
	public void compareTo_identical() {
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		assertThat(pattern1.compareTo(pattern2)).isEqualTo(0);
	}

	@Test
	public void compareTo_null() {
		final OperatingSystemPattern pattern = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		assertThat(pattern.compareTo(null)).isEqualTo(-1);
	}

	@Test
	public void compareTo_same() {
		final OperatingSystemPattern pattern = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		assertThat(pattern.compareTo(pattern)).isEqualTo(0);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void constructor_id_toSmall() {
		new OperatingSystemPattern(-1, Pattern.compile("[0-9]+"), 1);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void constructor_order_toSmall() {
		new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), -1);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void constructor_pattern_null() {
		new OperatingSystemPattern(1, null, 1);
	}

	@Test
	public void equals_different_flags() {
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE), 1);
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+", Pattern.MULTILINE), 1);
		assertThat(pattern1.equals(pattern2)).isFalse();
		assertThat(pattern1.hashCode() == pattern2.hashCode()).isFalse();
	}

	@Test
	public void equals_different_id() {
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(2, Pattern.compile("[0-9]+"), 1);
		assertThat(pattern1.equals(pattern2)).isFalse();
		assertThat(pattern1.hashCode() == pattern2.hashCode()).isFalse();
		assertThat(pattern1.compareTo(pattern2)).isEqualTo(-1);
	}

	@Test
	public void equals_different_pattern() {
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(1, Pattern.compile("[a-z]+"), 1);
		assertThat(pattern1.equals(pattern2)).isFalse();
		assertThat(pattern1.hashCode() == pattern2.hashCode()).isFalse();
		assertThat(pattern1.compareTo(pattern2) >= 0).isFalse();
	}

	@Test
	public void equals_different_position() {
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 2);
		assertThat(pattern1.equals(pattern2)).isFalse();
		assertThat(pattern1.hashCode() == pattern2.hashCode()).isFalse();
		assertThat(pattern1.compareTo(pattern2)).isEqualTo(-1);
	}

	@Test
	public void equals_identical() {
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		assertThat(pattern1.equals(pattern2)).isTrue();
		assertThat(pattern1.hashCode() == pattern2.hashCode()).isTrue();
		assertThat(pattern1.compareTo(pattern2)).isEqualTo(0);
	}

	@Test
	public void equals_null() {
		final OperatingSystemPattern pattern = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		assertThat(pattern.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass() {
		final OperatingSystemPattern pattern = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		final BrowserPattern otherClass = new BrowserPattern(1, Pattern.compile("[0-9]+"), 2);
		assertThat(pattern.equals(otherClass)).isFalse();
	}

	@Test
	public void equals_same() {
		final OperatingSystemPattern pattern = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		assertThat(pattern.equals(pattern)).isTrue();
		assertThat(pattern.compareTo(pattern)).isEqualTo(0);
	}

	@Test
	public void testGetters() {
		final Pattern p = Pattern.compile("[0-9]+");
		final OperatingSystemPattern pattern = new OperatingSystemPattern(12345, p, 98765);
		assertThat(pattern.getId()).isEqualTo(12345);
		assertThat(pattern.getPattern()).isSameAs(p);
		assertThat(pattern.getPosition()).isEqualTo(98765);
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final OperatingSystemPattern pattern = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		assertThat(pattern.toString()).isEqualTo("OperatingSystemPattern [id=1, pattern=[0-9]+, position=1]");
	}

}
