package net.sf.uadetector.datastore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataBlueprint;
import net.sf.uadetector.internal.util.FileUtil;
import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.parser.UpdatingUserAgentStringParserImpl;

import org.junit.Assert;
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

	/**
	 * Temporary folder to cache <em>UAS data</em> in a file. Created files in this folder are guaranteed to be deleted
	 * when the test method finishes (whether it passes or fails).
	 */
	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_charset_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), DATA_URL, VERSION_URL, null, Data.EMPTY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_dataUrl_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), null, VERSION_URL, CHARSET, Data.EMPTY);
	}

	@Test
	public void createCachingXmlDataStore_defaultCacheFile_successful() throws IOException, InterruptedException {
		// create caching data store
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(DATA_URL, VERSION_URL, CHARSET);
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");
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
	public void createCachingXmlDataStore_file1_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(null, DATA_URL, VERSION_URL, CHARSET);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_file2_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(null, DATA_URL, VERSION_URL, CHARSET, Data.EMPTY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_file3_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(null, Data.EMPTY);
	}

	@Test
	public void createCachingXmlDataStore_provokeFallback() throws IOException, InterruptedException {
		// create temp file
		final File cache = folder.newFile("uas_temp.xml");
		Assert.assertEquals("", readFile(cache));

		// create caching data store
		final String version = "fallback-data-version";
		final DataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, UNREACHABLE_URL, UNREACHABLE_URL, CHARSET,
				DataBlueprint.buildEmptyTestData(version));
		Assert.assertEquals(version, store.getData().getVersion());
	}

	@Test
	public void createCachingXmlDataStore_successful() throws IOException, InterruptedException {
		// create temp file
		final File cache = folder.newFile("uas_temp.xml");
		Assert.assertEquals("", readFile(cache));

		// create caching data store
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL, VERSION_URL, CHARSET);
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);

		Assert.assertTrue(readFile(cache).length() >= 721915);

		final long firstLastUpdateCheck = parser.getUpdater().getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + firstLastUpdateCheck);
		final long originalInterval = parser.getUpdateInterval();

		// reduce the interval since testing
		LOG.debug("Reducing the update interval during the test.");
		parser.setUpdateInterval(100l);
		// we have to read to activate the update mechanism
		parser.parse("check for updates");

		Thread.sleep(1000l);
		final long currentLastUpdateCheck = parser.getUpdater().getLastUpdateCheck();
		LOG.debug("LastUpdateCheck at: " + currentLastUpdateCheck);
		Assert.assertTrue(firstLastUpdateCheck < currentLastUpdateCheck);

		parser.setUpdateInterval(originalInterval);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_versionUrl_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(folder.newFile("uas_test.xml"), DATA_URL, null, CHARSET, Data.EMPTY);
	}

	@Test
	public void findOrCreateCacheFile() {
		CachingXmlDataStore.findOrCreateCacheFile().delete(); // delete if exists
		File temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file does not exist
		temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file exists
		temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file exists
		temp.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void readAndSave_charset_null() throws IOException {
		final File temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file should not exist
		CachingXmlDataStore.readAndSave(DATA_URL, temp, null);
		temp.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void readAndSave_dataUrl_null() throws IOException {
		final File temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file should not exist
		CachingXmlDataStore.readAndSave(null, temp, CHARSET);
		temp.delete();
	}

	@Test
	public void readAndSave_deleteAndRenameTempFileTest() throws MalformedURLException, IOException {
		final File temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file does not exist

		// Assert.assertTrue(temp.length() == 0); // does not work on any system
		Assert.assertTrue(FileUtil.isEmpty(temp, DataStore.DEFAULT_CHARSET));

		CachingXmlDataStore.readAndSave(DATA_URL, temp, CHARSET); // file will be created
		Assert.assertTrue(temp.length() >= 722015);

		CachingXmlDataStore.readAndSave(DATA_URL, temp, CHARSET); // file will be overwritten
		Assert.assertTrue(temp.length() >= 722015);

		temp.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void readAndSave_file_null() throws IOException {
		CachingXmlDataStore.readAndSave(DATA_URL, null, CHARSET);
	}

	@Test
	public void readAndSave_urlAndFileAreSameResource() throws MalformedURLException, IOException {
		final File temp = CachingXmlDataStore.findOrCreateCacheFile(); // cache file does not exist
		CachingXmlDataStore.readAndSave(temp.toURI().toURL(), temp, CHARSET);
		temp.delete();
	}

}
