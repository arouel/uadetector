package net.sf.uadetector.internal.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

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
 * This class is intended to create instances of {@code Data}.
 * 
 * @author André Rouél
 */
@NotThreadSafe
public class DataBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(DataBuilder.class);

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
	private final List<Robot> robots = new ArrayList<Robot>();

	private String version;

	@Nonnull
	private final Set<BrowserOperatingSystemMapping> browserOperatingSystemMappings = new HashSet<BrowserOperatingSystemMapping>();

	private static final OrderedPatternComparator<BrowserPattern> BROWSER_PATTERN_COMPARATOR = new OrderedPatternComparator<BrowserPattern>();

	private static final OrderedPatternComparator<OperatingSystemPattern> OS_PATTERN_COMPARATOR = new OrderedPatternComparator<OperatingSystemPattern>();

	public DataBuilder appendBrowser(@Nonnull final Browser browser) {
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
	public DataBuilder appendBrowserBuilder(@Nonnull final Browser.Builder browserBuilder) {
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
	public DataBuilder appendBrowserOperatingSystemMapping(@Nonnull final BrowserOperatingSystemMapping browserOsMapping) {
		Check.notNull(browserOsMapping, "browserOsMapping");

		browserOperatingSystemMappings.add(browserOsMapping);
		return this;
	}

	/**
	 * Appends a browser pattern to the map of pattern sorted by ID.
	 * 
	 * @param pattern
	 *            a pattern for a browser
	 * @return itself
	 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
	 *             if the given argument is {@code null}
	 */
	@Nonnull
	public DataBuilder appendBrowserPattern(@Nonnull final BrowserPattern pattern) {
		Check.notNull(pattern, "pattern");
		if (!browserPatterns.containsKey(pattern.getId())) {
			browserPatterns.put(pattern.getId(), new TreeSet<BrowserPattern>(BROWSER_PATTERN_COMPARATOR));
		}

		browserPatterns.get(pattern.getId()).add(pattern);
		return this;
	}

	@Nonnull
	public DataBuilder appendBrowserType(@Nonnull final BrowserType type) {
		Check.notNull(type, "type");

		browserTypes.put(type.getId(), type);
		return this;
	}

	@Nonnull
	public DataBuilder appendOperatingSystem(@Nonnull final OperatingSystem operatingSystem) {
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
	 * @throws net.sf.qualitycheck.exception.IllegalNegativeArgumentException
	 *             if the ID of the given builder is negative
	 * @throws net.sf.qualitycheck.exception.IllegalStateOfArgumentException
	 *             if a builder with the same ID already exists
	 */
	@Nonnull
	public DataBuilder appendOperatingSystemBuilder(@Nonnull final OperatingSystem.Builder operatingSystemBuilder) {
		Check.notNull(operatingSystemBuilder, "operatingSystemBuilder");
		Check.notNegative(operatingSystemBuilder.getId(), "operatingSystemBuilder.getId()");
		Check.stateIsTrue(!operatingSystemBuilders.containsKey(operatingSystemBuilder.getId()),
				"Operating system builder with ID '%s' already exists.", operatingSystemBuilder.getId());

		final OperatingSystem.Builder builder = operatingSystemBuilder.copy();
		operatingSystemBuilders.put(builder.getId(), builder);
		return this;
	}

	/**
	 * Appends an operating system pattern to the map of pattern sorted by ID.
	 * 
	 * @param pattern
	 *            a pattern for a browser
	 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
	 *             if the pattern is {@code null}
	 * @return itself
	 */
	@Nonnull
	public DataBuilder appendOperatingSystemPattern(@Nonnull final OperatingSystemPattern pattern) {
		Check.notNull(pattern, "pattern");

		if (!operatingSystemPatterns.containsKey(pattern.getId())) {
			operatingSystemPatterns.put(pattern.getId(), new TreeSet<OperatingSystemPattern>(OS_PATTERN_COMPARATOR));
		}

		operatingSystemPatterns.get(pattern.getId()).add(pattern);
		return this;
	}

	@Nonnull
	public DataBuilder appendRobot(@Nonnull final Robot robot) {
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
		osSet.addAll(operatingSystems);

		final Set<Browser> browserSet = buildBrowsers(browserBuilders);
		browserSet.addAll(browsers);

		final SortedMap<BrowserPattern, Browser> patternBrowserMap = buildPatternBrowserMap(browserSet);
		final SortedMap<OperatingSystemPattern, OperatingSystem> patternOsMap = buildPatternOperatingSystemMap(osSet);

		return new Data(browserSet, osSet, robots, patternBrowserMap, patternOsMap, version);
	}

	@Nonnull
	public DataBuilder setVersion(@Nonnull final String version) {
		Check.notNull(version, "version");

		this.version = version;
		return this;
	}

}
