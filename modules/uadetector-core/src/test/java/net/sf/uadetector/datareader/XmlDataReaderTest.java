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
package net.sf.uadetector.datareader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.datareader.XmlDataReader.XmlParser;
import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.datastore.TestXmlDataStore;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.UrlUtil;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.google.common.io.CharStreams;

public class XmlDataReaderTest {

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve the UAS data as XML (corrupted)
	 */
	private static final URL CORRUPTED_DATA_URL = XmlDataReaderTest.class.getClassLoader().getResource("uas_corrupted.xml");

  /**
   * URL to the DTD of the the UAS
   */
  private static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);

	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = XmlDataReaderTest.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * URL to retrieve the UAS data as XML (dirty)
	 */
	private static final URL DIRTY_DATA_URL = XmlDataReaderTest.class.getClassLoader().getResource("uas_dirty.xml");

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<XmlParser> constructor = XmlParser.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void read_charset_null() throws MalformedURLException {
		new XmlDataReader().read(new URL("http://localhost/"), DATA_DEF_URL, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void read_data_isNull() throws MalformedURLException {
		new XmlDataReader().read(null,DATA_DEF_URL);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void read_url_null() {
		new XmlDataReader().read((URL) null, DATA_DEF_URL, CHARSET);
	}

	@Test
	public void read_url_unreachable() throws MalformedURLException {
		final Data data = new XmlDataReader().read(new URL("http://unreachable.local/"), DATA_DEF_URL, CHARSET);
		assertThat(data).isSameAs(Data.EMPTY);
	}

	@Test
	public void readByString_parsingOfCorruptedData() throws IOException {
		final XmlDataReader reader = new XmlDataReader();
		final String dataAsString = CharStreams.toString(new InputStreamReader(CORRUPTED_DATA_URL.openStream()));
		final Data data = reader.read(dataAsString, DATA_DEF_URL);
		assertThat(data).isSameAs(Data.EMPTY);
	}

	@Test
	public void readByString_parsingOfDirtyData() throws IOException {
		final XmlDataReader reader = new XmlDataReader();
		final String dataAsString = CharStreams.toString(new InputStreamReader(DIRTY_DATA_URL.openStream()));
		final Data data = reader.read(dataAsString, DATA_DEF_URL);
		assertThat(data).isSameAs(Data.EMPTY);
	}

	@Test
	public void readByString_parsingSuccessful() throws IOException {
		final XmlDataReader reader = new XmlDataReader();
		final String dataAsString = CharStreams.toString(new InputStreamReader(DATA_URL.openStream()));
		final Data data = reader.read(dataAsString, DATA_DEF_URL);
		assertThat(data.getVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);
	}

	@Test
	public void readByUrl_parsingOfCorruptedData() throws IOException {
		final XmlDataReader reader = new XmlDataReader();
		final Data data = reader.read(CORRUPTED_DATA_URL, DATA_DEF_URL, CHARSET);
		assertThat(data).isSameAs(Data.EMPTY);
	}

	@Test
	public void readByUrl_parsingOfDirtyData() throws IOException {
		final XmlDataReader reader = new XmlDataReader();
		final Data data = reader.read(DIRTY_DATA_URL, DATA_DEF_URL, CHARSET);
		assertThat(data).isSameAs(Data.EMPTY);
	}

	@Test
	public void readByUrl_versionParsing() throws IOException {
		final DataReader reader = new XmlDataReader();
		final Data data = reader.read(DATA_URL, DATA_DEF_URL, CHARSET);
		assertThat(data.getVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void readXml_charset_null() throws IOException {
		XmlDataReader.readXml(new InputStream() {
			@Override
			public int read() throws IOException {
				return 0;
			}
		}, DATA_DEF_URL, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void readXml_url_null() throws IOException {
		XmlDataReader.readXml(null, DATA_DEF_URL, CHARSET);
	}

}
