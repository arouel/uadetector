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
package net.sf.uadetector.datastore;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;

import org.junit.Test;

public class OnlineXmlDataStoreTest {
	
	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_newer.xml");

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_newer.version");

	@Test
	public void construct_successful() {
		// create fallback data store
		final TestXmlDataStore fallbackDataStore = new TestXmlDataStore();

		final OnlineXmlDataStore store = new OnlineXmlDataStore(DATA_URL, VERSION_URL, fallbackDataStore);
		assertThat(!store.getData().getVersion().isEmpty()).isTrue();
		assertThat(store.getDataReader()).isNotNull();
		assertThat(store.getDataUrl()).isNotNull();
		assertThat(store.getVersionUrl().toExternalForm()).isNotNull();
		store.refresh();
	}

	@Test
	public void readData_failsAndReturnsFallbackData() {
		// create fallback data store
		final TestXmlDataStore fallbackDataStore = new TestXmlDataStore();

		final OnlineXmlDataStore store = new OnlineXmlDataStore(DATA_URL, VERSION_URL, fallbackDataStore);
		assertThat(store.getData().getVersion()).isEqualTo(fallbackDataStore.getData().getVersion());
		assertThat(store.getDataReader()).isNotNull();
		assertThat(store.getDataUrl()).isNotNull();
		assertThat(store.getVersionUrl().toExternalForm()).isNotNull();
		store.refresh();
	}

}
