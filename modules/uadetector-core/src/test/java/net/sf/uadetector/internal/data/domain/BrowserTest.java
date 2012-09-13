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
package net.sf.uadetector.internal.data.domain;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgent.Builder;
import net.sf.uadetector.UserAgentFamily;

import org.junit.Assert;
import org.junit.Test;

public class BrowserTest {

	@Test(expected = IllegalArgumentException.class)
	public void construct_family_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = null;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_icon_null() {
		final int id = 1;
		final String icon = null;
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.UNKNOWN;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_id_toSmall() {
		final int id = -1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.UNKNOWN;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_infoUrl_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = null;
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.UNKNOWN;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test
	public void construct_operatingSystem_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.UNKNOWN;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final OperatingSystem operatingSystem = null; // can be null
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_patternSet_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.UNKNOWN;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = null;
		new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_producer_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.UNKNOWN;
		final String producerUrl = "producer url";
		final String producer = null;
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_producerUrl_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.UNKNOWN;
		final String producerUrl = null;
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_type_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.UNKNOWN;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = null;
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_url_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = null;
		final UserAgentFamily family = UserAgentFamily.UNKNOWN;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
	}

	@Test
	public void copyTo_successful_withOperatingSystem() {
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", patternSet, "p1", "pu1", "u1");
		final Browser b = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final Builder builder = new UserAgent.Builder();
		b.copyTo(builder);
		Assert.assertEquals(b.getFamily(), builder.getFamily());
		Assert.assertEquals(b.getProducer(), builder.getProducer());
		Assert.assertEquals(b.getProducerUrl(), builder.getProducerUrl());
		Assert.assertEquals(b.getType().getName(), builder.getTypeName());
		Assert.assertEquals(b.getUrl(), builder.getUrl());
		Assert.assertNotNull(builder.getOperatingSystem());
	}

	@Test
	public void copyTo_successful_withoutOperatingSystem() {
		final Browser b = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), null);
		final Builder builder = new UserAgent.Builder();
		b.copyTo(builder);
		Assert.assertEquals(b.getFamily(), builder.getFamily());
		Assert.assertEquals(b.getProducer(), builder.getProducer());
		Assert.assertEquals(b.getProducerUrl(), builder.getProducerUrl());
		Assert.assertEquals(b.getType().getName(), builder.getTypeName());
		Assert.assertEquals(b.getUrl(), builder.getUrl());
		Assert.assertSame(net.sf.uadetector.OperatingSystem.EMPTY, builder.getOperatingSystem());
	}

	@Test
	public void equals_differentFamily() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.FIREFOX, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_differentIcon() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i1", "iu",
				new TreeSet<BrowserPattern>(), os);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i2", "iu",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_differentId() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final Browser b2 = new Browser(2, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_differentInfoUrl() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu1",
				new TreeSet<BrowserPattern>(), os);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu2",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_differentOperatingSystem() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f2", "i2", 2, "iu2", "n2", osPatternSet, "p2", "pu2", "u2");
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os1);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os2);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_differentOperatingSystem_oneIsNull() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = null;
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os1);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os2);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
		Assert.assertFalse(b2.equals(b1));
	}

	@Test
	public void equals_differentPatternSet_differentEntries() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final TreeSet<BrowserPattern> ps1 = new TreeSet<BrowserPattern>();
		ps1.add(new BrowserPattern(1, Pattern.compile("[0-9]"), 1));
		final TreeSet<BrowserPattern> ps2 = new TreeSet<BrowserPattern>();
		ps2.add(new BrowserPattern(2, Pattern.compile("[0-9]"), 2));
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu", ps1, os);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu", ps2, os);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_differentPatternSet_differentEntrySize() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final TreeSet<BrowserPattern> ps1 = new TreeSet<BrowserPattern>();
		ps1.add(new BrowserPattern(1, Pattern.compile("[0-9]"), 1));
		ps1.add(new BrowserPattern(2, Pattern.compile("[0-9]+"), 2));
		final TreeSet<BrowserPattern> ps2 = new TreeSet<BrowserPattern>();
		ps2.add(new BrowserPattern(2, Pattern.compile("[0-9]+"), 2));
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu", ps1, os);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu", ps2, os);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_differentPatternSet_identicalEntries() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final TreeSet<BrowserPattern> ps1 = new TreeSet<BrowserPattern>();
		ps1.add(new BrowserPattern(1, Pattern.compile("[0-9]"), 1));
		final TreeSet<BrowserPattern> ps2 = new TreeSet<BrowserPattern>();
		ps2.add(new BrowserPattern(1, Pattern.compile("[0-9]"), 1));
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu", ps1, os);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu", ps2, os);
		Assert.assertTrue(b1.hashCode() == b2.hashCode());
		Assert.assertTrue(b1.equals(b2));
	}

	@Test
	public void equals_differentProducer() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p1", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p2", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_differentProducerUrl() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu1", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu2", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_differentType() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final Browser b2 = new Browser(1, new BrowserType(2, "Email client"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_differentUrl() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u1", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u2", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertFalse(b1.hashCode() == b2.hashCode());
		Assert.assertFalse(b1.equals(b2));
	}

	@Test
	public void equals_identical_nullOperatingSystem() {
		final OperatingSystem os1 = null;
		final OperatingSystem os2 = null;
		final Browser b1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os1);
		final Browser b2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os2);
		Assert.assertTrue(b1.equals(b2));
		Assert.assertTrue(b2.equals(b1));
		Assert.assertTrue(b1.hashCode() == b2.hashCode());
	}

	@Test
	public void equals_null() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertFalse(b.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final String otherClass = "";
		Assert.assertFalse(b.equals(otherClass));
	}

	@Test
	public void equals_same() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final Browser b = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		Assert.assertTrue(b.equals(b));
		Assert.assertTrue(b.hashCode() == b.hashCode());
	}

	@Test
	public void testGetters() {
		final int id = 12354;
		final String icon = "bunt.png";
		final String infoUrl = "http://programming-motherfucker.com/";
		final String url = "http://user-agent-string.info/";
		final UserAgentFamily family = UserAgentFamily.FIREFOX;
		final String producerUrl = "https://github.com/before";
		final String producer = "Our Values";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		final Browser b = new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
		Assert.assertEquals(family, b.getFamily());
		Assert.assertEquals("bunt.png", b.getIcon());
		Assert.assertEquals(12354, b.getId());
		Assert.assertEquals("http://programming-motherfucker.com/", b.getInfoUrl());
		Assert.assertSame(operatingSystem, b.getOperatingSystem());
		Assert.assertSame(patternSet, b.getPatternSet());
		Assert.assertEquals("Our Values", b.getProducer());
		Assert.assertEquals("https://github.com/before", b.getProducerUrl());
		Assert.assertSame(type, b.getType());
		Assert.assertEquals("http://user-agent-string.info/", b.getUrl());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final BrowserType browserType = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		final Browser b = new Browser(1, browserType, UserAgentFamily.CHROME, "u2", "p2", "pu2", "i2", "iu2", patternSet, operatingSystem);
		final StringBuilder expected = new StringBuilder();
		expected.append("Browser [family=CHROME, icon=i2, id=1, infoUrl=iu2, operatingSystem=");
		expected.append(operatingSystem.toString());
		expected.append(", patternSet=[], producer=p2, producerUrl=pu2, type=");
		expected.append(browserType.toString());
		expected.append(", url=u2]");
		Assert.assertEquals(expected.toString(), b.toString());
	}

}
