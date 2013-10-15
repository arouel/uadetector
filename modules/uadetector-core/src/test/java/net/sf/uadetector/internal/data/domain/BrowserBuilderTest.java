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

import static org.fest.assertions.Assertions.assertThat;
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
		final OperatingSystem operatingSystem = new OperatingSystem(1, "n1", "f1", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
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
		assertThat(copy1.getFamily()).isEqualTo(UserAgentFamily.CHROME);
		assertThat(copy1.getIcon()).isEqualTo("i1");
		assertThat(copy1.getInfoUrl()).isEqualTo("iu1");
		assertThat(copy1.getOperatingSystem()).isSameAs(operatingSystem);
		assertThat(copy1.getPatterns()).isEqualTo(patternSet);
		assertThat(copy1.getProducer()).isEqualTo("p1");
		assertThat(copy1.getProducerUrl()).isEqualTo("pu1");
		assertThat(copy1.getType().getName()).isEqualTo("browser type test");
		assertThat(copy1.getType().getId()).isEqualTo(1);
		assertThat(copy1.getTypeId()).isEqualTo(1);
		assertThat(copy1.getUrl()).isEqualTo("u1");

		final Builder copy2 = original.copy();
		assertThat(copy2.getFamily()).isEqualTo(UserAgentFamily.CHROME);
		assertThat(copy2.getIcon()).isEqualTo("i1");
		assertThat(copy2.getInfoUrl()).isEqualTo("iu1");
		assertThat(copy2.getOperatingSystem()).isSameAs(operatingSystem);
		assertThat(copy2.getPatterns()).isEqualTo(patternSet);
		assertThat(copy2.getProducer()).isEqualTo("p1");
		assertThat(copy2.getProducerUrl()).isEqualTo("pu1");
		assertThat(copy2.getType().getName()).isEqualTo("browser type test");
		assertThat(copy2.getType().getId()).isEqualTo(1);
		assertThat(copy2.getTypeId()).isEqualTo(1);
		assertThat(copy2.getUrl()).isEqualTo("u1");
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

		assertThat(b1.equals(b2)).isTrue();
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
		final OperatingSystem operatingSystem = new OperatingSystem(1, "n1", "f1", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
		final SortedSet<BrowserPattern> patterns = new TreeSet<BrowserPattern>();

		final Browser.Builder builder = new Browser.Builder();
		assertThat(builder.setFamilyName(UserAgentFamily.SEAMONKEY.getName())).isSameAs(builder);
		assertThat(builder.setIcon("i1")).isSameAs(builder);
		assertThat(builder.setId(1)).isSameAs(builder);
		assertThat(builder.setInfoUrl("iu1")).isSameAs(builder);
		assertThat(builder.setOperatingSystem(operatingSystem)).isSameAs(builder);
		assertThat(builder.setPatterns(patterns)).isSameAs(builder);
		assertThat(builder.setProducer("p1")).isSameAs(builder);
		assertThat(builder.setProducerUrl("pu1")).isSameAs(builder);
		assertThat(builder.setType(browserType)).isSameAs(builder);
		assertThat(builder.setTypeId(1)).isSameAs(builder);
		assertThat(builder.setUrl("u1")).isSameAs(builder);

		assertThat(builder.getFamily()).isEqualTo(UserAgentFamily.SEAMONKEY);
		assertThat(builder.getFamilyName()).isEqualTo(UserAgentFamily.SEAMONKEY.getName());
		assertThat(builder.getIcon()).isEqualTo("i1");
		assertThat(builder.getInfoUrl()).isEqualTo("iu1");
		assertThat(builder.getOperatingSystem()).isSameAs(operatingSystem);
		assertThat(builder.getPatterns()).isEqualTo(patterns);
		assertThat(builder.getProducer()).isEqualTo("p1");
		assertThat(builder.getProducerUrl()).isEqualTo("pu1");
		assertThat(builder.getType().getName()).isEqualTo("browser type test");
		assertThat(builder.getType().getId()).isEqualTo(1);
		assertThat(builder.getTypeId()).isEqualTo(1);
		assertThat(builder.getUrl()).isEqualTo("u1");

		final Browser browser = builder.build();
		assertThat(browser.getFamily()).isEqualTo(UserAgentFamily.SEAMONKEY);
		assertThat(browser.getFamilyName()).isEqualTo(UserAgentFamily.SEAMONKEY.getName());
		assertThat(browser.getIcon()).isEqualTo("i1");
		assertThat(browser.getInfoUrl()).isEqualTo("iu1");
		assertThat(browser.getProducer()).isEqualTo("p1");
		assertThat(browser.getProducerUrl()).isEqualTo("pu1");
		assertThat(browser.getType().getName()).isEqualTo("browser type test");
		assertThat(browser.getType().getId()).isEqualTo(1);
		assertThat(browser.getUrl()).isEqualTo("u1");

		final Browser browserRebuild = new Builder(builder.build()).build();
		assertThat(browserRebuild.getFamily()).isEqualTo(UserAgentFamily.SEAMONKEY);
		assertThat(browserRebuild.getFamilyName()).isEqualTo(UserAgentFamily.SEAMONKEY.getName());
		assertThat(browserRebuild.getIcon()).isEqualTo("i1");
		assertThat(browserRebuild.getInfoUrl()).isEqualTo("iu1");
		assertThat(browserRebuild.getProducer()).isEqualTo("p1");
		assertThat(browserRebuild.getProducerUrl()).isEqualTo("pu1");
		assertThat(browserRebuild.getType().getName()).isEqualTo("browser type test");
		assertThat(browserRebuild.getType().getId()).isEqualTo(1);
		assertThat(browserRebuild.getUrl()).isEqualTo("u1");
	}

}
