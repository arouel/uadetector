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

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser.Builder;

import org.junit.Assert;
import org.junit.Test;

public class BrowserBuilderTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_builder_null() {
		new Builder((Builder) null);
	}

	@Test
	public void construct_copy_successful() {
		final BrowserType browserType = new BrowserType.Builder().setId("1").setName("browser type test").build();
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();

		final Browser.Builder original = new Browser.Builder();
		original.setFamilyName(UserAgentFamily.CHROME.getName());
		original.setIcon("i1");
		original.setId(1);
		original.setInfoUrl("iu1");
		original.setOperatingSystem(operatingSystem);
		original.setPatterns(patternSet);
		original.setProducer("p1");
		original.setProducerUrl("pu1");
		original.setType(browserType);
		original.setTypeId(1);
		original.setUrl("u1");

		final Builder copy1 = new Browser.Builder(original);
		Assert.assertEquals(UserAgentFamily.CHROME, copy1.getFamily());
		Assert.assertEquals("i1", copy1.getIcon());
		Assert.assertEquals("iu1", copy1.getInfoUrl());
		Assert.assertSame(operatingSystem, copy1.getOperatingSystem());
		Assert.assertEquals(patternSet, copy1.getPatterns());
		Assert.assertEquals("p1", copy1.getProducer());
		Assert.assertEquals("pu1", copy1.getProducerUrl());
		Assert.assertEquals("browser type test", copy1.getType().getName());
		Assert.assertEquals(1, copy1.getType().getId());
		Assert.assertEquals(1, copy1.getTypeId());
		Assert.assertEquals("u1", copy1.getUrl());

		final Builder copy2 = original.copy();
		Assert.assertEquals(UserAgentFamily.CHROME, copy2.getFamily());
		Assert.assertEquals("i1", copy2.getIcon());
		Assert.assertEquals("iu1", copy2.getInfoUrl());
		Assert.assertSame(operatingSystem, copy2.getOperatingSystem());
		Assert.assertEquals(patternSet, copy2.getPatterns());
		Assert.assertEquals("p1", copy2.getProducer());
		Assert.assertEquals("pu1", copy2.getProducerUrl());
		Assert.assertEquals("browser type test", copy2.getType().getName());
		Assert.assertEquals(1, copy2.getType().getId());
		Assert.assertEquals(1, copy2.getTypeId());
		Assert.assertEquals("u1", copy2.getUrl());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setFamily_null() {
		new Browser.Builder().setFamilyName(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setIcon_null() {
		new Browser.Builder().setIcon(null);
	}

	@Test(expected = NumberFormatException.class)
	public void setId_alphaString() {
		new Browser.Builder().setId("abc");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void setId_emptyString() {
		new Browser.Builder().setId("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setId_null() {
		new Browser.Builder().setId(null);
	}

	@Test
	public void setId_numericString() {
		final BrowserType type = new BrowserType.Builder().setId("13746").setName("browser type test").build();

		final Builder builder1 = new Builder();
		builder1.setId("378246");
		builder1.setFamilyName(UserAgentFamily.CHROMIUM.getName());
		builder1.setIcon("i1");
		builder1.setInfoUrl("iu1");
		builder1.setPatterns(new TreeSet<BrowserPattern>());
		builder1.setProducer("p1");
		builder1.setProducerUrl("pu1");
		builder1.setType(type).setUrl("u1");
		final Browser b1 = builder1.build();

		final Builder builder2 = new Builder();
		builder2.setId("378246");
		builder2.setFamilyName(UserAgentFamily.CHROMIUM.getName());
		builder2.setIcon("i1");
		builder2.setInfoUrl("iu1");
		builder2.setPatterns(new TreeSet<BrowserPattern>());
		builder2.setProducer("p1");
		builder2.setProducerUrl("pu1");
		builder2.setType(type).setUrl("u1");
		final Browser b2 = builder2.build();

		Assert.assertTrue(b1.equals(b2));
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void setId_toSmall() {
		new Browser.Builder().setId(-1);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setInfoUrl_null() {
		new Browser.Builder().setInfoUrl(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setOperatingSystem_null() {
		new Browser.Builder().setOperatingSystem(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setPatternSet_null() {
		new Browser.Builder().setPatterns(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setProducer_null() {
		new Browser.Builder().setProducer(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setProducerUrl_null() {
		new Browser.Builder().setProducerUrl(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setType_null() {
		new Browser.Builder().setType(null);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void setTypeId_emptyString() {
		new Browser.Builder().setTypeId("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setTypeId_null() {
		new Browser.Builder().setTypeId(null);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void setTypeId_toSmall() {
		new Browser.Builder().setTypeId(-1);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setUrl_null() {
		new Browser.Builder().setUrl(null);
	}

	@Test
	public void testGetters() throws Exception {
		final BrowserType browserType = new BrowserType.Builder().setId("1").setName("browser type test").build();
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final SortedSet<BrowserPattern> patterns = new TreeSet<BrowserPattern>();

		final Browser.Builder builder = new Browser.Builder();
		Assert.assertSame(builder, builder.setFamilyName(UserAgentFamily.SEAMONKEY.getName()));
		Assert.assertSame(builder, builder.setIcon("i1"));
		Assert.assertSame(builder, builder.setId(1));
		Assert.assertSame(builder, builder.setInfoUrl("iu1"));
		Assert.assertSame(builder, builder.setOperatingSystem(operatingSystem));
		Assert.assertSame(builder, builder.setPatterns(patterns));
		Assert.assertSame(builder, builder.setProducer("p1"));
		Assert.assertSame(builder, builder.setProducerUrl("pu1"));
		Assert.assertSame(builder, builder.setType(browserType));
		Assert.assertSame(builder, builder.setTypeId(1));
		Assert.assertSame(builder, builder.setUrl("u1"));

		Assert.assertEquals(UserAgentFamily.SEAMONKEY, builder.getFamily());
		Assert.assertEquals(UserAgentFamily.SEAMONKEY.getName(), builder.getFamilyName());
		Assert.assertEquals("i1", builder.getIcon());
		Assert.assertEquals("iu1", builder.getInfoUrl());
		Assert.assertSame(operatingSystem, builder.getOperatingSystem());
		Assert.assertEquals(patterns, builder.getPatterns());
		Assert.assertEquals("p1", builder.getProducer());
		Assert.assertEquals("pu1", builder.getProducerUrl());
		Assert.assertEquals("browser type test", builder.getType().getName());
		Assert.assertEquals(1, builder.getType().getId());
		Assert.assertEquals(1, builder.getTypeId());
		Assert.assertEquals("u1", builder.getUrl());

		final Browser browser = builder.build();
		Assert.assertEquals(UserAgentFamily.SEAMONKEY, browser.getFamily());
		Assert.assertEquals(UserAgentFamily.SEAMONKEY.getName(), browser.getFamilyName());
		Assert.assertEquals("i1", browser.getIcon());
		Assert.assertEquals("iu1", browser.getInfoUrl());
		Assert.assertEquals("p1", browser.getProducer());
		Assert.assertEquals("pu1", browser.getProducerUrl());
		Assert.assertEquals("browser type test", browser.getType().getName());
		Assert.assertEquals(1, browser.getType().getId());
		Assert.assertEquals("u1", browser.getUrl());

		final Browser browserRebuild = new Builder(builder.build()).build();
		Assert.assertEquals(UserAgentFamily.SEAMONKEY, browserRebuild.getFamily());
		Assert.assertEquals(UserAgentFamily.SEAMONKEY.getName(), browserRebuild.getFamilyName());
		Assert.assertEquals("i1", browserRebuild.getIcon());
		Assert.assertEquals("iu1", browserRebuild.getInfoUrl());
		Assert.assertEquals("p1", browserRebuild.getProducer());
		Assert.assertEquals("pu1", browserRebuild.getProducerUrl());
		Assert.assertEquals("browser type test", browserRebuild.getType().getName());
		Assert.assertEquals(1, browserRebuild.getType().getId());
		Assert.assertEquals("u1", browserRebuild.getUrl());
	}

}
