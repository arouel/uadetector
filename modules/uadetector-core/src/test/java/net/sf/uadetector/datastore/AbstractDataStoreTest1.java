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
import java.nio.charset.Charset;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataBlueprint;
import net.sf.uadetector.internal.util.UrlUtil;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class AbstractDataStoreTest1 {

	private static class TestDataStore extends AbstractDataStore {

		protected TestDataStore(final Data data, final DataReader reader, final Charset charset, final URL dataUrl, final URL versionUrl, final URL dataDefUrl) {
			super(data, reader, dataUrl, versionUrl, dataDefUrl, charset);
		}

	}

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

  /**
   * URL to the DTD of the the UAS
   */
  private static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);

	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = AbstractDataStoreTest1.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * {@link URL} that is not available
	 */
	private static final URL UNREACHABLE_URL = UrlUtil.build("http://unreachable.local/");

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_older.version");

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_charset_null() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(Data.EMPTY, new XmlDataReader(), null, url, url, DATA_DEF_URL);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_data_null() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore((Data) null, new XmlDataReader(), CHARSET, url, url, DATA_DEF_URL);
	}

  @Test(expected = IllegalNullArgumentException.class)
  public void construct_dataDef_null() throws MalformedURLException {
    final URL url = new URL("http://localhost");
    new TestDataStore(Data.EMPTY, new XmlDataReader(), CHARSET, url, url, null);
  }

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_dataReader_null() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(Data.EMPTY, null, CHARSET, url, url, DATA_DEF_URL);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_dataUrl_null() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(Data.EMPTY, new XmlDataReader(), CHARSET, null, url, DATA_DEF_URL);
	}

	@Test
	public void construct_successful() {
		final Data data = new DataBlueprint().version("test-version").build();
		final DataReader reader = new XmlDataReader();
		final TestDataStore store = new TestDataStore(data, reader, CHARSET, DATA_URL, VERSION_URL, DATA_DEF_URL);

		assertThat(store.getData().getVersion()).isEqualTo("test-version");
		assertThat(store.getData()).isSameAs(data);
		assertThat(store.getDataReader()).isEqualTo(reader);
		assertThat(store.getDataUrl()).isEqualTo(DATA_URL);
		assertThat(store.getVersionUrl()).isEqualTo(VERSION_URL);
		assertThat(store.getDataDefUrl()).isEqualTo(DATA_DEF_URL);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_versionUrl_null() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(Data.EMPTY, new XmlDataReader(), CHARSET, url, null, DATA_DEF_URL);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void readData_charset_null() {
		AbstractDataStore.readData(new XmlDataReader(), DATA_URL, DATA_DEF_URL, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void readData_dataUrl_null() {
		AbstractDataStore.readData(new XmlDataReader(), null, DATA_DEF_URL, CHARSET);
	}

	@Test
	public void readData_failsAndReturnsEMPTY() {
		final Data data = AbstractDataStore.readData(new XmlDataReader(), UNREACHABLE_URL, DATA_DEF_URL, CHARSET);
		assertThat(data).isEqualTo(Data.EMPTY);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void readData_reader_null() {
		AbstractDataStore.readData(null, DATA_URL, DATA_DEF_URL, CHARSET);
	}

	@Test
	public void readData_successful() {
		final Data data = AbstractDataStore.readData(new XmlDataReader(), DATA_URL, DATA_DEF_URL, CHARSET);
		assertThat(data.getVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);
	}

	@Test(expected = IllegalStateException.class)
	public void setData_EMPTY() {
		new TestDataStore(Data.EMPTY, new XmlDataReader(), CHARSET, DATA_URL, VERSION_URL, DATA_DEF_URL);
	}

}
