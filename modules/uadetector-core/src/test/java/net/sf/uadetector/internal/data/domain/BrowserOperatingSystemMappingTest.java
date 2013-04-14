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

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class BrowserOperatingSystemMappingTest {

	@Test(expected = IllegalNegativeArgumentException.class)
	public void construct_browserId_toSmall() {
		final int browserId = -1;
		final int operatingSystemId = 1;
		new BrowserOperatingSystemMapping(browserId, operatingSystemId);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void construct_operatingSystemId_toSmall() {
		final int browserId = 1;
		final int operatingSystemId = -1;
		new BrowserOperatingSystemMapping(browserId, operatingSystemId);
	}

	@Test
	public void construct_successful() {
		final int browserId = 1;
		final int operatingSystemId = 1;
		new BrowserOperatingSystemMapping(browserId, operatingSystemId);
	}

	@Test
	public void equals_differentBrowserId() {
		final BrowserOperatingSystemMapping m1 = new BrowserOperatingSystemMapping(1, 1);
		final BrowserOperatingSystemMapping m2 = new BrowserOperatingSystemMapping(2, 1);
		Assert.assertFalse(m1.hashCode() == m2.hashCode());
		Assert.assertFalse(m1.equals(m2));
	}

	@Test
	public void equals_differentOperatingSystemId() {
		final BrowserOperatingSystemMapping m1 = new BrowserOperatingSystemMapping(1, 1);
		final BrowserOperatingSystemMapping m2 = new BrowserOperatingSystemMapping(1, 2);
		Assert.assertFalse(m1.hashCode() == m2.hashCode());
		Assert.assertFalse(m1.equals(m2));
	}

	@Test
	public void equals_identical() {
		final BrowserOperatingSystemMapping m1 = new BrowserOperatingSystemMapping(1, 1);
		final BrowserOperatingSystemMapping m2 = new BrowserOperatingSystemMapping(1, 1);
		Assert.assertTrue(m1.hashCode() == m2.hashCode());
		Assert.assertTrue(m1.equals(m2));
	}

	@Test
	public void equals_null() {
		final BrowserOperatingSystemMapping m = new BrowserOperatingSystemMapping(1, 1);
		Assert.assertFalse(m.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final BrowserOperatingSystemMapping m = new BrowserOperatingSystemMapping(1, 1);
		final String otherClass = "";
		Assert.assertFalse(m.equals(otherClass));
	}

	@Test
	public void equals_same() {
		final BrowserOperatingSystemMapping m = new BrowserOperatingSystemMapping(12345, 98765);
		Assert.assertTrue(m.equals(m));
		Assert.assertTrue(m.hashCode() == m.hashCode());
	}

	@Test
	public void testGetters() {
		final int browserId = 12345;
		final int operatingSystemId = 98765;
		final BrowserOperatingSystemMapping b = new BrowserOperatingSystemMapping(browserId, operatingSystemId);
		Assert.assertEquals(12345, b.getBrowserId());
		Assert.assertEquals(98765, b.getOperatingSystemId());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final BrowserOperatingSystemMapping m = new BrowserOperatingSystemMapping(12345, 98765);
		Assert.assertEquals("BrowserOperatingSystemMapping [browserId=12345, operatingSystemId=98765]", m.toString());
	}

}
