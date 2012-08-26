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
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.datareader.XmlDataReader.XmlParser;
import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.exception.CanNotOpenStreamException;
import net.sf.uadetector.internal.data.Data;

import org.junit.Assert;
import org.junit.Test;

public class XmlDataReaderTest {

	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = XmlDataReaderTest.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<XmlParser> constructor = XmlParser.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void read_charset_null() throws MalformedURLException {
		new XmlDataReader().read(new URL("http://localhost/"), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void read_url_null() {
		new XmlDataReader().read((URL) null, CHARSET);
	}

	@Test(expected = CanNotOpenStreamException.class)
	public void read_url_unreachable() throws MalformedURLException {
		new XmlDataReader().read(new URL("http://unreachable.local/"), CHARSET);
	}

	@Test(expected = IllegalArgumentException.class)
	public void readXml_charset_null() throws IOException {
		XmlDataReader.readXml(new URL("http://localhost/"), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void readXml_url_null() throws IOException {
		XmlDataReader.readXml(null, CHARSET);
	}

	@Test
	public void testVersionParsing() throws IOException {
		final DataReader reader = new XmlDataReader();
		final Data data = reader.read(DATA_URL, CHARSET);
		Assert.assertEquals("20120817-01", data.getVersion());
	}

}
