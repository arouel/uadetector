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

import net.sf.uadetector.datastore.DataStore;

/**
 * This parser is an implementation of {@code UserAgentStringParser} interface and can detect user agents. The analysis
 * is based on the read {@code Data} of the given data source.
 * 
 * @author André Rouél
 */
public class UserAgentStringParserImpl<T extends DataStore> extends AbstractUserAgentStringParser {

	/**
	 * Storage for all detection informations for <i>UASparsers</i> from <a
	 * href="http://user-agent-string.info/">http://user-agent-string.info</a>.
	 */
	private final T store;

	/**
	 * Constructs an {@code UserAgentStringParser} using the given UAS data as detection source.
	 * 
	 * @param store
	 *            store for UAS data
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 */
	public UserAgentStringParserImpl(final T store) {
		super();

		if (store == null) {
			throw new IllegalArgumentException("Argument 'store' must not be null.");
		}

		this.store = store;
	}

	@Override
	protected T getDataStore() {
		return store;
	}

}
