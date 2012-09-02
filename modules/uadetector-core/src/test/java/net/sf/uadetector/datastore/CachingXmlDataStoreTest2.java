package net.sf.uadetector.datastore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Random;

import net.sf.uadetector.datareader.XmlDataReaderTest;
import net.sf.uadetector.parser.UpdatingUserAgentStringParserImpl;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CachingXmlDataStoreTest2 {

	/**
	 * URL to retrieve the older UAS data as XML
	 */
	private static final URL DATA_URL_OLDER = XmlDataReaderTest.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * URL to retrieve the older UAS data as XML
	 */
	private static final URL VERSION_URL_OLDER = XmlDataReaderTest.class.getClassLoader().getResource("uas_older.version");

	/**
	 * Version of the older UAS data
	 */
	private static final String DATA_VERSION_OLDER = "20120817-01";

	/**
	 * URL to retrieve a newer UAS data as XML
	 */
	public static final URL DATA_URL_NEWER = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.xml");

	/**
	 * URL to retrieve a newer UAS data as XML
	 */
	public static final URL VERSION_URL_NEWER = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.version");

	/**
	 * Version of the newer UAS data
	 */
	private static final String DATA_VERSION_NEWER = "20120822-01";

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * Temporary file to cache <em>UAS data</em>
	 */
	private static File temp;

	/**
	 * The default temporary-file directory
	 */
	private static final String CACHE_DIR = System.getProperty("java.io.tmpdir");

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

	@BeforeClass
	public static void setUp_class() throws IOException {
		// create temp file
		temp = new File(CACHE_DIR, "uas_temp_" + new Random().nextLong() + ".tmp");
		temp.createNewFile();
		temp.deleteOnExit();
		Assert.assertEquals("", readFile(temp));
	}

	@AfterClass
	public static void tearDown_class() throws IOException {
		// delete temp file
		temp.delete();
	}

	@Test
	public void _1_create_withEmptyCache() throws IOException, InterruptedException {
		// create caching data store
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(temp, DATA_URL_OLDER, VERSION_URL_OLDER, CHARSET);
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");

		final String readIn = readFile(temp);
		Assert.assertTrue(readIn.contains(DATA_VERSION_OLDER));
		Assert.assertTrue(readIn.length() >= 721915);
	}

	@Test
	public void _2_create_withFilledCache_andAvailableUpdate() throws IOException, InterruptedException {
		// create caching data store
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(temp, DATA_URL_NEWER, VERSION_URL_NEWER, CHARSET);
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");

		final String readIn = readFile(temp);
		Assert.assertTrue(readIn.contains(DATA_VERSION_NEWER));
		Assert.assertTrue(readIn.length() >= 721915);
	}

	@Test
	public void _3_create_withFilledCache_andNoAvailableUpdate() throws IOException, InterruptedException {
		// create caching data store
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(temp, DATA_URL_NEWER, VERSION_URL_NEWER, CHARSET);
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");

		final String readIn = readFile(temp);
		Assert.assertTrue(readIn.contains(DATA_VERSION_NEWER));
		Assert.assertTrue(readIn.length() >= 721915);
	}

}
