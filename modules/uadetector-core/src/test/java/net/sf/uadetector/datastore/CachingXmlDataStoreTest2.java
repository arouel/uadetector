package net.sf.uadetector.datastore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import net.sf.uadetector.datareader.XmlDataReaderTest;
import net.sf.uadetector.parser.UpdatingUserAgentStringParserImpl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

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
	public void testDifferentCacheFileAndUpdateStates() throws IOException, InterruptedException {
		// setup a temporary file
		final File cache = folder.newFile();

		// create fallback data store
		TestXmlDataStore fallbackDataStore = new TestXmlDataStore();

		// create caching data store without a cache file
		final CachingXmlDataStore store1 = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL_OLDER, VERSION_URL_OLDER, CHARSET,
				fallbackDataStore);
		final UpdatingUserAgentStringParserImpl parser1 = new UpdatingUserAgentStringParserImpl(store1);
		parser1.parse("test");

		Thread.sleep(1000l);
		final String readIn1 = readFile(cache);
		Assert.assertTrue(readIn1.contains(DATA_VERSION_OLDER));
		Assert.assertTrue(readIn1.length() >= 721915);

		// create caching data store with filled cache and available update
		final CachingXmlDataStore store2 = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL_NEWER, VERSION_URL_NEWER, CHARSET,
				fallbackDataStore);
		final UpdatingUserAgentStringParserImpl parser2 = new UpdatingUserAgentStringParserImpl(store2);
		parser2.parse("test");

		Thread.sleep(1000l);
		final String readIn2 = readFile(cache);
		Assert.assertTrue(readIn2.contains(DATA_VERSION_NEWER));
		Assert.assertTrue(readIn2.length() >= 721915);

		// create caching data store with filled cache and without an available update
		final CachingXmlDataStore store = CachingXmlDataStore.createCachingXmlDataStore(cache, DATA_URL_NEWER, VERSION_URL_NEWER, CHARSET,
				fallbackDataStore);
		final UpdatingUserAgentStringParserImpl parser = new UpdatingUserAgentStringParserImpl(store);
		parser.parse("test");

		Thread.sleep(1000l);
		final String readIn = readFile(cache);
		Assert.assertTrue(readIn.contains(DATA_VERSION_NEWER));
		Assert.assertTrue(readIn.length() >= 721915);
	}

}
