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
package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.uadetector.json.SerDeOption;
import net.sf.uadetector.json.internal.data.util.MessageCollector;

interface Deserializer extends MessageCollector {

	String EMPTY_HASH_CODE = "";

	String MSG_HASH_CODE_DIFFERENCE = "The computed hash code (%s) differs from the original (%s): %s";

	/**
	 * Gets the deserialization options.
	 * 
	 * @return options during deserialization
	 */
	EnumSet<SerDeOption> getOptions();

}
