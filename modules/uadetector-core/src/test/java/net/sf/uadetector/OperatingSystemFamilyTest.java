package net.sf.uadetector;

import junit.framework.Assert;

import org.junit.Test;

public class OperatingSystemFamilyTest {

	@Test
	public void evaluateByFamily_emptyString() {
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, OperatingSystemFamily.evaluateByFamily(""));
	}

	@Test
	public void evaluateByFamily_knownString_LINUX() {
		Assert.assertEquals(OperatingSystemFamily.LINUX, OperatingSystemFamily.evaluateByFamily("Linux"));
	}

	@Test
	public void evaluateByFamily_knownString_XMB() {
		Assert.assertEquals(OperatingSystemFamily.XROSSMEDIABAR, OperatingSystemFamily.evaluateByFamily("XrossMediaBar (XMB)"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void evaluateByFamily_null() {
		OperatingSystemFamily.evaluateByFamily(null);
	}

	@Test
	public void evaluateByFamily_unknownString() {
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, OperatingSystemFamily.evaluateByFamily("abcdefghijklmnopqrstuvw"));
	}

}
