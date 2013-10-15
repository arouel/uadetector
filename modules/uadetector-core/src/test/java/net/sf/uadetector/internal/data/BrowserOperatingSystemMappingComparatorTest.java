package net.sf.uadetector.internal.data;

import net.sf.uadetector.internal.data.domain.BrowserOperatingSystemMapping;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class BrowserOperatingSystemMappingComparatorTest {

	private static final BrowserOperatingSystemMapping MAPPING_1 = new BrowserOperatingSystemMapping(1, 1);

	private static final BrowserOperatingSystemMapping MAPPING_2 = new BrowserOperatingSystemMapping(2, 2);

	@Test
	public void compare_bothNull() {
		assertThat(BrowserOperatingSystemMappingComparator.INSTANCE.compare(null, null)).isEqualTo(0);
	}

	@Test
	public void compare_equalsBoth() {
		assertThat(BrowserOperatingSystemMappingComparator.INSTANCE.compare(MAPPING_1, MAPPING_1)).isEqualTo(0);
		assertThat(BrowserOperatingSystemMappingComparator.INSTANCE.compare(MAPPING_2, MAPPING_2)).isEqualTo(0);
	}

	@Test
	public void compare_leftHigher() {
		assertThat(BrowserOperatingSystemMappingComparator.INSTANCE.compare(MAPPING_1, MAPPING_2)).isEqualTo(-1);
	}

	@Test
	public void compare_leftNull() {
		assertThat(BrowserOperatingSystemMappingComparator.INSTANCE.compare(null, MAPPING_1)).isEqualTo(-1);
	}

	@Test
	public void compare_rightHigher() {
		assertThat(BrowserOperatingSystemMappingComparator.INSTANCE.compare(MAPPING_2, MAPPING_1)).isEqualTo(1);
	}

	@Test
	public void compare_rightNull() {
		assertThat(BrowserOperatingSystemMappingComparator.INSTANCE.compare(MAPPING_1, null)).isEqualTo(1);
	}

}
