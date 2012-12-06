package net.sf.uadetector.json.internal.data.comparator;

import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;

import org.junit.Assert;
import org.junit.Test;

public class OperatingSystemIdComparatorTest {

	private static final OperatingSystem OPERATINGSYSTEM_1 = create(1);

	private static final OperatingSystem OPERATINGSYSTEM_2 = create(2);

	private static final OperatingSystem create(final int id) {
		final String family = "family";
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		return new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test
	public void compare_bothNull() {
		Assert.assertEquals(0, new OperatingSystemIdComparator().compare(null, null));
	}

	@Test
	public void compare_equalsBoth() {
		Assert.assertEquals(0, new OperatingSystemIdComparator().compare(OPERATINGSYSTEM_1, OPERATINGSYSTEM_1));
		Assert.assertEquals(0, new OperatingSystemIdComparator().compare(OPERATINGSYSTEM_2, OPERATINGSYSTEM_2));
	}

	@Test
	public void compare_leftHigher() {
		Assert.assertEquals(-1, new OperatingSystemIdComparator().compare(OPERATINGSYSTEM_1, OPERATINGSYSTEM_2));
	}

	@Test
	public void compare_leftNull() {
		Assert.assertEquals(-1, new OperatingSystemIdComparator().compare(null, OPERATINGSYSTEM_1));
	}

	@Test
	public void compare_rightHigher() {
		Assert.assertEquals(1, new OperatingSystemIdComparator().compare(OPERATINGSYSTEM_2, OPERATINGSYSTEM_1));
	}

	@Test
	public void compare_rightNull() {
		Assert.assertEquals(1, new OperatingSystemIdComparator().compare(OPERATINGSYSTEM_1, null));
	}

}
