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
