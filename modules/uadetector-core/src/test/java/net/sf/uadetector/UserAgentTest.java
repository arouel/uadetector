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
package net.sf.uadetector;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.parser.VersionParser;

import org.junit.Assert;
import org.junit.Test;

public class UserAgentTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_family_null() {
		new UserAgent(null, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url", UserAgentType.BROWSER, "type", "url",
				VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_icon_null() {
		new UserAgent(UserAgentFamily.CHROMIUM, null, "name", OperatingSystem.EMPTY, "producer", "producer url", UserAgentType.BROWSER,
				"type", "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_null() {
		new UserAgent(UserAgentFamily.CHROMIUM, "icon", null, OperatingSystem.EMPTY, "producer", "producer url", UserAgentType.BROWSER,
				"type", "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_os_null() {
		new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", null, "producer", "producer url", UserAgentType.BROWSER, "type", "url",
				VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_producer_null() {
		new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, null, "producer url", UserAgentType.BROWSER, "type",
				"url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_producerUrl_null() {
		new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", null, UserAgentType.BROWSER, "type",
				"url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_type_null() {
		new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url", null, "type", "url",
				VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_typeName_null() {
		new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url", UserAgentType.BROWSER,
				null, "url", VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_url_null() {
		new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url", UserAgentType.BROWSER,
				"type", null, VersionParser.parseVersion("1"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_version_null() {
		new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url", UserAgentType.BROWSER,
				"type", "url", null);
	}

	@Test
	public void empty() {
		final UserAgent ua = UserAgent.EMPTY;
		Assert.assertEquals(UserAgentFamily.UNKNOWN, ua.getFamily());
		Assert.assertEquals("unknown", ua.getName());
		Assert.assertEquals("", ua.getProducer());
		Assert.assertEquals("", ua.getProducerUrl());
		Assert.assertEquals("", ua.getUrl());
	}

	@Test
	public void equals_differentFamily() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROME, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROME_MOBILE, "icon", "name", OperatingSystem.EMPTY, "producer",
				"producer url", UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertFalse(ua1.equals(ua2));
		Assert.assertFalse(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_differentIcon() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon1", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon2", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertFalse(ua1.equals(ua2));
		Assert.assertFalse(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_differentName() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name1", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name2", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertFalse(ua1.equals(ua2));
		Assert.assertFalse(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_differentOperatingSystem() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os1 = new OperatingSystem(linux, "Gentoo", "icon", "name1", "p", "pUrl", "url", new VersionNumber("1"));
		final OperatingSystem os2 = new OperatingSystem(linux, "Gentoo", "icon", "name2", "p", "pUrl", "url", new VersionNumber("1"));
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", os1, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", os2, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertFalse(ua1.equals(ua2));
		Assert.assertFalse(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_differentProducer() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer1", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer2", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertFalse(ua1.equals(ua2));
		Assert.assertFalse(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_differentProducerUrl() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url 1",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url 2",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertFalse(ua1.equals(ua2));
		Assert.assertFalse(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_differentType() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.LIBRARY, "type", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.ROBOT, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertFalse(ua1.equals(ua2));
		Assert.assertFalse(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_differentTypeName() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type1", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type2", "url", VersionParser.parseVersion("1"));
		Assert.assertFalse(ua1.equals(ua2));
		Assert.assertFalse(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_differentUrl() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url1", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url2", VersionParser.parseVersion("1"));
		Assert.assertFalse(ua1.equals(ua2));
		Assert.assertFalse(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_differentVersionNumber() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("2"));
		Assert.assertFalse(ua1.equals(ua2));
		Assert.assertFalse(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_EMPTY() {
		Assert.assertEquals(UserAgent.EMPTY, UserAgent.EMPTY);
		Assert.assertTrue(UserAgent.EMPTY.hashCode() == UserAgent.EMPTY.hashCode());
		Assert.assertSame(UserAgent.EMPTY, UserAgent.EMPTY);
	}

	@Test
	public void equals_identical() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertEquals(ua1, ua2);
		Assert.assertTrue(ua1.hashCode() == ua2.hashCode());
	}

	@Test
	public void equals_null() {
		Assert.assertFalse(UserAgent.EMPTY.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final UserAgent ua = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.MOBILE_BROWSER, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertFalse(ua.equals(OperatingSystem.EMPTY));
	}

	@Test
	public void testGetters() {
		final UserAgent ua = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertEquals(UserAgentFamily.CHROMIUM, ua.getFamily());
		Assert.assertEquals("icon", ua.getIcon());
		Assert.assertEquals("name", ua.getName());
		Assert.assertEquals(OperatingSystem.EMPTY, ua.getOperatingSystem());
		Assert.assertEquals("producer", ua.getProducer());
		Assert.assertEquals("producer url", ua.getProducerUrl());
		Assert.assertEquals("type", ua.getTypeName());
		Assert.assertEquals("url", ua.getUrl());
		Assert.assertEquals("1", ua.getVersionNumber().toVersionString());
	}

	@Test
	public void testHashCode() {
		final UserAgent ua1 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		final UserAgent ua2 = new UserAgent(UserAgentFamily.CHROMIUM, "icon", "name", OperatingSystem.EMPTY, "producer", "producer url",
				UserAgentType.BROWSER, "type", "url", VersionParser.parseVersion("1"));
		Assert.assertEquals(ua1.hashCode(), ua2.hashCode());
	}

	@Test
	public void testHashCode_EMPTY() {
		Assert.assertEquals(UserAgent.EMPTY.hashCode(), UserAgent.EMPTY.hashCode());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final UserAgent ua = new UserAgent(UserAgentFamily.SAFARI, "i1", "n1", OperatingSystem.EMPTY, "p1", "pu1",
				UserAgentType.USERAGENT_ANONYMIZER, "t1", "u1", VersionParser.parseVersion("1"));
		Assert.assertEquals(
				"UserAgent [family=SAFARI, icon=i1, name=n1, operatingSystem="
						+ OperatingSystem.EMPTY.toString()
						+ ", producer=p1, producerUrl=pu1, type=USERAGENT_ANONYMIZER, typeName=t1, url=u1, versionNumber=VersionNumber [groups=[1, , ], extension=]]",
				ua.toString());
	}

}
