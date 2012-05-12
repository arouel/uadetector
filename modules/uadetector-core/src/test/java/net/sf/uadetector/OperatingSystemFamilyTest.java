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
