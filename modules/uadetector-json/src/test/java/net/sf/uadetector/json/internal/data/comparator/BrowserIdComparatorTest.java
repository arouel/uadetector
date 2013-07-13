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
package net.sf.uadetector.json.internal.data.comparator;

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

public class BrowserIdComparatorTest {

	private static final Browser BROWSER_1 = create(1);

	private static final Browser BROWSER_2 = create(2);

	private static final Browser create(final int id) {
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
	public void compare_bothNull() {
		Assert.assertEquals(0, new BrowserIdComparator().compare(null, null));
	}

	@Test
	public void compare_equalsBoth() {
		Assert.assertEquals(0, new BrowserIdComparator().compare(BROWSER_1, BROWSER_1));
		Assert.assertEquals(0, new BrowserIdComparator().compare(BROWSER_2, BROWSER_2));
	}

	@Test
	public void compare_leftHigher() {
		Assert.assertEquals(-1, new BrowserIdComparator().compare(BROWSER_1, BROWSER_2));
	}

	@Test
	public void compare_leftNull() {
		Assert.assertEquals(-1, new BrowserIdComparator().compare(null, BROWSER_1));
	}

	@Test
	public void compare_rightHigher() {
		Assert.assertEquals(1, new BrowserIdComparator().compare(BROWSER_2, BROWSER_1));
	}

	@Test
	public void compare_rightNull() {
		Assert.assertEquals(1, new BrowserIdComparator().compare(BROWSER_1, null));
	}

}
