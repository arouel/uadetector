/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.lang.reflect.Constructor;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;

import org.junit.Assert;
import org.junit.Test;

public class BrowserHashCodeBuilderTest {

	protected static final Browser create() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.FIREFOX;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem operatingSystem = new OperatingSystem(1, "n1", "f1", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
		final SortedSet<BrowserPattern> patterns = new TreeSet<BrowserPattern>();
		patterns.add(new BrowserPattern(1, Pattern.compile("[0-9]"), 1));
		patterns.add(new BrowserPattern(2, Pattern.compile("[a-z]"), 2));
		return new Browser(id, family, family.getName(), patterns, type, operatingSystem, icon, infoUrl, producer, producerUrl, url);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<BrowserHashCodeBuilder> constructor = BrowserHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void test() {
		final String hash1 = BrowserHashCodeBuilder.build(create());
		final String hash2 = BrowserHashCodeBuilder.build(create());
		Assert.assertEquals(hash1, hash2);
		Assert.assertEquals("57a53822a3db2877cfd851ee98f1b4991dfbf4ea6b39e55433b0597242ad859e", hash1);
	}

}
