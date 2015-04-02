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

import java.net.URL;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.internal.util.UrlUtil;

/**
 * This is a very simple implementation of a {@link DataStore} for test cases. It initialize the store by reading the
 * <em>UAS data</em> via test URLs and store it only in the Java heap space.
 * 
 * @author André Rouél
 */
public class TestXmlDataStore extends AbstractRefreshableDataStore {

  /**
   * URL to the DTD of the UAS data
   */
  public static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);

	/**
	 * URL to retrieve the UAS data as XML
	 */
	public static final URL DATA_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * URL to retrieve a newer UAS data as XML
	 */
	public static final URL DATA_URL_NEWER = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.xml");

	/**
	 * The default data reader to read in <em>UAS data</em> in XML format
	 */
	private static final DataReader DEFAULT_DATA_READER = new XmlDataReader();

	/**
	 * Version of the newer UAS data
	 */
	public static final String VERSION_NEWER = "20131018-01";

	/**
	 * Version of the older UAS data
	 */
	public static final String VERSION_OLDER = "20131016-01";

	/**
	 * URL to retrieve the version information of the UAS data
	 */
	public static final URL VERSION_URL = TestXmlDataStore.class.getClassLoader().getResource("uas_older.version");

	/**
	 * URL to retrieve a newer version of the UAS data
	 */
	public static final URL VERSION_URL_NEWER = TestXmlDataStore.class.getClassLoader().getResource("uas_newer.version");

	/**
	 * Constructs an {@code OnlineXmlDataStore} by reading <em>UAS data</em> by the specified default URL
	 * {@link DataStore#DEFAULT_DATA_URL} (in XML format).
	 */
	public TestXmlDataStore() {
		super(DEFAULT_DATA_READER, DATA_URL, VERSION_URL, DATA_DEF_URL, DEFAULT_CHARSET, new SimpleXmlDataStore(DATA_URL, VERSION_URL, DATA_DEF_URL));
	}

  @Override
  public URL getDataDefUrl() {
    return DATA_DEF_URL;
  }

	@Override
	public URL getDataUrl() {
		return DATA_URL_NEWER;
	}

	@Override
	public URL getVersionUrl() {
		return VERSION_URL_NEWER;
	}

	@Override
	public void refresh() {
		// bypass non-blocking behavior
		getUpdateOperation().call();
	}

}
