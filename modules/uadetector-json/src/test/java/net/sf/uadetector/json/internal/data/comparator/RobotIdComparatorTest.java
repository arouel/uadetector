package net.sf.uadetector.json.internal.data.comparator;

import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;

public class RobotIdComparatorTest {

	private static final Robot ROBOT_1 = create(1);

	private static final Robot ROBOT_2 = create(2);

	private static final Robot create(final int id) {
		final UserAgentFamily family = UserAgentFamily.GOOGLEBOT;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String producer = "producer";
		final String producerUrl = "producer url";
		final String url = "url";
		final String userAgentString = "uas";
		return new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
	}

	@Test
	public void compare_bothNull() {
		Assert.assertEquals(0, new RobotIdComparator().compare(null, null));
	}

	@Test
	public void compare_equalsBoth() {
		Assert.assertEquals(0, new RobotIdComparator().compare(ROBOT_1, ROBOT_1));
		Assert.assertEquals(0, new RobotIdComparator().compare(ROBOT_2, ROBOT_2));
	}

	@Test
	public void compare_leftHigher() {
		Assert.assertEquals(-1, new RobotIdComparator().compare(ROBOT_1, ROBOT_2));
	}

	@Test
	public void compare_leftNull() {
		Assert.assertEquals(-1, new RobotIdComparator().compare(null, ROBOT_1));
	}

	@Test
	public void compare_rightHigher() {
		Assert.assertEquals(1, new RobotIdComparator().compare(ROBOT_2, ROBOT_1));
	}

	@Test
	public void compare_rightNull() {
		Assert.assertEquals(1, new RobotIdComparator().compare(ROBOT_1, null));
	}

}
