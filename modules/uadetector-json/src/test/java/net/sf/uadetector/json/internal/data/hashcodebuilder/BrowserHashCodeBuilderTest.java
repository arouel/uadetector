package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.lang.reflect.Constructor;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;

import org.junit.Assert;
import org.junit.Test;

public class BrowserHashCodeBuilderTest {

	protected static final Browser create() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.FIREFOX;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		patternSet.add(new BrowserPattern(1, Pattern.compile("[0-9]"), 1));
		patternSet.add(new BrowserPattern(2, Pattern.compile("[a-z]"), 2));
		return new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<BrowserHashCodeBuilder> constructor = BrowserHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void test() {
		final String hash1 = BrowserHashCodeBuilder.build(create());
		final String hash2 = BrowserHashCodeBuilder.build(create());
		Assert.assertEquals(hash1, hash2);
		Assert.assertEquals("f590ba4e9e9034d1ff548ac1b12583f06c87bbe805da9b6484257db27e218e39", hash1);
	}

}
