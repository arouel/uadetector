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

import net.sf.uadetector.OperatingSystem;
import net.sf.uadetector.OperatingSystemFamily;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.VersionNumber;
import net.sf.uadetector.datastore.NotUpdateableXmlDataStore;
import net.sf.uadetector.datastore.TestXmlDataStore;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdatingUserAgentStringParserImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(UpdatingUserAgentStringParserImplTest.class);

	private static final UpdatingUserAgentStringParserImpl PARSER = UpdatingUserAgentStringParserHolder.getInstance();

	@Test(expected = IllegalArgumentException.class)
	public void construct_store_null() throws Exception {
		new UpdatingUserAgentStringParserImpl(null);
	}

	@Test
	public void getCurrentVersion() {
		Assert.assertFalse("20120509-01".equals(PARSER.getDataStore().getData().getVersion()));
	}

	@Test
	public void getDataStore() {
		Assert.assertNotNull(PARSER.getDataStore());
		Assert.assertNotNull(PARSER.getDataStore().getData());
	}

	@Test
	public void parse_browser_CHROME() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals("Chrome", agent.getFamily());
		Assert.assertEquals("Chrome", agent.getName());
		Assert.assertEquals("Google Inc.", agent.getProducer());
		Assert.assertEquals("http://www.google.com/", agent.getProducerUrl());
		Assert.assertEquals("Browser", agent.getTypeName());
		Assert.assertEquals("http://www.google.com/chrome", agent.getUrl());
		Assert.assertEquals("13.0.782.112", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.OS_X, os.getFamily());
		Assert.assertEquals("OS X", os.getFamilyName());
		Assert.assertEquals("OS X 10.6 Snow Leopard", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://www.apple.com/osx/", os.getUrl());
	}

	@Test
	public void parse_browser_CHROME_withoutVersionInfo() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/$ Safari/535.1";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals("Safari", agent.getFamily());
		Assert.assertEquals("Safari", agent.getName());
		Assert.assertEquals("Apple Inc.", agent.getProducer());
		Assert.assertEquals("http://www.apple.com/", agent.getProducerUrl());
		Assert.assertEquals("Browser", agent.getTypeName());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Safari_%28web_browser%29", agent.getUrl());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.OS_X, os.getFamily());
		Assert.assertEquals("OS X", os.getFamilyName());
		Assert.assertEquals("OS X 10.6 Snow Leopard", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://www.apple.com/osx/", os.getUrl());
	}

	@Test
	public void parse_browser_SITESUCKER() throws Exception {
		final String userAgent = "SiteSucker/1.6.9";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals("SiteSucker", agent.getFamily());
		Assert.assertEquals("SiteSucker", agent.getName());
		Assert.assertEquals("Rick Cranisky", agent.getProducer());
		Assert.assertEquals("", agent.getProducerUrl());
		Assert.assertEquals("Offline Browser", agent.getTypeName());
		Assert.assertEquals("http://www.sitesucker.us/", agent.getUrl());
		Assert.assertEquals("1.6.9", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.MAC_OS, os.getFamily());
		Assert.assertEquals("Mac OS", os.getFamilyName());
		Assert.assertEquals("Mac OS", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Mac_OS", os.getUrl());
	}

	@Test
	public void parse_browser_SKYFIRE() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_7; en-us) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Safari/530.17 Skyfire/2.0";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals("Skyfire", agent.getFamily());
		Assert.assertEquals("Skyfire", agent.getName());
		Assert.assertEquals("Skyfire Labs, Inc.", agent.getProducer());
		Assert.assertEquals("http://www.skyfire.com/about", agent.getProducerUrl());
		Assert.assertEquals("Mobile Browser", agent.getTypeName());
		Assert.assertEquals("http://www.skyfire.com/", agent.getUrl());
		Assert.assertEquals("2.0", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.OS_X, os.getFamily());
		Assert.assertEquals("OS X", os.getFamilyName());
		Assert.assertEquals("OS X 10.5 Leopard", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://www.apple.com/osx/", os.getUrl());
	}

	@Test
	public void parse_browser_SKYFIRE_withoutOperatingSystemInfo() throws Exception {
		final String userAgent = "Mozilla/5.0 AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Safari/530.17 Skyfire/2.0";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertTrue(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals("Skyfire", agent.getFamily());
		Assert.assertEquals("Skyfire", agent.getName());
		Assert.assertEquals("Skyfire Labs, Inc.", agent.getProducer());
		Assert.assertEquals("http://www.skyfire.com/about", agent.getProducerUrl());
		Assert.assertEquals("Mobile Browser", agent.getTypeName());
		Assert.assertEquals("http://www.skyfire.com/", agent.getUrl());
		Assert.assertEquals("2.0", agent.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_emptyString() {
		final UserAgent agent = PARSER.parse("");

		// check user agent informations
		final UserAgent e = UserAgent.EMPTY;
		Assert.assertEquals(e.getFamily(), agent.getFamily());
		Assert.assertEquals(e.getName(), agent.getName());
		Assert.assertEquals(e.getProducer(), agent.getProducer());
		Assert.assertEquals(e.getProducerUrl(), agent.getProducerUrl());
		Assert.assertEquals(e.getTypeName(), agent.getTypeName());
		Assert.assertEquals(e.getUrl(), agent.getUrl());

		Assert.assertEquals(OperatingSystem.EMPTY, agent.getOperatingSystem());
	}

	@Test
	public void parse_robot_GOOGLEBOT() throws Exception {
		final String userAgent = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertTrue(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals("Googlebot", agent.getFamily());
		Assert.assertEquals("Googlebot/2.1", agent.getName());
		Assert.assertEquals("Google Inc.", agent.getProducer());
		Assert.assertEquals("http://www.google.com/", agent.getProducerUrl());
		Assert.assertEquals(Robot.TYPENAME, agent.getTypeName());
		Assert.assertEquals("", agent.getUrl());
		Assert.assertEquals("2.1", agent.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_unknownString() {
		final UserAgent agent = PARSER.parse("qwertzuiopasdfghjklyxcvbnm");

		// check user agent informations
		final UserAgent e = UserAgent.EMPTY;
		Assert.assertEquals(e.getFamily(), agent.getFamily());
		Assert.assertEquals(e.getName(), agent.getName());
		Assert.assertEquals(e.getProducer(), agent.getProducer());
		Assert.assertEquals(e.getProducerUrl(), agent.getProducerUrl());
		Assert.assertEquals(e.getTypeName(), agent.getTypeName());
		Assert.assertEquals(e.getUrl(), agent.getUrl());
		Assert.assertEquals(VersionNumber.UNKNOWN, agent.getVersionNumber());

		Assert.assertEquals(OperatingSystem.EMPTY, agent.getOperatingSystem());
	}

	@Test
	public void refresh() throws Exception {
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(new TestXmlDataStore());
		parser.getDataStore().refresh();
	}

	@Test(expected = IllegalArgumentException.class)
	public void setUpdateInterval_toSmall() throws MalformedURLException {
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(new TestXmlDataStore());
		parser.setUpdateInterval(-1l);
	}

	@Test
	public void testUpdateMechanismWhileParsing() throws InterruptedException {
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(new TestXmlDataStore());
		final long firstLastUpdateCheck = parser.getUpdater().getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + firstLastUpdateCheck);
		final long originalInterval = parser.getUpdateInterval();

		// reduce the interval since testing
		LOG.debug("Reducing the update interval during the test.");
		parser.setUpdateInterval(100l);
		// we have to read to activate the update mechanism
		parser.parse("check for updates");

		Thread.sleep(1000l);
		final long currentLastUpdateCheck = parser.getUpdater().getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + currentLastUpdateCheck);
		Assert.assertTrue(firstLastUpdateCheck < currentLastUpdateCheck);

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
