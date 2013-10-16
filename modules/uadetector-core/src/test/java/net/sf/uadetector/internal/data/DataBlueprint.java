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
package net.sf.uadetector.internal.data;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * This class is intended to simplify test cases for {@link Data} and tests which needs a prepared instance of
 * {@link Data}.
 */
@NotThreadSafe
public final class DataBlueprint {

	private Map<Integer, SortedSet<BrowserPattern>> browserPatterns = Maps.newHashMap();

	private Set<Browser> browsers = Sets.newHashSet();

	private Set<BrowserOperatingSystemMapping> browserToOperatingSystemMappings = Sets.newHashSet();

	private Map<Integer, BrowserType> browserTypes = Maps.newHashMap();

	private Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns = Maps.newHashMap();

	private Set<OperatingSystem> operatingSystems = Sets.newHashSet();

	private SortedMap<BrowserPattern, Browser> patternToBrowserMap = Maps.newTreeMap();

	private SortedMap<OperatingSystemPattern, OperatingSystem> patternToOperatingSystemMap = Maps.newTreeMap();

	private List<Robot> robots = Lists.newArrayList();

	private Set<Device> devices = Sets.newHashSet();

	private Map<Integer, SortedSet<DevicePattern>> devicePatterns = Maps.newHashMap();

	private SortedMap<DevicePattern, Device> patternToDeviceMap = Maps.newTreeMap();

	private String version = "test-version";

	public DataBlueprint() {
		final TreeSet<BrowserPattern> browserPatternSet = Sets.newTreeSet();
		final BrowserPattern browserPattern = new BrowserPattern(1, Pattern.compile("[a-z]+"), 1);
		browserPatternSet.add(browserPattern);
		browserPatterns.put(1, browserPatternSet);

		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystemPattern operatingSystemPattern = new OperatingSystemPattern(1, Pattern.compile("1"), 1);
		osPatternSet.add(operatingSystemPattern);
		final OperatingSystem operatingSystem = new OperatingSystem(1, "n1", "f1", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
		operatingSystems.add(operatingSystem);

		patternToOperatingSystemMap.put(operatingSystemPattern, operatingSystem);

		final BrowserType browserType = new BrowserType(1, "Browser");
		browserTypes.put(browserType.getId(), browserType);

		final Browser browser = new Browser(4256, UserAgentFamily.FIREBIRD, UserAgentFamily.FIREBIRD.getName(), browserPatternSet,
				browserType, operatingSystem, "icn", "iu1", "p1", "pu1", "u1");
		browsers.add(browser);

		patternToBrowserMap.put(browserPattern, browser);

		browserToOperatingSystemMappings.add(new BrowserOperatingSystemMapping(browser.getId(), operatingSystem.getId()));

		final TreeSet<DevicePattern> devicePatternSet = Sets.newTreeSet();
		final DevicePattern devicePattern = new DevicePattern(1, Pattern.compile("[a-z]+"), 1);
		devicePatternSet.add(devicePattern);
		devicePatterns.put(1, devicePatternSet);

		final Device device = new Device("device-category-name", 1, Category.OTHER, "device.png", "device-info", devicePatternSet);
		devices.add(device);

		patternToDeviceMap.put(devicePattern, device);

		final Robot robot = new Robot(12, "Majestic-12", UserAgentFamily.MJ12BOT, "Majestic-12 bot", "http://majestic12.co.uk/bot.php",
				"Majestic-12", "http://www.majestic12.co.uk/", "MJ12bot/v1.4.3", "mj12.png");
		robots.add(robot);
	}

	public DataBlueprint browserPatterns(final Map<Integer, SortedSet<BrowserPattern>> browserPatterns) {
		this.browserPatterns = browserPatterns;
		return this;
	}

	public DataBlueprint browsers(final Set<Browser> browsers) {
		this.browsers = browsers;
		return this;
	}

	public DataBlueprint browserToOperatingSystemMappings(final Set<BrowserOperatingSystemMapping> browserToOperatingSystemMappings) {
		this.browserToOperatingSystemMappings = browserToOperatingSystemMappings;
		return this;
	}

	public DataBlueprint browserTypes(final Map<Integer, BrowserType> browserTypes) {
		this.browserTypes = browserTypes;
		return this;
	}

	@Nonnull
	public Data build() {
		return new Data(browsers, browserPatterns, browserTypes, patternToBrowserMap, browserToOperatingSystemMappings, operatingSystems,
				operatingSystemPatterns, patternToOperatingSystemMap, robots, devices, devicePatterns, patternToDeviceMap, version);
	}

	public DataBlueprint devicePatterns(final Map<Integer, SortedSet<DevicePattern>> devicePatterns) {
		this.devicePatterns = devicePatterns;
		return this;
	}

	public DataBlueprint devices(final Set<Device> devices) {
		this.devices = devices;
		return this;
	}

	public DataBlueprint operatingSystemPatterns(final Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns) {
		this.operatingSystemPatterns = operatingSystemPatterns;
		return this;
	}

	public DataBlueprint operatingSystems(final Set<OperatingSystem> operatingSystems) {
		this.operatingSystems = operatingSystems;
		return this;
	}

	public DataBlueprint patternToBrowserMap(final SortedMap<BrowserPattern, Browser> patternToBrowserMap) {
		this.patternToBrowserMap = patternToBrowserMap;
		return this;
	}

	public DataBlueprint patternToDeviceMap(final SortedMap<DevicePattern, Device> patternToDeviceMap) {
		this.patternToDeviceMap = patternToDeviceMap;
		return this;
	}

	public DataBlueprint patternToOperatingSystemMap(final SortedMap<OperatingSystemPattern, OperatingSystem> patternToOperatingSystemMap) {
		this.patternToOperatingSystemMap = patternToOperatingSystemMap;
		return this;
	}

	public DataBlueprint robots(final List<Robot> robots) {
		this.robots = robots;
		return this;
	}

	public DataBlueprint version(final String version) {
		this.version = version;
		return this;
	}

}
