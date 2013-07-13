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

import java.net.URL;

import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.datastore.SimpleXmlDataStore;

public final class TestDataStoreFactory {

	public static final URL DATA_URL = TestDataStoreFactory.class.getClassLoader().getResource("samples/uas.xml");

	public static final URL VERSION_URL = TestDataStoreFactory.class.getClassLoader().getResource("samples/uas.version");

	public static DataStore produce() {
		return new SimpleXmlDataStore(DATA_URL, VERSION_URL);
	}

}
