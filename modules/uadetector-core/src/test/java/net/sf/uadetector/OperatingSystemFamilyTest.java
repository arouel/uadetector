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

import org.junit.Assert;

import org.junit.Test;

public class OperatingSystemFamilyTest {

	@Test
	public void evaluate_emptyString() {
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, OperatingSystemFamily.evaluate(""));
	}

	@Test
	public void evaluate_knownString_LINUX() {
		Assert.assertEquals(OperatingSystemFamily.LINUX, OperatingSystemFamily.evaluate("Linux"));
	}

	@Test
	public void evaluate_knownString_XMB() {
		Assert.assertEquals(OperatingSystemFamily.XROSSMEDIABAR, OperatingSystemFamily.evaluate("XrossMediaBar (XMB)"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void evaluate_null() {
		OperatingSystemFamily.evaluate(null);
	}

	@Test
	public void evaluate_unknownString() {
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, OperatingSystemFamily.evaluate("abcdefghijklmnopqrstuvw"));
	}

	@Test
	public void evaluateByName_emptyString() {
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, OperatingSystemFamily.evaluateByName(""));
	}

	@Test
	public void evaluateByName_knownString_IOS() {
		Assert.assertEquals(OperatingSystemFamily.IOS, OperatingSystemFamily.evaluateByName("iOS"));
		Assert.assertFalse(OperatingSystemFamily.IOS == OperatingSystemFamily.evaluateByName("iPhone OS"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void evaluateByName_null() {
		OperatingSystemFamily.evaluateByName(null);
	}

	@Test
	public void evaluateByName_unknownString() {
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, OperatingSystemFamily.evaluateByName("abcdefghijklmnopqrstuvw"));
	}

	@Test
	public void evaluateByPattern_emptyString() {
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, OperatingSystemFamily.evaluateByPattern(""));
	}

	@Test
	public void evaluateByPattern_knownString_IOS() {
		Assert.assertEquals(OperatingSystemFamily.IOS, OperatingSystemFamily.evaluateByPattern("iOS"));
		Assert.assertEquals(OperatingSystemFamily.IOS, OperatingSystemFamily.evaluateByPattern("iPhone OS"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void evaluateByPattern_null() {
		OperatingSystemFamily.evaluateByPattern(null);
	}

	@Test
	public void evaluateByPattern_unknownString() {
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, OperatingSystemFamily.evaluateByPattern("abcdefghijklmnopqrstuvw"));
	}

}
