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
package net.sf.uadetector.parser;

import java.net.MalformedURLException;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.OperatingSystem;
import net.sf.uadetector.OperatingSystemFamily;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.VersionNumber;
import net.sf.uadetector.datastore.NotUpdateableXmlDataStore;
import net.sf.uadetector.datastore.TestXmlDataStore;
import net.sf.uadetector.internal.data.domain.Robot;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdatingUserAgentStringParserImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(UpdatingUserAgentStringParserImplTest.class);

	private static final UpdatingUserAgentStringParserImpl PARSER = UpdatingUserAgentStringParserHolder.getInstance();

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_store_null() throws Exception {
		new UpdatingUserAgentStringParserImpl(null);
	}

	@Test
	public void getCurrentVersion() {
		assertThat("20120509-01".equals(PARSER.getDataStore().getData().getVersion())).isFalse();
		assertThat("20120509-01".equals(PARSER.getDataVersion())).isFalse();
	}

	@Test
	public void getDataStore() {
		assertThat(PARSER.getDataStore()).isNotNull();
		assertThat(PARSER.getDataStore().getData()).isNotNull();
	}

	@Test
	public void parse_browser_CHROME() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.CHROME);
		assertThat(agent.getName()).isEqualTo("Chrome");
		assertThat(agent.getProducer()).isEqualTo("Google Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.google.com/");
		assertThat(agent.getTypeName()).isEqualTo("Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.google.com/chrome");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("13.0.782.112");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.OS_X);
		assertThat(os.getFamilyName()).isEqualTo("OS X");
		assertThat(os.getName()).isEqualTo("OS X 10.6 Snow Leopard");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://www.apple.com/osx/");
	}

	@Test
	public void parse_browser_CHROME_withoutVersionInfo() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/$ Safari/535.1";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.SAFARI);
		assertThat(agent.getName()).isEqualTo("Safari");
		assertThat(agent.getProducer()).isEqualTo("Apple Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(agent.getTypeName()).isEqualTo("Browser");
		assertThat(agent.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Safari_%28web_browser%29");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.OS_X);
		assertThat(os.getFamilyName()).isEqualTo("OS X");
		assertThat(os.getName()).isEqualTo("OS X 10.6 Snow Leopard");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://www.apple.com/osx/");
	}

	@Test
	public void parse_browser_SITESUCKER() throws Exception {
		final String userAgent = "SiteSucker/1.6.9";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.SITESUCKER);
		assertThat(agent.getName()).isEqualTo("SiteSucker");
		assertThat(agent.getProducer()).isEqualTo("Rick Cranisky");
		assertThat(agent.getProducerUrl()).isEqualTo("");
		assertThat(agent.getTypeName()).isEqualTo("Offline Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.sitesucker.us/");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("1.6.9");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.MAC_OS);
		assertThat(os.getFamilyName()).isEqualTo("Mac OS");
		assertThat(os.getName()).isEqualTo("Mac OS");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Mac_OS");
	}

	@Test
	public void parse_browser_SKYFIRE() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_7; en-us) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Safari/530.17 Skyfire/2.0";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.SKYFIRE);
		assertThat(agent.getName()).isEqualTo("Skyfire");
		assertThat(agent.getProducer()).isEqualTo("Skyfire Labs, Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.skyfire.com/about");
		assertThat(agent.getTypeName()).isEqualTo("Mobile Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.skyfire.com/");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("2.0");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.OS_X);
		assertThat(os.getFamilyName()).isEqualTo("OS X");
		assertThat(os.getName()).isEqualTo("OS X 10.5 Leopard");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://www.apple.com/osx/");
	}

	@Test
	public void parse_browser_SKYFIRE_withoutOperatingSystemInfo() throws Exception {
		final String userAgent = "Mozilla/5.0 AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Safari/530.17 Skyfire/2.0";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isTrue();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.SKYFIRE);
		assertThat(agent.getName()).isEqualTo("Skyfire");
		assertThat(agent.getProducer()).isEqualTo("Skyfire Labs, Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.skyfire.com/about");
		assertThat(agent.getTypeName()).isEqualTo("Mobile Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.skyfire.com/");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("2.0");
	}

	@Test
	public void parse_emptyString() {
		final UserAgent agent = PARSER.parse("");

		// check user agent informations
		final UserAgent e = UserAgent.EMPTY;
		assertThat(agent.getFamily()).isEqualTo(e.getFamily());
		assertThat(agent.getName()).isEqualTo(e.getName());
		assertThat(agent.getProducer()).isEqualTo(e.getProducer());
		assertThat(agent.getProducerUrl()).isEqualTo(e.getProducerUrl());
		assertThat(agent.getTypeName()).isEqualTo(e.getTypeName());
		assertThat(agent.getUrl()).isEqualTo(e.getUrl());

		assertThat(agent.getOperatingSystem()).isEqualTo(OperatingSystem.EMPTY);
	}

	@Test
	public void parse_robot_GOOGLEBOT() throws Exception {
		final String userAgent = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isTrue();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.GOOGLEBOT);
		assertThat(agent.getName()).isEqualTo("Googlebot/2.1");
		assertThat(agent.getProducer()).isEqualTo("Google Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.google.com/");
		assertThat(agent.getTypeName()).isEqualTo(Robot.TYPENAME);
		assertThat(agent.getUrl()).isEqualTo("/list-of-ua/bot-detail?bot=Googlebot");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("2.1");
	}

	@Test
	public void parse_unknownString() {
		final UserAgent agent = PARSER.parse("qwertzuiopasdfghjklyxcvbnm");

		// check user agent informations
		final UserAgent e = UserAgent.EMPTY;
		assertThat(agent.getFamily()).isEqualTo(e.getFamily());
		assertThat(agent.getName()).isEqualTo(e.getName());
		assertThat(agent.getProducer()).isEqualTo(e.getProducer());
		assertThat(agent.getProducerUrl()).isEqualTo(e.getProducerUrl());
		assertThat(agent.getTypeName()).isEqualTo(e.getTypeName());
		assertThat(agent.getUrl()).isEqualTo(e.getUrl());
		assertThat(agent.getVersionNumber()).isEqualTo(VersionNumber.UNKNOWN);

		assertThat(agent.getOperatingSystem()).isEqualTo(OperatingSystem.EMPTY);
	}

	@Test
	public void refresh() throws Exception {
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(new TestXmlDataStore());
		parser.getDataStore().refresh();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void setUpdateInterval_toSmall() throws MalformedURLException {
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(new TestXmlDataStore());
		parser.setUpdateInterval(-1l);
	}

	@Test
	public void shutdown() {
		// shutdown must not interrupt the caller
		new UpdatingUserAgentStringParserImpl(new TestXmlDataStore()).shutdown();
	}

	@Test
	public void testUpdateMechanismWhileParsing() throws InterruptedException {
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(new TestXmlDataStore());
		final long firstLastUpdateCheck = parser.getDataStore().getUpdateOperation().getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + firstLastUpdateCheck);
		final long originalInterval = parser.getUpdateInterval();

		// reduce the interval since testing
		LOG.debug("Reducing the update interval during the test.");
		parser.setUpdateInterval(400l);
		// we have to read to activate the update mechanism
		parser.parse("check for updates");

		Thread.sleep(1000l);
		final long currentLastUpdateCheck = parser.getDataStore().getUpdateOperation().getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + currentLastUpdateCheck);
		assertThat(firstLastUpdateCheck < currentLastUpdateCheck).isTrue();

		parser.setUpdateInterval(originalInterval);
	}

	@Test
	public void testWrongUrl() throws Exception {
		LOG.debug("Testing the update ability with a wrong URL.");
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(new NotUpdateableXmlDataStore());
		LOG.debug("Reducing the update interval during the test.");
		parser.setUpdateInterval(10l);
		parser.parse("");
		LOG.debug("Testing the update ability with a wrong URL done.");
	}

}
