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

import java.lang.reflect.Constructor;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.OperatingSystemFamily;
import net.sf.uadetector.VersionNumber;

import org.junit.Assert;
import org.junit.Test;

public class VersionParserTest {

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<VersionParser> constructor = VersionParser.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void identifyBadaVersion() {

		final String bada10 = "Mozilla/5.0 (SAMSUNG; SAMSUNG-GT-S7230E/S723EXXJJ3; U; Bada/1.0; nl-nl) AppleWebKit/533.1 (KHTML, like Gecko) Dolfin/2.0 Mobile WQVGA SMM-MMS/1.2.0 OPN-B";
		Assert.assertEquals("1.0", VersionParser.identifyBadaVersion(bada10).toVersionString());

		final String bada12 = "Mozilla/5.0 (SAMSUNG; SAMSUNG-GT-S8500/S8500JHKC1; U; Bada/1.2; en-us) AppleWebKit/533.1 (KHTML, like Gecko) Dolfin/2.2 Mobile WVGA SMM-MMS/1.2.0 OPN-B";
		Assert.assertEquals("1.2", VersionParser.identifyBadaVersion(bada12).toVersionString());

		final String bada20 = "Mozilla/5.0 (SAMSUNG; SAMSUNG-GT-S5380K/S5380KDDKK6; U; Bada/2.0; en-us) AppleWebKit/534.20 (KHTML";
		Assert.assertEquals("2.0", VersionParser.identifyBadaVersion(bada20).toVersionString());
	}

	@Test
	public void identifyBSDVersion() {

		final String openbsd51 = "Wget/1.13.4 (openbsd5.1)";
		Assert.assertEquals("5.1", VersionParser.identifyBSDVersion(openbsd51).toVersionString());

		final String freebsd90 = "Wget/1.12 (freebsd9.0)";
		Assert.assertEquals("9.0", VersionParser.identifyBSDVersion(freebsd90).toVersionString());

		final String openbsd42 = "curl/7.16.2 (i386-unknown-openbsd4.2) libcurl/7.16.2 OpenSSL/0.9.7j zlib/1.2.3 libidn/0.6.1";
		Assert.assertEquals("4.2", VersionParser.identifyBSDVersion(openbsd42).toVersionString());

		final String freebsd49 = "curl/7.14.0 (i386-portbld-freebsd4.9) libcurl/7.14.0 OpenSSL/0.9.7b zlib/1.1.4";
		Assert.assertEquals("4.9", VersionParser.identifyBSDVersion(freebsd49).toVersionString());

		final String freebsd82 = "Opera/9.80 (X11; FreeBSD 8.2-STABLE amd64; U; en) Presto/2.9.168 Version/11.52";
		Assert.assertEquals("8.2-STABLE", VersionParser.identifyBSDVersion(freebsd82).toVersionString());

		final String freebsd43 = "Mozilla/5.0 (X11; U; FreeBSD 4.3-YAHOO-20010518 i386; en-US; rv:0.9) Gecko/20010719";
		Assert.assertEquals("4.3-YAHOO-20010518", VersionParser.identifyBSDVersion(freebsd43).toVersionString());
	}

	@Test
	public void identifyIOSVersion_versionWithDots() {
		final String pix51 = "Pixellent 1.5.7 rv:31 (iPad; iPhone OS 5.1; de_DE)";
		Assert.assertEquals("5.1", VersionParser.identifyIOSVersion(pix51).toVersionString());
	}

	@Test
	public void identifyIOSVersion_versionWithUnderline() {
		final String ipad = "Mozilla/5.0 (iPad; U; CPU OS 5_1 like Mac OS X; en_us) AppleWebKit/6534.46 (KHTML, like Gecko) Version/5.0.2 Mobile/8L1 Safari/6533.18.5";
		Assert.assertEquals("5.1", VersionParser.identifyIOSVersion(ipad).toVersionString());

		final String iphone = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";
		Assert.assertEquals("5.1.1", VersionParser.identifyIOSVersion(iphone).toVersionString());
	}

