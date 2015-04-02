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
package net.sf.uadetector.datastore;

import static org.fest.assertions.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataBlueprint;
import net.sf.uadetector.internal.util.UrlUtil;

import org.junit.Test;

public class AbstractRefreshableDataStoreTest_constructor {

  private static class TestDataStore extends AbstractRefreshableDataStore {

    protected TestDataStore(
        final DataReader reader,
        final Charset charset,
        final URL dataUrl,
        final URL versionUrl,
        final URL dataDefUrl,
        final DataStore fallback) {
      super(reader, dataUrl, versionUrl, dataDefUrl, charset, fallback);
    }

  }

  /**
   * The character set to read UAS data
   */
  private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

  /**
   * URL to retrieve the DTD of the UAS data
   */
  private static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);

  /**
   * URL to retrieve the UAS data as XML
   */
  private static final URL DATA_URL = AbstractRefreshableDataStoreTest_constructor.class.getClassLoader().getResource(
      "uas_older.xml");

  /**
   * URL to retrieve the version of the UAS data
   */
  private static final URL VERSION_URL = AbstractRefreshableDataStoreTest_constructor.class.getClassLoader().getResource(
      "uas_older.version");

  /**
   * Fallback data store
   */
  private static final DataStore FALLBACK = new DataStore() {

    @Override
    public Charset getCharset() {
      return null;
    }

    @Override
    public Data getData() {
      return new DataBlueprint().version("20120801-00").build();
    }

    @Override
    public URL getDataDefUrl() {
      return null;
    }

    @Override
    public DataReader getDataReader() {
      return null;
    }

    @Override
    public URL getDataUrl() {
      return null;
    }

    @Override
    public URL getVersionUrl() {
      return null;
    }
  };

  @Test(expected = IllegalNullArgumentException.class)
  public void construct_charset_isNull() throws MalformedURLException {
    final URL url = new URL("http://localhost");
    new TestDataStore(new XmlDataReader(), null, url, url, url, FALLBACK);
  }

  @Test(expected = IllegalNullArgumentException.class)
  public void construct_dataDefUrl_isNull() throws MalformedURLException {
    final URL url = new URL("http://localhost");
    new TestDataStore(new XmlDataReader(), CHARSET, url, url, null, FALLBACK);
  }

  @Test(expected = IllegalNullArgumentException.class)
  public void construct_dataReader_isNull() throws MalformedURLException {
    final URL url = new URL("http://localhost");
    new TestDataStore(null, CHARSET, url, url, url, FALLBACK);
  }

  @Test(expected = IllegalNullArgumentException.class)
  public void construct_dataUrl_isNull() throws MalformedURLException {
    final URL url = new URL("http://localhost");
    new TestDataStore(new XmlDataReader(), CHARSET, null, url, url, FALLBACK);
  }

  @Test(expected = IllegalNullArgumentException.class)
  public void construct_fallback_isNull() throws MalformedURLException {
    final URL url = new URL("http://localhost");
    new TestDataStore(new XmlDataReader(), CHARSET, url, url, url, null);
  }

  @Test
  public void construct_successful() throws Exception {
    final DataReader reader = new XmlDataReader();
    final TestDataStore store = new TestDataStore(reader, CHARSET, DATA_URL, VERSION_URL, DATA_DEF_URL, FALLBACK);
    assertThat(store.getData().getVersion()).isEqualTo(FALLBACK.getData().getVersion());
    store.getUpdateOperation().call();
    assertThat(store.getDataDefUrl()).isEqualTo(DATA_DEF_URL);
    assertThat(store.getDataReader()).isEqualTo(reader);
    assertThat(store.getDataUrl()).isEqualTo(DATA_URL);
    assertThat(store.getVersionUrl()).isEqualTo(VERSION_URL);
  }

  @Test(expected = IllegalNullArgumentException.class)
  public void construct_versionUrl_isNull() throws MalformedURLException {
    final URL url = new URL("http://localhost");
    new TestDataStore(new XmlDataReader(), CHARSET, url, null, url, FALLBACK);
  }

}
