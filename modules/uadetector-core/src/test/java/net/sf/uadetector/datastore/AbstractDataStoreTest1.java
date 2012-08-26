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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.TreeMap;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;

public class AbstractDataStoreTest1 {

	private static class TestDataStore extends AbstractDataStore {

		protected TestDataStore(final Data data, final DataReader reader, final URL dataUrl, final URL versionUrl) {
			super(data, reader, dataUrl, versionUrl);
		}

	}

	/**
	 * URL to retrieve the current UAS data as XML
	 */
	private static final URL DATA_URL = AbstractDataStoreTest1.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * URL to retrieve the current version of the UAS data
	 */
	private static final URL VERSION_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_older.version");

	@Test(expected = IllegalArgumentException.class)
	public void construct_data_null() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore((Data) null, new XmlDataReader(), url, url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_dataReader_null() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(Data.EMPTY, null, url, url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_dataUrl_null() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(Data.EMPTY, new XmlDataReader(), null, url);
	}

	@Test
	public void construct_successful() {

		final Data data = new Data(new HashSet<Browser>(), new HashSet<OperatingSystem>(), new HashSet<Robot>(0),
				new TreeMap<BrowserPattern, Browser>(), new TreeMap<OperatingSystemPattern, OperatingSystem>(), "test-version");
		final DataReader reader = new XmlDataReader();
		final TestDataStore store = new TestDataStore(data, reader, DATA_URL, VERSION_URL);

		Assert.assertEquals("test-version", store.getData().getVersion());
		Assert.assertSame(data, store.getData());
		Assert.assertEquals(reader, store.getDataReader());
		Assert.assertEquals(DATA_URL, store.getDataUrl());
		Assert.assertEquals(VERSION_URL, store.getVersionUrl());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_versionUrl_null() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(Data.EMPTY, new XmlDataReader(), url, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void readData_dataReader_null() throws MalformedURLException {
		AbstractDataStore.readData(new InputStream() {
			@Override
			public int read() throws IOException {
				return 0;
			}
		}, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void readData_stream_null() throws MalformedURLException {
		AbstractDataStore.readData((InputStream) null, new XmlDataReader());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setData_null() {
		final Data data = Data.EMPTY;
		final DataReader reader = new XmlDataReader();
		final TestDataStore store = new TestDataStore(data, reader, DATA_URL, VERSION_URL);
		store.setData(null);
	}

	@Test
	public void setData_successful() {
		final Data data = Data.EMPTY;
		final DataReader reader = new XmlDataReader();
		final TestDataStore store = new TestDataStore(data, reader, DATA_URL, VERSION_URL);

		final Data data2 = new Data(new HashSet<Browser>(), new HashSet<OperatingSystem>(), new HashSet<Robot>(0),
				new TreeMap<BrowserPattern, Browser>(), new TreeMap<OperatingSystemPattern, OperatingSystem>(), "test-version");
		store.setData(data2);
		Assert.assertSame(data2, store.getData());
	}

}
