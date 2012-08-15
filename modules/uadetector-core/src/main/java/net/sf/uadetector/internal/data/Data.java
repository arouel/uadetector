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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserOperatingSystemMapping;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code Data} class represents the detection information for <i>UASparsers</i> from <a
 * href="http://user-agent-string.info/">http://user-agent-string.info</a><br>
 * <br>
 * A {@code Data} object is immutable, their values cannot be changed after creation.
 * 
 * @author André Rouél
 */
public class Data {

	/**
	 * This builder is not thread safe.
	 * 
	 * @author André Rouél
	 */
	public static final class Builder {

		private static final Logger LOG = LoggerFactory.getLogger(Builder.class);

		private static void addOperatingSystemToBrowser(final Map<Integer, Browser.Builder> browserBuilders,
				final Map<Integer, OperatingSystem> operatingSystems, final Map<Integer, Integer> browserOsMap) {
			Browser.Builder browserBuilder;
			for (final Map.Entry<Integer, Integer> entry : browserOsMap.entrySet()) {
				if (browserBuilders.containsKey(entry.getKey())) {
					browserBuilder = browserBuilders.get(entry.getKey());
					if (operatingSystems.containsKey(entry.getValue())) {
						browserBuilder.setOperatingSystem(operatingSystems.get(entry.getValue()));
					} else {
						LOG.warn("Can not find an operating system with ID '" + entry.getValue() + "' for browser '"
								+ browserBuilder.getProducer() + " " + browserBuilder.getFamily() + "'.");
					}
				} else {
					LOG.warn("Can not find a browser with ID '" + entry.getKey() + "'.");
				}
			}
		}

		private static void addPatternToBrowser(final Map<Integer, Browser.Builder> builders,
				final Map<Integer, SortedSet<BrowserPattern>> patterns) {
			for (final Map.Entry<Integer, Browser.Builder> entry : builders.entrySet()) {
				if (patterns.containsKey(entry.getKey())) {
					entry.getValue().setPatternSet(patterns.get(entry.getKey()));
				} else {
					LOG.warn("No pattern available for '" + entry.getValue().getProducer() + " " + entry.getValue().getFamily() + "'.");
				}
			}
		}

		private static void addPatternToOperatingSystem(final Map<Integer, OperatingSystem.Builder> builders,
				final Map<Integer, SortedSet<OperatingSystemPattern>> patterns) {
			for (final Map.Entry<Integer, OperatingSystem.Builder> entry : builders.entrySet()) {
				final SortedSet<OperatingSystemPattern> patternSet = patterns.get(entry.getKey());
				if (patternSet != null) {
					entry.getValue().addPatternSet(patternSet);
				} else {
					LOG.debug("No patterns for operating system entry (with id '" + entry.getKey() + "') available.");
				}
			}
		}

		private static void addTypeToBrowser(final Map<Integer, Browser.Builder> builders, final Map<Integer, BrowserType> types) {
			int typeId;
			for (final Map.Entry<Integer, Browser.Builder> entry : builders.entrySet()) {
				typeId = entry.getValue().getTypeId();
				if (typeId >= 0) {
					if (types.containsKey(typeId)) {
						entry.getValue().setType(types.get(typeId));
					} else {
						LOG.warn("No type available for '" + entry.getValue().getProducer() + " " + entry.getValue().getFamily() + "'.");
					}
				}
			}
		}

		private static Set<Browser> buildBrowsers(final Map<Integer, Browser.Builder> browserBuilders) {
			final Set<Browser> browsers = new HashSet<Browser>();
			for (final Map.Entry<Integer, Browser.Builder> entry : browserBuilders.entrySet()) {
				try {
					browsers.add(entry.getValue().build());
				} catch (final Exception e) {
					LOG.warn("Can not build browser: " + e.getLocalizedMessage());
				}
			}
			return browsers;
		}

		private static Map<Integer, OperatingSystem> buildOperatingSystems(final Map<Integer, OperatingSystem.Builder> osBuilders) {
			final Map<Integer, OperatingSystem> operatingSystems = new HashMap<Integer, OperatingSystem>();
			for (final Map.Entry<Integer, OperatingSystem.Builder> entry : osBuilders.entrySet()) {
				try {
					operatingSystems.put(entry.getKey(), entry.getValue().build());
				} catch (final Exception e) {
					LOG.warn("Can not build operating system: " + e.getLocalizedMessage());
				}
			}
			return operatingSystems;
		}

