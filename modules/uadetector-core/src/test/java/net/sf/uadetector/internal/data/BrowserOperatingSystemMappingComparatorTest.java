package net.sf.uadetector.internal.data;

import net.sf.uadetector.internal.data.domain.BrowserOperatingSystemMapping;

import org.junit.Assert;
import org.junit.Test;

public class BrowserOperatingSystemMappingComparatorTest {

	private static final BrowserOperatingSystemMapping MAPPING_1 = new BrowserOperatingSystemMapping(1, 1);

	private static final BrowserOperatingSystemMapping MAPPING_2 = new BrowserOperatingSystemMapping(2, 2);

	@Test
	public void compare_bothNull() {
		Assert.assertEquals(0, BrowserOperatingSystemMappingComparator.INSTANCE.compare(null, null));
	}

	@Test
	public void compare_equalsBoth() {
		Assert.assertEquals(0, BrowserOperatingSystemMappingComparator.INSTANCE.compare(MAPPING_1, MAPPING_1));
		Assert.assertEquals(0, BrowserOperatingSystemMappingComparator.INSTANCE.compare(MAPPING_2, MAPPING_2));
	}

	@Test
	public void compare_leftHigher() {
		Assert.assertEquals(-1, BrowserOperatingSystemMappingComparator.INSTANCE.compare(MAPPING_1, MAPPING_2));
	}

	@Test
	public void compare_leftNull() {
		Assert.assertEquals(-1, BrowserOperatingSystemMappingComparator.INSTANCE.compare(null, MAPPING_1));
	}

	@Test
	public void compare_rightHigher() {
		Assert.assertEquals(1, BrowserOperatingSystemMappingComparator.INSTANCE.compare(MAPPING_2, MAPPING_1));
	}

	@Test
	public void compare_rightNull() {
		Assert.assertEquals(1, BrowserOperatingSystemMappingComparator.INSTANCE.compare(MAPPING_1, null));
	}

}
