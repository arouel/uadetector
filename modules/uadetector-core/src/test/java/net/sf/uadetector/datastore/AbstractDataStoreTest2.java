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
import net.sf.uadetector.internal.util.UrlUtil;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class AbstractDataStoreTest2 {

	private static class TestDataStore extends AbstractDataStore {

		protected TestDataStore(final DataReader reader, final URL dataUrl, final URL versionUrl, final URL dataDefUrl, final Charset charset) {
			super(reader, dataUrl, versionUrl, dataDefUrl, charset);
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
	private static final URL DATA_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_newer.xml");

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_newer.version");

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_charset_null() throws MalformedURLException {
		new TestDataStore(new XmlDataReader(), DATA_URL, VERSION_URL, DATA_DEF_URL, null);
	}

  @Test(expected = IllegalNullArgumentException.class)
  public void construct_dataDefUrl_null() throws MalformedURLException {
    new TestDataStore(new XmlDataReader(), DATA_URL, VERSION_URL, null, CHARSET);
  }

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_dataReader_null() throws MalformedURLException {
		new TestDataStore(null, DATA_URL, VERSION_URL, DATA_DEF_URL, CHARSET);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_dataUrl_null() throws MalformedURLException {
		new TestDataStore(new XmlDataReader(), null, VERSION_URL, DATA_DEF_URL, CHARSET);
	}

	@Test
	public void construct_successful() {
		final DataReader reader = new XmlDataReader();
		final DataStore store = new TestDataStore(reader, DATA_URL, VERSION_URL, DATA_DEF_URL, CHARSET);

		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_NEWER);
		assertThat(store.getDataReader()).isEqualTo(reader);
		assertThat(store.getDataUrl()).isEqualTo(DATA_URL);
		assertThat(store.getVersionUrl()).isEqualTo(VERSION_URL);
    assertThat(store.getDataDefUrl()).isEqualTo(DATA_DEF_URL);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_versionUrl_null() throws MalformedURLException {
		new TestDataStore(new XmlDataReader(), DATA_URL, null, DATA_DEF_URL, CHARSET);
	}

}
