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
package net.sf.uadetector.internal.parser;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class OnlineUserAgentStringParserHolder {

	private static final String RESOURCE = "uas_test.xml";

	private static final Logger LOG = LoggerFactory.getLogger(OnlineUserAgentStringParserImpl.class);

	private static OnlineUserAgentStringParserImpl INSTANCE;

	public static OnlineUserAgentStringParserImpl getInstance() {
		if (INSTANCE == null) {
			try {
				INSTANCE = new OnlineUserAgentStringParserImpl(OnlineUserAgentStringParserImpl.class.getClassLoader().getResourceAsStream(RESOURCE));
			} catch (final MalformedURLException e) {
				LOG.warn(e.getLocalizedMessage(), e);
			}
		}
		return INSTANCE;
	}

	private OnlineUserAgentStringParserHolder() {
		// should not be instantiated
	}

}
