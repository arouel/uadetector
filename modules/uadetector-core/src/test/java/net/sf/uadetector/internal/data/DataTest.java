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

import static org.fest.assertions.Assertions.assertThat;

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
import net.sf.uadetector.ReadableDeviceCategory.Category;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserOperatingSystemMapping;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.Device;
import net.sf.uadetector.internal.data.domain.DevicePattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

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

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_DEVICEPATTERNS() {
		final Map<Integer, SortedSet<DevicePattern>> patterns1 = Maps.newHashMap();
		final TreeSet<DevicePattern> pattern1 = Sets.newTreeSet();
		pattern1.add(new DevicePattern(1, Pattern.compile("1"), 1));
		patterns1.put(1, pattern1);
		final Data a = new DataBlueprint().devicePatterns(patterns1).build();

		final Map<Integer, SortedSet<DevicePattern>> patterns2 = Maps.newHashMap();
		final TreeSet<DevicePattern> pattern2 = Sets.newTreeSet();
		pattern2.add(new DevicePattern(1, Pattern.compile("2"), 1));
		patterns2.put(1, pattern2);
		final Data b = new DataBlueprint().devicePatterns(patterns2).build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_BROWSERS() {
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem(1, "n1", "f1", "iu1", patternSet, "p1", "pu1", "u1", "i1");

		final BrowserType browserType = new BrowserType(2, "Browser");

		final Browser browser1 = new Browser(1, UserAgentFamily.CHROMIUM, UserAgentFamily.CHROMIUM.getName(),
				new TreeSet<BrowserPattern>(), browserType, os, "icn", "iu", "p", "pu", "u");
		final Data a = new DataBlueprint().browsers(Sets.newHashSet(browser1)).build();

		final Browser browser2 = new Browser(1, UserAgentFamily.FIREFOX, UserAgentFamily.FIREFOX.getName(), new TreeSet<BrowserPattern>(),
				browserType, os, "icn", "iu", "p", "pu", "u");
		final Data b = new DataBlueprint().browsers(Sets.newHashSet(browser2)).build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_BROWSERTOOPERATINGSYSTEMMAP() {
		final Set<BrowserOperatingSystemMapping> map1 = Sets.newHashSet();
		map1.add(new BrowserOperatingSystemMapping(1, 1));
		final Data a = new DataBlueprint().browserToOperatingSystemMappings(map1).build();

		final Set<BrowserOperatingSystemMapping> map2 = Sets.newHashSet();
		map2.add(new BrowserOperatingSystemMapping(1, 2));
		final Data b = new DataBlueprint().browserToOperatingSystemMappings(map2).build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_BROWSERTYPES() {
		final Map<Integer, BrowserType> types1 = Maps.newHashMap();
		types1.put(1, new BrowserType(1, "Browser"));
		final Data a = new DataBlueprint().browserTypes(types1).build();

		final Map<Integer, BrowserType> types2 = Maps.newHashMap();
		types2.put(1, new BrowserType(2, "Feedreader"));
		final Data b = new DataBlueprint().browserTypes(types2).build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_DEVICES() {
		final Device device1 = new Device("dev1", 123, Category.OTHER, "icon", "infoUrl", new TreeSet<DevicePattern>());
		final Data a = new DataBlueprint().devices(Sets.newHashSet(device1)).build();

		final Device device2 = new Device("dev2", 234, Category.OTHER, "icon", "infoUrl", new TreeSet<DevicePattern>());
		final Data b = new DataBlueprint().devices(Sets.newHashSet(device2)).build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
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

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_OPERATINGSYSTEMS() {
		final OperatingSystem os1 = new OperatingSystem(1, "n1", "f1", "iu1", new TreeSet<OperatingSystemPattern>(), "p1", "pu1", "u1",
				"i1");
		final Data a = new DataBlueprint().operatingSystems(Sets.newHashSet(os1)).build();

		final OperatingSystem os2 = new OperatingSystem(2, "n1", "f1", "iu1", new TreeSet<OperatingSystemPattern>(), "p1", "pu1", "u1",
				"i1");
		final Data b = new DataBlueprint().operatingSystems(Sets.newHashSet(os2)).build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PATTERNTOBROWSERMAP() {
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem(1, "n1", "f1", "iu1", patternSet, "p1", "pu1", "u1", "i1");

		final BrowserPattern pattern1 = new BrowserPattern(1, Pattern.compile("1"), 1);
		final BrowserType browserType = new BrowserType(1, "Browser");
		final Browser browser1 = new Browser(1, UserAgentFamily.CHROME, UserAgentFamily.CHROME.getName(), new TreeSet<BrowserPattern>(),
				browserType, os, "icn", "iu", "p", "pu", "u");
		final SortedMap<BrowserPattern, Browser> map1 = Maps.newTreeMap();
		map1.put(pattern1, browser1);
		final Data a = new DataBlueprint().patternToBrowserMap(map1).build();

		final BrowserPattern pattern2 = new BrowserPattern(1, Pattern.compile("2"), 1);
		final Browser browser2 = new Browser(1, UserAgentFamily.CHROME, UserAgentFamily.CHROME.getName(), new TreeSet<BrowserPattern>(),
				browserType, os, "icn", "iu", "p", "pu", "u");
		final SortedMap<BrowserPattern, Browser> map2 = Maps.newTreeMap();
		map2.put(pattern2, browser2);
		final Data b = new DataBlueprint().patternToBrowserMap(map2).build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PATTERNTODEVICEMAP() {
		final DevicePattern pattern1 = new DevicePattern(1, Pattern.compile("1"), 1);
		final Device device1 = new Device("dev1", 234, Category.OTHER, "icon", "infoUrl", new TreeSet<DevicePattern>());
		final SortedMap<DevicePattern, Device> map1 = Maps.newTreeMap();
		map1.put(pattern1, device1);
		final Data a = new DataBlueprint().patternToDeviceMap(map1).build();

		final DevicePattern pattern2 = new DevicePattern(1, Pattern.compile("2"), 1);
		final Device device2 = new Device("dev2", 235, Category.OTHER, "icon", "infoUrl", new TreeSet<DevicePattern>());
		final SortedMap<DevicePattern, Device> map2 = Maps.newTreeMap();
		map2.put(pattern2, device2);
		final Data b = new DataBlueprint().patternToDeviceMap(map2).build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PATTERNTOOPERATINGSYSTEMMAP() {
		final SortedMap<OperatingSystemPattern, OperatingSystem> map1 = Maps.newTreeMap();
		final OperatingSystemPattern pattern1 = new OperatingSystemPattern(1, Pattern.compile("1"), 1);
		final SortedSet<OperatingSystemPattern> osPatternSet1 = new TreeSet<OperatingSystemPattern>();
		osPatternSet1.add(pattern1);
		final OperatingSystem os1 = new OperatingSystem(1, "n1", "f1", "iu1", osPatternSet1, "p1", "pu1", "u1", "i1");
		map1.put(pattern1, os1);
		final Data a = new DataBlueprint().patternToOperatingSystemMap(map1).build();

		final SortedMap<OperatingSystemPattern, OperatingSystem> map2 = Maps.newTreeMap();
		final OperatingSystemPattern pattern2 = new OperatingSystemPattern(1, Pattern.compile("1"), 1);
		final SortedSet<OperatingSystemPattern> osPatternSet2 = new TreeSet<OperatingSystemPattern>();
		osPatternSet2.add(pattern2);
		final OperatingSystem os2 = new OperatingSystem(2, "n1", "f1", "iu1", osPatternSet2, "p1", "pu1", "u1", "i1");
		map2.put(pattern2, os2);
		final Data b = new DataBlueprint().patternToOperatingSystemMap(map2).build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_ROBOTS() {
		final Robot robot1 = new Robot(1, "n1", UserAgentFamily.BINGBOT, "fn1", "iu1", "p1", "pu1", "uas1", "icn1");
		final Data a = new DataBlueprint().robots(Lists.newArrayList(robot1)).build();

		final Robot robot2 = new Robot(2, "n2", UserAgentFamily.YAHOOFEEDSEEKER, "fn2", "iu2", "p2", "pu2", "uas2", "icn2");
		final Data b = new DataBlueprint().robots(Lists.newArrayList(robot2)).build();

		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_VERSION() {
		final Data a = new DataBlueprint().version("v1.0").build();
		final Data b = new DataBlueprint().version("v2.0").build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_EMPTY() {
		assertThat(Data.EMPTY).isSameAs(Data.EMPTY);

		// create new empty instance
		final Data empty = new Data(new HashSet<Browser>(0), new HashMap<Integer, SortedSet<BrowserPattern>>(0),
				new HashMap<Integer, BrowserType>(0), new TreeMap<BrowserPattern, Browser>(),
				new HashSet<BrowserOperatingSystemMapping>(0), new HashSet<OperatingSystem>(0),
				new HashMap<Integer, SortedSet<OperatingSystemPattern>>(0), new TreeMap<OperatingSystemPattern, OperatingSystem>(),
				new ArrayList<Robot>(0), new HashSet<Device>(0), new HashMap<Integer, SortedSet<DevicePattern>>(0),
				new TreeMap<DevicePattern, Device>(), "");
		assertThat(empty).isEqualTo(Data.EMPTY);
		assertThat(Data.EMPTY.hashCode() == empty.hashCode()).isTrue();
	}

	@Test
	public void equals_identical() {
		final Data a = new DataBlueprint().build();
		final Data b = new DataBlueprint().build();
		assertThat(b).isEqualTo(a);
		assertThat(a.hashCode() == b.hashCode()).isTrue();
	}

	@Test
	public void equals_null() {
		final Data a = new DataBlueprint().build();
		assertThat(a.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass() {
		final Data a = new DataBlueprint().build();
		assertThat(a.equals("")).isFalse();
	}

	@Test
	public void equals_same() {
		final Data a = new DataBlueprint().build();
		assertThat(a).isEqualTo(a);
		assertThat(a).isSameAs(a);
		assertThat(a.hashCode() == a.hashCode()).isTrue();
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
		final OperatingSystem operatingSystem = new OperatingSystem(9765, "Solaris", "Unix",
				"http://en.wikipedia.org/wiki/Sun_Microsystems", osPatternSet, "Sun", "http://sun.com", "http://sun.com", "solaris.png");
		operatingSystems.add(operatingSystem);
		patternToOperatingSystemMap.put(operatingSystemPattern, operatingSystem);
		final BrowserType browserType = new BrowserType(2, "Browser");
		browserTypes.put(browserType.getId(), browserType);
		final Browser browser = new Browser(1, UserAgentFamily.NCSA_MOSAIC, UserAgentFamily.NCSA_MOSAIC.getName(),
				new TreeSet<BrowserPattern>(), browserType, operatingSystem, "icn", "iu", "p", "pu", "http://www.ncsa.uiuc.edu/");
		browsers.add(browser);
		patternToBrowserMap.put(browserPattern, browser);
		browserToOperatingSystemMappings.add(new BrowserOperatingSystemMapping(browser.getId(), operatingSystem.getId()));
		final Robot robot = new Robot(123, "r-name", UserAgentFamily.MJ12BOT, "MJ-12 bot", "info-url", "prod-1", "p-url-1", "uas", "icn1");
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
		assertThat(data.getBrowsers()).isEqualTo(browsers);
		assertThat(data.getBrowserPatterns()).isEqualTo(browserPatterns);
		assertThat(data.getBrowserTypes()).isEqualTo(browserTypes);
		assertThat(data.getPatternToBrowserMap()).isEqualTo(patternToBrowserMap);
		assertThat(data.getBrowserToOperatingSystemMappings()).isEqualTo(browserToOperatingSystemMappings);
		assertThat(data.getOperatingSystems()).isEqualTo(operatingSystems);
		assertThat(data.getOperatingSystemPatterns()).isEqualTo(operatingSystemPatterns);
		assertThat(data.getPatternToOperatingSystemMap()).isEqualTo(patternToOperatingSystemMap);
		assertThat(data.getRobots()).isEqualTo(robots);
		assertThat(data.getVersion()).isSameAs(version);
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
		final Set<Device> devices = new HashSet<Device>(0);
		final Map<Integer, SortedSet<DevicePattern>> devicePatterns = new HashMap<Integer, SortedSet<DevicePattern>>(0);
		final SortedMap<DevicePattern, Device> patternToDeviceMap = new TreeMap<DevicePattern, Device>();
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
		dataBlueprint.devices(devices);
		dataBlueprint.devicePatterns(devicePatterns);
		dataBlueprint.patternToDeviceMap(patternToDeviceMap);
		dataBlueprint.version(version);
		final Data data = dataBlueprint.build();

		assertThat(data.toString())
				.isEqualTo(
						"Data [browsers=[], browserPatterns={}, browserTypes={}, patternToBrowserMap={}, browserToOperatingSystemMap=[], operatingSystems=[], operatingSystemPatterns={}, patternToOperatingSystemMap={}, robots=[], devices=[], devicePatterns={}, patternToDeviceMap={}, version=test]");
	}
}