	@Test
	public void identifyJavaVersion() {
		Assert.assertEquals("1.2", VersionParser.identifyJavaVersion("Java1.2").toVersionString());
		Assert.assertEquals("1.4.2_07", VersionParser.identifyJavaVersion("Java1.4.2_07").toVersionString());
		Assert.assertEquals("1.4.2_07", VersionParser.identifyJavaVersion("BlogBridge Service Java/1.4.2_07").toVersionString());
		Assert.assertEquals("1.6.0-beta", VersionParser.identifyJavaVersion("Java/1.6.0-beta").toVersionString());
		Assert.assertEquals("1.7.0_04-ea", VersionParser.identifyJavaVersion("Java/1.7.0_04-ea").toVersionString());
	}

	@Test
	public void identifyOSXVersion_versionWithDots() {
		final String userAgent = "Mozilla/4.0 (compatible; MSIE 6.0; PPC Mac OS X 10.6.5; Tasman 1.0)";
		final VersionNumber v = VersionParser.identifyOSXVersion(userAgent);
		Assert.assertEquals("10.6.5", v.toVersionString());
	}

	@Test
	public void identifyOSXVersion_versionWithUnderlineAndRoundBracket() {
		final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) Version/5.1.5 Safari/534.55.3";
		final VersionNumber v = VersionParser.identifyOSXVersion(userAgent);
		Assert.assertEquals("10.7.3", v.toVersionString());
	}

	@Test
	public void identifyOSXVersion_versionWithUnderlineAndSemicolon() {
		final String userAgent = "Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_5_8; pt-br) AppleWebKit/533.18.1 (KHTML, like Gecko) Version/5.0.2 Safari/533.18.5";
		final VersionNumber v = VersionParser.identifyOSXVersion(userAgent);
		Assert.assertEquals("10.5.8", v.toVersionString());
	}

	@Test
	public void identifySymbianVersion() {

		final String sym70s = "Nokia6600/1.0 (3.38.0) SymbianOS/7.0s Series60/2.0 Profile/MIDP-2.0 Configuration/CLDC-1.0";
		Assert.assertEquals("7.0s", VersionParser.identifySymbianVersion(sym70s).toVersionString());

		final String sym94 = "SonyEricssonU1i/R1BB; Mozilla/5.0 (SymbianOS/9.4; U; Series60/5.0 Profile/MIDP-2.1 Configuration/CLDC-1.1 model-orange) AppleWebKit/525 (KHTML, like Gecko) Version/3.0 Safari/525";
		Assert.assertEquals("9.4", VersionParser.identifySymbianVersion(sym94).toVersionString());

		final String sym70 = "X700/1.0 SymbianOS/7.0 Series60/2.0 Profile/MIDP-2.0 Configuration/CLDC-1.0";
		Assert.assertEquals("7.0", VersionParser.identifySymbianVersion(sym70).toVersionString());

		final String sym92 = "Mozilla/5.0 (SymbianOS/9.2; U; Series60/3.1 NokiaE71-1/300.21.012; Profile/MIDP-2.0 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML, like Gecko) Safari/413";
		Assert.assertEquals("9.2", VersionParser.identifySymbianVersion(sym92).toVersionString());

		final String sym61 = "6600/1.0 (Profile/MIDP-1.0 Configuration/CLDC-1.0; Series60/0.9; SymbianOS/6.1)";
		Assert.assertEquals("6.1", VersionParser.identifySymbianVersion(sym61).toVersionString());
	}

	@Test
	public void identifyVersionTest_unidentifiable() {
		final String userAgent = "abcdefghjklmnop";
		Assert.assertEquals(VersionNumber.UNKNOWN, VersionParser.identifyAndroidVersion(userAgent));
		Assert.assertEquals(VersionNumber.UNKNOWN, VersionParser.identifyBSDVersion(userAgent));
		Assert.assertEquals(VersionNumber.UNKNOWN, VersionParser.identifyBadaVersion(userAgent));
		Assert.assertEquals(VersionNumber.UNKNOWN, VersionParser.identifyIOSVersion(userAgent));
		Assert.assertEquals(VersionNumber.UNKNOWN, VersionParser.identifyJavaVersion(userAgent));
		Assert.assertEquals(VersionNumber.UNKNOWN, VersionParser.identifyOSXVersion(userAgent));
		Assert.assertEquals(VersionNumber.UNKNOWN, VersionParser.identifySymbianVersion(userAgent));
		Assert.assertEquals(VersionNumber.UNKNOWN, VersionParser.identifyWebOSVersion(userAgent));
		Assert.assertEquals(VersionNumber.UNKNOWN, VersionParser.identifyWindowsVersion(userAgent));
	}

	@Test
	public void identifyWebOSVersion() {
		final String hpwos305 = "Mozilla/5.0 (hp-tablet; Linux; hpwOS/3.0.5; U; en-US) AppleWebKit/534.6 (KHTML, like Gecko) wOSBrowser/234.83 Safari/534.6 TouchPad/1.0";
		Assert.assertEquals("3.0.5", VersionParser.identifyWebOSVersion(hpwos305).toVersionString());
		final String webos10 = "Mozilla/5.0 (webOS/1.0; U; en-US) AppleWebKit/525.27.1 (KHTML, like Gecko) Version/1.0 Safari/525.27.1 Pre/1.0";
		Assert.assertEquals("1.0", VersionParser.identifyWebOSVersion(webos10).toVersionString());
		final String webos201 = "Mozilla/5.0 (webOS/2.0.1; U; en-GB)(iPhone; U; en) AppleWebKit/532.2 Version/1.0 Safari/532.2 Pre/1.2 -CET";
		Assert.assertEquals("2.0.1", VersionParser.identifyWebOSVersion(webos201).toVersionString());
	}

	@Test
	public void identifyWindowsVersion() {
		final String windows31 = "Mozilla/2.0 (compatible; MSIE 3.0b; AOL 4.0; Windows 3.1)";
		Assert.assertEquals("3.1", VersionParser.identifyWindowsVersion(windows31).toVersionString());

		final String windows95_1 = "Mozilla/4.0 (compatible;  MSIE 5.0;  Windows 95)";
		Assert.assertEquals("95", VersionParser.identifyWindowsVersion(windows95_1).toVersionString());

		final String windows95_2 = "Mozilla/3.01 ( compatible; [de]; Windows 95; win9x/NT 4.90 )";
		Assert.assertEquals("95", VersionParser.identifyWindowsVersion(windows95_2).toVersionString());

		final String windows98 = "Mozilla/3.01 ( compatible; [en]; Windows 98; Compaq )";
		Assert.assertEquals("98", VersionParser.identifyWindowsVersion(windows98).toVersionString());

		final String windowsNT = "Mozilla/4.0 (compatible ; MSIE 5.5 SP2 ; Windows NT 4.0 SP6a ; IC-Client)";
		Assert.assertEquals("4.0", VersionParser.identifyWindowsVersion(windowsNT).toVersionString());

		final String windows2000 = "Mozilla/5.0 (Windows; U; Windows NT 5.0; de-DE; rv:1.0.2) Gecko/20030208 Netscape/7.02";
		Assert.assertEquals("5.0", VersionParser.identifyWindowsVersion(windows2000).toVersionString());

		final String windowsXP = "Mozilla/4.0 (compatible;  MSIE 6.0;  Windows NT 5.1;  SLCC1;  .NET CLR 1.1.4322;  .NET CLR 2.0.50727;  .NET CLR 3.0.30729;  .NET CLR 3.5.30707;  InfoPath.2)";
		Assert.assertEquals("5.1", VersionParser.identifyWindowsVersion(windowsXP).toVersionString());

		final String windows2003Server = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; WOW64; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; InfoPath.2)";
		Assert.assertEquals("5.2", VersionParser.identifyWindowsVersion(windows2003Server).toVersionString());

		final String windowsVista = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Trident/4.0; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; InfoPath.2; OfficeLiveConnector.1.3; OfficeLivePatch.0.0; .NET CLR 3.5.30729; .NET CLR 3.0.30618)";
		Assert.assertEquals("6.0", VersionParser.identifyWindowsVersion(windowsVista).toVersionString());

		final String windows7 = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Win64; x64; Trident/4.0; GTB7.2; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3; Zune 4.7)";
		Assert.assertEquals("6.1", VersionParser.identifyWindowsVersion(windows7).toVersionString());

		final String windowsPhone7 = "Mozilla/4.0 (compatible; MSIE 7.0; Windows Phone OS 7.0; Trident/3.1; IEMobile/7.0; HTC; 7 Mozart; Orange)";
		Assert.assertEquals("7.0", VersionParser.identifyWindowsVersion(windowsPhone7).toVersionString());
	}

	@Test
	public void parseFirstVersionNumber_emptyString() {
		final String userAgent = "";
		final VersionNumber v = VersionParser.parseFirstVersionNumber(userAgent);
		Assert.assertEquals(VersionNumber.UNKNOWN, v);
	}

	@Test
	public void parseFirstVersionNumber_MOZILLA() {
		final String userAgent = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
		final String version = "5.0";
		final VersionNumber v = VersionParser.parseFirstVersionNumber(userAgent);
		Assert.assertEquals(version, v.toVersionString());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void parseFirstVersionNumber_null() {
		VersionParser.parseFirstVersionNumber(null);
	}

	@Test
	public void parseLastVersionNumber_emptyString() {
		final String userAgent = "";
		final VersionNumber v = VersionParser.parseLastVersionNumber(userAgent);
		Assert.assertEquals(VersionNumber.UNKNOWN, v);
	}

	@Test
	public void parseLastVersionNumber_GOOGLEBOT() {
		final String userAgent = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
		final String version = "2.1";
		final VersionNumber v = VersionParser.parseLastVersionNumber(userAgent);
		Assert.assertEquals(version, v.toVersionString());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void parseLastVersionNumber_null() {
		VersionParser.parseLastVersionNumber(null);
	}

	@Test
	public void parseLastVersionNumber_OOZBOT() {
		final String userAgent = "OOZBOT/0.20 ( Setooz výrazný ako say-th-uuz, znamená mosty.  ; http://www.setooz.com/oozbot.html ; agentname at setooz dot_com )";
		final String version = "0.20";
		final VersionNumber v = VersionParser.parseLastVersionNumber(userAgent);
		Assert.assertEquals(version, v.toVersionString());
	}

	@Test
	public void parseLastVersionNumber_unknown() {
		final String userAgent = "qwertzuiopasdfghjklyxcvbnm";
		final VersionNumber v = VersionParser.parseLastVersionNumber(userAgent);
		System.out.println(v.toVersionString());
		Assert.assertEquals(VersionNumber.UNKNOWN, v);
	}

	@Test
	public void parseOperatingSystemVersion_differentFamilies() {

		final String bada = "Mozilla/5.0 (SAMSUNG; SAMSUNG-GT-S7230E/S723EXXJJ3; U; Bada/1.0; nl-nl) AppleWebKit/533.1 (KHTML, like Gecko) Dolfin/2.0 Mobile WQVGA SMM-MMS/1.2.0 OPN-B";
		Assert.assertEquals("1.0", VersionParser.parseOperatingSystemVersion(OperatingSystemFamily.BADA, bada).toVersionString());

		final String freebsd = "Opera/9.80 (X11; FreeBSD 8.2-STABLE amd64; U; en) Presto/2.9.168 Version/11.52";
		Assert.assertEquals("8.2-STABLE", VersionParser.parseOperatingSystemVersion(OperatingSystemFamily.BSD, freebsd).toVersionString());

		final String ipad = "Mozilla/5.0 (iPad; U; CPU OS 5_1 like Mac OS X; en_us) AppleWebKit/6534.46 (KHTML, like Gecko) Version/5.0.2 Mobile/8L1 Safari/6533.18.5";
		Assert.assertEquals("5.1", VersionParser.parseOperatingSystemVersion(OperatingSystemFamily.IOS, ipad).toVersionString());

		final String jvm = "Java/1.7.0_04-ea";
		Assert.assertEquals("1.7.0_04-ea", VersionParser.parseOperatingSystemVersion(OperatingSystemFamily.JVM, jvm).toVersionString());

		final String macosx = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) Version/5.1.5 Safari/534.55.3";
		Assert.assertEquals("10.7.3", VersionParser.parseOperatingSystemVersion(OperatingSystemFamily.OS_X, macosx).toVersionString());

		final String symbian = "SonyEricssonU1i/R1BB; Mozilla/5.0 (SymbianOS/9.4; U; Series60/5.0 Profile/MIDP-2.1 Configuration/CLDC-1.1 model-orange) AppleWebKit/525 (KHTML, like Gecko) Version/3.0 Safari/525";
		Assert.assertEquals("9.4", VersionParser.parseOperatingSystemVersion(OperatingSystemFamily.SYMBIAN, symbian).toVersionString());

		final String webos = "Mozilla/5.0 (hp-tablet; Linux; hpwOS/3.0.5; U; en-US) AppleWebKit/534.6 (KHTML, like Gecko) wOSBrowser/234.83 Safari/534.6 TouchPad/1.0";
		Assert.assertEquals("3.0.5", VersionParser.parseOperatingSystemVersion(OperatingSystemFamily.WEBOS, webos).toVersionString());

		final String windows8 = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)";
		Assert.assertEquals("6.2", VersionParser.parseOperatingSystemVersion(OperatingSystemFamily.WINDOWS, windows8).toVersionString());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void parseOperatingSystemVersion_family_null() {
		VersionParser.parseOperatingSystemVersion(null, "a user agent string");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void parseOperatingSystemVersion_userAgentString_null() {
		VersionParser.parseOperatingSystemVersion(OperatingSystemFamily.UNKNOWN, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void parseVersion() {
		VersionParser.parseVersion(null);
	}

	@Test
	public void parseVersion_1() {
		final String version = "1";
		final VersionNumber v = VersionParser.parseVersion(version);
		Assert.assertEquals(version, v.toVersionString());
	}

	@Test
	public void parseVersion_1_0_2_stable() {
		final String version = "1.0.2-stable";
		final VersionNumber v = VersionParser.parseVersion(version);
		Assert.assertEquals(version, v.toVersionString());
	}

	@Test
	public void parseVersion_emptyString() {
		final VersionNumber v = VersionParser.parseVersion("");
		Assert.assertEquals(VersionNumber.UNKNOWN, v);
	}

	@Test
	public void parseVersion_leadingZero() {
		Assert.assertEquals("3.5.07", VersionParser.parseVersion("3.5.07").toVersionString());
		Assert.assertEquals("10.00", VersionParser.parseVersion("10.00").toVersionString());
		Assert.assertEquals("5.0.0176", VersionParser.parseVersion("5.0.0176").toVersionString());
		Assert.assertEquals("10.00", VersionParser.parseVersion("10.00").toVersionString());
		Assert.assertEquals("10.00", VersionParser.parseVersion("10.00").toVersionString());
		Assert.assertEquals("5.01", VersionParser.parseVersion("5.01").toVersionString());
		Assert.assertEquals("4.01", VersionParser.parseVersion("4.01").toVersionString());
		Assert.assertEquals("6.02", VersionParser.parseVersion("6.02").toVersionString());
		Assert.assertEquals("4.01", VersionParser.parseVersion("4.01").toVersionString());
		Assert.assertEquals("0.016", VersionParser.parseVersion("0.016").toVersionString());
		Assert.assertEquals("4.01", VersionParser.parseVersion("4.01").toVersionString());
		Assert.assertEquals("10.00", VersionParser.parseVersion("10.00").toVersionString());
		Assert.assertEquals("3.5.07", VersionParser.parseVersion("3.5.07").toVersionString());
		Assert.assertEquals("5.05", VersionParser.parseVersion("5.05").toVersionString());
		Assert.assertEquals("5.01", VersionParser.parseVersion("5.01").toVersionString());
		Assert.assertEquals("0.002", VersionParser.parseVersion("0.002").toVersionString());
		Assert.assertEquals("2.60.0008", VersionParser.parseVersion("2.60.0008").toVersionString());
		Assert.assertEquals("4.04 [en]", VersionParser.parseVersion("4.04 [en]").toVersionString());
		Assert.assertEquals("4.08 [en]", VersionParser.parseVersion("4.08 [en]").toVersionString());
		Assert.assertEquals("7.02", VersionParser.parseVersion("7.02").toVersionString());
		Assert.assertEquals("11.01", VersionParser.parseVersion("11.01").toVersionString());
		Assert.assertEquals("7.03", VersionParser.parseVersion("7.03").toVersionString());
		Assert.assertEquals("10.00", VersionParser.parseVersion("10.00").toVersionString());
		Assert.assertEquals("11.01", VersionParser.parseVersion("11.01").toVersionString());
		Assert.assertEquals("11.00", VersionParser.parseVersion("11.00").toVersionString());
		Assert.assertEquals("6.01", VersionParser.parseVersion("6.01").toVersionString());
		Assert.assertEquals("9.02", VersionParser.parseVersion("9.02").toVersionString());
		Assert.assertEquals("0.016", VersionParser.parseVersion("0.016").toVersionString());
		Assert.assertEquals("0.020", VersionParser.parseVersion("0.020").toVersionString());
		Assert.assertEquals("3.04", VersionParser.parseVersion("3.04").toVersionString());
		Assert.assertEquals("5.0.0176", VersionParser.parseVersion("5.0.0176").toVersionString());
		Assert.assertEquals("10.00", VersionParser.parseVersion("10.00").toVersionString());
		Assert.assertEquals("5.01", VersionParser.parseVersion("5.01").toVersionString());
		Assert.assertEquals("6.00", VersionParser.parseVersion("6.00").toVersionString());
		Assert.assertEquals("1.00.A", VersionParser.parseVersion("1.00.A").toVersionString());
		Assert.assertEquals("0.06", VersionParser.parseVersion("0.06").toVersionString());
		Assert.assertEquals("1.01", VersionParser.parseVersion("1.01").toVersionString());
		Assert.assertEquals("1.00", VersionParser.parseVersion("1.00").toVersionString());
		Assert.assertEquals("0.01", VersionParser.parseVersion("0.01").toVersionString());
		Assert.assertEquals("9.00.00.3086", VersionParser.parseVersion("9.00.00.3086").toVersionString());
		Assert.assertEquals("9.04", VersionParser.parseVersion("9.04").toVersionString());
		Assert.assertEquals("9.04-beta1", VersionParser.parseVersion("9.04-beta1").toVersionString());
		Assert.assertEquals("6.03", VersionParser.parseVersion("6.03").toVersionString());
	}

	@Test
	public void parseVersion_minus1() {
		final String version = "-1";
		final VersionNumber v = VersionParser.parseVersion(version);
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, v.getMajor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, v.getMinor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, v.getBugfix());
		Assert.assertEquals(version, v.getExtension());
		Assert.assertEquals(version, v.toVersionString());
	}

	@Test
	public void parseVersion_specialChars() {
		final String version = "$%-";
		final VersionNumber v = VersionParser.parseVersion(version);
		Assert.assertEquals(version, v.getExtension());
		Assert.assertEquals(version, v.toVersionString());
	}

	@Test
	public void parseVersion_V() {
		final String version = "V";
		final VersionNumber v = VersionParser.parseVersion(version);
		Assert.assertEquals(version, v.getExtension());
		Assert.assertEquals(version, v.toVersionString());
	}

}
