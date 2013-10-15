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

import static org.fest.assertions.Assertions.assertThat;
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
		assertThat(PARSER.getDataStore().getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);
		assertThat(PARSER.getDataVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);
	}

	@Test
	public void getDataStore() {
		assertThat(PARSER.getDataStore()).isNotNull();
		assertThat(PARSER.getDataStore().getData()).isNotNull();
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
		assertThat(agent.getIcon()).isEqualTo("safari.png");
		assertThat(agent.getName()).isEqualTo("Safari");
		assertThat(agent.getProducer()).isEqualTo("Apple Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Browser");
		assertThat(agent.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Safari_%28web_browser%29");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.OS_X);
		assertThat(os.getFamilyName()).isEqualTo("OS X");
		assertThat(os.getIcon()).isEqualTo("macosx.png");
		assertThat(os.getName()).isEqualTo("OS X 10.6 Snow Leopard");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://www.apple.com/osx/");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("10.6.8");
	}

	@Test
	public void parse_browser_CHROME13() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.CHROME);
		assertThat(agent.getIcon()).isEqualTo("chrome.png");
		assertThat(agent.getName()).isEqualTo("Chrome");
		assertThat(agent.getProducer()).isEqualTo("Google Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.google.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.google.com/chrome");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("13.0.782.112");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.OS_X);
		assertThat(os.getFamilyName()).isEqualTo("OS X");
		assertThat(os.getIcon()).isEqualTo("macosx.png");
		assertThat(os.getName()).isEqualTo("OS X 10.6 Snow Leopard");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://www.apple.com/osx/");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("10.6.8");
	}

	@Test
	public void parse_browser_CHROME19() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_4) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.CHROME);
		assertThat(agent.getIcon()).isEqualTo("chrome.png");
		assertThat(agent.getName()).isEqualTo("Chrome");
		assertThat(agent.getProducer()).isEqualTo("Google Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.google.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.google.com/chrome");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("19.0.1084.46");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.OS_X);
		assertThat(os.getFamilyName()).isEqualTo("OS X");
		assertThat(os.getIcon()).isEqualTo("macosx.png");
		assertThat(os.getName()).isEqualTo("OS X 10.7 Lion");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://www.apple.com/osx/");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("10.7.4");
	}

	@Test
	public void parse_browser_EUDORA() throws Exception {
		final String userAgent = "Eudora";
		final UserAgent agent = PARSER.parse(userAgent);
		System.out.println(agent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isTrue();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.EUDORA);
		assertThat(agent.getIcon()).isEqualTo("eudora.png");
		assertThat(agent.getName()).isEqualTo("Eudora");
		assertThat(agent.getProducer()).isEqualTo("Qualcomm Incorporated.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.qualcomm.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.EMAIL_CLIENT);
		assertThat(agent.getTypeName()).isEqualTo("Email client");
		assertThat(agent.getUrl()).isEqualTo("http://www.eudora.com/archive.html");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("");
	}

	@Test
	public void parse_browser_EUDORA_withVersion() throws Exception {
		final String userAgent = "Eudora/6.2.4b8 (MacOS)";
		final UserAgent agent = PARSER.parse(userAgent);
		System.out.println(agent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.EUDORA);
		assertThat(agent.getIcon()).isEqualTo("eudora.png");
		assertThat(agent.getName()).isEqualTo("Eudora");
		assertThat(agent.getProducer()).isEqualTo("Qualcomm Incorporated.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.qualcomm.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.EMAIL_CLIENT);
		assertThat(agent.getTypeName()).isEqualTo("Email client");
		assertThat(agent.getUrl()).isEqualTo("http://www.eudora.com/archive.html");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("6.2.4b8");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.MAC_OS);
		assertThat(os.getFamilyName()).isEqualTo("Mac OS");
		assertThat(os.getIcon()).isEqualTo("macos.png");
		assertThat(os.getName()).isEqualTo("Mac OS");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Mac_OS");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("");
	}

	@Test
	public void parse_browser_FIREFOX6() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:6.0) Gecko/20100101 Firefox/6.0";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.FIREFOX);
		assertThat(agent.getIcon()).isEqualTo("firefox.png");
		assertThat(agent.getName()).isEqualTo("Firefox");
		assertThat(agent.getProducer()).isEqualTo("Mozilla Foundation");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.mozilla.org/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.firefox.com/");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("6.0");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.OS_X);
		assertThat(os.getFamilyName()).isEqualTo("OS X");
		assertThat(os.getIcon()).isEqualTo("macosx.png");
		assertThat(os.getName()).isEqualTo("OS X 10.7 Lion");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://www.apple.com/osx/");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("10.7");
	}

	@Test
	public void parse_browser_ICEWEASEL() throws Exception {
		final String userAgent = "Mozilla/5.0 (X11; U; Linux i686; de; rv:1.9.1.5) Gecko/20091112 Iceweasel/3.5.5 (like Firefox/3.5.5; Debian-3.5.5-1)";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.ICEWEASEL);
		assertThat(agent.getIcon()).isEqualTo("iceweasel.png");
		assertThat(agent.getName()).isEqualTo("IceWeasel");
		assertThat(agent.getProducer()).isEqualTo("Software in the Public Interest, Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.spi-inc.org/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.gnu.org/software/gnuzilla/");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("3.5.5");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.LINUX);
		assertThat(os.getFamilyName()).isEqualTo("Linux");
		assertThat(os.getIcon()).isEqualTo("linux_debian.png");
		assertThat(os.getName()).isEqualTo("Linux (Debian)");
		assertThat(os.getProducer()).isEqualTo("Software in the Public Interest, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.spi-inc.org/");
		assertThat(os.getUrl()).isEqualTo("http://www.debian.org/");
		assertThat(os.getVersionNumber()).isEqualTo(VersionNumber.UNKNOWN);
	}

	@Test
	public void parse_browser_IE7() throws Exception {
		final String userAgent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/4.0; GTB6.4; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; OfficeLiveConnector.1.3; OfficeLivePatch.0.0; .NET CLR 1.1.4322)";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.IE);
		assertThat(agent.getIcon()).isEqualTo("msie.png");
		assertThat(agent.getName()).isEqualTo("IE");
		assertThat(agent.getProducer()).isEqualTo("Microsoft Corporation.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.microsoft.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Browser");
		assertThat(agent.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Internet_Explorer");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("7.0");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.WINDOWS);
		assertThat(os.getFamilyName()).isEqualTo("Windows");
		assertThat(os.getIcon()).isEqualTo("windows-7.png");
		assertThat(os.getName()).isEqualTo("Windows 7");
		assertThat(os.getProducer()).isEqualTo("Microsoft Corporation.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.microsoft.com/");
		assertThat(os.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Windows_7");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("6.1");
	}

	@Test
	public void parse_browser_JVM() throws Exception {
		final String userAgent = "Java/1.6.0_31";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.JAVA);
		assertThat(agent.getIcon()).isEqualTo("java.png");
		assertThat(agent.getName()).isEqualTo("Java");
		assertThat(agent.getProducer()).isEqualTo("Sun Microsystems, Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.sun.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.LIBRARY);
		assertThat(agent.getTypeName()).isEqualTo("Library");
		assertThat(agent.getUrl()).isEqualTo("http://www.sun.com/java/");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("1.6.0_31");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.JVM);
		assertThat(os.getFamilyName()).isEqualTo("JVM");
		assertThat(os.getIcon()).isEqualTo("java.png");
		assertThat(os.getName()).isEqualTo("JVM (Java)");
		assertThat(os.getProducer()).isEqualTo("Sun Microsystems, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://en.wikipedia.org/wiki/Sun_Microsystems");
		assertThat(os.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Jvm");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("1.6.0_31");
	}

	@Test
	public void parse_browser_mobile_CHROME() throws Exception {
		final String userAgent = "Mozilla/5.0 (Linux; U; Android-4.0.3; en-us; Galaxy Nexus Build/IML74K) AppleWebKit/535.7 (KHTML, like Gecko) CrMo/16.0.912.75 Mobile Safari/535.7";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.CHROME_MOBILE);
		assertThat(agent.getIcon()).isEqualTo("chrome.png");
		assertThat(agent.getName()).isEqualTo("Chrome Mobile");
		assertThat(agent.getProducer()).isEqualTo("Google Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.google.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.MOBILE_BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Mobile Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.google.com/intl/en/chrome/browser/mobile/");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("16.0.912.75");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.ANDROID);
		assertThat(os.getFamilyName()).isEqualTo("Android");
		assertThat(os.getIcon()).isEqualTo("android.png");
		assertThat(os.getName()).isEqualTo("Android 4.0.x Ice Cream Sandwich");
		assertThat(os.getProducer()).isEqualTo("Google, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.google.com/");
		assertThat(os.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Android_%28operating_system%29");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("4.0.3");
	}

	@Test
	public void parse_browser_mobile_SAFARI_IPAD() throws Exception {
		final String userAgent = "Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.MOBILE_SAFARI);
		assertThat(agent.getIcon()).isEqualTo("safari.png");
		assertThat(agent.getName()).isEqualTo("Mobile Safari");
		assertThat(agent.getProducer()).isEqualTo("Apple Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.MOBILE_BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Mobile Browser");
		assertThat(agent.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Safari_%28web_browser%29");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("5.0.2");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.IOS);
		assertThat(os.getFamilyName()).isEqualTo("iOS");
		assertThat(os.getIcon()).isEqualTo("iphone.png");
		assertThat(os.getName()).isEqualTo("iOS");
		assertThat(os.getProducer()).isEqualTo("Apple Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/IOS");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("4.2.1");

		// distinguish as iPad
		if (OperatingSystemFamily.IOS == agent.getOperatingSystem().getFamily() && userAgent.contains("iPad")) {
			LOG.debug("I'm an iPad.");
			assertThat(true).isTrue();
		}
	}

	@Test
	public void parse_browser_mobile_SAFARI_IPAD_IOS5() throws Exception {
		final String userAgent = "Mozilla/5.0 (iPad; CPU OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.MOBILE_SAFARI);
		assertThat(agent.getIcon()).isEqualTo("safari.png");
		assertThat(agent.getName()).isEqualTo("Mobile Safari");
		assertThat(agent.getProducer()).isEqualTo("Apple Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.MOBILE_BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Mobile Browser");
		assertThat(agent.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Safari_%28web_browser%29");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("5.1");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.IOS);
		assertThat(os.getFamilyName()).isEqualTo("iOS");
		assertThat(os.getIcon()).isEqualTo("iphone.png");
		assertThat(os.getName()).isEqualTo("iOS 5");
		assertThat(os.getProducer()).isEqualTo("Apple Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/IOS_5");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("5.0");

		// distinguish as iPad
		if (OperatingSystemFamily.IOS == agent.getOperatingSystem().getFamily() && userAgent.contains("iPad")) {
			LOG.debug("I'm an iPad.");
			assertThat(true).isTrue();
		}
	}

	@Test
	public void parse_browser_mobile_SAFARI_IPHONE() throws Exception {
		final String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.MOBILE_SAFARI);
		assertThat(agent.getIcon()).isEqualTo("safari.png");
		assertThat(agent.getName()).isEqualTo("Mobile Safari");
		assertThat(agent.getProducer()).isEqualTo("Apple Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.MOBILE_BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Mobile Browser");
		assertThat(agent.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Safari_%28web_browser%29");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("5.1");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.IOS);
		assertThat(os.getFamilyName()).isEqualTo("iOS");
		assertThat(os.getIcon()).isEqualTo("iphone.png");
		assertThat(os.getName()).isEqualTo("iOS 5");
		assertThat(os.getProducer()).isEqualTo("Apple Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/IOS_5");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("5.1.1");

		// distinguish as iPhone
		if (OperatingSystemFamily.IOS == agent.getOperatingSystem().getFamily() && userAgent.contains("iPhone")) {
			LOG.debug("I'm an iPhone.");
			assertThat(true).isTrue();
		}
	}

	@Test
	public void parse_browser_OPERA() throws Exception {
		final String userAgent = "Opera/9.80 (Windows NT 5.1; U; cs) Presto/2.2.15 Version/10.00";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.OPERA);
		assertThat(agent.getIcon()).isEqualTo("opera.png");
		assertThat(agent.getName()).isEqualTo("Opera");
		assertThat(agent.getProducer()).isEqualTo("Opera Software ASA.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.opera.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.opera.com/");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("10.00");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.WINDOWS);
		assertThat(os.getFamilyName()).isEqualTo("Windows");
		assertThat(os.getIcon()).isEqualTo("windowsxp.png");
		assertThat(os.getName()).isEqualTo("Windows XP");
		assertThat(os.getProducer()).isEqualTo("Microsoft Corporation.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.microsoft.com/");
		assertThat(os.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Windows_XP");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("5.1");
	}

	@Test
	public void parse_browser_SAFARI() throws Exception {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) Version/5.1.5 Safari/534.55.3";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isFalse();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.SAFARI);
		assertThat(agent.getIcon()).isEqualTo("safari.png");
		assertThat(agent.getName()).isEqualTo("Safari");
		assertThat(agent.getProducer()).isEqualTo("Apple Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Browser");
		assertThat(agent.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Safari_%28web_browser%29");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("5.1.5");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.OS_X);
		assertThat(os.getFamilyName()).isEqualTo("OS X");
		assertThat(os.getIcon()).isEqualTo("macosx.png");
		assertThat(os.getName()).isEqualTo("OS X 10.7 Lion");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://www.apple.com/osx/");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("10.7.3");
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
		assertThat(agent.getIcon()).isEqualTo("sitesucker.png");
		assertThat(agent.getName()).isEqualTo("SiteSucker");
		assertThat(agent.getProducer()).isEqualTo("Rick Cranisky");
		assertThat(agent.getProducerUrl()).isEqualTo("");
		assertThat(agent.getType()).isEqualTo(UserAgentType.OFFLINE_BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Offline Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.sitesucker.us/");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("1.6.9");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		assertThat(os.getFamily()).isEqualTo(OperatingSystemFamily.MAC_OS);
		assertThat(os.getFamilyName()).isEqualTo("Mac OS");
		assertThat(os.getIcon()).isEqualTo("macos.png");
		assertThat(os.getName()).isEqualTo("Mac OS");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://en.wikipedia.org/wiki/Mac_OS");
		assertThat(os.getVersionNumber()).isEqualTo(VersionNumber.UNKNOWN);
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
		assertThat(agent.getIcon()).isEqualTo("skyfire.png");
		assertThat(agent.getName()).isEqualTo("Skyfire");
		assertThat(agent.getProducer()).isEqualTo("Skyfire Labs, Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.skyfire.com/about");
		assertThat(agent.getType()).isEqualTo(UserAgentType.MOBILE_BROWSER);
		assertThat(agent.getTypeName()).isEqualTo("Mobile Browser");
		assertThat(agent.getUrl()).isEqualTo("http://www.skyfire.com/");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("2.0");

		// check operating system informations
		final OperatingSystem os = agent.getOperatingSystem();
		// assertThat( os.getFamily()).isEqualTo(OperatingSystemFamily.OS_X);
		// assertThat( os.getFamilyName()).isEqualTo("OS X");
		assertThat(os.getIcon()).isEqualTo("macosx.png");
		assertThat(os.getName()).isEqualTo("OS X 10.5 Leopard");
		assertThat(os.getProducer()).isEqualTo("Apple Computer, Inc.");
		assertThat(os.getProducerUrl()).isEqualTo("http://www.apple.com/");
		assertThat(os.getUrl()).isEqualTo("http://www.apple.com/osx/");
		assertThat(os.getVersionNumber().toVersionString()).isEqualTo("10.5.7");
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
		assertThat(agent.getIcon()).isEqualTo("skyfire.png");
		assertThat(agent.getName()).isEqualTo("Skyfire");
		assertThat(agent.getProducer()).isEqualTo("Skyfire Labs, Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.skyfire.com/about");
		assertThat(agent.getType()).isEqualTo(UserAgentType.MOBILE_BROWSER);
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
		assertThat(agent.getIcon()).isEqualTo(e.getIcon());
		assertThat(agent.getName()).isEqualTo(e.getName());
		assertThat(agent.getProducer()).isEqualTo(e.getProducer());
		assertThat(agent.getProducerUrl()).isEqualTo(e.getProducerUrl());
		assertThat(agent.getTypeName()).isEqualTo(e.getTypeName());
		assertThat(agent.getUrl()).isEqualTo(e.getUrl());
		assertThat(agent.getVersionNumber()).isEqualTo(VersionNumber.UNKNOWN);

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
		assertThat(agent.getIcon()).isEqualTo("bot_googlebot.png");
		assertThat(agent.getName()).isEqualTo("Googlebot/2.1");
		assertThat(agent.getProducer()).isEqualTo("Google Inc.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.google.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.ROBOT);
		assertThat(agent.getTypeName()).isEqualTo(Robot.TYPENAME);
		assertThat(agent.getUrl()).isEqualTo("/list-of-ua/bot-detail?bot=Googlebot");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("2.1");
	}

	@Test
	public void parse_robot_SETOOZ() throws Exception {
		final String userAgent = "OOZBOT/0.20 ( Setooz výrazný ako say-th-uuz, znamená mosty.  ; http://www.setooz.com/oozbot.html ; agentname at setooz dot_com )";
		final UserAgent agent = PARSER.parse(userAgent);
		assertThat(agent).isNotNull();
		assertThat(UserAgent.EMPTY.equals(agent)).isFalse();
		assertThat(OperatingSystem.EMPTY.equals(agent.getOperatingSystem())).isTrue();

		// check user agent informations
		assertThat(agent.getFamily()).isEqualTo(UserAgentFamily.SETOOZBOT);
		assertThat(agent.getIcon()).isEqualTo("bot.png");
		assertThat(agent.getName()).isEqualTo("OOZBOT/0.20 b");
		assertThat(agent.getProducer()).isEqualTo("SETU Software Systems (P) Ltd.");
		assertThat(agent.getProducerUrl()).isEqualTo("http://www.setusoftware.com/");
		assertThat(agent.getType()).isEqualTo(UserAgentType.ROBOT);
		assertThat(agent.getTypeName()).isEqualTo(Robot.TYPENAME);
		assertThat(agent.getUrl()).isEqualTo("/list-of-ua/bot-detail?bot=Setoozbot");
		assertThat(agent.getVersionNumber().toVersionString()).isEqualTo("0.20 b");
	}

	@Test
	public void parse_unknownString() {
		final UserAgent agent = PARSER.parse("qwertzuiopasdfghjklyxcvbnm");

		// check user agent informations
		final UserAgent e = UserAgent.EMPTY;
		assertThat(agent.getFamily()).isEqualTo(e.getFamily());
		assertThat(agent.getIcon()).isEqualTo(e.getIcon());
		assertThat(agent.getName()).isEqualTo(e.getName());
		assertThat(agent.getProducer()).isEqualTo(e.getProducer());
		assertThat(agent.getProducerUrl()).isEqualTo(e.getProducerUrl());
		assertThat(agent.getType()).isEqualTo(UserAgentType.UNKNOWN);
		assertThat(agent.getTypeName()).isEqualTo(e.getTypeName());
		assertThat(agent.getUrl()).isEqualTo(e.getUrl());
		assertThat(agent.getVersionNumber()).isEqualTo(VersionNumber.UNKNOWN);

		assertThat(agent.getOperatingSystem()).isEqualTo(OperatingSystem.EMPTY);
	}

	@Test
	public void shutdown() {
		// shutdown must not interrupt the caller
		new UserAgentStringParserImpl<DataStore>(new TestXmlDataStore()).shutdown();
	}

}
