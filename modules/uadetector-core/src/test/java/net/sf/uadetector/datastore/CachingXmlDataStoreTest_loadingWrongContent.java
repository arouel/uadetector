package net.sf.uadetector.datastore;

import static org.junit.Assert.assertEquals;

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
	 * URL to retrieve a newer UAS data as XML
	 */
	private static final URL DATA_NEWER_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.xml");

	/**
	 * Version of the newer UAS data
	 */
	private static final String DATA_VERSION = "20120822-01";

	/**
	 * Temporary folder to cache <em>UAS data</em> in a file. Created files in this folder are guaranteed to be deleted
	 * when the test method finishes (whether it passes or fails).
	 */
	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void loadingWrongContent_useFallback_doNotOverrideCacheFile() throws IOException, InterruptedException, URISyntaxException {
		// setup a temporary file
		final File cache = folder.newFile();

		// fill cache file with usable UAS data
		Files.write(ByteStreams.toByteArray(DATA_NEWER_URL.openStream()), cache);

		// create fallback data store
		final TestXmlDataStore fallback = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_CONNECTION_ERROR_URL,
				VERSION_CONNECTION_ERROR_URL, CHARSET, fallback);

		assertEquals(DATA_VERSION, store.getData().getVersion());

		// check that cache file's content is still correctly filled
		assertEquals(Files.toString(new File(DATA_NEWER_URL.toURI()), CHARSET), Files.toString(cache, CHARSET));

		// try to update the content
		store.refresh();
		Thread.sleep(1000L);

		// test that no corrupt data will be saved into cache file
		assertEquals(DATA_VERSION, store.getData().getVersion());
		assertEquals(Files.toString(new File(DATA_NEWER_URL.toURI()), CHARSET), Files.toString(cache, CHARSET));
	}

}
