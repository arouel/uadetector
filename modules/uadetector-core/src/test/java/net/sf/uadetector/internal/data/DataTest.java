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
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;

public class DataTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_browsers_null() {
		final Set<Browser> browsers = null;
		final List<Robot> robots = new ArrayList<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_operatingSystems_null() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final List<Robot> robots = new ArrayList<Robot>();
		final Set<OperatingSystem> operatingSystems = null;
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_patternBrowserMap_null() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final List<Robot> robots = new ArrayList<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = null;
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_patternOsMap_null() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final List<Robot> robots = new ArrayList<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = null;
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_robots_null() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final List<Robot> robots = null;
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_version_null() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final List<Robot> robots = new ArrayList<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = null;
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test
	public void testGetters() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final List<Robot> robots = new ArrayList<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		final Data data = new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
		Assert.assertEquals(browsers, data.getBrowsers());
		Assert.assertEquals(robots, data.getRobots());
		Assert.assertEquals(operatingSystems, data.getOperatingSystems());
		Assert.assertEquals(patternBrowserMap, data.getPatternToBrowserMap());
		Assert.assertSame(version, data.getVersion());
		Assert.assertEquals(patternOsMap, data.getPatternToOperatingSystemMap());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final Set<Browser> browsers = new HashSet<Browser>();
		final List<Robot> robots = new ArrayList<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		final Data data = new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
		Assert.assertEquals("Data [browsers=[], operatingSystems=[], robots=[], version=test, patternBrowserMap={}, patternOsMap={}]",
				data.toString());
	}

}
