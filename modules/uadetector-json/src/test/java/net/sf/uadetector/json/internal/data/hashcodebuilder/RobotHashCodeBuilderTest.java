package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.lang.reflect.Constructor;

import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;

public class RobotHashCodeBuilderTest {

	private static final Robot create() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.YAHOO;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final String userAgentString = "I'm a robot";
		return new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<RobotHashCodeBuilder> constructor = RobotHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void test() {
		final String hash1 = RobotHashCodeBuilder.build(create());
		final String hash2 = RobotHashCodeBuilder.build(create());
		Assert.assertEquals(hash1, hash2);
		Assert.assertEquals("daee7cddc2cc153a54be5440e9310b1d46c3188c2b73c00db6df539132937c88", hash1);
	}

}
