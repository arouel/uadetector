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

import junit.framework.Assert;

import org.junit.Test;

public class BrowserTypeTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructor_id_toSmall() {
		new BrowserType(-1, "Email client");
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_name_null() {
		new BrowserType(1, null);
	}

	@Test
	public void equals_different() {
		// different id
		final BrowserType type1 = new BrowserType(1, "Email client");
		final BrowserType type2 = new BrowserType(2, "Email client");
		Assert.assertFalse(type1.equals(type2));

		// different name
		final BrowserType type3 = new BrowserType(1, "Browser");
		final BrowserType type4 = new BrowserType(1, "Email client");
		Assert.assertFalse(type3.equals(type4));

		// different class
		final BrowserType type5 = new BrowserType(1, "Browser");
		final String type6 = "";
		Assert.assertFalse(type5.equals(type6));

		// different to null
		final BrowserType type7 = new BrowserType(1, "Browser");
		final BrowserType type8 = null;
		Assert.assertFalse(type7.equals(type8));
	}

	@Test
	public void equals_identical() {
		final BrowserType type1 = new BrowserType(1, "Email client");
		final BrowserType type2 = new BrowserType(1, "Email client");
		Assert.assertTrue(type1.equals(type2));
	}

	@Test
	public void equals_same() {
		final BrowserType type = new BrowserType(1, "Email client");
		Assert.assertTrue(type.equals(type));
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final BrowserType type = new BrowserType(1, "Email client");
		Assert.assertEquals("BrowserType [id=1, name=Email client]", type.toString());
	}

}
