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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.datastore.SimpleXmlDataStore;
import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.json.internal.data.JsonConverter;
import net.sf.uadetector.json.internal.data.serializer.Serialization;

public class JsonDataCreator {

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

	public static void createOrUpdate(final DataStore store) throws IOException {
		final Serialization serialization = JsonConverter.serialize(store.getData(), SerDeOption.PRETTY_PRINTING);
		final String json = serialization.getJson();

		// get resources directory
		final File resourcesDir = new File(ClassLoader.getSystemResource(".").getPath() + "../../src/test/resources");
		Check.stateIsTrue(resourcesDir.exists(), "the specified resources directory must be available");

		// create JSON data file
		final File dataFile = new File(resourcesDir, "uas.json");
		if (dataFile.exists()) {
			Check.stateIsTrue(dataFile.delete(), "the current JSON data file can not be deleted");
		}

		// write JSON data file
		final FileWriter dataOut = new FileWriter(dataFile);
		dataOut.append(json).flush();
		dataOut.close();

		// create JSON data file
		final File versionFile = new File(resourcesDir, "uas.version");
		if (versionFile.exists()) {
			Check.stateIsTrue(versionFile.delete(), "the current JSON version file can not be deleted");
		}

		// write JSON data file
		final FileWriter versionOut = new FileWriter(versionFile);
		versionOut.append(store.getData().getVersion()).flush();
		versionOut.close();
	}

	private static final DataStore determineDataStore(final String argument) {
		final boolean isOffline = argument != null && argument.toLowerCase().equals("offline");
		final DataStore fallback = new SimpleXmlDataStore(DATA_URL, VERSION_URL, DATA_DEF_URL);
		return isOffline ? fallback : new OnlineXmlDataStore();
	}

	public static void main(final String[] args) {
		try {
			final DataStore store = determineDataStore(args.length > 0 ? args[0] : "online");
			createOrUpdate(store);
			System.out.println("New UAS data in JSON format created.");
		} catch (final IOException e) {
			System.err.println("Can not create or update UAS data in JSON format: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}
