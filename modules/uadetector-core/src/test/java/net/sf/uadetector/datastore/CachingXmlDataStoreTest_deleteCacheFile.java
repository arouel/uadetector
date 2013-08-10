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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.easymock.EasyMock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ File.class })
public class CachingXmlDataStoreTest_deleteCacheFile {

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

	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void loadCorruptedCacheFile_useFallback_doNotOverrideCacheFileWithFallbackData_cacheFileIsNotDeletable() throws IOException,
			InterruptedException, URISyntaxException {

		final File cache = PowerMock.createMock(File.class);
		EasyMock.expect(cache.delete()).andReturn(false).anyTimes();
		EasyMock.expect(cache.exists()).andReturn(true).anyTimes();
		EasyMock.expect(cache.getPath()).andReturn(folder.newFile().getPath()).anyTimes();
		EasyMock.expect(cache.toURI()).andReturn(folder.newFile().toURI()).anyTimes();
		PowerMock.replay(cache);

		// fill cache file with false content
		final byte[] wrongContent = ByteStreams.toByteArray(DATA_CONNECTION_ERROR_URL.openStream());
		Files.write(wrongContent, cache);

		// create working fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_CONNECTION_ERROR_URL,
				VERSION_CONNECTION_ERROR_URL, CHARSET, fallback);
		new FileInputStream(cache).read();

		assertEquals(TestXmlDataStore.VERSION_OLDER, store.getData().getVersion());

		// check that cache was removed
		assertTrue(cache.exists());

		// try to update the content
		store.refresh();
		Thread.sleep(1000L);

		// test that no fallback data will be written to cache file
		assertEquals(TestXmlDataStore.VERSION_OLDER, store.getData().getVersion());
		assertTrue(cache.exists());
	}

}