		private static SortedMap<BrowserPattern, Browser> buildPatternBrowserMap(final Set<Browser> browserSet) {
			final SortedMap<BrowserPattern, Browser> patternBrowser = new TreeMap<BrowserPattern, Browser>(BROWSER_PATTERN_COMPARATOR);
			for (final Browser browser : browserSet) {
				for (final BrowserPattern pattern : browser.getPatternSet()) {
					patternBrowser.put(pattern, browser);
				}
			}
			return patternBrowser;
		}

		private static SortedMap<OperatingSystemPattern, OperatingSystem> buildPatternOperatingSystemMap(final Set<OperatingSystem> osSet) {
			final SortedMap<OperatingSystemPattern, OperatingSystem> map = new TreeMap<OperatingSystemPattern, OperatingSystem>(
					OS_PATTERN_COMPARATOR);
			for (final OperatingSystem os : osSet) {
				for (final OperatingSystemPattern pattern : os.getPatternSet()) {
					map.put(pattern, os);
				}
			}
			return map;
		}

		private static Map<Integer, Integer> convertBrowserOsMapping(final Set<BrowserOperatingSystemMapping> browserOperatingSystemMappings) {
			final Map<Integer, Integer> result = new HashMap<Integer, Integer>();
			for (final BrowserOperatingSystemMapping mapping : browserOperatingSystemMappings) {
				result.put(mapping.getBrowserId(), mapping.getOperatingSystemId());
			}
			return result;
		}

		private static Set<OperatingSystem> convertOperatingSystems(final Map<Integer, OperatingSystem> operatingSystems) {
			final Set<OperatingSystem> result = new HashSet<OperatingSystem>();
			for (final Entry<Integer, OperatingSystem> entry : operatingSystems.entrySet()) {
				result.add(entry.getValue());
			}
			return result;
		}

		private final Map<Integer, BrowserType> browserTypes = new HashMap<Integer, BrowserType>();

		private final Map<Integer, SortedSet<BrowserPattern>> browserPatterns = new HashMap<Integer, SortedSet<BrowserPattern>>();

		private final Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns = new HashMap<Integer, SortedSet<OperatingSystemPattern>>();

		private final Map<Integer, Browser.Builder> browserBuilders = new HashMap<Integer, Browser.Builder>();

		private final Set<Browser> browsers = new HashSet<Browser>();

		private final Map<Integer, OperatingSystem.Builder> operatingSystemBuilders = new HashMap<Integer, OperatingSystem.Builder>();

		private final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();

		private final Set<Robot> robots = new HashSet<Robot>();

		private String version;

		private final Set<BrowserOperatingSystemMapping> browserOperatingSystemMappings = new HashSet<BrowserOperatingSystemMapping>();

		private static final OrderedPatternComparator<BrowserPattern> BROWSER_PATTERN_COMPARATOR = new OrderedPatternComparator<BrowserPattern>();

		private static final OrderedPatternComparator<OperatingSystemPattern> OS_PATTERN_COMPARATOR = new OrderedPatternComparator<OperatingSystemPattern>();

		public Builder appendBrowser(final Browser browser) {
			if (browser == null) {
				throw new IllegalArgumentException("Argument 'browser' must not be null.");
			}

			browsers.add(browser);
			return this;
		}

		/**
		 * Appends a copy of the given {@code Browser.Builder} to the internal data structure.
		 * 
		 * @param browserBuilder
		 *            {@code Browser.Builder} to be copied and appended
		 * @return this {@code Builder}, for chaining
		 * @throws IllegalArgumentException
		 *             if the given argument is {@code null}
		 * @throws IllegalArgumentException
		 *             if the ID of the given builder is invalid
		 * @throws IllegalArgumentException
		 *             if a builder with the same ID already exists
		 */
		public Builder appendBrowserBuilder(final Browser.Builder browserBuilder) {
			if (browserBuilder == null) {
				throw new IllegalArgumentException("Argument 'browserBuilder' must not be null.");
			}
			if (browserBuilder.getId() < 0) {
				throw new IllegalArgumentException("The ID of argument 'browserBuilder' must not be smaller than 0.");
			}
			if (browserBuilder.getType() == null && browserBuilder.getTypeId() < 0) {
				throw new IllegalArgumentException("A Type or Type-ID of argument 'browserBuilder' must be set.");
			}
			if (browserBuilders.containsKey(browserBuilder.getId())) {
				throw new IllegalArgumentException("The browser builder '" + browserBuilder.getProducer() + " "
						+ browserBuilder.getFamily() + "' is already in the map.");
			}

			final Browser.Builder builder = browserBuilder.copy();
			browserBuilders.put(builder.getId(), builder);
			return this;
		}

		public Builder appendBrowserOperatingSystemMapping(final BrowserOperatingSystemMapping browserOsMapping) {
			if (browserOsMapping == null) {
				throw new IllegalArgumentException("Argument 'browserOsMapping' must not be null.");
			}

			browserOperatingSystemMappings.add(browserOsMapping);
			return this;
		}

