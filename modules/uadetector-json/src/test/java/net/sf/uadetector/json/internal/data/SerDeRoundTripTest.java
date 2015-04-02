/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.uadetector.json.internal.data;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;

import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.datastore.SimpleXmlDataStore;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.json.JsonDataCreator;
import net.sf.uadetector.json.SerDeOption;

import org.junit.Test;

public class SerDeRoundTripTest {

  /**
   * URL to the DTD of the the UAS
   */
  private static final URL DATA_DEF_URL = UrlUtil.build(DataStore.DEFAULT_DATA_DEF_URL);
  
	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = JsonDataCreator.class.getClassLoader().getResource("samples/uas.xml");

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = JsonDataCreator.class.getClassLoader().getResource("samples/uas.version");

	private static final Data fromJson(final String json) {
		return JsonConverter.deserialize(json, SerDeOption.HASH_VALIDATING).getData();
	}

	private static final String toJson(final Data data) {
		return JsonConverter.serialize(data, SerDeOption.PRETTY_PRINTING).getJson();
	}

	@Test
	public void test() {
		final DataStore dataStore = new SimpleXmlDataStore(DATA_URL, VERSION_URL, DATA_DEF_URL);
		final String json = JsonConverter.serialize(dataStore.getData(), SerDeOption.PRETTY_PRINTING).getJson();
		String redo = json;
		for (int i = 0; i < 100; i++) {
			redo = toJson(fromJson(redo));
			assertThat(redo).isEqualTo(json);
		}
	}

}
