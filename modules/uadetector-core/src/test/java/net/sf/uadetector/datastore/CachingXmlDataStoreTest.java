package net.sf.uadetector.datastore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Random;

import net.sf.uadetector.internal.util.FileUtil;
import net.sf.uadetector.parser.UpdatingUserAgentStringParserImpl;

import org.junit.Assert;
import org.junit.Test;
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

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_charset_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(new File("test"), DATA_URL, VERSION_URL, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_dataUrl_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(new File("test"), null, VERSION_URL, CHARSET);
	}

	@Test
	public void createCachingXmlDataStore_defaultCacheFile_successful() throws IOException, InterruptedException {
		// create caching data store
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(DATA_URL, VERSION_URL, CHARSET);
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_file_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(null, DATA_URL, VERSION_URL, CHARSET);
	}

	@Test
	public void createCachingXmlDataStore_successful() throws IOException, InterruptedException {
		// create temp file
		final File temp = File.createTempFile("uas_temp_" + new Random().nextLong(), ".data");
		temp.deleteOnExit();
		Assert.assertEquals("", readFile(temp));

		// create caching data store
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(temp, DATA_URL, VERSION_URL, CHARSET);
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);

		Assert.assertTrue(readFile(temp).length() >= 721915);

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
		temp.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createCachingXmlDataStore_versionUrl_null() throws IOException {
		CachingXmlDataStore.createCachingXmlDataStore(new File("test"), DATA_URL, null, CHARSET);
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
