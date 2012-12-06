package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.lang.reflect.Constructor;
import java.util.regex.Pattern;

import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;

import org.junit.Assert;
import org.junit.Test;

public class OrderedPatternHashCodeBuilderTest {

	protected static final BrowserPattern createBrowserPattern() {
		return new BrowserPattern(1, Pattern.compile("[0-9]+"), 123);
	}

	protected static final OperatingSystemPattern createOperatingSystemPattern() {
		return new OperatingSystemPattern(3, Pattern.compile("[A-Z]+", Pattern.CASE_INSENSITIVE), 987);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<OrderedPatternHashCodeBuilder> constructor = OrderedPatternHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testWithBrowserPattern() {
		final String hash1 = OrderedPatternHashCodeBuilder.build(createBrowserPattern());
		final String hash2 = OrderedPatternHashCodeBuilder.build(createBrowserPattern());
		Assert.assertEquals(hash1, hash2);
		Assert.assertEquals("f0a6e328afe4c0d5c8d75852ba614af9a71e9ce8c8405bd36bb56a45943a700a", hash2);
	}

	@Test
	public void testWithOperatingSystemPattern() {
		final String hash1 = OrderedPatternHashCodeBuilder.build(createOperatingSystemPattern());
		final String hash2 = OrderedPatternHashCodeBuilder.build(createOperatingSystemPattern());
		Assert.assertEquals(hash1, hash2);
		Assert.assertEquals("7ab26ea8a1080050329c5a0426eb9bfb4c43ea45d84b29cfaf343583e49e1632", hash2);
	}

}
