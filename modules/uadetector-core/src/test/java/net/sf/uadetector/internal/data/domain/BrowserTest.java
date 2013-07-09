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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgent.Builder;
import net.sf.uadetector.UserAgentFamily;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

public class BrowserTest {

	private static final class Blueprint {

		private UserAgentFamily family = UserAgentFamily.NCSA_MOSAIC;

		private String familyName = "test-family-name";

		private String icon = "test-icon.png";

		private int id = 9876543;

		private String infoUrl = "test-info-url";

		private OperatingSystem operatingSystem;

		private SortedSet<BrowserPattern> patterns = Sets.newTreeSet();

		private String producer = "test-producer";

		private String producerUrl = "test-producer-url";

		private BrowserType type = new BrowserType(9876, "test-type");

		private String url = "test-url";

		public Blueprint() {
			patterns.add(new BrowserPattern(9876, Pattern.compile("[a-z]+"), 1));
			patterns.add(new BrowserPattern(7652, Pattern.compile("[0-9]+"), 2));
			final SortedSet<OperatingSystemPattern> osPatterns = Sets.newTreeSet();
			osPatterns.add(new OperatingSystemPattern(3265, Pattern.compile(".*"), 1));
			operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatterns, "p1", "pu1", "u1");
		}

		@Nonnull
		public Browser build() {
			return new Browser(id, family, familyName, patterns, type, operatingSystem, icon, infoUrl, producer, producerUrl, url);
		}

		public Blueprint family(final UserAgentFamily family) {
			this.family = family;
			return this;
		}

		public Blueprint familyName(final String familyName) {
			this.familyName = familyName;
			return this;
		}

		public Blueprint icon(final String icon) {
			this.icon = icon;
			return this;
		}

		public Blueprint id(final int id) {
			this.id = id;
			return this;
		}

		public Blueprint infoUrl(final String infoUrl) {
			this.infoUrl = infoUrl;
			return this;
		}

		public Blueprint operatingSystem(final OperatingSystem operatingSystem) {
			this.operatingSystem = operatingSystem;
			return this;
		}

		public Blueprint patterns(final SortedSet<BrowserPattern> patterns) {
			this.patterns = patterns;
			return this;
		}

		public Blueprint producer(final String producer) {
			this.producer = producer;
			return this;
		}

		public Blueprint producerUrl(final String producerUrl) {
			this.producerUrl = producerUrl;
			return this;
		}

		public Blueprint type(final BrowserType type) {
			this.type = type;
			return this;
		}

