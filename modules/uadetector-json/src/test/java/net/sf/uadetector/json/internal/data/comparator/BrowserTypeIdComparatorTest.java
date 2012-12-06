package net.sf.uadetector.json.internal.data.comparator;

import net.sf.uadetector.internal.data.domain.BrowserType;

import org.junit.Assert;
import org.junit.Test;

public class BrowserTypeIdComparatorTest {

	private static final BrowserType BROWSER = new BrowserType(1, "Browser");

	private static final BrowserType MOBILE_BROWSER = new BrowserType(2, "Mobile Browser");

	@Test
	public void compare_bothNull() {
		Assert.assertEquals(0, new BrowserTypeIdComparator().compare(null, null));
	}

	@Test
	public void compare_equalsBoth() {
		Assert.assertEquals(0, new BrowserTypeIdComparator().compare(BROWSER, BROWSER));
		Assert.assertEquals(0, new BrowserTypeIdComparator().compare(MOBILE_BROWSER, MOBILE_BROWSER));
	}

	@Test
	public void compare_leftHigher() {
		Assert.assertEquals(-1, new BrowserTypeIdComparator().compare(BROWSER, MOBILE_BROWSER));
	}

	@Test
	public void compare_leftNull() {
		Assert.assertEquals(-1, new BrowserTypeIdComparator().compare(null, BROWSER));
	}

	@Test
	public void compare_rightHigher() {
		Assert.assertEquals(1, new BrowserTypeIdComparator().compare(MOBILE_BROWSER, BROWSER));
	}

	@Test
	public void compare_rightNull() {
		Assert.assertEquals(1, new BrowserTypeIdComparator().compare(BROWSER, null));
	}

}
