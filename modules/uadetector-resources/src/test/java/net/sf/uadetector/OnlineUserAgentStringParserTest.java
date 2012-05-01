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

import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.parser.OnlineUserAgentStringParserImpl;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnlineUserAgentStringParserTest {

	private static final Logger LOG = LoggerFactory.getLogger(OnlineUserAgentStringParserTest.class);

	private static final OnlineUserAgentStringParserImpl parser = (OnlineUserAgentStringParserImpl) UADetectorServiceFactory
			.getOnlineUserAgentStringParser();

	@Test
	public void parse_CHROME13() throws Exception {
		final OperatingSystem os = new OperatingSystem("Mac OS X", "Mac OS X 10.6 Snow Leopard", "Apple Computer, Inc.",
				"http://www.apple.com/", "http://www.apple.com/macosx/");
		final UserAgent ua = parser
				.parse("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1");
		Assert.assertEquals("Chrome", ua.getFamily());
		Assert.assertEquals("Chrome", ua.getName());
		Assert.assertEquals(os, ua.getOperatingSystem());
		Assert.assertEquals("Google Inc.", ua.getProducer());
		Assert.assertEquals("http://www.google.com/", ua.getProducerUrl());
		Assert.assertEquals("Browser", ua.getType());
		Assert.assertEquals("http://www.google.com/chrome", ua.getUrl());
		Assert.assertEquals("13.0.782.112", ua.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_empty() throws Exception {
		final UserAgent.Builder builder = new UserAgent.Builder();
		final UserAgent expected = builder.build();
		final UserAgent unknownAgentString = parser.parse("");
		Assert.assertEquals(expected, unknownAgentString);
	}

	@Test
	public void parse_FIREFOX6() throws Exception {
		final OperatingSystem os = new OperatingSystem("Mac OS X", "Mac OS X 10.7 Lion", "Apple Computer, Inc.", "http://www.apple.com/",
				"http://www.apple.com/macosx/");
		final UserAgent ua = parser.parse("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:6.0) Gecko/20100101 Firefox/6.0");
		Assert.assertEquals("Firefox", ua.getFamily());
		Assert.assertEquals("Firefox", ua.getName());
		Assert.assertEquals(os, ua.getOperatingSystem());
		Assert.assertEquals("Mozilla Foundation", ua.getProducer());
		Assert.assertEquals("http://www.mozilla.org/", ua.getProducerUrl());
		Assert.assertEquals("Browser", ua.getType());
		Assert.assertEquals("http://www.firefox.com/", ua.getUrl());
		Assert.assertEquals("6.0", ua.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_GOOGLEBOT() throws Exception {
		final UserAgent ua = parser.parse("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
		Assert.assertEquals("Googlebot", ua.getFamily());
		Assert.assertEquals("Googlebot/2.1", ua.getName());
		Assert.assertEquals(OperatingSystem.EMPTY, ua.getOperatingSystem());
		Assert.assertEquals("Google Inc.", ua.getProducer());
		Assert.assertEquals("http://www.google.com/", ua.getProducerUrl());
		Assert.assertEquals(Robot.TYPENAME, ua.getType());
		Assert.assertEquals("", ua.getUrl());
		Assert.assertEquals("2.1", ua.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_SITESUCKER() throws Exception {
		final OperatingSystem os = new OperatingSystem("Mac OS", "Mac OS", "Apple Computer, Inc.", "http://www.apple.com/",
				"http://en.wikipedia.org/wiki/Mac_OS");
		final UserAgent ua = parser.parse("SiteSucker/1.6.9");
		Assert.assertEquals("SiteSucker", ua.getFamily());
		Assert.assertEquals("SiteSucker", ua.getName());
		Assert.assertEquals(os, ua.getOperatingSystem());
		Assert.assertEquals("Rick Cranisky", ua.getProducer());
		Assert.assertEquals("", ua.getProducerUrl());
		Assert.assertEquals("Offline Browser", ua.getType());
		Assert.assertEquals("http://www.sitesucker.us/", ua.getUrl());
		Assert.assertEquals("1.6.9", ua.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_unknown() throws Exception {
		final UserAgent unknownAgentString = parser.parse("qwertzuiopasdfghjklyxcvbnm");
		Assert.assertEquals(UserAgent.EMPTY, unknownAgentString);
		Assert.assertEquals(OperatingSystem.EMPTY, unknownAgentString.getOperatingSystem());
	}

	@Test
	public void testUpdateMechanismWhileParsing() throws InterruptedException {
		final long firstLastUpdateCheck = parser.getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + firstLastUpdateCheck);
		final long originalInterval = parser.getUpdateInterval();

		// reduce the interval since testing
		LOG.debug("Reducing the update interval during the test.");
		parser.setUpdateInterval(10l);
		// we have to read to activate the update mechanism
		parser.parse("check for updates");

		Thread.sleep(100l);
		final long currentLastUpdateCheck = parser.getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + currentLastUpdateCheck);
		Assert.assertTrue(firstLastUpdateCheck < currentLastUpdateCheck);

		parser.setUpdateInterval(originalInterval);
	}

}