		public Blueprint url(final String url) {
			this.url = url;
			return this;
		}

	}

	@Test
	public void copyTo_withOperatingSystem() {
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", patternSet, "p1", "pu1", "u1");
		final Browser b = new Blueprint().operatingSystem(os).build();
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
	public void copyTo_withoutOperatingSystem() {
		final Browser b = new Blueprint().operatingSystem(null).build();
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
	public void equals_different_FAMILY() {
		final Browser a = new Blueprint().family(UserAgentFamily.FIREBIRD).build();
		final Browser b = new Blueprint().family(UserAgentFamily.FIREFOX).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_FAMILYNAME() {
		final Browser a = new Blueprint().familyName("name1").build();
		final Browser b = new Blueprint().familyName("name2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_ICON() {
		final Browser a = new Blueprint().icon("icon.png").build();
		final Browser b = new Blueprint().icon("icon.gif").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_ID() {
		final Browser a = new Blueprint().id(725).build();
		final Browser b = new Blueprint().id(289).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_INFOURL() {
		final Browser a = new Blueprint().infoUrl("info-1").build();
		final Browser b = new Blueprint().infoUrl("info-2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_OPERATINGSYSTEM() {
		final OperatingSystem os1 = new OperatingSystem("Linux", "tux.png", 132, "info", "Gentoo", new TreeSet<OperatingSystemPattern>(),
				"Gentoo Org.", "http://gentoo.org", "http://gentoo.org");
		final Browser a = new Blueprint().operatingSystem(os1).build();

		final OperatingSystem os2 = new OperatingSystem("Linux", "tux.png", 764, "info", "Suse", new TreeSet<OperatingSystemPattern>(),
				"SUSE Linux GmbH", "https://www.suse.com", "https://www.suse.com");
		final Browser b = new Blueprint().operatingSystem(os2).build();

		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_PATTERNS() {
		final SortedSet<BrowserPattern> patterns1 = Sets.newTreeSet();
		patterns1.add(new BrowserPattern(7654, Pattern.compile("d*[a-f]+"), 46));
		final Browser a = new Blueprint().patterns(patterns1).build();

		final SortedSet<BrowserPattern> patterns2 = Sets.newTreeSet();
		patterns2.add(new BrowserPattern(1932, Pattern.compile("f*[1-4]+"), 733));
		final Browser b = new Blueprint().patterns(patterns2).build();

		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_PRODUCER() {
		final Browser a = new Blueprint().producer("prod-1").build();
		final Browser b = new Blueprint().producer("prod-2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_PRODUCERURL() {
		final Browser a = new Blueprint().producerUrl("prod-url-1").build();
		final Browser b = new Blueprint().producerUrl("prod-url-2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_TYPE() {
		final Browser a = new Blueprint().type(new BrowserType(1, "Type 1")).build();
		final Browser b = new Blueprint().type(new BrowserType(2, "Type 2")).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_URL() {
		final Browser a = new Blueprint().url("url.net").build();
		final Browser b = new Blueprint().url("url.org").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final Browser a = new Blueprint().build();
		final Browser b = new Blueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Browser a = new Blueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Browser a = new Blueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Browser a = new Blueprint().build();
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_FAMILY() {
		new Blueprint().family(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_FAMILYNAME() {
		new Blueprint().familyName(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_ICON() {
		new Blueprint().icon(null).build();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void precondition_ID() {
		new Blueprint().id(-1).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_INFOURL() {
		new Blueprint().infoUrl(null).build();
	}

	@Test
	public void precondition_OPERATINGSYSTEM_nullIsAllowed() {
		new Blueprint().operatingSystem(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PATTERNS() {
		new Blueprint().patterns(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PRODUCER() {
		new Blueprint().producer(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PRODUCERURL() {
		new Blueprint().producerUrl(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_TYPE() {
		new Blueprint().type(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_URL() {
		new Blueprint().url(null).build();
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
		final SortedSet<BrowserPattern> patterns = new TreeSet<BrowserPattern>();
		final Browser b = new Browser(id, family, family.getName(), patterns, type, operatingSystem, icon, infoUrl, producer, producerUrl,
				url);
		Assert.assertEquals(family, b.getFamily());
		Assert.assertEquals("bunt.png", b.getIcon());
		Assert.assertEquals(12354, b.getId());
		Assert.assertEquals("http://programming-motherfucker.com/", b.getInfoUrl());
		Assert.assertEquals(operatingSystem, b.getOperatingSystem());
		Assert.assertEquals(patterns, b.getPatterns());
		Assert.assertEquals("Our Values", b.getProducer());
		Assert.assertEquals("https://github.com/before", b.getProducerUrl());
		Assert.assertEquals(type, b.getType());
		Assert.assertEquals("http://user-agent-string.info/", b.getUrl());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final int id = 12354;
		final String icon = "bunt.png";
		final String infoUrl = "http://programming-motherfucker.com/";
		final String url = "http://user-agent-string.info/";
		final UserAgentFamily family = UserAgentFamily.FIREFOX;
		final String producerUrl = "https://github.com/before";
		final String producer = "Our Values";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patterns = new TreeSet<BrowserPattern>();
		final Browser b = new Browser(id, family, family.getName(), patterns, type, os, icon, infoUrl, producer, producerUrl, url);

		Assert.assertEquals(
				"Browser [id=12354, family=FIREFOX, familyName=Firefox, patterns=[], type=BrowserType [id=1, name=Browser], operatingSystem=OperatingSystem [family=f1, icon=i1, id=1, infoUrl=iu1, name=n1, patternSet=[], producer=p1, producerUrl=pu1, url=u1], icon=bunt.png, infoUrl=http://programming-motherfucker.com/, producer=Our Values, producerUrl=https://github.com/before, url=http://user-agent-string.info/]",
				b.toString());
	}

}
