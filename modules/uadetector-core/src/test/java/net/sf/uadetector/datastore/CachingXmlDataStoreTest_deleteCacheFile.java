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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.internal.util.UrlUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

public class CachingXmlDataStoreTest_deleteCacheFile {

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
   * URL to the DTD of the the UAS
   */
  private static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);

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
		final File cache = createMock(File.class);
		expect(cache.canRead()).andReturn(true).anyTimes();
		expect(cache.isFile()).andReturn(true).anyTimes();
		// expect(cache.isInvalid()).andReturn(false).anyTimes(); // maybe necessary when using JDK7
		expect(cache.delete()).andReturn(false).anyTimes();
		expect(cache.exists()).andReturn(true).anyTimes();
		final File tmpFile = folder.newFile();
		expect(cache.getPath()).andReturn(tmpFile.getPath()).anyTimes();
		expect(cache.toURI()).andReturn(tmpFile.toURI()).anyTimes();
		replay(cache);

		// fill cache file with false content
		final byte[] wrongContent = ByteStreams.toByteArray(DATA_CONNECTION_ERROR_URL.openStream());
		Files.write(wrongContent, tmpFile);

		// create working fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_CONNECTION_ERROR_URL,
				VERSION_CONNECTION_ERROR_URL, DATA_DEF_URL, CHARSET, fallback);

		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);

		// check that cache was removed
		assertThat(cache.exists()).isTrue();

		// try to update the content
		store.refresh();
		Thread.sleep(1000L);

		// test that no fallback data will be written to cache file
		assertThat(store.getData().getVersion()).isEqualTo(TestXmlDataStore.VERSION_OLDER);
		assertThat(cache.exists()).isTrue();

		verify(cache);
	}

}
