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
package net.sf.uadetector.writer;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.datastore.SimpleXmlDataStore;
import net.sf.uadetector.datastore.TestXmlDataStore;
import net.sf.uadetector.internal.util.UrlUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IniDataWriterTest {

	private static final Logger LOG = LoggerFactory.getLogger(IniDataWriterTest.class);

	private static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);

	private static final URL DATA_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_older.xml");

  private static final URL VERSION_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_older.version");

	private static final DataStore DATA_STORE = new SimpleXmlDataStore(DATA_URL, VERSION_URL, DATA_DEF_URL);

	@Test
	public void write() throws Exception {
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		IniDataWriter.write(DATA_STORE.getData(), outputStream);
		final String actual = new String(outputStream.toByteArray(), "UTF-8");
		LOG.debug(actual);
	}

}
