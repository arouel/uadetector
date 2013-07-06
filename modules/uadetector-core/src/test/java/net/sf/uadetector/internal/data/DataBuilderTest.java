/*******************************************************************************
 * Copyright 2012 André Rouél
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.uadetector.internal.data;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserOperatingSystemMapping;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;

public class DataBuilderTest {

	@Test
	public void appendBrowser() {
		final DataBuilder b = new DataBuilder();
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser br = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.FIREFOX, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertSame(b, b.appendBrowser(br));
		Assert.assertSame(b, b.appendBrowser(br)); // testing to add same one more time
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendBrowser_null() {
		final DataBuilder b = new DataBuilder();
		b.appendBrowser(null);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void appendBrowserBuilder_addSameOneMoreTime() {
		final DataBuilder b = new DataBuilder();
		final Browser.Builder builder = new Browser.Builder();
		builder.setId(1);
		builder.setFamily(UserAgentFamily.FIREFOX);
		builder.setType(new BrowserType(1, "Browser"));
		Assert.assertSame(b, b.appendBrowserBuilder(builder));
		Assert.assertSame(b, b.appendBrowserBuilder(builder)); // testing to add same one more time
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void appendBrowserBuilder_id_toSmall() {
		final DataBuilder b = new DataBuilder();
		b.appendBrowserBuilder(new Browser.Builder());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendBrowserBuilder_null() {
		final DataBuilder b = new DataBuilder();
		b.appendBrowserBuilder(null);
	}

	@Test
	public void appendBrowserBuilder_successful_testCopyFunction() {
		final DataBuilder d = new DataBuilder().setVersion("test version");
		final Browser.Builder builder = new Browser.Builder();
		builder.setId(1);
		builder.setFamily(UserAgentFamily.FIREFOX);
		builder.setType(new BrowserType(1, "Browser"));
		Assert.assertSame(d, d.appendBrowserBuilder(builder));
		builder.setId(2);
		builder.setFamily(UserAgentFamily.CHROME);
		builder.setType(new BrowserType(1, "Browser"));
		Assert.assertSame(d, d.appendBrowserBuilder(builder));
		final Data data = d.build();
		Assert.assertEquals(2, data.getBrowsers().size());
	}

	@Test
	public void appendBrowserBuilder_successful_withoutPattern() {
		final DataBuilder d = new DataBuilder().setVersion("test version");
		final Browser.Builder b1 = new Browser.Builder();
		b1.setId(1);
		b1.setFamily(UserAgentFamily.FIREFOX);
		b1.setType(new BrowserType(1, "Browser"));
		Assert.assertSame(d, d.appendBrowserBuilder(b1));
		final Browser.Builder b2 = new Browser.Builder();
		b2.setId(2);
		b2.setFamily(UserAgentFamily.CHROME);
		b2.setType(new BrowserType(1, "Browser"));
		Assert.assertSame(d, d.appendBrowserBuilder(b2));
		final Data data = d.build();
		Assert.assertEquals(2, data.getBrowsers().size());
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void appendBrowserBuilder_withoutType() {
		final DataBuilder b = new DataBuilder();
		final Browser.Builder builder = new Browser.Builder();
		builder.setId(1);
		builder.setFamily(UserAgentFamily.FIREFOX);
		b.appendBrowserBuilder(builder);
	}

	@Test
	public void appendBrowserBuilder_withTypeId() {
		final DataBuilder d = new DataBuilder().setVersion("test version");
		final BrowserType type = new BrowserType(2, "Email client");
		d.appendBrowserType(type);
		final Browser.Builder builder = new Browser.Builder();
		builder.setId(1);
		builder.setFamily(UserAgentFamily.FIREFOX);
		builder.setTypeId(2);
		Assert.assertSame(d, d.appendBrowserBuilder(builder));
		final Data data = d.build();
		Assert.assertEquals(1, data.getBrowsers().size());
		Assert.assertEquals(type, data.getBrowsers().iterator().next().getType());
	}

	@Test
	public void appendBrowserBuilder_withUnknownTypeId() {
		final DataBuilder d = new DataBuilder().setVersion("test version");
		final Browser.Builder builder = new Browser.Builder();
		builder.setId(1);
		builder.setFamily(UserAgentFamily.FIREFOX);
		builder.setTypeId(1); // type does not exist, a log message occur
		Assert.assertSame(d, d.appendBrowserBuilder(builder));
		final Data data = d.build();
		Assert.assertEquals(0, data.getBrowsers().size());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendBrowserOperatingSystemMapping_null() {
		final DataBuilder b = new DataBuilder();
		b.appendBrowserOperatingSystemMapping(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendBrowserPattern_null() {
		final DataBuilder b = new DataBuilder();
		b.appendBrowserPattern(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendBrowserType() {
		final DataBuilder b = new DataBuilder();
		b.appendBrowserType(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendOperatingSystem_null() {
		final DataBuilder b = new DataBuilder();
		b.appendOperatingSystem(null);
	}

	@Test
	public void appendOperatingSystem_successful() {
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", patternSet, "p1", "pu1", "u1");
		final DataBuilder b = new DataBuilder();
		Assert.assertSame(b, b.appendOperatingSystem(os));
		b.appendOperatingSystem(os); // testing to add same one more time
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void appendOperatingSystemBuilder_addSameOneMoreTime() {
		final OperatingSystem.Builder builder = new OperatingSystem.Builder().setId("1").setFamily("f1").setIcon("i1").setInfoUrl("iu1")
				.setProducer("p1").setProducerUrl("pu1").setUrl("u1");
		final DataBuilder b = new DataBuilder();
		Assert.assertSame(b, b.appendOperatingSystemBuilder(builder));
		b.appendOperatingSystemBuilder(builder); // testing to add same one more time fails
	}

	@Test
	public void testOperatingSystemToBrowserMapping_withoutMatchingEntries() {
		final DataBuilder d = new DataBuilder().setVersion("test version");

		// add mapping
		d.appendBrowserOperatingSystemMapping(new BrowserOperatingSystemMapping(909, 303));

		final Data data = d.build();
		Assert.assertNotNull(data);
		Assert.assertEquals(0, data.getOperatingSystems().size());
	}

	@Test
	public void testOperatingSystemToBrowserMapping_withMatchingEntries() {
		final DataBuilder d = new DataBuilder().setVersion("test version");
		d.appendOperatingSystemPattern(new OperatingSystemPattern(303, Pattern.compile("[0-9]"), 1));

		// browser builder entry
		final Browser.Builder browserBuilder = new Browser.Builder();
		browserBuilder.setId(1);
		browserBuilder.setFamily(UserAgentFamily.FIREFOX);
		browserBuilder.setType(new BrowserType(1, "Browser"));
		d.appendBrowserBuilder(browserBuilder);
		browserBuilder.setId(2);
		browserBuilder.setFamily(UserAgentFamily.CHROME);
		browserBuilder.setType(new BrowserType(1, "Browser"));
		d.appendBrowserBuilder(browserBuilder);

		// operating system entry
		final OperatingSystem.Builder builder = new OperatingSystem.Builder();
		builder.setId(303);
		builder.setName("MyOS");
		d.appendOperatingSystemBuilder(builder);

		// add mapping
		d.appendBrowserOperatingSystemMapping(new BrowserOperatingSystemMapping(1, 303));
		d.appendBrowserOperatingSystemMapping(new BrowserOperatingSystemMapping(2, 909));

		final Data data = d.build();
		Assert.assertEquals(2, data.getBrowsers().size());
		Assert.assertEquals(1, data.getOperatingSystems().size());
		final OperatingSystem os = data.getOperatingSystems().iterator().next();
		Assert.assertEquals("MyOS", os.getName());
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void appendOperatingSystemBuilder_id_toSmall() {
		final DataBuilder b = new DataBuilder();
		b.appendOperatingSystemBuilder(new OperatingSystem.Builder());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendOperatingSystemBuilder_null() {
		final DataBuilder b = new DataBuilder();
		b.appendOperatingSystemBuilder(null);
	}

	@Test
	public void appendOperatingSystemBuilder_onlyWithIdAndPattern() {
		final DataBuilder d = new DataBuilder().setVersion("test version");
		d.appendOperatingSystemPattern(new OperatingSystemPattern(101, Pattern.compile("[0-9]"), 1));

		final OperatingSystem.Builder builder = new OperatingSystem.Builder();
		builder.setId(101);
		d.appendOperatingSystemBuilder(builder);

		final Data data = d.build();
		Assert.assertEquals(1, data.getOperatingSystems().size());
		final OperatingSystem os = data.getOperatingSystems().iterator().next();
		Assert.assertEquals("unknown", os.getName());
	}

	@Test
	public void appendOperatingSystemBuilder_withoutPatternSet() {
		final DataBuilder d = new DataBuilder().setVersion("test version");

		// no matching pattern for the following OS, a log message occur
		d.appendOperatingSystemPattern(new OperatingSystemPattern(200, Pattern.compile("[0-9]"), 1));

		final OperatingSystem.Builder builder = new OperatingSystem.Builder();
		builder.setFamily("f1");
		builder.setIcon("i1");
		builder.setId(100);
		builder.setInfoUrl("iu1");
		builder.setName("n1");
		builder.setProducer("p1");
		builder.setProducerUrl("pu1");
		builder.setUrl("u1");
		d.appendOperatingSystemBuilder(builder);

		final Data data = d.build();
		Assert.assertEquals(1, data.getOperatingSystems().size());
	}

	@Test
	public void appendOperatingSystemPattern_addIdenticalPatternMoreTimes() {
		final DataBuilder b = new DataBuilder();
		b.appendOperatingSystemPattern(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));
		b.appendOperatingSystemPattern(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));
		b.appendOperatingSystemPattern(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendOperatingSystemPattern_null() {
		final DataBuilder b = new DataBuilder();
		b.appendOperatingSystemPattern(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void appendRobot_null() {
		final DataBuilder b = new DataBuilder();
		b.appendRobot(null);
	}

	@Test
	public void appendRobot_successful() {
		final Robot robot = new Robot(UserAgentFamily.BINGBOT, "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final DataBuilder b = new DataBuilder();
		Assert.assertSame(b, b.appendRobot(robot));
		b.appendRobot(robot); // testing to add same one more time
	}

	@Test
	public void build_successful_onlyWithVersionInfo() {
		new DataBuilder().setVersion("empty test version").build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void build_withoutData() {
		new DataBuilder().build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setVersion_null() {
		final DataBuilder b = new DataBuilder();
		b.setVersion(null);
	}

}
