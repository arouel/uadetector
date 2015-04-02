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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.datareader.XmlDataReaderTest;
import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.parser.UpdatingUserAgentStringParserImpl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.io.Files;

public class CachingXmlDataStoreTest_differentCacheFileAndUpdateStates {

  /**
   * URL to the DTD of the the UAS
   */
  private static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);

	/**
	 * URL to retrieve the older UAS data as XML
	 */
	private static final URL DATA_URL_OLDER = XmlDataReaderTest.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * URL to retrieve the older UAS data as XML
	 */
	private static final URL VERSION_URL_OLDER = XmlDataReaderTest.class.getClassLoader().getResource("uas_older.version");

	/**
	 * Version of the older UAS data
	 */
	private static final String DATA_VERSION_OLDER = TestXmlDataStore.VERSION_OLDER;

	/**
	 * URL to retrieve a newer UAS data as XML
	 */
	public static final URL DATA_URL_NEWER = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.xml");

	/**
	 * URL to retrieve a newer UAS data as XML
	 */
	public static final URL VERSION_URL_NEWER = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.version");

	/**
	 * Version of the newer UAS data
	 */
	private static final String DATA_VERSION_NEWER = TestXmlDataStore.VERSION_NEWER;

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	private static String readFile(final File file) throws IOException {
		byte[] bytes = Files.toByteArray(file);
		return new String(bytes, Charset.defaultCharset());
	}

	/**
	 * Temporary folder to cache <em>UAS data</em> in a file. Created files in this folder are guaranteed to be deleted
	 * when the test method finishes (whether it passes or fails).
	 */
	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testDifferentCacheFileAndUpdateStates() throws IOException, InterruptedException {
		// setup a temporary file
		final File cache = folder.newFile();

		// create fallback data store
		final TestXmlDataStore fallbackDataStore = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store1 = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL_OLDER, VERSION_URL_OLDER, DATA_DEF_URL, CHARSET,
				fallbackDataStore);
		final UpdatingUserAgentStringParserImpl parser1 = new UpdatingUserAgentStringParserImpl(store1);
		parser1.parse("test");

		Thread.sleep(1000l);
		final String readIn1 = readFile(cache);
		assertThat(readIn1.contains(DATA_VERSION_OLDER)).isTrue();
		assertThat(readIn1.length() >= 721915).isTrue();

		// create caching data store with filled cache and available update
		final CachingXmlDataStore store2 = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL_NEWER, VERSION_URL_NEWER, DATA_DEF_URL, CHARSET,
				fallbackDataStore);
		final UpdatingUserAgentStringParserImpl parser2 = new UpdatingUserAgentStringParserImpl(store2);
		parser2.parse("test");

		Thread.sleep(1000l);
		final String readIn2 = readFile(cache);
		assertThat(readIn2.contains(DATA_VERSION_NEWER)).isTrue();
		assertThat(readIn2.length() >= 721915).isTrue();

		// create caching data store with filled cache and without an available update
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL_NEWER, VERSION_URL_NEWER, DATA_DEF_URL, CHARSET,
				fallbackDataStore);
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");

		Thread.sleep(1000l);
		final String readIn = readFile(cache);
		assertThat(readIn.contains(DATA_VERSION_NEWER)).isTrue();
		assertThat(readIn.length() >= 721915).isTrue();
	}

}
