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

import junit.framework.Assert;

import org.junit.Test;

public class OperatingSystemFamilyTest {

	@Test
	public void evaluateByFamily_emptyString() {
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, OperatingSystemFamily.evaluateByFamilyName(""));
	}

	@Test
	public void evaluateByFamily_knownString_LINUX() {
		Assert.assertEquals(OperatingSystemFamily.LINUX, OperatingSystemFamily.evaluateByFamilyName("Linux"));
	}

	@Test
	public void evaluateByFamily_knownString_XMB() {
		Assert.assertEquals(OperatingSystemFamily.XROSSMEDIABAR, OperatingSystemFamily.evaluateByFamilyName("XrossMediaBar (XMB)"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void evaluateByFamily_null() {
		OperatingSystemFamily.evaluateByFamilyName(null);
	}

	@Test
	public void evaluateByFamily_unknownString() {
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, OperatingSystemFamily.evaluateByFamilyName("abcdefghijklmnopqrstuvw"));
	}

}
