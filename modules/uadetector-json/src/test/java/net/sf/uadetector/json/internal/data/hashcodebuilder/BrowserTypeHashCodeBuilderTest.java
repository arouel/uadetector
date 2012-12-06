package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.lang.reflect.Constructor;

import net.sf.uadetector.internal.data.domain.BrowserType;

import org.junit.Assert;
import org.junit.Test;

public class BrowserTypeHashCodeBuilderTest {

	protected static final BrowserType create() {
		return new BrowserType(1, "Browser");
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<BrowserTypeHashCodeBuilder> constructor = BrowserTypeHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void test() {
		final String hash1 = BrowserTypeHashCodeBuilder.build(create());
		final String hash2 = BrowserTypeHashCodeBuilder.build(create());
		Assert.assertEquals(hash1, hash2);
		Assert.assertEquals("d31de1a5c5c8ba2a210a167cf0d0dc2425c57ea7525f4b73a4b7ab934af79dfc", hash2);
	}

}