		/**
		 * Appends a browser pattern to the map unless the ID is not already present. If the ID of the pattern is
		 * already set an {@code IllegalArgumentException} will be thrown.
		 * 
		 * @param pattern
		 *            a pattern for a browser
		 * @throws IllegalArgumentException
		 *             if pattern is {@code null}
		 * @return itself
		 */
		public Builder appendBrowserPattern(final BrowserPattern pattern) {
			if (pattern == null) {
				throw new IllegalArgumentException("Argument 'pattern' must not be null.");
			}
			if (!browserPatterns.containsKey(pattern.getId())) {
				browserPatterns.put(pattern.getId(), new TreeSet<BrowserPattern>(BROWSER_PATTERN_COMPARATOR));
			}

			browserPatterns.get(pattern.getId()).add(pattern);
			return this;
		}

		public Builder appendBrowserType(final BrowserType type) {
			if (type == null) {
				throw new IllegalArgumentException("Argument 'type' must not be null.");
			}

			browserTypes.put(type.getId(), type);
			return this;
		}

		public Builder appendOperatingSystem(final OperatingSystem operatingSystem) {
			if (operatingSystem == null) {
				throw new IllegalArgumentException("Argument 'operatingSystem' must not be null.");
			}

			operatingSystems.add(operatingSystem);
			return this;
		}

		/**
		 * Appends a copy of the given {@code OperatingSystem.Builder} to the internal data structure.
		 * 
		 * @param operatingSystemBuilder
		 *            {@code OperatingSystem.Builder} to be copied and appended
		 * @return this {@code Builder}, for chaining
		 * @throws IllegalArgumentException
		 *             if the given argument is {@code null}
		 * @throws IllegalArgumentException
		 *             if the ID of the given builder is invalid
		 * @throws IllegalArgumentException
		 *             if a builder with the same ID already exists
		 */
		public Builder appendOperatingSystemBuilder(final OperatingSystem.Builder operatingSystemBuilder) {
			if (operatingSystemBuilder == null) {
				throw new IllegalArgumentException("Argument 'operatingSystemBuilder' must not be null.");
			}

			if (operatingSystemBuilder.getId() < 0) {
				throw new IllegalArgumentException("The ID of argument 'operatingSystemBuilder' can not be smaller than 0.");
			}

			final OperatingSystem.Builder builder = operatingSystemBuilder.copy();
			operatingSystemBuilders.put(builder.getId(), builder);
			return this;
		}

		/**
		 * Appends a operating system pattern to the map unless the ID is not already present. If the ID of the pattern
		 * is already set an {@code IllegalArgumentException} will be thrown.
		 * 
		 * @param pattern
		 *            a pattern for a browser
		 * @throws IllegalArgumentException
		 *             if the pattern is {@code null}
		 * @return itself
		 */
		public Builder appendOperatingSystemPattern(final OperatingSystemPattern pattern) {
			if (pattern == null) {
				throw new IllegalArgumentException("Argument 'pattern' must not be null.");
			}

			if (!operatingSystemPatterns.containsKey(pattern.getId())) {
				operatingSystemPatterns.put(pattern.getId(), new TreeSet<OperatingSystemPattern>(OS_PATTERN_COMPARATOR));
			}

			operatingSystemPatterns.get(pattern.getId()).add(pattern);
			return this;
		}

		public Builder appendRobot(final Robot robot) {
			if (robot == null) {
				throw new IllegalArgumentException("Argument 'robot' must not be null.");
			}

			robots.add(robot);
			return this;
		}

		public Data build() {
			addTypeToBrowser(browserBuilders, browserTypes);
			addPatternToBrowser(browserBuilders, browserPatterns);
			addPatternToOperatingSystem(operatingSystemBuilders, operatingSystemPatterns);

			final Map<Integer, OperatingSystem> operatingSystems = buildOperatingSystems(operatingSystemBuilders);
			addOperatingSystemToBrowser(browserBuilders, operatingSystems, convertBrowserOsMapping(browserOperatingSystemMappings));

			final Set<OperatingSystem> osSet = convertOperatingSystems(operatingSystems);
			osSet.addAll(this.operatingSystems);

			final Set<Browser> browserSet = buildBrowsers(browserBuilders);
			browserSet.addAll(browsers);

			final SortedMap<BrowserPattern, Browser> patternBrowserMap = buildPatternBrowserMap(browserSet);
			final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = buildPatternOperatingSystemMap(osSet);

			return new Data(browserSet, osSet, robots, patternBrowserMap, patternOsMap, version);
		}

