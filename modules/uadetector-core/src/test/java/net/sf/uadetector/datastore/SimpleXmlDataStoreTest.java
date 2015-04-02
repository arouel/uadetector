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

import net.sf.uadetector.internal.util.UrlUtil;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class SimpleXmlDataStoreTest {

  /**
   * URL to the DTD of the the UAS
   */
  private static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);

	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_newer.xml");

	/**
	 * {@link URL} that is not available
	 */
	private static final URL UNREACHABLE_URL = UrlUtil.build("http://unreachable.local/");

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = AbstractDataStoreTest2.class.getClassLoader().getResource("uas_newer.version");

	@Test
	public void construct_successful() {
		final SimpleXmlDataStore store = new SimpleXmlDataStore(DATA_URL, VERSION_URL, DATA_DEF_URL);
		assertThat(!store.getData().getVersion().isEmpty()).isTrue();
		assertThat(store.getDataReader()).isNotNull();
		assertThat(store.getDataUrl()).isNotNull();
		assertThat(store.getVersionUrl()).isNotNull();
	}

	@Test(expected = IllegalStateException.class)
	public void readData_fails() {
		new SimpleXmlDataStore(UNREACHABLE_URL, UNREACHABLE_URL, DATA_DEF_URL);
	}

}
