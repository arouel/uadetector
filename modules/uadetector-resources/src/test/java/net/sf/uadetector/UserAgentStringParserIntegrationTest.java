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

import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.internal.parser.OnlineUserAgentStringParserImpl;

import org.apache.commons.csv.CSVParser;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAgentStringParserIntegrationTest {

	private static final String CHARSET = "UTF-8";

	/**
	 * Default log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OnlineUserAgentStringParserImpl.class);

	private static final UserAgentStringParser parser = UADetectorServiceFactory.getUserAgentStringParser();

	@Test
	public void testOperatingSystemExamples() throws Exception {
		final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("examples/uasOS_example.csv");
		final CSVParser csvParser = new CSVParser(new InputStreamReader(stream, CHARSET));
		String[] line = null;
		int i = 0;
		do {
			line = csvParser.getLine();
			if (line != null) {
				i++;
				if (line.length == 2) {
					final UserAgent agent = parser.parse(line[1]);
					Assert.assertEquals(line[0], agent.getOperatingSystem().getName());
				} else {
					LOG.warn("Not enough fields: " + line.length);
				}
			}
		} while (line != null);
		LOG.info(i + " operating system examples validated");
	}

	@Test
	public void testUserAgentExamples() throws Exception {
		final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("examples/uas_example.csv");
		final CSVParser csvParser = new CSVParser(new InputStreamReader(stream, CHARSET));
		String[] line = null;
		int i = 0;
		do {
			line = csvParser.getLine();
			if (line != null) {
				i++;
				if (line.length == 3) {
					final UserAgent agent = parser.parse(line[2]);
					Assert.assertEquals(line[1], agent.getFamily());
					final String type = "robot".equals(line[0]) ? Robot.TYPENAME : line[0];
					Assert.assertEquals(type, agent.getType());
				} else {
					LOG.warn("Not enough fields: " + line.length);
				}
			}
		} while (line != null);
		LOG.info(i + " User-Agent examples validated");
	}

}
