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

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.exception.CanNotOpenStreamException;

import org.junit.Assert;
import org.junit.Test;

public class AbstractDataStoreTest3 {

	private static class TestDataStore extends AbstractDataStore {

		protected TestDataStore(final DataReader reader, final String dataUrl, final String versionUrl) {
			super(reader, dataUrl, versionUrl);
		}

	}

	/**
	 * URL to retrieve the current UAS data as XML
	 */
	private static final String DATA_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_newer.xml").toExternalForm();

	/**
	 * URL to retrieve the current version of the UAS data
	 */
	private static final String VERSION_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_newer.version").toString();

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

		Assert.assertTrue(!store.getData().getVersion().isEmpty());
		Assert.assertEquals(reader, store.getDataReader());
		Assert.assertEquals(DATA_URL, store.getDataUrl().toExternalForm());
		Assert.assertEquals(VERSION_URL, store.getVersionUrl().toExternalForm());
	}

	@Test(expected = CanNotOpenStreamException.class)
	public void construct_unreachable_url() {
		final DataReader reader = new XmlDataReader();
		final String unreachable = "http://unreachable,local";
		new TestDataStore(reader, unreachable, unreachable);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_versionUrl_null() throws MalformedURLException {
		new TestDataStore(new XmlDataReader(), DATA_URL, null);
	}

}
