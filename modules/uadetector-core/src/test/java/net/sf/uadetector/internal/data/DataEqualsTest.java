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
package net.sf.uadetector.internal.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;

public class DataEqualsTest {

	private static Browser createBrowser(final int id) {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		return new Browser(id, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
	}

	private static OperatingSystem createOperatingSystem(final int id) {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		return new OperatingSystem("f1", "i1", id, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
	}

	private static final Robot createRobot(final int id) {
		return new Robot(UserAgentFamily.GOOGLEBOT, "i1", id, "iu1", "n1", "p1", "pu1", "u1", "uas1");
	}

	@Test
	public void equals_different_browserPatterns() {
		final Browser browser = createBrowser(1);
		final BrowserPattern pattern1 = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		final BrowserPattern pattern2 = new BrowserPattern(2, Pattern.compile("[0-9]+"), 1);
		final BrowserPattern pattern3 = new BrowserPattern(3, Pattern.compile("[0-9]+"), 3);
		final BrowserPattern pattern4 = new BrowserPattern(3, Pattern.compile("[0-9]+"), 3);
		final TreeMap<BrowserPattern, Browser> patterns1 = new TreeMap<BrowserPattern, Browser>();
		patterns1.put(pattern1, browser);
		patterns1.put(pattern2, browser);
		final TreeMap<BrowserPattern, Browser> patterns2 = new TreeMap<BrowserPattern, Browser>();
		patterns2.put(pattern1, browser);
		final TreeMap<BrowserPattern, Browser> patterns3 = new TreeMap<BrowserPattern, Browser>();
		patterns3.put(pattern3, browser);
		final TreeMap<BrowserPattern, Browser> patterns4 = new TreeMap<BrowserPattern, Browser>();
		patterns4.put(pattern4, browser);

		final Data data1 = new DataBlueprint().patternToBrowserMap(patterns1).build();
		final Data data2 = new DataBlueprint().patternToBrowserMap(patterns2).build();
		final Data data3 = new DataBlueprint().patternToBrowserMap(patterns3).build();
		final Data data4 = new DataBlueprint().patternToBrowserMap(patterns4).build();

		Assert.assertFalse(data1.equals(data2));
		Assert.assertFalse(data1.hashCode() == data2.hashCode());
		Assert.assertFalse(data2.equals(data1));

		Assert.assertFalse(data1.equals(data3));
		Assert.assertFalse(data1.hashCode() == data3.hashCode());
		Assert.assertFalse(data3.equals(data1));

		Assert.assertTrue(data3.equals(data4));
		Assert.assertTrue(data3.hashCode() == data4.hashCode());
		Assert.assertTrue(data4.equals(data3));

		Assert.assertFalse(data2.equals(data3));
		Assert.assertFalse(data2.hashCode() == data3.hashCode());

		Assert.assertFalse(data1.equals(data4));
		Assert.assertFalse(data1.hashCode() == data4.hashCode());
	}

	@Test
	public void equals_different_browsers() {
		final Browser browser1 = createBrowser(1);
		final Browser browser2 = createBrowser(2);
		final Browser browser3 = createBrowser(3);
		final Set<Browser> browsers1 = new HashSet<Browser>();
		browsers1.add(browser1);
		browsers1.add(browser2);
		final Set<Browser> browsers2 = new HashSet<Browser>();
		browsers2.add(browser3);
		browsers2.add(browser2);
		browsers2.add(browser1);
		final Data data1 = new DataBlueprint().browsers(browsers1).build();
		final Data data2 = new DataBlueprint().browsers(browsers2).build();
		Assert.assertFalse(data1.equals(data2));
	}

	@Test
	public void equals_different_operatingSystemPatterns() {
		final OperatingSystem operatingSystem = createOperatingSystem(1);
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("[0-9]+"), 1);
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(2, Pattern.compile("[0-9]+"), 1);
		final OperatingSystemPattern pattern3 = new OperatingSystemPattern(3, Pattern.compile("[0-9]+"), 3);
		final OperatingSystemPattern pattern4 = new OperatingSystemPattern(3, Pattern.compile("[0-9]+"), 3);
		final TreeMap<OperatingSystemPattern, OperatingSystem> patterns1 = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		patterns1.put(pattern1, operatingSystem);
		patterns1.put(pattern2, operatingSystem);
		final TreeMap<OperatingSystemPattern, OperatingSystem> patterns2 = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		patterns2.put(pattern1, operatingSystem);
		final TreeMap<OperatingSystemPattern, OperatingSystem> patterns3 = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		patterns3.put(pattern3, operatingSystem);
		final TreeMap<OperatingSystemPattern, OperatingSystem> patterns4 = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		patterns4.put(pattern4, operatingSystem);

		final Data data1 = new DataBlueprint().patternToOperatingSystemMap(patterns1).build();
		final Data data2 = new DataBlueprint().patternToOperatingSystemMap(patterns2).build();
		final Data data3 = new DataBlueprint().patternToOperatingSystemMap(patterns3).build();
		final Data data4 = new DataBlueprint().patternToOperatingSystemMap(patterns4).build();

		Assert.assertFalse(data1.equals(data2));
		Assert.assertFalse(data1.hashCode() == data2.hashCode());
		Assert.assertFalse(data2.equals(data1));

		Assert.assertFalse(data1.equals(data3));
		Assert.assertFalse(data1.hashCode() == data3.hashCode());
		Assert.assertFalse(data3.equals(data1));

		Assert.assertTrue(data3.equals(data4));
		Assert.assertTrue(data3.hashCode() == data4.hashCode());
		Assert.assertTrue(data4.equals(data3));

		Assert.assertFalse(data2.equals(data3));
		Assert.assertFalse(data2.hashCode() == data3.hashCode());

		Assert.assertFalse(data1.equals(data4));
		Assert.assertFalse(data1.hashCode() == data4.hashCode());
	}

	@Test
	public void equals_different_operatingSystems() {
		final OperatingSystem os1 = createOperatingSystem(1);
		final OperatingSystem os2 = createOperatingSystem(2);
		final OperatingSystem os3 = createOperatingSystem(3);
		final Set<OperatingSystem> operatingSystems1 = new HashSet<OperatingSystem>();
		operatingSystems1.add(os1);
		operatingSystems1.add(os2);
		final Set<OperatingSystem> operatingSystems2 = new HashSet<OperatingSystem>();
		operatingSystems2.add(os1);
		operatingSystems2.add(os3);
		final Data data1 = new DataBlueprint().operatingSystems(operatingSystems1).build();
		final Data data2 = new DataBlueprint().operatingSystems(operatingSystems2).build();
		Assert.assertFalse(data1.equals(data2));
		Assert.assertFalse(data1.hashCode() == data2.hashCode());
	}

	@Test
	public void equals_different_robots() {
		final Robot robot1 = createRobot(1);
		final Robot robot2 = createRobot(2);
		final List<Robot> robots1 = new ArrayList<Robot>();
		robots1.add(robot1);
		robots1.add(robot2);
		final List<Robot> robots2 = new ArrayList<Robot>();
		robots2.add(robot1);
		final Data data1 = new DataBlueprint().robots(robots1).build();
		final Data data2 = new DataBlueprint().robots(robots2).build();
		Assert.assertFalse(data1.equals(data2));
		Assert.assertFalse(data1.hashCode() == data2.hashCode());
	}

	@Test
	public void equals_different_version() {
		final Data data1 = new DataBlueprint().version("v1").build();
		final Data data2 = new DataBlueprint().version("v2").build();
		Assert.assertFalse(data1.equals(data2));
		Assert.assertFalse(data1.hashCode() == data2.hashCode());
	}

	@Test
	public void equals_identical() {
		final Data data1 = new DataBlueprint().version("identical").build();
		final Data data2 = new DataBlueprint().version("identical").build();
		Assert.assertTrue(data1.equals(data2));
		Assert.assertTrue(data1.hashCode() == data2.hashCode());
	}

	@Test
	public void equals_null() {
		final Data data = new DataBlueprint().build();
		Assert.assertFalse(data.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Data data = new DataBlueprint().build();
		final String otherClass = "";
		Assert.assertFalse(data.equals(otherClass));
	}

	@Test
	public void equals_same() {
		final Data data = new DataBlueprint().version("same").build();
		Assert.assertTrue(data.equals(data));
		Assert.assertTrue(data.hashCode() == data.hashCode());
	}

}
