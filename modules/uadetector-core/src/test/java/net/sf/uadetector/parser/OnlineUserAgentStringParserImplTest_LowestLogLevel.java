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

import net.sf.uadetector.util.FinalStaticFieldValueChanger;
import net.sf.uadetector.util.LowestLogLevelTestLogger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Suite.class)
@SuiteClasses({ OnlineUserAgentStringParserImplTest.class })
public class OnlineUserAgentStringParserImplTest_LowestLogLevel {

	private static final Logger LOG = LoggerFactory.getLogger(OnlineUserAgentStringParserImplTest_LowestLogLevel.class);

	@BeforeClass
	public static void setUp() throws SecurityException, NoSuchFieldException, Exception {
		LOG.info("Deactivating default logger for '" + OnlineUserAgentStringParserImpl.class.getSimpleName() + "'.");
		final Logger log = new LowestLogLevelTestLogger();
		FinalStaticFieldValueChanger.setFinalStatic(OnlineUserAgentStringParserImpl.class.getDeclaredField("LOG"), log);
	}

	@AfterClass
	public static void tearDown() throws SecurityException, NoSuchFieldException, Exception {
		LOG.info("Activating default logger for '" + OnlineUserAgentStringParserImpl.class.getSimpleName() + "'.");
		final Logger log = LoggerFactory.getLogger(OnlineUserAgentStringParserImpl.class);
		FinalStaticFieldValueChanger.setFinalStatic(OnlineUserAgentStringParserImpl.class.getDeclaredField("LOG"), log);
	}

}
