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
package net.sf.uadetector.json;

import java.io.IOException;
import java.net.URISyntaxException;

import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.json.internal.data.JsonConverter;
import net.sf.uadetector.json.internal.data.serializer.Serialization;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataConversionTest {

	private static final Logger LOG = LoggerFactory.getLogger(DataConversionTest.class);

	public static String humanReadableByteCount(final long bytes, final boolean si) {
		final int unit = si ? 1000 : 1024;
		if (bytes < unit) {
			return bytes + " B";
		}
		final int exp = (int) (Math.log(bytes) / Math.log(unit));
		final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	@Test
	public void testConversionViceVersa() throws IOException, URISyntaxException {
		final SerDeOption options = SerDeOption.PRETTY_PRINTING;
		final DataStore store = TestDataStoreFactory.produce();
		Serialization serialization = JsonConverter.serialize(store.getData(), options);
		assertThat(serialization.getWarnings()).isEmpty();
		final String json = serialization.getJson();
		final Data parsedData = JsonConverter.deserialize(json).getData();

		// can not be equals, because the IDs are different
		assertThat(store.getData().equals(parsedData)).isFalse();

		// must be equals, because the IDs are identical after conversion
		Serialization serialization2 = JsonConverter.serialize(store.getData(), options);
		assertThat(serialization2.getWarnings()).isEmpty();
		final String expectedJson = serialization2.getJson();
		final Data expectedData = JsonConverter.deserialize(expectedJson).getData();
		assertThat(json).isEqualTo(expectedJson);
		assertThat(parsedData).isEqualTo(expectedData);

		// print some JSON
		LOG.info(json);

		// print some stats
		LOG.info(store.getData().toStats());
		LOG.info(parsedData.toStats());

		// print size comparison
		final String uasDataAsXml = UrlUtil.read(TestDataStoreFactory.DATA_URL, DataStore.DEFAULT_CHARSET);
		LOG.info("size XML: " + humanReadableByteCount(uasDataAsXml.getBytes().length, false));
		LOG.info("size JSON: " + humanReadableByteCount(JsonConverter.serialize(store.getData()).getJson().getBytes().length, false));
	}

}
