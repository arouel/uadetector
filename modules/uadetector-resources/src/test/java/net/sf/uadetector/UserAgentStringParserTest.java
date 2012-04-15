/*******************************************************************************
 * Copyright 2011 André Rouél
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

public class UserAgentStringParserTest {

	private static final UserAgentStringParser parser = UADetectorServiceFactory.getUserAgentStringParser();

	@Test
	public void testChrome13Parsing() throws Exception {
		final UserAgent.Builder builder = new UserAgent.Builder();
		builder.setProducerUrl("http://www.google.com/");
		builder.setProducer("Google Inc.");
		builder.setFamily("Chrome");
		builder.setName("Chrome 13.0.782.112");
		builder.setOperatingSystem(new OperatingSystem("Mac OS X", "Mac OS X 10.6 Snow Leopard", "Apple Computer, Inc.",
				"http://www.apple.com/", "http://www.apple.com/macosx/"));
		builder.setType("Browser");
		builder.setUrl("http://www.google.com/chrome");
		final UserAgent expected = builder.build();
		final UserAgent unknownAgentString = parser
				.parse("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1");
		Assert.assertEquals(expected, unknownAgentString);
	}

	@Test
	public void testEmptyUserAgentStringParsing() throws Exception {
		final UserAgent.Builder builder = new UserAgent.Builder();
		final UserAgent expected = builder.build();
		final UserAgent unknownAgentString = parser.parse("");
		Assert.assertEquals(expected, unknownAgentString);
	}

	@Test
	public void testFirefox6Parsing() throws Exception {
		final UserAgent.Builder builder = new UserAgent.Builder();
		builder.setProducerUrl("http://www.mozilla.org/");
		builder.setProducer("Mozilla Foundation");
		builder.setFamily("Firefox");
		builder.setName("Firefox 6.0");
		builder.setOperatingSystem(new OperatingSystem("Mac OS X", "Mac OS X 10.7 Lion", "Apple Computer, Inc.", "http://www.apple.com/",
				"http://www.apple.com/macosx/"));
		builder.setType("Browser");
		builder.setUrl("http://www.firefox.com/");
		final UserAgent expected = builder.build();
		final UserAgent unknownAgentString = parser
				.parse("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:6.0) Gecko/20100101 Firefox/6.0");
		Assert.assertEquals(expected, unknownAgentString);
	}

	@Test
	public void testGooglebotParsing() throws Exception {
		final UserAgent.Builder builder = new UserAgent.Builder();
		builder.setProducerUrl("http://www.google.com/");
		builder.setProducer("Google Inc.");
		builder.setFamily("Googlebot");
		builder.setName("Googlebot/2.1");
		builder.setUrl("");
		final UserAgent expected = builder.build();
		final UserAgent unknownAgentString = parser.parse("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
		Assert.assertEquals(expected, unknownAgentString);
	}

	@Test
	public void testSiteSuckerParsing() throws Exception {
		final UserAgent.Builder builder = new UserAgent.Builder();
		builder.setProducerUrl("");
		builder.setProducer("Rick Cranisky");
		builder.setFamily("SiteSucker");
		builder.setName("SiteSucker 1.6.9");
		builder.setOperatingSystem(new OperatingSystem("Mac OS", "Mac OS", "Apple Computer, Inc.", "http://www.apple.com/",
				"http://en.wikipedia.org/wiki/Mac_OS"));
		builder.setType("Offline Browser");
		builder.setUrl("http://www.sitesucker.us/");
		final UserAgent expected = builder.build();
		final UserAgent unknownAgentString = parser.parse("SiteSucker/1.6.9");
		Assert.assertEquals(expected, unknownAgentString);
	}

	@Test
	public void testUnknownUserAgentStringParsing() throws Exception {
		final UserAgent unknownAgentString = parser.parse("qwertzuiopasdfghjklyxcvbnm");
		Assert.assertEquals(UserAgent.EMPTY, unknownAgentString);
		Assert.assertEquals(OperatingSystem.EMPTY, unknownAgentString.getOperatingSystem());
	}

}
