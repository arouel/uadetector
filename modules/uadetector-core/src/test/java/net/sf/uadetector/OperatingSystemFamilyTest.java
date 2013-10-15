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
package net.sf.uadetector;

import static org.fest.assertions.Assertions.assertThat;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class OperatingSystemFamilyTest {

	@Test
	public void evaluate_emptyString() {
		assertThat(OperatingSystemFamily.evaluate("")).isEqualTo(OperatingSystemFamily.UNKNOWN);
	}

	@Test
	public void evaluate_knownString_LINUX() {
		assertThat(OperatingSystemFamily.evaluate("Linux")).isEqualTo(OperatingSystemFamily.LINUX);
	}

	@Test
	public void evaluate_knownString_XMB() {
		assertThat(OperatingSystemFamily.evaluate("XrossMediaBar (XMB)")).isEqualTo(OperatingSystemFamily.XROSSMEDIABAR);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluate_null() {
		OperatingSystemFamily.evaluate(null);
	}

	@Test
	public void evaluate_unknownString() {
		assertThat(OperatingSystemFamily.evaluate("abcdefghijklmnopqrstuvw")).isEqualTo(OperatingSystemFamily.UNKNOWN);
	}

	@Test
	public void evaluateByName_emptyString() {
		assertThat(OperatingSystemFamily.evaluateByName("")).isEqualTo(OperatingSystemFamily.UNKNOWN);
	}

	@Test
	public void evaluateByName_knownString_IOS() {
		assertThat(OperatingSystemFamily.evaluateByName("iOS")).isEqualTo(OperatingSystemFamily.IOS);
		assertThat(OperatingSystemFamily.evaluateByName("iPhone OS")).isNotEqualTo(OperatingSystemFamily.IOS);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluateByName_null() {
		OperatingSystemFamily.evaluateByName(null);
	}

	@Test
	public void evaluateByName_unknownString() {
		assertThat(OperatingSystemFamily.evaluateByName("abcdefghijklmnopqrstuvw")).isEqualTo(OperatingSystemFamily.UNKNOWN);
	}

	@Test
	public void evaluateByPattern_emptyString() {
		assertThat(OperatingSystemFamily.evaluateByPattern("")).isEqualTo(OperatingSystemFamily.UNKNOWN);
	}

	@Test
	public void evaluateByPattern_knownString_IOS() {
		assertThat(OperatingSystemFamily.evaluateByPattern("iOS")).isEqualTo(OperatingSystemFamily.IOS);
		assertThat(OperatingSystemFamily.evaluateByPattern("iPhone OS")).isEqualTo(OperatingSystemFamily.IOS);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluateByPattern_null() {
		OperatingSystemFamily.evaluateByPattern(null);
	}

	@Test
	public void evaluateByPattern_unknownString() {
		assertThat(OperatingSystemFamily.evaluateByPattern("abcdefghijklmnopqrstuvw")).isEqualTo(OperatingSystemFamily.UNKNOWN);
	}

}
