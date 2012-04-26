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
package net.sf.uadetector.internal.util;

import java.lang.reflect.Constructor;

import junit.framework.Assert;
import net.sf.uadetector.VersionNumber;

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

	@Test(expected = IllegalArgumentException.class)
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

	@Test(expected = IllegalArgumentException.class)
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
