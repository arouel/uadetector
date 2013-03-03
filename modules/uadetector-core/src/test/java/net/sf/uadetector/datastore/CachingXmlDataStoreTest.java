package net.sf.uadetector.datastore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataBlueprint;
import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.parser.UpdatingUserAgentStringParserImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CachingXmlDataStoreTest {

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = CachingXmlDataStoreTest.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * Corresponding default logger of this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CachingXmlDataStoreTest.class);

	/**
	 * {@link URL} that is not available
	 */
	private static final URL UNREACHABLE_URL = UrlUtil.build("http://unreachable.local/");

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = CachingXmlDataStoreTest.class.getClassLoader().getResource("uas_older.version");

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

	private DataStore fallback;

	/**
	 * Temporary folder to cache <em>UAS data</em> in a file. Created files in this folder are guaranteed to be deleted
	 * when the test method finishes (whether it passes or fails).
	 */
	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void before() {
		fallback = new TestXmlDataStore();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_charset_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), DATA_URL, VERSION_URL, null, fallback);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_dataUrl_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), null, VERSION_URL, CHARSET, fallback);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_fallback1_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), DATA_URL, VERSION_URL, CHARSET, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_fallback2_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(DATA_URL, VERSION_URL, CHARSET, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_fallback3_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_file2_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(null, DATA_URL, VERSION_URL, CHARSET, fallback);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_file3_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(null, fallback);
	}

	@Test
	public void createCachingXmlDataStore_provokeFallback() throws IOException, InterruptedException {
		// create temp file
		final File cache = folder.newFile("uas_temp.xml");
		Assert.assertEquals("", readFile(cache));

		// create fallback data store
		final String version = "fallback-data-version";
		final DataStore fallback = new DataStore() {

			@Override
			public Charset getCharset() {
				return null;
			}

			@Override
			public Data getData() {
				return DataBlueprint.buildEmptyTestData(version);
			}

			@Override
			public DataReader getDataReader() {
				return null;
			}

			@Override
			public URL getDataUrl() {
				return null;
			}

			@Override
			public URL getVersionUrl() {
				return null;
			}
		};

		// create caching data store
		final DataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, UNREACHABLE_URL, UNREACHABLE_URL, CHARSET, fallback);
		Assert.assertEquals(version, store.getData().getVersion());
	}

	@Test
	public void createCachingXmlDataStore_successful() throws IOException, InterruptedException {
		// create temp file
		final File cache = folder.newFile("uas_temp.xml");
		Assert.assertEquals("", readFile(cache));

		// create caching data store
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL, VERSION_URL, CHARSET, fallback);
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);

		Thread.sleep(1000l);
		Assert.assertTrue(readFile(cache).length() >= 721915);

		final long firstLastUpdateCheck = store.getUpdateOperation().getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + firstLastUpdateCheck);
		final long originalInterval = parser.getUpdateInterval();

		// reduce the interval since testing
		LOG.debug("Reducing the update interval during the test.");
		parser.setUpdateInterval(1000l);
		// we have to read to activate the update mechanism
		parser.parse("check for updates");

		Thread.sleep(3000l);
		final long currentLastUpdateCheck = store.getUpdateOperation().getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + currentLastUpdateCheck);
		Assert.assertTrue(firstLastUpdateCheck < currentLastUpdateCheck);

		parser.setUpdateInterval(originalInterval);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_versionUrl_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), DATA_URL, null, CHARSET, fallback);
	}

	@Test
	public void findOrCreateCacheFile() {
		CachingXmlDataStore.findOrCreateCacheFile().delete(); // delete if exists
		File temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file does not exist
		Assert.assertTrue(temp.exists());
		temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file exists
		Assert.assertTrue(temp.exists());
		temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file exists
		Assert.assertTrue(temp.exists());
		temp.delete();
		Assert.assertFalse(temp.exists());
	}

}
