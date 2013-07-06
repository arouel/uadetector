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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserOperatingSystemMapping;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class DataTest {

	@Test
	public void equals_different_BROWSERPATTERNS() {
		final Map<Integer, SortedSet<BrowserPattern>> patterns1 = Maps.newHashMap();
		final TreeSet<BrowserPattern> pattern1 = Sets.newTreeSet();
		pattern1.add(new BrowserPattern(1, Pattern.compile("1"), 1));
		patterns1.put(1, pattern1);
		final Data a = new DataBlueprint().browserPatterns(patterns1).build();

		final Map<Integer, SortedSet<BrowserPattern>> patterns2 = Maps.newHashMap();
		final TreeSet<BrowserPattern> pattern2 = Sets.newTreeSet();
		pattern2.add(new BrowserPattern(1, Pattern.compile("2"), 1));
		patterns2.put(1, pattern2);
		final Data b = new DataBlueprint().browserPatterns(patterns2).build();

		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_BROWSERS() {
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", patternSet, "p1", "pu1", "u1");

		final Browser browser1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final Data a = new DataBlueprint().browsers(Sets.newHashSet(browser1)).build();

		final Browser browser2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.FIREFOX, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final Data b = new DataBlueprint().browsers(Sets.newHashSet(browser2)).build();

		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_BROWSERTOOPERATINGSYSTEMMAP() {
		final Set<BrowserOperatingSystemMapping> map1 = Sets.newHashSet();
		map1.add(new BrowserOperatingSystemMapping(1, 1));
		final Data a = new DataBlueprint().browserToOperatingSystemMappings(map1).build();

		final Set<BrowserOperatingSystemMapping> map2 = Sets.newHashSet();
		map2.add(new BrowserOperatingSystemMapping(1, 2));
		final Data b = new DataBlueprint().browserToOperatingSystemMappings(map2).build();

		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_BROWSERTYPES() {
		final Map<Integer, BrowserType> types1 = Maps.newHashMap();
		types1.put(1, new BrowserType(1, "Browser"));
		final Data a = new DataBlueprint().browserTypes(types1).build();

		final Map<Integer, BrowserType> types2 = Maps.newHashMap();
		types2.put(1, new BrowserType(2, "Feedreader"));
		final Data b = new DataBlueprint().browserTypes(types2).build();

		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_OPERATINGSYSTEMPATTERNS() {
		final SortedSet<OperatingSystemPattern> patterns1 = new TreeSet<OperatingSystemPattern>();
		patterns1.add(new OperatingSystemPattern(1, Pattern.compile("1"), 1));
		final Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns1 = Maps.newHashMap();
		operatingSystemPatterns1.put(1, patterns1);
		final Data a = new DataBlueprint().operatingSystemPatterns(operatingSystemPatterns1).build();

		final SortedSet<OperatingSystemPattern> patterns2 = new TreeSet<OperatingSystemPattern>();
		patterns2.add(new OperatingSystemPattern(1, Pattern.compile("1"), 1));
		final Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns2 = Maps.newHashMap();
		operatingSystemPatterns1.put(1, patterns2);
		final Data b = new DataBlueprint().operatingSystemPatterns(operatingSystemPatterns2).build();

		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_OPERATINGSYSTEMS() {
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", new TreeSet<OperatingSystemPattern>(), "p1", "pu1",
				"u1");
		final Data a = new DataBlueprint().operatingSystems(Sets.newHashSet(os1)).build();

		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 2, "iu1", "n1", new TreeSet<OperatingSystemPattern>(), "p1", "pu1",
				"u1");
		final Data b = new DataBlueprint().operatingSystems(Sets.newHashSet(os2)).build();

		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_PATTERNTOBROWSERMAP() {
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", patternSet, "p1", "pu1", "u1");

		final BrowserPattern pattern1 = new BrowserPattern(1, Pattern.compile("1"), 1);
		final Browser browser1 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final SortedMap<BrowserPattern, Browser> map1 = Maps.newTreeMap();
		map1.put(pattern1, browser1);
		final Data a = new DataBlueprint().patternToBrowserMap(map1).build();

		final BrowserPattern pattern2 = new BrowserPattern(1, Pattern.compile("2"), 1);
		final Browser browser2 = new Browser(1, new BrowserType(1, "Browser"), UserAgentFamily.CHROME, "u", "p", "pu", "i", "iu",
				new TreeSet<BrowserPattern>(), os);
		final SortedMap<BrowserPattern, Browser> map2 = Maps.newTreeMap();
		map2.put(pattern2, browser2);
		final Data b = new DataBlueprint().patternToBrowserMap(map2).build();

		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_PATTERNTOOPERATINGSYSTEMMAP() {
		final SortedMap<OperatingSystemPattern, OperatingSystem> map1 = Maps.newTreeMap();
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("1"), 1);
		final SortedSet<OperatingSystemPattern> osPatternSet1 = new TreeSet<OperatingSystemPattern>();
		osPatternSet1.add(pattern1);
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet1, "p1", "pu1", "u1");
		map1.put(pattern1, os1);
		final Data a = new DataBlueprint().patternToOperatingSystemMap(map1).build();

		final SortedMap<OperatingSystemPattern, OperatingSystem> map2 = Maps.newTreeMap();
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(1, Pattern.compile("1"), 1);
		final SortedSet<OperatingSystemPattern> osPatternSet2 = new TreeSet<OperatingSystemPattern>();
		osPatternSet2.add(pattern2);
		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 2, "iu1", "n1", osPatternSet2, "p1", "pu1", "u1");
		map2.put(pattern2, os2);
		final Data b = new DataBlueprint().patternToOperatingSystemMap(map2).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_ROBOTS() {
		final Robot robot1 = new Robot(UserAgentFamily.BINGBOT, "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Data a = new DataBlueprint().robots(Lists.newArrayList(robot1)).build();

		final Robot robot2 = new Robot(UserAgentFamily.YAHOOFEEDSEEKER, "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Data b = new DataBlueprint().robots(Lists.newArrayList(robot2)).build();

		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_VERSION() {
		final Data a = new DataBlueprint().version("v1.0").build();
		final Data b = new DataBlueprint().version("v2.0").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_EMPTY() {
		Assert.assertSame(Data.EMPTY, Data.EMPTY);

		// create new empty instance
		final Data empty = new Data(new HashSet<Browser>(0), new HashMap<Integer, SortedSet<BrowserPattern>>(0),
				new HashMap<Integer, BrowserType>(0), new TreeMap<BrowserPattern, Browser>(),
				new HashSet<BrowserOperatingSystemMapping>(0), new HashSet<OperatingSystem>(0),
				new HashMap<Integer, SortedSet<OperatingSystemPattern>>(0), new TreeMap<OperatingSystemPattern, OperatingSystem>(),
				new ArrayList<Robot>(0), "");
		Assert.assertEquals(Data.EMPTY, empty);
		Assert.assertTrue(Data.EMPTY.hashCode() == empty.hashCode());
	}

	@Test
	public void equals_identical() {
		final Data a = new DataBlueprint().build();
		final Data b = new DataBlueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Data a = new DataBlueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Data a = new DataBlueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Data a = new DataBlueprint().build();
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_BROWSERPATTERNS() {
		new DataBlueprint().browserPatterns(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_BROWSERS() {
		new DataBlueprint().browsers(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_BROWSERTOOPERATINGSYSTEMMAP() {
		new DataBlueprint().browserToOperatingSystemMappings(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_BROWSERTYPES() {
		new DataBlueprint().browserTypes(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_OPERATINGSYSTEMPATTERNS() {
		new DataBlueprint().operatingSystemPatterns(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_OPERATINGSYSTEMS() {
		new DataBlueprint().operatingSystems(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PATTERNTOBROWSERMAP() {
		new DataBlueprint().patternToBrowserMap(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PATTERNTOOPERATINGSYSTEMMAP() {
		new DataBlueprint().patternToOperatingSystemMap(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_ROBOTS() {
		new DataBlueprint().robots(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_VERSION() {
		new DataBlueprint().version(null).build();
	}

	@Test
	public void testGetters() {
		// create data structures
		final Set<Browser> browsers = new HashSet<Browser>();
		final Map<Integer, SortedSet<BrowserPattern>> browserPatterns = Maps.newHashMap();
		final Map<Integer, BrowserType> browserTypes = Maps.newHashMap();
		final SortedMap<BrowserPattern, Browser> patternToBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final Set<BrowserOperatingSystemMapping> browserToOperatingSystemMappings = Sets.newHashSet();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns = Maps.newHashMap();
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternToOperatingSystemMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		final List<Robot> robots = new ArrayList<Robot>();
		final String version = "test";

		// fill structures with data
		final TreeSet<BrowserPattern> browserPatternSet = Sets.newTreeSet();
		final BrowserPattern browserPattern = new BrowserPattern(465, Pattern.compile("NCSA_Mosaic/([0-9]+(\\.[0-9]+)*).*"), 1);
		browserPatternSet.add(browserPattern);
		browserPatterns.put(1, browserPatternSet);
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystemPattern operatingSystemPattern = new OperatingSystemPattern(21435, Pattern.compile("[0-9]+"), 1);
		osPatternSet.add(operatingSystemPattern);
		final OperatingSystem operatingSystem = new OperatingSystem("Solaris", "i1", 9765, "iu1", "Solaris", osPatternSet, "p1", "pu1",
				"http://en.wikipedia.org/wiki/Sun_Microsystems");
		operatingSystems.add(operatingSystem);
		patternToOperatingSystemMap.put(operatingSystemPattern, operatingSystem);
		final BrowserType browserType = new BrowserType(2, "Browser");
		browserTypes.put(browserType.getId(), browserType);
		final Browser browser = new Browser(1234, browserType, UserAgentFamily.NCSA_MOSAIC, "u", "NCSA", "http://www.ncsa.uiuc.edu/", "i",
				"iu", browserPatternSet, operatingSystem);
		browsers.add(browser);
		patternToBrowserMap.put(browserPattern, browser);
		browserToOperatingSystemMappings.add(new BrowserOperatingSystemMapping(browser.getId(), operatingSystem.getId()));
		final Robot robot = new Robot(UserAgentFamily.GOOGLEBOT, "i1", 1, "iu1", "n1", "p1", "pu1", "u1",
				"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
		robots.add(robot);

		// create Data instance
		final DataBlueprint dataBlueprint = new DataBlueprint();
		dataBlueprint.browsers(browsers);
		dataBlueprint.browserPatterns(browserPatterns);
		dataBlueprint.browserTypes(browserTypes);
		dataBlueprint.patternToBrowserMap(patternToBrowserMap);
		dataBlueprint.browserToOperatingSystemMappings(browserToOperatingSystemMappings);
		dataBlueprint.operatingSystems(operatingSystems);
		dataBlueprint.operatingSystemPatterns(operatingSystemPatterns);
		dataBlueprint.patternToOperatingSystemMap(patternToOperatingSystemMap);
		dataBlueprint.robots(robots);
		dataBlueprint.version(version);
		final Data data = dataBlueprint.build();

		// check
		Assert.assertEquals(browsers, data.getBrowsers());
		Assert.assertEquals(browserPatterns, data.getBrowserPatterns());
		Assert.assertEquals(browserTypes, data.getBrowserTypes());
		Assert.assertEquals(patternToBrowserMap, data.getPatternToBrowserMap());
		Assert.assertEquals(browserToOperatingSystemMappings, data.getBrowserToOperatingSystemMappings());
		Assert.assertEquals(operatingSystems, data.getOperatingSystems());
		Assert.assertEquals(operatingSystemPatterns, data.getOperatingSystemPatterns());
		Assert.assertEquals(patternToOperatingSystemMap, data.getPatternToOperatingSystemMap());
		Assert.assertEquals(robots, data.getRobots());
		Assert.assertSame(version, data.getVersion());
	}

	@Test
	public void testToString() {
		// reduces also some noise in coverage report

		final Set<Browser> browsers = new HashSet<Browser>();
		final Map<Integer, SortedSet<BrowserPattern>> browserPatterns = Maps.newHashMap();
		final Map<Integer, BrowserType> browserTypes = Maps.newHashMap();
		final SortedMap<BrowserPattern, Browser> patternToBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final Set<BrowserOperatingSystemMapping> browserToOperatingSystemMappings = Sets.newHashSet();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns = Maps.newHashMap();
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternToOperatingSystemMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		final List<Robot> robots = new ArrayList<Robot>();
		final String version = "test";

		final DataBlueprint dataBlueprint = new DataBlueprint();
		dataBlueprint.browsers(browsers);
		dataBlueprint.browserPatterns(browserPatterns);
		dataBlueprint.browserTypes(browserTypes);
		dataBlueprint.patternToBrowserMap(patternToBrowserMap);
		dataBlueprint.browserToOperatingSystemMappings(browserToOperatingSystemMappings);
		dataBlueprint.operatingSystems(operatingSystems);
		dataBlueprint.operatingSystemPatterns(operatingSystemPatterns);
		dataBlueprint.patternToOperatingSystemMap(patternToOperatingSystemMap);
		dataBlueprint.robots(robots);
		dataBlueprint.version(version);
		final Data data = dataBlueprint.build();

		Assert.assertEquals(
				"Data [browsers=[], browserPatterns={}, browserTypes={}, patternToBrowserMap={}, browserToOperatingSystemMap=[], operatingSystems=[], operatingSystemPatterns={}, patternToOperatingSystemMap={}, robots=[], version=test]",
				data.toString());
	}
}
