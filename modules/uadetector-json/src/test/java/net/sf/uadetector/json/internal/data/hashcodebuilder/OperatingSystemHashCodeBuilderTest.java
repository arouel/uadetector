package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.lang.reflect.Constructor;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;

import org.junit.Assert;
import org.junit.Test;

public class OperatingSystemHashCodeBuilderTest {

	protected static final OperatingSystem create() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String family = "f1";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		patternSet.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));
		patternSet.add(new OperatingSystemPattern(2, Pattern.compile("[a-z]"), 2));
		return new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<OperatingSystemHashCodeBuilder> constructor = OperatingSystemHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void test() {
		final String hash1 = OperatingSystemHashCodeBuilder.build(create());
		final String hash2 = OperatingSystemHashCodeBuilder.build(create());
		Assert.assertEquals(hash1, hash2);
		Assert.assertEquals("cff51b9b31579a45cc4982ed536ef7ca162fce780024582d1f14782ba900b0e9", hash2);
	}

}
