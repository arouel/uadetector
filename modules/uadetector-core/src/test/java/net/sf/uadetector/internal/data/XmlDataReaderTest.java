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
package net.sf.uadetector.internal.data;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataReader;
import net.sf.uadetector.internal.data.XmlDataReader;

import org.junit.Assert;
import org.junit.Test;

public class XmlDataReaderTest {

	@Test(expected = IllegalArgumentException.class)
	public void read_stream_null() {
		new XmlDataReader().read((InputStream) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void read_url_null() {
		new XmlDataReader().read((URL) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void read_url_unreachable() throws MalformedURLException {
		new XmlDataReader().read(new URL("http://unreachable.local/"));
	}

	@Test
	public void testVersionParsing() {
		final DataReader reader = new XmlDataReader();
		final Data data = reader.read(getClass().getClassLoader().getResourceAsStream("uas_test.xml"));
		Assert.assertEquals("20120323-01", data.getVersion());
	}

}
