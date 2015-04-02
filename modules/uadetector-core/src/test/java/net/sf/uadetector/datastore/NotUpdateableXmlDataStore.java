/*******************************************************************************
 * Copyright 2012 André Rouél
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

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.internal.util.UrlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a very simple implementation of a {@link DataStore} for test cases which want to update the <em>UAS data</em>
 * with unreachable URLs.
 * 
 * @author André Rouél
 */
public final class NotUpdateableXmlDataStore extends AbstractRefreshableDataStore {

	private static final Logger LOG = LoggerFactory.getLogger(NotUpdateableXmlDataStore.class);

	/**
	 * The default data reader to read in <em>UAS data</em> in XML format
	 */
	private static final DataReader DEFAULT_DATA_READER = new XmlDataReader();

  /**
   * URL to the DTD of the UAS data
   */
  public static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);

	/**
	 * URL to retrieve the UAS data as XML
	 */
	public static final URL DATA_URL = NotUpdateableXmlDataStore.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * URL to retrieve the version information of the UAS data
	 */
	public static final URL VERSION_URL = NotUpdateableXmlDataStore.class.getClassLoader().getResource("uas_older.version");

	/**
	 * Unreachable URL to retrieve a newer UAS data as XML
	 */
	public static URL DATA_URL_UNREACHABLE;

	/**
	 * Unreachable URL to retrieve a newer version of the UAS data
	 */
	public static URL VERSION_URL_UNREACHABLE;

	static {
		try {
			DATA_URL_UNREACHABLE = new URL("http://localhost/unreachable.xml");
			VERSION_URL_UNREACHABLE = new URL("http://localhost/unreachable.version");
		} catch (final MalformedURLException e) {
			LOG.warn(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Constructs an {@code OnlineXmlDataStore} by reading <em>UAS data</em> by the specified default URL
	 * {@link DataStore#DEFAULT_DATA_URL} (in XML format).
	 */
	public NotUpdateableXmlDataStore() {
		super(DEFAULT_DATA_READER, DATA_URL, VERSION_URL, DATA_DEF_URL, DEFAULT_CHARSET, new TestXmlDataStore());
	}

	@Override
	public URL getDataUrl() {
		return DATA_URL_UNREACHABLE;
	}

	@Override
	public DataStore getFallback() {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getVersionUrl() {
		return VERSION_URL_UNREACHABLE;
	}

	@Override
	public void refresh() {
		// bypass non-blocking behavior
		getUpdateOperation().call();
	}

}
