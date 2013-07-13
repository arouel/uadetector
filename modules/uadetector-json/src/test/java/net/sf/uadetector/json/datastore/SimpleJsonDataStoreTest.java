/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.uadetector.json.datastore;

import java.net.URL;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class SimpleJsonDataStoreTest {

	private static final URL DATA_URL = SimpleJsonDataStoreTest.class.getClassLoader().getResource("uas.json");

	private static final URL VERSION_URL = SimpleJsonDataStoreTest.class.getClassLoader().getResource("uas.version");

	@Test(expected = IllegalNullArgumentException.class)
	public void construct1_dataIsNull() {
		new SimpleJsonDataStore(null, VERSION_URL);
	}

	@Test
	public void construct1_successful() {
		Assert.assertNotNull(new SimpleJsonDataStore(DATA_URL, VERSION_URL));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct1_versionIsNull() {
		new SimpleJsonDataStore(DATA_URL, null);
	}

}
