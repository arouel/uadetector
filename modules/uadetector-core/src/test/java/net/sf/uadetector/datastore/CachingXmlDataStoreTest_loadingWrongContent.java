package net.sf.uadetector.datastore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	 * URL to retrieve a newer UAS data as XML
	 */
	private static final URL DATA_NEWER_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.xml");

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

	@Test(expected = IllegalStateException.class)
	public void loadCorruptedCacheFile_useFallback_overrideCacheFileWithWorkingData() throws IOException, InterruptedException,
			URISyntaxException {
		final File cache = folder.newFile();

		// fill cache file with false content
		Files.write(ByteStreams.toByteArray(DATA_CONNECTION_ERROR_URL.openStream()), cache);

		// create working fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_CONNECTION_ERROR_URL,
				VERSION_CONNECTION_ERROR_URL, CHARSET, fallback);

		assertEquals(TestXmlDataStore.VERSION_OLDER, store.getData().getVersion());

		// check that cache file's content is still correctly filled
		assertEquals(Files.toString(new File(DATA_NEWER_URL.toURI()), CHARSET), Files.toString(cache, CHARSET));

		// try to update the content
		store.refresh();
		Thread.sleep(1000L);

		// test that no corrupt data will be saved into cache file
		assertEquals(TestXmlDataStore.VERSION_NEWER, store.getData().getVersion());
		assertEquals(Files.toString(new File(DATA_NEWER_URL.toURI()), CHARSET), Files.toString(cache, CHARSET));
	}

	@Test
	public void loadEmptyCacheFile_useFallback_overrideCacheFileWithWorkingData() throws IOException, InterruptedException,
			URISyntaxException {
		final File cache = folder.newFile();

		// create working fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_CONNECTION_ERROR_URL,
				VERSION_CONNECTION_ERROR_URL, CHARSET, fallback);

		assertEquals(TestXmlDataStore.VERSION_OLDER, store.getData().getVersion());

		// check that cache file is empty, because fallback data store will be taken
		assertTrue(Files.toString(cache, CHARSET).isEmpty());

		// try to update the content
		store.refresh();
		Thread.sleep(1000L);

		// test that no corrupt data will be saved into cache file
		assertEquals(TestXmlDataStore.VERSION_NEWER, store.getData().getVersion());
		assertEquals(Files.toString(new File(DATA_NEWER_URL.toURI()), CHARSET), Files.toString(cache, CHARSET));
	}

	@Test
	public void loadingWrongContent_useFallback_doNotOverrideCacheFile() throws IOException, InterruptedException, URISyntaxException {
		final File cache = folder.newFile();

		// fill cache file with usable UAS data
		Files.write(ByteStreams.toByteArray(DATA_NEWER_URL.openStream()), cache);

		// create working fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_CONNECTION_ERROR_URL,
				VERSION_CONNECTION_ERROR_URL, CHARSET, fallback);

		assertEquals(TestXmlDataStore.VERSION_NEWER, store.getData().getVersion());

		// check that cache file's content is still correctly filled
		assertEquals(Files.toString(new File(DATA_NEWER_URL.toURI()), CHARSET), Files.toString(cache, CHARSET));

		// try to update the content
		store.refresh();
		Thread.sleep(1000L);

		// test that no corrupt data will be saved into cache file
		assertEquals(TestXmlDataStore.VERSION_NEWER, store.getData().getVersion());
		assertEquals(Files.toString(new File(DATA_NEWER_URL.toURI()), CHARSET), Files.toString(cache, CHARSET));
	}

}
