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

import org.junit.Assert;
import org.junit.Test;

public class OnlineXmlDataStoreTest {

	@Test
	public void construct_successful() {
		// create fallback data store
		final TestXmlDataStore fallbackDataStore = new TestXmlDataStore();

		final OnlineXmlDataStore store = new OnlineXmlDataStore(fallbackDataStore);
		Assert.assertTrue(!store.getData().getVersion().isEmpty());
		Assert.assertNotNull(store.getDataReader());
		Assert.assertNotNull(store.getDataUrl());
		Assert.assertNotNull(store.getVersionUrl().toExternalForm());
		store.refresh();
	}

	@Test
	public void readData_failsAndReturnsFallbackData() {
		// create fallback data store
		final TestXmlDataStore fallbackDataStore = new TestXmlDataStore();

		final OnlineXmlDataStore store = new OnlineXmlDataStore(fallbackDataStore);
		Assert.assertEquals(fallbackDataStore.getData().getVersion(), store.getData().getVersion());
		Assert.assertNotNull(store.getDataReader());
		Assert.assertNotNull(store.getDataUrl());
		Assert.assertNotNull(store.getVersionUrl().toExternalForm());
		store.refresh();
	}

}
