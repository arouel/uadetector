package net.sf.uadetector.internal.data;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import junit.framework.Assert;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Test;

public class DataTest {

	@Test(expected = IllegalArgumentException.class)
	public void construct_browsers_null() {
		final Set<Browser> browsers = null;
		final Set<Robot> robots = new HashSet<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_operatingSystems_null() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final Set<Robot> robots = new HashSet<Robot>();
		final Set<OperatingSystem> operatingSystems = null;
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_patternBrowserMap_null() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final Set<Robot> robots = new HashSet<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = null;
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_patternOsMap_null() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final Set<Robot> robots = new HashSet<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = null;
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_robots_null() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final Set<Robot> robots = null;
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_version_null() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final Set<Robot> robots = new HashSet<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = null;
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
	}

	@Test
	public void testGetters() {
		final Set<Browser> browsers = new HashSet<Browser>();
		final Set<Robot> robots = new HashSet<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		final Data data = new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
		Assert.assertEquals(browsers, data.getBrowsers());
		Assert.assertEquals(robots, data.getRobots());
		Assert.assertEquals(operatingSystems, data.getOperatingSystems());
		Assert.assertEquals(patternBrowserMap, data.getPatternBrowserMap());
		Assert.assertSame(version, data.getVersion());
		Assert.assertEquals(patternOsMap, data.getPatternOsMap());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final Set<Browser> browsers = new HashSet<Browser>();
		final Set<Robot> robots = new HashSet<Robot>();
		final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();
		final SortedMap<BrowserPattern, Browser> patternBrowserMap = new TreeMap<BrowserPattern, Browser>();
		final String version = "test";
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = new TreeMap<OperatingSystemPattern, OperatingSystem>();
		final Data data = new Data(browsers, operatingSystems, robots, patternBrowserMap, patternOsMap, version);
		Assert.assertEquals("Data [browsers=[], operatingSystems=[], robots=[], version=test, patternBrowserMap={}, patternOsMap={}]",
				data.toString());
	}

}
