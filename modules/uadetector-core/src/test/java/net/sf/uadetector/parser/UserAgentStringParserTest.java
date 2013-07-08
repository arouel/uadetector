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

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.OperatingSystem;
import net.sf.uadetector.OperatingSystemFamily;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.UserAgentType;
import net.sf.uadetector.VersionNumber;
import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.datastore.TestXmlDataStore;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAgentStringParserTest {

	private static final Logger LOG = LoggerFactory.getLogger(UserAgentStringParserTest.class);

	private static final UserAgentStringParserImpl<DataStore> PARSER = new UserAgentStringParserImpl<DataStore>(new TestXmlDataStore());

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_stream_null() throws Exception {
		new UserAgentStringParserImpl<DataStore>(null);
	}

	@Test
	public void getCurrentVersion() {
		Assert.assertEquals("20130321-01", PARSER.getDataStore().getData().getVersion());
		Assert.assertEquals("20130321-01", PARSER.getDataVersion());
	}

	@Test
	public void getDataStore() {
		Assert.assertNotNull(PARSER.getDataStore());
		Assert.assertNotNull(PARSER.getDataStore().getData());
	}

	@Test
	public void parse_browser_CHROME_withoutVersionInfo() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/$ Safari/535.1";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.SAFARI, agent.getFamily());
		Assert.assertEquals("safari.png", agent.getIcon());
		Assert.assertEquals("Safari", agent.getName());
		Assert.assertEquals("Apple Inc.", agent.getProducer());
		Assert.assertEquals("http://www.apple.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.BROWSER, agent.getType());
		Assert.assertEquals("Browser", agent.getTypeName());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Safari_%28web_browser%29", agent.getUrl());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.OS_X, os.getFamily());
		Assert.assertEquals("OS X", os.getFamilyName());
		Assert.assertEquals("macosx.png", os.getIcon());
		Assert.assertEquals("OS X 10.6 Snow Leopard", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://www.apple.com/osx/", os.getUrl());
		Assert.assertEquals("10.6.8", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_CHROME13() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.CHROME, agent.getFamily());
		Assert.assertEquals("chrome.png", agent.getIcon());
		Assert.assertEquals("Chrome", agent.getName());
		Assert.assertEquals("Google Inc.", agent.getProducer());
		Assert.assertEquals("http://www.google.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.BROWSER, agent.getType());
		Assert.assertEquals("Browser", agent.getTypeName());
		Assert.assertEquals("http://www.google.com/chrome", agent.getUrl());
		Assert.assertEquals("13.0.782.112", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.OS_X, os.getFamily());
		Assert.assertEquals("OS X", os.getFamilyName());
		Assert.assertEquals("macosx.png", os.getIcon());
		Assert.assertEquals("OS X 10.6 Snow Leopard", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://www.apple.com/osx/", os.getUrl());
		Assert.assertEquals("10.6.8", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_CHROME19() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_4) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.CHROME, agent.getFamily());
		Assert.assertEquals("chrome.png", agent.getIcon());
		Assert.assertEquals("Chrome", agent.getName());
		Assert.assertEquals("Google Inc.", agent.getProducer());
		Assert.assertEquals("http://www.google.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.BROWSER, agent.getType());
		Assert.assertEquals("Browser", agent.getTypeName());
		Assert.assertEquals("http://www.google.com/chrome", agent.getUrl());
		Assert.assertEquals("19.0.1084.46", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.OS_X, os.getFamily());
		Assert.assertEquals("OS X", os.getFamilyName());
		Assert.assertEquals("macosx.png", os.getIcon());
		Assert.assertEquals("OS X 10.7 Lion", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://www.apple.com/osx/", os.getUrl());
		Assert.assertEquals("10.7.4", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_EUDORA() throws Exception {
		final String userAgent = "Eudora";
		final UserAgent agent = PARSER.parse(userAgent);
		System.out.println(agent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertTrue(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.EUDORA, agent.getFamily());
		Assert.assertEquals("eudora.png", agent.getIcon());
		Assert.assertEquals("Eudora", agent.getName());
		Assert.assertEquals("Qualcomm Incorporated.", agent.getProducer());
		Assert.assertEquals("http://www.qualcomm.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.EMAIL_CLIENT, agent.getType());
		Assert.assertEquals("Email client", agent.getTypeName());
		Assert.assertEquals("http://www.eudora.com/archive.html", agent.getUrl());
		Assert.assertEquals("", agent.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_EUDORA_withVersion() throws Exception {
		final String userAgent = "Eudora/6.2.4b8 (MacOS)";
		final UserAgent agent = PARSER.parse(userAgent);
		System.out.println(agent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.EUDORA, agent.getFamily());
		Assert.assertEquals("eudora.png", agent.getIcon());
		Assert.assertEquals("Eudora", agent.getName());
		Assert.assertEquals("Qualcomm Incorporated.", agent.getProducer());
		Assert.assertEquals("http://www.qualcomm.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.EMAIL_CLIENT, agent.getType());
		Assert.assertEquals("Email client", agent.getTypeName());
		Assert.assertEquals("http://www.eudora.com/archive.html", agent.getUrl());
		Assert.assertEquals("6.2.4b8", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.MAC_OS, os.getFamily());
		Assert.assertEquals("Mac OS", os.getFamilyName());
		Assert.assertEquals("macos.png", os.getIcon());
		Assert.assertEquals("Mac OS", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Mac_OS", os.getUrl());
		Assert.assertEquals("", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_FIREFOX6() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:6.0) Gecko/20100101 Firefox/6.0";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.FIREFOX, agent.getFamily());
		Assert.assertEquals("firefox.png", agent.getIcon());
		Assert.assertEquals("Firefox", agent.getName());
		Assert.assertEquals("Mozilla Foundation", agent.getProducer());
		Assert.assertEquals("http://www.mozilla.org/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.BROWSER, agent.getType());
		Assert.assertEquals("Browser", agent.getTypeName());
		Assert.assertEquals("http://www.firefox.com/", agent.getUrl());
		Assert.assertEquals("6.0", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.OS_X, os.getFamily());
		Assert.assertEquals("OS X", os.getFamilyName());
		Assert.assertEquals("macosx.png", os.getIcon());
		Assert.assertEquals("OS X 10.7 Lion", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://www.apple.com/osx/", os.getUrl());
		Assert.assertEquals("10.7", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_ICEWEASEL() throws Exception {
		final String userAgent = "Mozilla/5.0 (X11; U; Linux i686; de; rv:1.9.1.5) Gecko/20091112 Iceweasel/3.5.5 (like Firefox/3.5.5; Debian-3.5.5-1)";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.ICEWEASEL, agent.getFamily());
		Assert.assertEquals("iceweasel.png", agent.getIcon());
		Assert.assertEquals("IceWeasel", agent.getName());
		Assert.assertEquals("Software in the Public Interest, Inc.", agent.getProducer());
		Assert.assertEquals("http://www.spi-inc.org/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.BROWSER, agent.getType());
		Assert.assertEquals("Browser", agent.getTypeName());
		Assert.assertEquals("http://www.gnu.org/software/gnuzilla/", agent.getUrl());
		Assert.assertEquals("3.5.5", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.LINUX, os.getFamily());
		Assert.assertEquals("Linux", os.getFamilyName());
		Assert.assertEquals("linux_debian.png", os.getIcon());
		Assert.assertEquals("Linux (Debian)", os.getName());
		Assert.assertEquals("Software in the Public Interest, Inc.", os.getProducer());
		Assert.assertEquals("http://www.spi-inc.org/", os.getProducerUrl());
		Assert.assertEquals("http://www.debian.org/", os.getUrl());
		Assert.assertEquals(VersionNumber.UNKNOWN, os.getVersionNumber());
	}

	@Test
	public void parse_browser_IE7() throws Exception {
		final String userAgent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/4.0; GTB6.4; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; OfficeLiveConnector.1.3; OfficeLivePatch.0.0; .NET CLR 1.1.4322)";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.IE, agent.getFamily());
		Assert.assertEquals("msie.png", agent.getIcon());
		Assert.assertEquals("IE", agent.getName());
		Assert.assertEquals("Microsoft Corporation.", agent.getProducer());
		Assert.assertEquals("http://www.microsoft.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.BROWSER, agent.getType());
		Assert.assertEquals("Browser", agent.getTypeName());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Internet_Explorer", agent.getUrl());
		Assert.assertEquals("7.0", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.WINDOWS, os.getFamily());
		Assert.assertEquals("Windows", os.getFamilyName());
		Assert.assertEquals("windows-7.png", os.getIcon());
		Assert.assertEquals("Windows 7", os.getName());
		Assert.assertEquals("Microsoft Corporation.", os.getProducer());
		Assert.assertEquals("http://www.microsoft.com/", os.getProducerUrl());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Windows_7", os.getUrl());
		Assert.assertEquals("6.1", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_JVM() throws Exception {
		final String userAgent = "Java/1.6.0_31";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.JAVA, agent.getFamily());
		Assert.assertEquals("java.png", agent.getIcon());
		Assert.assertEquals("Java", agent.getName());
		Assert.assertEquals("Sun Microsystems, Inc.", agent.getProducer());
		Assert.assertEquals("http://www.sun.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.LIBRARY, agent.getType());
		Assert.assertEquals("Library", agent.getTypeName());
		Assert.assertEquals("http://www.sun.com/java/", agent.getUrl());
		Assert.assertEquals("1.6.0_31", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.JVM, os.getFamily());
		Assert.assertEquals("JVM", os.getFamilyName());
		Assert.assertEquals("java.png", os.getIcon());
		Assert.assertEquals("JVM (Java)", os.getName());
		Assert.assertEquals("Sun Microsystems, Inc.", os.getProducer());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Sun_Microsystems", os.getProducerUrl());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Jvm", os.getUrl());
		Assert.assertEquals("1.6.0_31", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_mobile_CHROME() throws Exception {
		final String userAgent = "Mozilla/5.0 (Linux; U; Android-4.0.3; en-us; Galaxy Nexus Build/IML74K) AppleWebKit/535.7 (KHTML, like Gecko) CrMo/16.0.912.75 Mobile Safari/535.7";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.CHROME_MOBILE, agent.getFamily());
		Assert.assertEquals("chrome.png", agent.getIcon());
		Assert.assertEquals("Chrome Mobile", agent.getName());
		Assert.assertEquals("Google Inc.", agent.getProducer());
		Assert.assertEquals("http://www.google.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.MOBILE_BROWSER, agent.getType());
		Assert.assertEquals("Mobile Browser", agent.getTypeName());
		Assert.assertEquals("http://www.google.com/intl/en/chrome/browser/mobile/", agent.getUrl());
		Assert.assertEquals("16.0.912.75", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.ANDROID, os.getFamily());
		Assert.assertEquals("Android", os.getFamilyName());
		Assert.assertEquals("android.png", os.getIcon());
		Assert.assertEquals("Android 4.0.x Ice Cream Sandwich", os.getName());
		Assert.assertEquals("Google, Inc.", os.getProducer());
		Assert.assertEquals("http://www.google.com/", os.getProducerUrl());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Android_%28operating_system%29", os.getUrl());
		Assert.assertEquals("4.0.3", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_mobile_SAFARI_IPAD() throws Exception {
		final String userAgent = "Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.MOBILE_SAFARI, agent.getFamily());
		Assert.assertEquals("safari.png", agent.getIcon());
		Assert.assertEquals("Mobile Safari", agent.getName());
		Assert.assertEquals("Apple Inc.", agent.getProducer());
		Assert.assertEquals("http://www.apple.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.MOBILE_BROWSER, agent.getType());
		Assert.assertEquals("Mobile Browser", agent.getTypeName());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Safari_%28web_browser%29", agent.getUrl());
		Assert.assertEquals("5.0.2", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.IOS, os.getFamily());
		Assert.assertEquals("iOS", os.getFamilyName());
		Assert.assertEquals("iphone.png", os.getIcon());
		Assert.assertEquals("iOS", os.getName());
		Assert.assertEquals("Apple Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://en.wikipedia.org/wiki/IOS", os.getUrl());
		Assert.assertEquals("4.2.1", os.getVersionNumber().toVersionString());

		// distinguish as iPad
		if (OperatingSystemFamily.IOS == agent.getOperatingSystem().getFamily() && userAgent.contains("iPad")) {
			LOG.debug("I'm an iPad.");
			Assert.assertTrue(true);
		}
	}

	@Test
	public void parse_browser_mobile_SAFARI_IPAD_IOS5() throws Exception {
		final String userAgent = "Mozilla/5.0 (iPad; CPU OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.MOBILE_SAFARI, agent.getFamily());
		Assert.assertEquals("safari.png", agent.getIcon());
		Assert.assertEquals("Mobile Safari", agent.getName());
		Assert.assertEquals("Apple Inc.", agent.getProducer());
		Assert.assertEquals("http://www.apple.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.MOBILE_BROWSER, agent.getType());
		Assert.assertEquals("Mobile Browser", agent.getTypeName());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Safari_%28web_browser%29", agent.getUrl());
		Assert.assertEquals("5.1", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.IOS, os.getFamily());
		Assert.assertEquals("iOS", os.getFamilyName());
		Assert.assertEquals("iphone.png", os.getIcon());
		Assert.assertEquals("iOS 5", os.getName());
		Assert.assertEquals("Apple Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://en.wikipedia.org/wiki/IOS", os.getUrl());
		Assert.assertEquals("5.0", os.getVersionNumber().toVersionString());

		// distinguish as iPad
		if (OperatingSystemFamily.IOS == agent.getOperatingSystem().getFamily() && userAgent.contains("iPad")) {
			LOG.debug("I'm an iPad.");
			Assert.assertTrue(true);
		}
	}

	@Test
	public void parse_browser_mobile_SAFARI_IPHONE() throws Exception {
		final String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.MOBILE_SAFARI, agent.getFamily());
		Assert.assertEquals("safari.png", agent.getIcon());
		Assert.assertEquals("Mobile Safari", agent.getName());
		Assert.assertEquals("Apple Inc.", agent.getProducer());
		Assert.assertEquals("http://www.apple.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.MOBILE_BROWSER, agent.getType());
		Assert.assertEquals("Mobile Browser", agent.getTypeName());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Safari_%28web_browser%29", agent.getUrl());
		Assert.assertEquals("5.1", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.IOS, os.getFamily());
		Assert.assertEquals("iOS", os.getFamilyName());
		Assert.assertEquals("iphone.png", os.getIcon());
		Assert.assertEquals("iOS 5", os.getName());
		Assert.assertEquals("Apple Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://en.wikipedia.org/wiki/IOS", os.getUrl());
		Assert.assertEquals("5.1.1", os.getVersionNumber().toVersionString());

		// distinguish as iPhone
		if (OperatingSystemFamily.IOS == agent.getOperatingSystem().getFamily() && userAgent.contains("iPhone")) {
			LOG.debug("I'm an iPhone.");
			Assert.assertTrue(true);
		}
	}

	@Test
	public void parse_browser_OPERA() throws Exception {
		final String userAgent = "Opera/9.80 (Windows NT 5.1; U; cs) Presto/2.2.15 Version/10.00";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.OPERA, agent.getFamily());
		Assert.assertEquals("opera.png", agent.getIcon());
		Assert.assertEquals("Opera", agent.getName());
		Assert.assertEquals("Opera Software ASA.", agent.getProducer());
		Assert.assertEquals("http://www.opera.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.BROWSER, agent.getType());
		Assert.assertEquals("Browser", agent.getTypeName());
		Assert.assertEquals("http://www.opera.com/", agent.getUrl());
		Assert.assertEquals("10.00", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.WINDOWS, os.getFamily());
		Assert.assertEquals("Windows", os.getFamilyName());
		Assert.assertEquals("windowsxp.png", os.getIcon());
		Assert.assertEquals("Windows XP", os.getName());
		Assert.assertEquals("Microsoft Corporation.", os.getProducer());
		Assert.assertEquals("http://www.microsoft.com/", os.getProducerUrl());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Windows_XP", os.getUrl());
		Assert.assertEquals("5.1", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_SAFARI() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) Version/5.1.5 Safari/534.55.3";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.SAFARI, agent.getFamily());
		Assert.assertEquals("safari.png", agent.getIcon());
		Assert.assertEquals("Safari", agent.getName());
		Assert.assertEquals("Apple Inc.", agent.getProducer());
		Assert.assertEquals("http://www.apple.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.BROWSER, agent.getType());
		Assert.assertEquals("Browser", agent.getTypeName());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Safari_%28web_browser%29", agent.getUrl());
		Assert.assertEquals("5.1.5", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.OS_X, os.getFamily());
		Assert.assertEquals("OS X", os.getFamilyName());
		Assert.assertEquals("macosx.png", os.getIcon());
		Assert.assertEquals("OS X 10.7 Lion", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://www.apple.com/osx/", os.getUrl());
		Assert.assertEquals("10.7.3", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_SITESUCKER() throws Exception {
		final String userAgent = "SiteSucker/1.6.9";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.SITESUCKER, agent.getFamily());
		Assert.assertEquals("sitesucker.png", agent.getIcon());
		Assert.assertEquals("SiteSucker", agent.getName());
		Assert.assertEquals("Rick Cranisky", agent.getProducer());
		Assert.assertEquals("", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.OFFLINE_BROWSER, agent.getType());
		Assert.assertEquals("Offline Browser", agent.getTypeName());
		Assert.assertEquals("http://www.sitesucker.us/", agent.getUrl());
		Assert.assertEquals("1.6.9", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		Assert.assertEquals(OperatingSystemFamily.MAC_OS, os.getFamily());
		Assert.assertEquals("Mac OS", os.getFamilyName());
		Assert.assertEquals("macos.png", os.getIcon());
		Assert.assertEquals("Mac OS", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Mac_OS", os.getUrl());
		Assert.assertEquals(VersionNumber.UNKNOWN, os.getVersionNumber());
	}

	@Test
	public void parse_browser_SKYFIRE() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_7; en-us) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Safari/530.17 Skyfire/2.0";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertFalse(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.SKYFIRE, agent.getFamily());
		Assert.assertEquals("skyfire.png", agent.getIcon());
		Assert.assertEquals("Skyfire", agent.getName());
		Assert.assertEquals("Skyfire Labs, Inc.", agent.getProducer());
		Assert.assertEquals("http://www.skyfire.com/about", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.MOBILE_BROWSER, agent.getType());
		Assert.assertEquals("Mobile Browser", agent.getTypeName());
		Assert.assertEquals("http://www.skyfire.com/", agent.getUrl());
		Assert.assertEquals("2.0", agent.getVersionNumber().toVersionString());

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		// Assert.assertEquals(OperatingSystemFamily.OS_X, os.getFamily());
		// Assert.assertEquals("OS X", os.getFamilyName());
		Assert.assertEquals("macosx.png", os.getIcon());
		Assert.assertEquals("OS X 10.5 Leopard", os.getName());
		Assert.assertEquals("Apple Computer, Inc.", os.getProducer());
		Assert.assertEquals("http://www.apple.com/", os.getProducerUrl());
		Assert.assertEquals("http://www.apple.com/osx/", os.getUrl());
		Assert.assertEquals("10.5.7", os.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_browser_SKYFIRE_withoutOperatingSystemInfo() throws Exception {
		final String userAgent = "Mozilla/5.0 AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Safari/530.17 Skyfire/2.0";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertTrue(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.SKYFIRE, agent.getFamily());
		Assert.assertEquals("skyfire.png", agent.getIcon());
		Assert.assertEquals("Skyfire", agent.getName());
		Assert.assertEquals("Skyfire Labs, Inc.", agent.getProducer());
		Assert.assertEquals("http://www.skyfire.com/about", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.MOBILE_BROWSER, agent.getType());
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
		Assert.assertEquals(e.getIcon(), agent.getIcon());
		Assert.assertEquals(e.getName(), agent.getName());
		Assert.assertEquals(e.getProducer(), agent.getProducer());
		Assert.assertEquals(e.getProducerUrl(), agent.getProducerUrl());
		Assert.assertEquals(e.getTypeName(), agent.getTypeName());
		Assert.assertEquals(e.getUrl(), agent.getUrl());
		Assert.assertEquals(VersionNumber.UNKNOWN, agent.getVersionNumber());

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
		Assert.assertEquals(UserAgentFamily.GOOGLEBOT, agent.getFamily());
		Assert.assertEquals("bot_googlebot.png", agent.getIcon());
		Assert.assertEquals("Googlebot/2.1", agent.getName());
		Assert.assertEquals("Google Inc.", agent.getProducer());
		Assert.assertEquals("http://www.google.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.ROBOT, agent.getType());
		Assert.assertEquals(Robot.TYPENAME, agent.getTypeName());
		Assert.assertEquals("/list-of-ua/bot-detail?bot=Googlebot", agent.getUrl());
		Assert.assertEquals("2.1", agent.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_robot_SETOOZ() throws Exception {
		final String userAgent = "OOZBOT/0.20 ( Setooz výrazný ako say-th-uuz, znamená mosty.  ; http://www.setooz.com/oozbot.html ; agentname at setooz dot_com )";
		final UserAgent agent = PARSER.parse(userAgent);
		Assert.assertNotNull(agent);
		Assert.assertFalse(UserAgent.EMPTY.equals(agent));
		Assert.assertTrue(OperatingSystem.EMPTY.equals(agent.getOperatingSystem()));

		// check user agent informations
		Assert.assertEquals(UserAgentFamily.SETOOZBOT, agent.getFamily());
		Assert.assertEquals("bot.png", agent.getIcon());
		Assert.assertEquals("OOZBOT/0.20 b", agent.getName());
		Assert.assertEquals("SETU Software Systems P. Ltd.", agent.getProducer());
		Assert.assertEquals("http://www.setusoftware.com/", agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.ROBOT, agent.getType());
		Assert.assertEquals(Robot.TYPENAME, agent.getTypeName());
		Assert.assertEquals("/list-of-ua/bot-detail?bot=Setoozbot", agent.getUrl());
		Assert.assertEquals("0.20 b", agent.getVersionNumber().toVersionString());
	}

	@Test
	public void parse_unknownString() {
		final UserAgent agent = PARSER.parse("qwertzuiopasdfghjklyxcvbnm");

		// check user agent informations
		final UserAgent e = UserAgent.EMPTY;
		Assert.assertEquals(e.getFamily(), agent.getFamily());
		Assert.assertEquals(e.getIcon(), agent.getIcon());
		Assert.assertEquals(e.getName(), agent.getName());
		Assert.assertEquals(e.getProducer(), agent.getProducer());
		Assert.assertEquals(e.getProducerUrl(), agent.getProducerUrl());
		Assert.assertEquals(UserAgentType.UNKNOWN, agent.getType());
		Assert.assertEquals(e.getTypeName(), agent.getTypeName());
		Assert.assertEquals(e.getUrl(), agent.getUrl());
		Assert.assertEquals(VersionNumber.UNKNOWN, agent.getVersionNumber());

		Assert.assertEquals(OperatingSystem.EMPTY, agent.getOperatingSystem());
	}

}
