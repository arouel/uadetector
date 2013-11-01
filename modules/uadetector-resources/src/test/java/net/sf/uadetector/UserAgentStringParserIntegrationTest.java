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
package net.sf.uadetector;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Formatter;
import java.util.List;

import net.sf.uadetector.ReadableDeviceCategory.Category;
import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.service.UADetectorServiceFactory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAgentStringParserIntegrationTest {

	/**
	 * Output buffer to store informations from the detection process of the examples.
	 */
	public static class Output {

		public static final String DEFAULT_FORMAT = "%-30.30s %-20.20s %s";

		public static final char NEWLINE = '\n';

		private final String format;

		private final StringBuilder buffer = new StringBuilder();

		public Output() {
			this(DEFAULT_FORMAT);
		}

		public Output(final String format) {
			this.format = format;
		}

		public void print(final String name, final VersionNumber version, final String userAgent) {
			final Formatter formatter = new Formatter(buffer);
			formatter.format(format, name, version.toVersionString(), userAgent);
			buffer.append(NEWLINE);
		}

		@Override
		public String toString() {
			return buffer.toString();
		}

	}

	/**
	 * Default log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UserAgentStringParserIntegrationTest.class);

	private static final List<DeviceCategoryExample> DC_EXAMPLES = DeviceCategoryExamplesReader.read();

	private static final List<OperatingSystemExample> OS_EXAMPLES = OperatingSystemExamplesReader.read();

	private static final List<UserAgentExample> UA_EXAMPLES = UserAgentExamplesReader.read();

	private static final UserAgentStringParser PARSER = UADetectorServiceFactory.getResourceModuleParser();

	@Test
	public void testDeviceCategoryExamples() throws Exception {
		int i = 0;
		for (final DeviceCategoryExample example : DC_EXAMPLES) {
			final ReadableUserAgent agent = PARSER.parse(example.getUserAgentString());

			// abort if category is unknown
			final String msg1 = "Unknown device category for: " + example.getUserAgentString();
			assertThat(agent.getDeviceCategory().getCategory()).as(msg1).isNotEqualTo(Category.UNKNOWN);

			// abort if category is unknown
			final String msg2 = "Different device category for: " + example.getUserAgentString();
			assertThat(agent.getDeviceCategory().getName()).as(msg2).isEqualTo(example.getCategory());

			i++;
		}
		LOG.info(i + " device category examples validated");
	}

	@Test
	public void testOperatingSystemExamples() throws Exception {
		final Output out = new Output();
		int i = 0;
		for (final OperatingSystemExample example : OS_EXAMPLES) {
			final ReadableUserAgent agent = PARSER.parse(example.getUserAgentString());

			// comparing the name
			assertThat(agent.getOperatingSystem().getName()).isEqualTo(example.getName());

			// check for unknown family
			if (OperatingSystemFamily.UNKNOWN == agent.getOperatingSystem().getFamily()) {
				LOG.info("Unknown operating system family found. Please update the enum 'OperatingSystemFamily' for '"
						+ agent.getOperatingSystem().getName() + "'.");
			}

			// abort if family is unknown
			final String msg = "Unknown operating system for: " + example.getUserAgentString();
			assertThat(agent.getOperatingSystem().getFamily()).as(msg).isNotEqualTo(OperatingSystemFamily.UNKNOWN);

			// save read OS for printing out
			out.print(agent.getOperatingSystem().getName(), agent.getOperatingSystem().getVersionNumber(), example.getUserAgentString());

			i++;
		}
		LOG.info(Output.NEWLINE + out.toString());
		LOG.info(i + " operating system examples validated");
	}

	@Test
	public void testUserAgentExamples() throws Exception {
		final Output out = new Output("%-40.40s %-30.30s %s");
		int i = 0;
		for (final UserAgentExample example : UA_EXAMPLES) {
			final ReadableUserAgent agent = PARSER.parse(example.getUserAgentString());

			// comparing the name
			final UserAgentFamily family = UserAgentFamily.evaluate(example.getName());
			if (family != agent.getFamily()) {
				LOG.info("Unexpected user agent family found. Please check the user agent string '" + example.getUserAgentString() + "'.");
			}
			final String msgForFamilyDiff = "'" + family + "' != '" + agent.getFamily() + "' : " + example.getUserAgentString();
			assertThat(agent.getFamily()).as(msgForFamilyDiff).isEqualTo(family);

			final String type = "robot".equals(example.getType()) ? Robot.TYPENAME : example.getType();
			if (Robot.TYPENAME.equals(type)) {
				// save read robot for printing out
				out.print(agent.getName(), agent.getVersionNumber(), example.getUserAgentString());
			}

			// abort if the type is not the expected one
			final String msgForTypeDiff = "'" + type + "' != '" + agent.getTypeName() + "' : " + example.getUserAgentString();
			assertThat(agent.getTypeName()).as(msgForTypeDiff).isEqualTo(type);

			i++;
		}
		LOG.info(Output.NEWLINE + out.toString());
		LOG.info(i + " User-Agent examples validated");
	}

}
