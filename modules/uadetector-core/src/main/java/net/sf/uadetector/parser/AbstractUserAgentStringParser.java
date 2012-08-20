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
package net.sf.uadetector.parser;

import java.util.Map.Entry;
import java.util.regex.Matcher;

import net.sf.uadetector.DataStore;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.VersionNumber;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.internal.util.VersionParser;

public abstract class AbstractUserAgentStringParser implements UserAgentStringParser {

	/**
	 * Examines the user agent string whether it is a browser.
	 * 
	 * @param userAgent
	 *            String of an user agent
	 * @param builder
	 *            Builder for an user agent information
	 */
	private static void examineAsBrowser(final UserAgent.Builder builder, final Data data) {
		Matcher matcher;
		VersionNumber version;
		for (final Entry<BrowserPattern, Browser> entry : data.getPatternBrowserMap().entrySet()) {
			matcher = entry.getKey().getPattern().matcher(builder.getUserAgentString());
			if (matcher.find()) {

				entry.getValue().copyTo(builder);

				// try to get the browser version from the first subgroup
				version = VersionParser.parseVersion(matcher.groupCount() > 0 ? matcher.group(1) : "");
				builder.setVersionNumber(version);

				break;
			}
		}
	}

	/**
	 * Examines the user agent string whether it is a robot.
	 * 
	 * @param userAgent
	 *            String of an user agent
	 * @param builder
	 *            Builder for an user agent information
	 * @return {@code true} if it is a robot, otherwise {@code false}
	 */
	private static boolean examineAsRobot(final UserAgent.Builder builder, final Data data) {
		boolean isRobot = false;
		VersionNumber version;
		for (final Robot robot : data.getRobots()) {
			if (robot.getUserAgentString().equals(builder.getUserAgentString())) {
				isRobot = true;
				robot.copyTo(builder);

				// try to get the version from the last found group
				version = VersionParser.parseLastVersionNumber(robot.getName());
				builder.setVersionNumber(version);

				break;
			}
		}
		return isRobot;
	}

	/**
	 * Examines the operating system of the user agent string, if not available.
	 * 
	 * @param userAgent
	 *            String of an user agent
	 * @param builder
	 *            Builder for an user agent information
	 */
	private static void examineOperatingSystem(final UserAgent.Builder builder, final Data data) {
		if (net.sf.uadetector.OperatingSystem.EMPTY.equals(builder.getOperatingSystem())) {
			for (final Entry<OperatingSystemPattern, OperatingSystem> entry : data.getPatternOsMap().entrySet()) {
				final Matcher matcher = entry.getKey().getPattern().matcher(builder.getUserAgentString());
				if (matcher.find()) {
					entry.getValue().copyTo(builder);
					break;
				}
			}
		}
	}

	/**
	 * Gets the data store of this parser.
	 * 
	 * @return data store of this parser
	 */
	protected abstract DataStore getDataStore();

	@Override
	public UserAgent parse(final String userAgent) {
		final UserAgent.Builder builder = new UserAgent.Builder(userAgent);

		// work during the analysis always with the same reference of data
		final Data data = getDataStore().getData();

		if (!examineAsRobot(builder, data)) {
			examineAsBrowser(builder, data);
			examineOperatingSystem(builder, data);
		}
		return builder.build();
	}

}
