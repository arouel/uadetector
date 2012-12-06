package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.lang.reflect.Constructor;

import net.sf.uadetector.UserAgentFamily;

import org.junit.Assert;
import org.junit.Test;

public class HashCodeGeneratorTest {

	@Test
	public void generate_forBrowser() {
		final String hash = HashCodeGenerator.generate(BrowserHashCodeBuilderTest.create());
		Assert.assertFalse(hash.isEmpty());
	}

	@Test
	public void generate_forBrowserPattern() {
		final String hash = HashCodeGenerator.generate(OrderedPatternHashCodeBuilderTest.createBrowserPattern());
		Assert.assertFalse(hash.isEmpty());
	}

	@Test
	public void generate_forBrowserType() {
		final String hash = HashCodeGenerator.generate(BrowserTypeHashCodeBuilderTest.create());
		Assert.assertFalse(hash.isEmpty());
	}

	@Test
	public void generate_forOperatingSystem() {
		final String hash = HashCodeGenerator.generate(OperatingSystemHashCodeBuilderTest.create());
		Assert.assertFalse(hash.isEmpty());
	}

	@Test
	public void generate_forOperatingSystemPattern() {
		final String hash = HashCodeGenerator.generate(OrderedPatternHashCodeBuilderTest.createOperatingSystemPattern());
		Assert.assertFalse(hash.isEmpty());
	}

	@Test
	public void generate_forUserAgentFamily() {
		final String hash = HashCodeGenerator.generate(UserAgentFamily.FIREFOX);
		Assert.assertFalse(hash.isEmpty());
	}

	@Test
	public void generate_UNKNOWN() {
		final String hash = HashCodeGenerator.generate("unknown type");
		Assert.assertTrue(hash.isEmpty());
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<BrowserTypeHashCodeBuilder> constructor = BrowserTypeHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

}