		public Builder setVersion(final String version) {
			if (version == null) {
				throw new IllegalArgumentException("Argument 'version' must not be null.");
			}

			this.version = version;
			return this;
		}

	}

	/**
	 * An <i>immutable</i> empty {@code Data} object.
	 */
	public static final Data EMPTY = new Data(new HashSet<Browser>(), new HashSet<OperatingSystem>(), new HashSet<Robot>(0),
			new TreeMap<BrowserPattern, Browser>(), new TreeMap<OperatingSystemPattern, OperatingSystem>(), "");

	private final Set<Browser> browsers;

	private final Set<OperatingSystem> operatingSystems;

	private final Set<Robot> robots;

	/**
	 * Version information of the UAS data
	 */
	private final String version;

	private final SortedMap<BrowserPattern, Browser> patternBrowserMap;

	private final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap;

	public Data(final Set<Browser> browsers, final Set<OperatingSystem> operatingSystems, final Set<Robot> robots,
			final SortedMap<BrowserPattern, Browser> patternBrowserMap,
			final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap, final String version) {

		if (browsers == null) {
			throw new IllegalArgumentException("Argument 'browsers' must not be null.");
		}
		if (operatingSystems == null) {
			throw new IllegalArgumentException("Argument 'operatingSystems' must not be null.");
		}
		if (robots == null) {
			throw new IllegalArgumentException("Argument 'robots' must not be null.");
		}
		if (patternBrowserMap == null) {
			throw new IllegalArgumentException("Argument 'patternBrowserMap' must not be null.");
		}
		if (patternOsMap == null) {
			throw new IllegalArgumentException("Argument 'patternOsMap' must not be null.");
		}
		if (version == null) {
			throw new IllegalArgumentException("Argument 'version' must not be null.");
		}

		this.browsers = browsers;
		this.operatingSystems = operatingSystems;
		this.patternBrowserMap = patternBrowserMap;
		this.patternOsMap = patternOsMap;
		this.robots = robots;
		this.version = version;
	}

	public Set<Browser> getBrowsers() {
		return Collections.unmodifiableSet(browsers);
	}

	public Set<OperatingSystem> getOperatingSystems() {
		return Collections.unmodifiableSet(operatingSystems);
	}

	public SortedMap<BrowserPattern, Browser> getPatternBrowserMap() {
		return patternBrowserMap;
	}

	public SortedMap<OperatingSystemPattern, OperatingSystem> getPatternOsMap() {
		return patternOsMap;
	}

	public Set<Robot> getRobots() {
		return Collections.unmodifiableSet(robots);
	}

	/**
	 * Gets the version of the UAS data which are available within this instance.
	 * 
	 * @return version of UAS data
	 */
	public String getVersion() {
		return version;
	}

	public String toStats() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UAS data stats\n");
		builder.append("----------------------------------------------------------------");
		builder.append('\n');
		builder.append("version:\t\t");
		builder.append(version);
		builder.append('\n');
		builder.append("browser:\t\t");
		builder.append(browsers.size());
		builder.append('\n');
		final Map<String, AtomicInteger> browserByType = new HashMap<String, AtomicInteger>();
		for (final Browser browser : browsers) {
			final AtomicInteger counter = browserByType.get(browser.getType().getName());
			if (counter == null) {
				browserByType.put(browser.getType().getName(), new AtomicInteger(1));
			} else {
				counter.incrementAndGet();
			}
		}
		for (final Entry<String, AtomicInteger> entry : browserByType.entrySet()) {
			builder.append('\t');
			builder.append('\t');
			builder.append('\t');
			builder.append(entry.getKey());
			builder.append(":\t");
			builder.append(entry.getValue().get());
			builder.append('\n');
		}
		builder.append("browser patterns:\t");
		builder.append(patternBrowserMap.size());
		builder.append('\n');
		builder.append("operating systems:\t");
		builder.append(operatingSystems.size());
		builder.append('\n');
		builder.append("os patterns:\t\t");
		builder.append(patternOsMap.size());
		builder.append('\n');
		builder.append("robots:\t\t\t");
		builder.append(robots.size());
		builder.append('\n');
		builder.append("----------------------------------------------------------------");
		return builder.toString();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Data [browsers=");
		builder.append(browsers);
		builder.append(", operatingSystems=");
		builder.append(operatingSystems);
		builder.append(", robots=");
		builder.append(robots);
		builder.append(", version=");
		builder.append(version);
		builder.append(", patternBrowserMap=");
		builder.append(patternBrowserMap);
		builder.append(", patternOsMap=");
		builder.append(patternOsMap);
		builder.append("]");
		return builder.toString();
	}

}
