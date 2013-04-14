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

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
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

		@Nonnull
		private final Map<Integer, BrowserType> browserTypes = new HashMap<Integer, BrowserType>();

		@Nonnull
		private final Map<Integer, SortedSet<BrowserPattern>> browserPatterns = new HashMap<Integer, SortedSet<BrowserPattern>>();

		@Nonnull
		private final Map<Integer, SortedSet<OperatingSystemPattern>> operatingSystemPatterns = new HashMap<Integer, SortedSet<OperatingSystemPattern>>();

		@Nonnull
		private final Map<Integer, Browser.Builder> browserBuilders = new HashMap<Integer, Browser.Builder>();

		@Nonnull
		private final Set<Browser> browsers = new HashSet<Browser>();

		@Nonnull
		private final Map<Integer, OperatingSystem.Builder> operatingSystemBuilders = new HashMap<Integer, OperatingSystem.Builder>();

		@Nonnull
		private final Set<OperatingSystem> operatingSystems = new HashSet<OperatingSystem>();

		@Nonnull
		private final Set<Robot> robots = new HashSet<Robot>();

		private String version;

		@Nonnull
		private final Set<BrowserOperatingSystemMapping> browserOperatingSystemMappings = new HashSet<BrowserOperatingSystemMapping>();

		private static final OrderedPatternComparator<BrowserPattern> BROWSER_PATTERN_COMPARATOR = new OrderedPatternComparator<BrowserPattern>();

		private static final OrderedPatternComparator<OperatingSystemPattern> OS_PATTERN_COMPARATOR = new OrderedPatternComparator<OperatingSystemPattern>();

		public Builder appendBrowser(@Nonnull final Browser browser) {
			Check.notNull(browser, "browser");

			browsers.add(browser);
			return this;
		}

		/**
		 * Appends a copy of the given {@code Browser.Builder} to the internal data structure.
		 * 
		 * @param browserBuilder
		 *            {@code Browser.Builder} to be copied and appended
		 * @return this {@code Builder}, for chaining
		 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
		 *             if the given argument is {@code null}
		 * @throws net.sf.qualitycheck.exception.IllegalStateOfArgumentException
		 *             if the ID of the given builder is invalid
		 * @throws net.sf.qualitycheck.exception.IllegalStateOfArgumentException
		 *             if a builder with the same ID already exists
		 */
		@Nonnull
		public Builder appendBrowserBuilder(@Nonnull final Browser.Builder browserBuilder) {
			Check.notNull(browserBuilder, "browserBuilder");
			Check.notNegative(browserBuilder.getId(), "browserBuilder.getId()");
			if (browserBuilder.getType() == null && browserBuilder.getTypeId() < 0) {
				throw new IllegalStateOfArgumentException("A Type or Type-ID of argument 'browserBuilder' must be set.");
			}
			if (browserBuilders.containsKey(browserBuilder.getId())) {
				throw new IllegalStateOfArgumentException("The browser builder '" + browserBuilder.getProducer() + " "
						+ browserBuilder.getFamily() + "' is already in the map.");
			}

			final Browser.Builder builder = browserBuilder.copy();
			browserBuilders.put(builder.getId(), builder);
			return this;
		}

		@Nonnull
		public Builder appendBrowserOperatingSystemMapping(@Nonnull final BrowserOperatingSystemMapping browserOsMapping) {
			Check.notNull(browserOsMapping, "browserOsMapping");

			browserOperatingSystemMappings.add(browserOsMapping);
			return this;
		}

		/**
		 * Appends a browser pattern to the map unless the ID is not already present. If the ID of the pattern is
		 * already set an {@code IllegalArgumentException} will be thrown.
		 * 
		 * @param pattern
		 *            a pattern for a browser
		 * @return itself
		 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
		 *             if the given argument is {@code null}
		 */
		@Nonnull
		public Builder appendBrowserPattern(@Nonnull final BrowserPattern pattern) {
			Check.notNull(pattern, "pattern");
			if (!browserPatterns.containsKey(pattern.getId())) {
				browserPatterns.put(pattern.getId(), new TreeSet<BrowserPattern>(BROWSER_PATTERN_COMPARATOR));
			}

			browserPatterns.get(pattern.getId()).add(pattern);
			return this;
		}

		@Nonnull
		public Builder appendBrowserType(@Nonnull final BrowserType type) {
			Check.notNull(type, "type");

			browserTypes.put(type.getId(), type);
			return this;
		}

		@Nonnull
		public Builder appendOperatingSystem(@Nonnull final OperatingSystem operatingSystem) {
			Check.notNull(operatingSystem, "operatingSystem");

			operatingSystems.add(operatingSystem);
			return this;
		}

		/**
		 * Appends a copy of the given {@code OperatingSystem.Builder} to the internal data structure.
		 * 
		 * @param operatingSystemBuilder
		 *            {@code OperatingSystem.Builder} to be copied and appended
		 * @return this {@code Builder}, for chaining
		 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
		 *             if the given argument is {@code null}
		 * @throws IllegalArgumentException
		 *             if the ID of the given builder is invalid
		 * @throws IllegalArgumentException
		 *             if a builder with the same ID already exists
		 */
		@Nonnull
		public Builder appendOperatingSystemBuilder(@Nonnull final OperatingSystem.Builder operatingSystemBuilder) {
			Check.notNull(operatingSystemBuilder, "operatingSystemBuilder");
			Check.notNegative(operatingSystemBuilder.getId(), "operatingSystemBuilder.getId()");

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
		 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
		 *             if the pattern is {@code null}
		 * @return itself
		 */
		@Nonnull
		public Builder appendOperatingSystemPattern(@Nonnull final OperatingSystemPattern pattern) {
			Check.notNull(pattern, "pattern");

			if (!operatingSystemPatterns.containsKey(pattern.getId())) {
				operatingSystemPatterns.put(pattern.getId(), new TreeSet<OperatingSystemPattern>(OS_PATTERN_COMPARATOR));
			}

			operatingSystemPatterns.get(pattern.getId()).add(pattern);
			return this;
		}

		@Nonnull
		public Builder appendRobot(@Nonnull final Robot robot) {
			Check.notNull(robot, "robot");

			robots.add(robot);
			return this;
		}

		@Nonnull
		public Data build() {
			addTypeToBrowser(browserBuilders, browserTypes);
			addPatternToBrowser(browserBuilders, browserPatterns);
			addPatternToOperatingSystem(operatingSystemBuilders, operatingSystemPatterns);

			final Map<Integer, OperatingSystem> systems = buildOperatingSystems(operatingSystemBuilders);
			addOperatingSystemToBrowser(browserBuilders, systems, convertBrowserOsMapping(browserOperatingSystemMappings));

			final Set<OperatingSystem> osSet = convertOperatingSystems(systems);
			osSet.addAll(this.operatingSystems);

			final Set<Browser> browserSet = buildBrowsers(browserBuilders);
			browserSet.addAll(browsers);

			final SortedMap<BrowserPattern, Browser> patternBrowserMap = buildPatternBrowserMap(browserSet);
			final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = buildPatternOperatingSystemMap(osSet);

			return new Data(browserSet, osSet, robots, patternBrowserMap, patternOsMap, version);
		}

		@Nonnull
		public Builder setVersion(@Nonnull final String version) {
			Check.notNull(version, "version");

			this.version = version;
			return this;
		}

	}

	/**
	 * An <i>immutable</i> empty {@code Data} object.
	 */
	public static final Data EMPTY = new Data(new HashSet<Browser>(), new HashSet<OperatingSystem>(), new HashSet<Robot>(0),
			new TreeMap<BrowserPattern, Browser>(), new TreeMap<OperatingSystemPattern, OperatingSystem>(), "");

	@Nonnull
	private final Set<Browser> browsers;

	@Nonnull
	private final Set<OperatingSystem> operatingSystems;

	@Nonnull
	private final Set<Robot> robots;

	/**
	 * Version information of the UAS data
	 */
	@Nonnull
	private final String version;

	@Nonnull
	private final SortedMap<BrowserPattern, Browser> patternBrowserMap;

	@Nonnull
	private final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap;

	public Data(@Nonnull final Set<Browser> browsers, @Nonnull final Set<OperatingSystem> operatingSystems,
			@Nonnull final Set<Robot> robots, @Nonnull final SortedMap<BrowserPattern, Browser> patternBrowserMap,
			@Nonnull final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap, @Nonnull final String version) {
		Check.notNull(browsers, "browsers");
		Check.notNull(operatingSystems, "operatingSystems");
		Check.notNull(robots, "robots");
		Check.notNull(patternBrowserMap, "patternBrowserMap");
		Check.notNull(patternOsMap, "patternOsMap");
		Check.notNull(version, "version");

		this.browsers = browsers;
		this.operatingSystems = operatingSystems;
		this.patternBrowserMap = patternBrowserMap;
		this.patternOsMap = patternOsMap;
		this.robots = robots;
		this.version = version;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Data other = (Data) obj;
		if (!browsers.equals(other.browsers)) {
			return false;
		}
		if (!operatingSystems.equals(other.operatingSystems)) {
			return false;
		}
		if (!patternBrowserMap.equals(other.patternBrowserMap)) {
			return false;
		}
		if (!patternOsMap.equals(other.patternOsMap)) {
			return false;
		}
		if (!robots.equals(other.robots)) {
			return false;
		}
		if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	@Nonnull
	public Set<Browser> getBrowsers() {
		return Collections.unmodifiableSet(browsers);
	}

	@Nonnull
	public Set<OperatingSystem> getOperatingSystems() {
		return Collections.unmodifiableSet(operatingSystems);
	}

	@Nonnull
	public SortedMap<BrowserPattern, Browser> getPatternBrowserMap() {
		return patternBrowserMap;
	}

	@Nonnull
	public SortedMap<OperatingSystemPattern, OperatingSystem> getPatternOsMap() {
		return patternOsMap;
	}

	@Nonnull
	public Set<Robot> getRobots() {
		return Collections.unmodifiableSet(robots);
	}

	/**
	 * Gets the version of the UAS data which are available within this instance.
	 * 
	 * @return version of UAS data
	 */
	@Nonnull
	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + browsers.hashCode();
		result = prime * result + operatingSystems.hashCode();
		result = prime * result + patternBrowserMap.hashCode();
		result = prime * result + patternOsMap.hashCode();
		result = prime * result + robots.hashCode();
		result = prime * result + version.hashCode();
		return result;
	}

	@Nonnull
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
