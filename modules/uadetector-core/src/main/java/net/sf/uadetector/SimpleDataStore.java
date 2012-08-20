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
package net.sf.uadetector;

import java.io.InputStream;

import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataReader;
import net.sf.uadetector.internal.data.XmlDataReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The simplest implementation to store UAD data only in the heap.<br>
 * <br>
 * A store must always have an usable instance of {@link Data}. It is recommended to initialize it with the supplied UAS
 * file in the <em>uadetector-resources</em> module.
 * 
 * @author André Rouél
 */
public class SimpleDataStore implements DataStore {

	/**
	 * Corresponding default logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SimpleDataStore.class);

	/**
	 * This method reads the given {@link InputStream} by using an {@link XmlDataReader}. The new created instance of
	 * {@link Data} will be returned.
	 * 
	 * @param stream
	 * @return new created instance of {@code Data} and never {@code null}
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 */
	protected static final Data readXmlData(final InputStream stream) {
		if (stream == null) {
			throw new IllegalArgumentException("Argument 'stream' must not be null.");
		}

		final DataReader reader = new XmlDataReader();
		return reader.read(stream);
	}

	/**
	 * Current UAS data
	 */
	private Data data;

	/**
	 * Constructs an new instance of {@link SimpleDataStore}.
	 * 
	 * @param data
	 *            first UAS data which will be available in the store
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 */
	public SimpleDataStore(final Data data) {
		if (data == null) {
			throw new IllegalArgumentException("Argument 'data' must not be null.");
		}
		this.data = data;
	}

	/**
	 * Constructs an {@code SimpleDataStore} reading the given {@link InputStream} as UAS data.
	 * 
	 * @param stream
	 *            {@code InputStream} with UAS data
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 */
	public SimpleDataStore(final InputStream stream) {
		this(readXmlData(stream));
	}

	@Override
	public Data getData() {
		return data;
	}

	/**
	 * Sets new UAS data in the store.
	 * 
	 * @param data
	 *            UAS data to override the current ({@code null} is not allowed)
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 */
	@Override
	public void setData(final Data data) {
		if (data == null) {
			throw new IllegalArgumentException("Argument 'data' must not be null.");
		}

		this.data = data;

		// add some useful UAS data informations to the log
		if (LOG.isDebugEnabled()) {
			LOG.debug(data.toStats());
		}
	}

}
