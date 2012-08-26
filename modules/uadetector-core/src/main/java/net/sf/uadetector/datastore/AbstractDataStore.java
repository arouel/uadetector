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
package net.sf.uadetector.datastore;

import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.UrlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The abstract implementation to store <em>UAS data</em> only in the heap space.<br>
 * <br>
 * A store must always have an usable instance of {@link Data}. It is recommended to initialize it with the supplied UAS
 * file in the <em>uadetector-resources</em> module.
 * 
 * @author André Rouél
 */
public abstract class AbstractDataStore implements DataStore {

	/**
	 * Corresponding default logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractDataStore.class);

	/**
	 * This method reads the given {@link URL} by using an {@link DataReader}. The new created instance of {@link Data}
	 * will be returned.
	 * 
	 * @param url
	 *            URL to <em>UAS data</em>
	 * @param charset
	 *            the character set in which the data should be read
	 * @return new created instance of {@code Data} and never {@code null}
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 * @throws net.sf.uadetector.exception.CanNotOpenStreamException
	 *             if no stream to the given {@code URL} can be established
	 */
	protected static final Data readData(final DataReader reader, final URL url, Charset charset) {
		if (reader == null) {
			throw new IllegalArgumentException("Argument 'reader' must not be null.");
		}
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' must not be null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException("Argument 'charset' must not be null.");
		}

		return reader.read(url, charset);
	}

	/**
	 * Current the character set in which the <em>UAS data</em> will be read
	 */
	private final Charset charset;

	/**
	 * Current <em>UAS data</em>
	 */
	private Data data;

	/**
	 * The data reader to read in <em>UAS data</em>
	 */
	private final DataReader reader;

	/**
	 * The {@code URL} to get <em>UAS data</em>
	 */
	private final URL dataUrl;

	/**
	 * The {@code URL} to get the latest version information of <em>UAS data</em>
	 */
	private final URL versionUrl;

	/**
	 * Constructs an new instance of {@link AbstractDataStore}.
	 * 
	 * @param data
	 *            first <em>UAS data</em> which will be available in the store
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 */
	protected AbstractDataStore(final Data data, final DataReader reader, final URL dataUrl, final URL versionUrl, final Charset charset) {
		if (data == null) {
			throw new IllegalArgumentException("Argument 'data' must not be null.");
		}
		if (reader == null) {
			throw new IllegalArgumentException("Argument 'reader' must not be null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException("Argument 'charset' must not be null.");
		}
		if (dataUrl == null) {
			throw new IllegalArgumentException("Argument 'dataUrl' must not be null.");
		}
		if (versionUrl == null) {
			throw new IllegalArgumentException("Argument 'versionUrl' must not be null.");
		}

		this.data = data;
		this.reader = reader;
		this.dataUrl = dataUrl;
		this.versionUrl = versionUrl;
		this.charset = charset;
	}

	/**
	 * Constructs an {@code SimpleDataStore} by reading the given {@code dataUrl} as <em>UAS data</em>.
	 * 
	 * @param reader
	 *            data reader to read the given {@code dataUrl}
	 * @param dataUrl
	 *            URL to <em>UAS data</em>
	 * @param versionUrl
	 *            URL to version information about the given <em>UAS data</em>
	 * @throws IllegalArgumentException
	 *             if one of given arguments is {@code null}
	 * @throws IllegalArgumentException
	 *             if the given strings are not valid URLs
	 * @throws net.sf.uadetector.exception.CanNotOpenStreamException
	 *             when no streams to the given {@code URL}s can be established
	 */
	protected AbstractDataStore(final DataReader reader, final String dataUrl, final String versionUrl, final Charset charset) {
		this(reader, UrlUtil.build(dataUrl), UrlUtil.build(versionUrl), charset);
	}

	/**
	 * Constructs an {@code SimpleDataStore} by reading the given {@code dataUrl} as <em>UAS data</em>.
	 * 
	 * @param reader
	 *            data reader to read the given {@code dataUrl}
	 * @param dataUrl
	 *            URL to <em>UAS data</em>
	 * @param versionUrl
	 *            URL to version information about the given <em>UAS data</em>
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 * @throws net.sf.uadetector.exception.CanNotOpenStreamException
	 *             when no streams to the given {@code URL}s can be established
	 */
	protected AbstractDataStore(final DataReader reader, final URL dataUrl, final URL versionUrl, final Charset charset) {
		this(readData(reader, dataUrl, charset), reader, dataUrl, versionUrl, charset);
	}

	@Override
	public Charset getCharset() {
		return charset;
	}

	@Override
	public Data getData() {
		return data;
	}

	@Override
	public DataReader getDataReader() {
		return reader;
	}

	@Override
	public URL getDataUrl() {
		return dataUrl;
	}

	@Override
	public URL getVersionUrl() {
		return versionUrl;
	}

	/**
	 * Sets new <em>UAS data</em> in the store.
	 * 
	 * @param data
	 *            <em>UAS data</em> to override the current ({@code null} is not allowed)
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
