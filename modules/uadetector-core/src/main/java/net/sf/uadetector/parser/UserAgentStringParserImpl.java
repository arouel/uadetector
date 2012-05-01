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

import java.io.InputStream;

import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataReader;
import net.sf.uadetector.internal.data.XmlDataReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This parser is an implementation of {@code UserAgentStringParser} interface and can detect user agents. The analysis
 * is based on the read {@code Data} of the given data source.
 * 
 * @author André Rouél
 */
public class UserAgentStringParserImpl extends AbstractUserAgentStringParser {

	/**
	 * Detection informations for <i>UASparsers</i> from <a
	 * href="http://user-agent-string.info/">http://user-agent-string.info</a>.
	 */
	private Data data;

	/**
	 * Data reader to read a UAS file or stream
	 */
	private final DataReader dataReader;

	/**
	 * Current version of {@code Data} or an empty String (never {@code null})
	 */
	private String currentVersion = "";

	/**
	 * Default logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UserAgentStringParserImpl.class);

	/**
	 * Constructs an {@code UserAgentStringParser} using the given UAS data as detection source.
	 * 
	 * @param stream
	 *            {@code InputStream} with UAS data
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 */
	public UserAgentStringParserImpl(final InputStream stream) {
		super();

		if (stream == null) {
			throw new IllegalArgumentException("Argument 'stream' must not be null.");
		}

		dataReader = new XmlDataReader();
		setData(read(stream));
	}

	protected String getCurrentVersion() {
		return currentVersion;
	}

	@Override
	protected Data getData() {
		return data;
	}

	protected final DataReader getDataReader() {
		return dataReader;
	}

	/**
	 * Reads UAS data from {@code InputStream} and returns a {@code Data} object.
	 * 
	 * @param stream
	 *            {@code InputStream} with UAS data
	 * @return an <i>immutable</i> {@code Data} object
	 */
	private final Data read(final InputStream stream) {
		return dataReader.read(stream);
	}

	/**
	 * Sets the given {@code Data} and version information for this parser.
	 * 
	 * @param data
	 *            instance of {@code Data}
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 */
	protected final void setData(final Data data) {
		if (data == null) {
			throw new IllegalArgumentException("Argument 'data' must not be null.");
		}

		LOG.debug(data.toStats());
		this.data = data;
		currentVersion = data.getVersion();
	}

}
