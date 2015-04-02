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
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

public class CachingXmlDataStoreTest_loadingWrongContent {

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve wrong content instead of UAS data as XML
	 */
	private static final URL DATA_CONNECTION_ERROR_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_connection_error.xml");

	/**
	 * URL to retrieve wrong content instead of UAS data as XML
	 */
	private static final URL VERSION_CONNECTION_ERROR_URL = TestXmlDataStore.class.getClassLoader().getResource(
			"uas_connection_error.version");

	/**
	 * Temporary folder to cache <em>UAS data</em> in a file. Created files in this folder are guaranteed to be deleted
	 * when the test method finishes (whether it passes or fails).
	 */
	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void loadCorruptedCacheFile_useFallback_doNotOverrideCacheFileWithFallbackData() throws IOException, InterruptedException,
			URISyntaxException {
		final File cache = folder.newFile();

		// fill cache file with false content
		final byte[] wrongContent = ByteStreams.toByteArray(DATA_CONNECTION_ERROR_URL.openStream());
		Files.write(wrongContent, cache);

		// create working fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_CONNECTION_ERROR_URL,
				VERSION_CONNECTION_ERROR_URL, TestXmlDataStore.DATA_DEF_URL, CHARSET, fallback);

		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);

		// check that cache was removed
		assertThat(cache.exists()).isFalse();

		// try to update the content
		store.refresh();
		Thread.sleep(1000L);

		// test that no fallback data will be written to cache file
		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);
		assertThat(cache.exists()).isFalse();
	}

	@Test
	public void loadCorruptedCacheFile_useRemoteData_overrideCacheFileWithWorkingData() throws IOException, InterruptedException,
			URISyntaxException {
		final File cache = folder.newFile();

		// fill cache file with false content
		final byte[] wrongContent = ByteStreams.toByteArray(DATA_CONNECTION_ERROR_URL.openStream());
		Files.write(wrongContent, cache);

		// create working fallback data store
		final DataStore fallback = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL, TestXmlDataStore.VERSION_URL, TestXmlDataStore.DATA_DEF_URL);

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, TestXmlDataStore.DATA_URL_NEWER,
				TestXmlDataStore.VERSION_URL_NEWER, TestXmlDataStore.DATA_DEF_URL, CHARSET, fallback);

		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);

		// check that cache was removed
		assertThat(cache.exists()).isFalse();

		// try to update the content
		store.refresh();
		Thread.sleep(1000L);

		// test that no corrupt data will be saved into cache file
		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_NEWER);
		assertThat(Files.toString(cache, CHARSET)).isEqualTo(Files.toString(new File(TestXmlDataStore.DATA_URL_NEWER.toURI()), CHARSET));
	}

	@Test
	public void loadEmptyCacheFile_useFallback_overrideCacheFileWithWorkingData() throws IOException, InterruptedException,
			URISyntaxException {
		final File cache = folder.newFile();

		// create working fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_CONNECTION_ERROR_URL,
				VERSION_CONNECTION_ERROR_URL, TestXmlDataStore.DATA_DEF_URL, CHARSET, fallback);

		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);

		// check that cache file is empty, because fallback data store will be taken
		assertThat(Files.toString(cache, CHARSET).isEmpty()).isTrue();

		// try to update the content
		store.refresh();
		Thread.sleep(1000L);

		// test that no corrupt data will be saved into cache file
		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_NEWER);
		assertThat(Files.toString(cache, CHARSET)).isEqualTo(Files.toString(new File(TestXmlDataStore.DATA_URL_NEWER.toURI()), CHARSET));
	}

	@Test
	public void loadingWrongContent_useFallback_doNotOverrideCacheFile() throws IOException, InterruptedException, URISyntaxException {
		final File cache = folder.newFile();

		// fill cache file with usable UAS data
		Files.write(ByteStreams.toByteArray(TestXmlDataStore.DATA_URL_NEWER.openStream()), cache);

		// create working fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_CONNECTION_ERROR_URL,
				VERSION_CONNECTION_ERROR_URL, TestXmlDataStore.DATA_DEF_URL, CHARSET, fallback);

		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_NEWER);

		// check that cache file's content is still correctly filled
		assertThat(Files.toString(cache, CHARSET)).isEqualTo(Files.toString(new File(TestXmlDataStore.DATA_URL_NEWER.toURI()), CHARSET));

		// try to update the content
		store.refresh();
		Thread.sleep(1000L);

		// test that no corrupt data will be saved into cache file
		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_NEWER);
		assertThat(Files.toString(cache, CHARSET)).isEqualTo(Files.toString(new File(TestXmlDataStore.DATA_URL_NEWER.toURI()), CHARSET));
	}
}
