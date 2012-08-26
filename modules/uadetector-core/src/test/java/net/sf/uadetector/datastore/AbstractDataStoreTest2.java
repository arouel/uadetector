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

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;

import org.junit.Assert;
import org.junit.Test;

public class AbstractDataStoreTest2 {

	private static class TestDataStore extends AbstractDataStore {

		protected TestDataStore(final DataReader reader, final URL dataUrl, final URL versionUrl) {
			super(reader, dataUrl, versionUrl);
		}

	}

	/**
	 * URL to retrieve the current UAS data as XML
	 */
	private static final URL DATA_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_newer.xml");

	/**
	 * URL to retrieve the current version of the UAS data
	 */
	private static final URL VERSION_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_newer.version");

	@Test(expected = IllegalArgumentException.class)
	public void construct_dataReader_null() throws MalformedURLException {
		new TestDataStore(null, DATA_URL, VERSION_URL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_dataUrl_null() throws MalformedURLException {
		new TestDataStore(new XmlDataReader(), null, VERSION_URL);
	}

	@Test
	public void construct_successful() {
		final DataReader reader = new XmlDataReader();
		final DataStore store = new TestDataStore(reader, DATA_URL, VERSION_URL);

		Assert.assertEquals("20120822-01", store.getData().getVersion());
		Assert.assertEquals(reader, store.getDataReader());
		Assert.assertEquals(DATA_URL, store.getDataUrl());
		Assert.assertEquals(VERSION_URL, store.getVersionUrl());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_versionUrl_null() throws MalformedURLException {
		new TestDataStore(new XmlDataReader(), DATA_URL, null);
	}

}
