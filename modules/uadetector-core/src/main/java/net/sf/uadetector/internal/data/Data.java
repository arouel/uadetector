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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

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
	 * An <i>immutable</i> empty {@code Data} object.
	 */
	public static final Data EMPTY = new Data(new HashSet<Browser>(), new HashSet<OperatingSystem>(), new ArrayList<Robot>(0),
			new TreeMap<BrowserPattern, Browser>(), new TreeMap<OperatingSystemPattern, OperatingSystem>(), "");

	@Nonnull
	private final Set<Browser> browsers;

	@Nonnull
	private final Set<OperatingSystem> operatingSystems;

	@Nonnull
	private final List<Robot> robots;

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
			@Nonnull final List<Robot> robots, @Nonnull final SortedMap<BrowserPattern, Browser> patternBrowserMap,
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
	public List<Robot> getRobots() {
		return Collections.unmodifiableList(robots);
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
