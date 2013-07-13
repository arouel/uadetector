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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.parser.UpdatingUserAgentStringParserImpl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CachingXmlDataStoreTest_unreachableRemoteHost {

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve a newer UAS data as XML
	 */
	private static final URL DATA_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.xml");

	/**
	 * Version of the newer UAS data
	 */
	private static final String DATA_VERSION = "20130329-01";

	/**
	 * {@link URL} that is not available
	 */
	private static final URL UNREACHABLE_URL = UrlUtil.build("http://unreachable.local/");

	/**
	 * URL to retrieve a newer UAS data as XML
	 */
	private static final URL VERSION_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.version");

	private static String readFile(final File file) throws IOException {
		final FileInputStream stream = new FileInputStream(file);
		try {
			final FileChannel fc = stream.getChannel();
			final MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
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
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL, VERSION_URL, CHARSET, fallback);
		Assert.assertEquals(fallback.getData().getVersion(), store.getData().getVersion());
		final long duration = System.currentTimeMillis() - startTime;
		Assert.assertTrue("loading unreachable remote data takes too long", duration < 1000);

		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");

		final long lookupStartTime = System.currentTimeMillis();
		do {
			Thread.sleep(1000);
		} while (!readFile(cache).contains(DATA_VERSION) && !(System.currentTimeMillis() - lookupStartTime > 1000 * 10));
		Assert.assertEquals(DATA_VERSION, store.getData().getVersion());

		final String readIn = readFile(cache);
		Assert.assertTrue(readIn.contains(DATA_VERSION));
		Assert.assertTrue(readIn.length() >= 721915);
	}

	@Test
	public void loadingOfUnreachableRemoteData_checkToBeFast() throws IOException, InterruptedException {
		// setup a temporary file
		final File cache = folder.newFile();

		// create fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final long startTime = System.currentTimeMillis();
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, UNREACHABLE_URL, UNREACHABLE_URL, CHARSET,
				fallback);
		Assert.assertEquals(fallback.getData().getVersion(), store.getData().getVersion());
		final long duration = System.currentTimeMillis() - startTime;
		Assert.assertTrue("loading unreachable remote data takes too long", duration < 1000);
	}

	@Test
	public void loadingOfUnreachableRemoteData_useFallback() throws IOException, InterruptedException {
		// setup a temporary file
		final File cache = folder.newFile();

		// create fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, UNREACHABLE_URL, UNREACHABLE_URL, CHARSET,
				fallback);
		Assert.assertEquals(fallback.getData().getVersion(), store.getData().getVersion());

		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");

		final String newerVersionOfFallback = UrlUtil.read(TestXmlDataStore.VERSION_URL_NEWER, DataStore.DEFAULT_CHARSET);
		final long lookupStartTime = System.currentTimeMillis();
		do {
			Thread.sleep(1000l);
		} while (!readFile(cache).contains(newerVersionOfFallback) && !(System.currentTimeMillis() - lookupStartTime > 1000 * 10));

		Assert.assertEquals(newerVersionOfFallback, store.getData().getVersion());
		Assert.assertTrue(readFile(cache).contains(newerVersionOfFallback));
	}

}
