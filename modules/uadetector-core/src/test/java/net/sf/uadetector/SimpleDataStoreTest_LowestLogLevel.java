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
@SuiteClasses({ SimpleDataStoreTest.class })
public class SimpleDataStoreTest_LowestLogLevel {

	private static final String FIELD_NAME = "LOG";

	private static final Logger LOG = LoggerFactory.getLogger(SimpleDataStoreTest_LowestLogLevel.class);

	@BeforeClass
	public static void setUp() throws SecurityException, NoSuchFieldException, Exception {
		LOG.info("Deactivating default logger for '" + SimpleDataStore.class.getSimpleName() + "'.");
		final Logger log = new LowestLogLevelTestLogger();
		FinalStaticFieldValueChanger.setFinalStatic(SimpleDataStore.class.getDeclaredField(FIELD_NAME), log);
	}

	@AfterClass
	public static void tearDown() throws SecurityException, NoSuchFieldException, Exception {
		LOG.info("Activating default logger for '" + SimpleDataStore.class.getSimpleName() + "'.");
		final Logger log = LoggerFactory.getLogger(SimpleDataStore.class);
		FinalStaticFieldValueChanger.setFinalStatic(SimpleDataStore.class.getDeclaredField(FIELD_NAME), log);
	}

}
