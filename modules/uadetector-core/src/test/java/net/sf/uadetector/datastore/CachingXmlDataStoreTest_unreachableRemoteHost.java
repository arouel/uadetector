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

import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.parser.UpdatingUserAgentStringParserImpl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.io.Files;

public class CachingXmlDataStoreTest_unreachableRemoteHost {

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * Time to wait before the test can be aborted (in milliseconds)
	 */
	private static final long WAIT_UNTIL_ABORT = 30000;

	/**
	 * URL to retrieve a newer UAS data as XML
	 */
	private static final URL DATA_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.xml");

  /**
   * URL to the DTD of the the UAS
   */
  private static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);

	/**
	 * Version of the newer UAS data
	 */
	private static final String DATA_VERSION = TestXmlDataStore.VERSION_NEWER;

	/**
	 * {@link URL} that is not available
	 */
	private static final URL UNREACHABLE_URL = UrlUtil.build("http://unreachable.local/");

	/**
	 * URL to retrieve a newer UAS data as XML
	 */
	private static final URL VERSION_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.version");

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
	public void loadingOfRemoteData_successfully() throws IOException, InterruptedException {
		// setup a temporary file
		final File cache = folder.newFile();

		// create fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final long startTime = System.currentTimeMillis();
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL, VERSION_URL, DATA_DEF_URL, CHARSET, fallback);
		assertThat(store.getData().getVersion()).isEqualTo(fallback.getData().getVersion());
		final long duration = System.currentTimeMillis() - startTime;
		assertThat(duration < 1000).as("loading unreachable remote data takes too long").isTrue();

		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");

		final long lookupStartTime = System.currentTimeMillis();
		do {
			Thread.sleep(1000);
		} while (!readFile(cache).contains(DATA_VERSION) && !(System.currentTimeMillis() - lookupStartTime > WAIT_UNTIL_ABORT));
		assertThat(store.getData().getVersion()).isEqualTo(DATA_VERSION);

		final String readIn = readFile(cache);
		assertThat(readIn.contains(DATA_VERSION)).isTrue();
		assertThat(readIn.length() >= 721915).isTrue();
	}

	@Test
	public void loadingOfUnreachableRemoteData_checkToBeFast() throws IOException, InterruptedException {
		// setup a temporary file
		final File cache = folder.newFile();

		// create fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final long startTime = System.currentTimeMillis();
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, UNREACHABLE_URL, UNREACHABLE_URL, DATA_DEF_URL, CHARSET,
				fallback);
		assertThat(store.getData().getVersion()).isEqualTo(fallback.getData().getVersion());
		final long duration = System.currentTimeMillis() - startTime;
		assertThat(duration < 1000).as("loading unreachable remote data takes too long").isTrue();
	}

	@Test
	public void loadingOfUnreachableRemoteData_useFallback() throws IOException, InterruptedException {
		// setup a temporary file
		final File cache = folder.newFile();

		// create fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, UNREACHABLE_URL, UNREACHABLE_URL, DATA_DEF_URL, CHARSET,
				fallback);
		assertThat(store.getData().getVersion()).isEqualTo(fallback.getData().getVersion());

		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");

		final String newerVersionOfFallback = UrlUtil.read(TestXmlDataStore.VERSION_URL_NEWER, DataStore.DEFAULT_CHARSET);
		final long lookupStartTime = System.currentTimeMillis();
		do {
			Thread.sleep(1000l);
		} while (!readFile(cache).contains(newerVersionOfFallback) && !(System.currentTimeMillis() - lookupStartTime > WAIT_UNTIL_ABORT));

		assertThat(store.getData().getVersion()).isEqualTo(newerVersionOfFallback);
		assertThat(readFile(cache).contains(newerVersionOfFallback)).isTrue();
	}

}
