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
package net.sf.uadetector.parser;

import net.sf.uadetector.datastore.RefreshableDataStore;
import net.sf.uadetector.datastore.TestXmlDataStore;

final class UpdatingUserAgentStringParserHolder {

	private static UpdatingUserAgentStringParserImpl INSTANCE;

	public static UpdatingUserAgentStringParserImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UpdatingUserAgentStringParserImpl(setUpDataStore());
		}
		return INSTANCE;
	}

	private static RefreshableDataStore setUpDataStore() {
		return new TestXmlDataStore();
	}

	private UpdatingUserAgentStringParserHolder() {
		// should not be instantiated
	}

}
