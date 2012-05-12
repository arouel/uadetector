package net.sf.uadetector;

import junit.framework.Assert;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Test;

public class UserAgentTypeTest {

	@Test
	public void evaluateByType_emptyString() {
		Assert.assertEquals(UserAgentType.UNKNOWN, UserAgentType.evaluateByTypeName(""));
	}

	@Test
	public void evaluateByType_knownString_BROWSER() {
		Assert.assertEquals(UserAgentType.BROWSER, UserAgentType.evaluateByTypeName("Browser"));
	}

	@Test
	public void evaluateByType_knownString_ROBOT() {
		Assert.assertEquals(UserAgentType.ROBOT, UserAgentType.evaluateByTypeName(Robot.TYPENAME));
	}

	@Test(expected = IllegalArgumentException.class)
	public void evaluateByType_null() {
		UserAgentType.evaluateByTypeName(null);
	}

	@Test
	public void evaluateByType_unknownString() {
		Assert.assertEquals(UserAgentType.UNKNOWN, UserAgentType.evaluateByTypeName("abcdefghijklmnopqrstuvw"));
	}

}
