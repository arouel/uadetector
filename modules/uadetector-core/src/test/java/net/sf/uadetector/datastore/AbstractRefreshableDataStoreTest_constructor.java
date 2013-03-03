package net.sf.uadetector.datastore;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.TreeMap;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.internal.util.UrlUtil;

import org.junit.Assert;
import org.junit.Test;

public class AbstractRefreshableDataStoreTest_constructor {

	private static class TestDataStore extends AbstractRefreshableDataStore {

		protected TestDataStore(final DataReader reader, final Charset charset, final URL dataUrl, final URL versionUrl,
				final DataStore fallback) {
			super(reader, dataUrl, versionUrl, charset, fallback);
		}

	}

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = AbstractRefreshableDataStoreTest_constructor.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = AbstractRefreshableDataStoreTest_constructor.class.getClassLoader().getResource(
			"uas_older.version");

	/**
	 * Fallback data store
	 */
	private static final DataStore FALLBACK = new DataStore() {

		@Override
		public Charset getCharset() {
			return null;
		}

		@Override
		public Data getData() {
			return new Data(new HashSet<Browser>(), new HashSet<OperatingSystem>(), new HashSet<Robot>(0),
					new TreeMap<BrowserPattern, Browser>(), new TreeMap<OperatingSystemPattern, OperatingSystem>(), "20120801-fallback");
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

	@Test(expected = IllegalArgumentException.class)
	public void construct_charset_isNull() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(new XmlDataReader(), null, url, url, FALLBACK);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_dataReader_isNull() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(null, CHARSET, url, url, FALLBACK);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_dataUrl_isNull() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(new XmlDataReader(), CHARSET, null, url, FALLBACK);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_fallback_isNull() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(new XmlDataReader(), CHARSET, url, url, null);
	}

	@Test
	public void construct_successful() throws Exception {
		final DataReader reader = new XmlDataReader();
		final TestDataStore store = new TestDataStore(reader, CHARSET, DATA_URL, VERSION_URL, FALLBACK);
		Assert.assertEquals(FALLBACK.getData().getVersion(), store.getData().getVersion());
		store.getUpdateOperation().call();
		Assert.assertEquals(UrlUtil.read(VERSION_URL, CHARSET), store.getData().getVersion());
		Assert.assertEquals(reader, store.getDataReader());
		Assert.assertEquals(DATA_URL, store.getDataUrl());
		Assert.assertEquals(VERSION_URL, store.getVersionUrl());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_versionUrl_isNull() throws MalformedURLException {
		final URL url = new URL("http://localhost");
		new TestDataStore(new XmlDataReader(), CHARSET, url, null, FALLBACK);
	}

}
