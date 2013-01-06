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

import org.junit.Assert;
import org.junit.Test;

public class OperatingSystemTest {

	@Test(expected = IllegalArgumentException.class)
	public void construct_family_null() {
		new OperatingSystem(null, "family", "icon", "name", "producer", "producer url", "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_familyName_null() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, null, "icon", "name", "producer", "producer url", "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_icon_null() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", null, "name", "producer", "producer url", "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_name_null() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", "icon", null, "producer", "producer url", "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_producer_null() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", "icon", "name", null, "producer url", "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_producerUrl_null() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", "icon", "name", "producer", null, "url", new VersionNumber("1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_url_null() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", null, new VersionNumber("1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_version_null() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url", null);
	}

	@Test
	public void empty() {
		final OperatingSystem os = OperatingSystem.EMPTY;
		Assert.assertEquals(OperatingSystemFamily.UNKNOWN, os.getFamily());
		Assert.assertEquals("unknown", os.getFamilyName());
		Assert.assertEquals("unknown.png", os.getIcon());
		Assert.assertEquals("unknown", os.getName());
		Assert.assertEquals("", os.getProducer());
		Assert.assertEquals("", os.getProducerUrl());
		Assert.assertEquals("", os.getUrl());
	}

	@Test
	public void equals_differentFamily() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystemFamily aix = OperatingSystemFamily.AIX;
		final OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
				new VersionNumber("1"));
		final OperatingSystem os2 = new OperatingSystem(aix, "family", "icon", "name", "producer", "producer url", "url",
				new VersionNumber("1"));
		Assert.assertFalse(os1.equals(os2));
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
	}

	@Test
	public void equals_differentFamilyName() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os1 = new OperatingSystem(linux, "family1", "icon", "name", "prod", "prodUrl", "url", new VersionNumber("1"));
		final OperatingSystem os2 = new OperatingSystem(linux, "family2", "icon", "name", "prod", "prodUrl", "url", new VersionNumber("1"));
		Assert.assertFalse(os1.equals(os2));
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
	}

	@Test
	public void equals_differentIcon() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os1 = new OperatingSystem(linux, "family", "icon1", "name", "producer", "producer url", "url",
				new VersionNumber("1"));
		final OperatingSystem os2 = new OperatingSystem(linux, "family", "icon2", "name", "producer", "producer url", "url",
				new VersionNumber("1"));
		Assert.assertFalse(os1.equals(os2));
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
	}

	@Test
	public void equals_differentName() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name1", "producer", "producer url", "url",
				new VersionNumber("1"));
		final OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name2", "producer", "producer url", "url",
				new VersionNumber("1"));
		Assert.assertFalse(os1.equals(os2));
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
	}

	@Test
	public void equals_differentProducer() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer1", "producer url", "url",
				new VersionNumber("1"));
		final OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer2", "producer url", "url",
				new VersionNumber("1"));
		Assert.assertFalse(os1.equals(os2));
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
	}

	@Test
	public void equals_differentProducerUrl() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url 1", "url",
				new VersionNumber("1"));
		final OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url 2", "url",
				new VersionNumber("1"));
		Assert.assertFalse(os1.equals(os2));
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
	}

	@Test
	public void equals_differentUrl() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url1",
				new VersionNumber("1"));
		final OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url2",
				new VersionNumber("1"));
		Assert.assertFalse(os1.equals(os2));
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
	}

	@Test
	public void equals_differentVersionNumber() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
				new VersionNumber("1", "0"));
		final OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
				new VersionNumber("1", "1"));
		Assert.assertFalse(os1.equals(os2));
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
	}

	@Test
	public void equals_EMPTY() {
		Assert.assertEquals(OperatingSystem.EMPTY, OperatingSystem.EMPTY);
		Assert.assertSame(OperatingSystem.EMPTY, OperatingSystem.EMPTY);
	}

	@Test
	public void equals_identical() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
				new VersionNumber("1"));
		final OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
				new VersionNumber("1"));
		Assert.assertEquals(os1, os2);
	}

	@Test
	public void equals_null() {
		Assert.assertFalse(OperatingSystem.EMPTY.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
				new VersionNumber("1"));
		Assert.assertFalse(os.equals(123456));
	}

	@Test
	public void testGetters() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
				new VersionNumber("1"));
		Assert.assertEquals(linux, os.getFamily());
		Assert.assertEquals("family", os.getFamilyName());
		Assert.assertEquals("icon", os.getIcon());
		Assert.assertEquals("name", os.getName());
		Assert.assertEquals("producer", os.getProducer());
		Assert.assertEquals("producer url", os.getProducerUrl());
		Assert.assertEquals("url", os.getUrl());
	}

	@Test
	public void testHashCode() {
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem os1 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
				new VersionNumber("1"));
		final OperatingSystem os2 = new OperatingSystem(linux, "family", "icon", "name", "producer", "producer url", "url",
				new VersionNumber("1"));
		Assert.assertEquals(os1.hashCode(), os2.hashCode());
	}

	@Test
	public void testHashCode_EMPTY() {
		Assert.assertEquals(OperatingSystem.EMPTY.hashCode(), OperatingSystem.EMPTY.hashCode());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final OperatingSystemFamily linux = OperatingSystemFamily.LINUX;
		final OperatingSystem ua = new OperatingSystem(linux, "f1", "i1", "n1", "p1", "pu1", "u1", new VersionNumber("1", "0", "0",
				"-stable"));
		Assert.assertEquals(
				"OperatingSystem [family=LINUX, familyName=f1, icon=i1, name=n1, producer=p1, producerUrl=pu1, url=u1, versionNumber=VersionNumber [groups=[1, 0, 0], extension=-stable]]",
				ua.toString());
	}

}
