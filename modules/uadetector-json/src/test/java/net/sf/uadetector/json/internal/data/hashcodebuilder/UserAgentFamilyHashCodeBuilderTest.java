package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.lang.reflect.Constructor;

import net.sf.uadetector.UserAgentFamily;

import org.junit.Assert;
import org.junit.Test;

public class UserAgentFamilyHashCodeBuilderTest {

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<UserAgentFamilyHashCodeBuilder> constructor = UserAgentFamilyHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void test() {
		final String hash1 = UserAgentFamilyHashCodeBuilder.build(UserAgentFamily.CAMINO);
		final String hash2 = UserAgentFamilyHashCodeBuilder.build(UserAgentFamily.CAMINO);
		Assert.assertEquals(hash1, hash2);
		Assert.assertEquals("565bfb6dc1b336d2f42c7d27e12e3f8a75fc8441580002b3fa5e5431e90b1a52", hash2);
	}

}
