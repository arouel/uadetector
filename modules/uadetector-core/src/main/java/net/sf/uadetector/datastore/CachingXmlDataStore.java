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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.UrlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of a {@link DataStore} which is able to recover <em>UAS data</em> in XML format from a cache file. If
 * the cache file is empty, the data will be read from the given data URL.<br>
 * <br>
 * You can also update the data of the store at any time if you trigger {@link CachingXmlDataStore#refresh()}.
 * 
 * @author André Rouél
 */
public final class CachingXmlDataStore extends AbstractDataStore implements RefreshableDataStore {

	/**
	 * The suffix string to be used in generating the cache file's name; may be {@code null}, in which case the suffix "
	 * {@code .tmp}" will be used
	 */
	private static final String SUFFIX = "";

	/**
	 * The prefix string to be used in generating the cache file's name; must be at least three characters long
	 */
	private static final String PREFIX = "uas";

	/**
	 * The default temporary-file directory
	 */
	private static final String CACHE_DIR = System.getProperty("java.io.tmpdir");

	/**
	 * Corresponding default logger of this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CachingXmlDataStore.class);

	/**
	 * Message for the log if the cache file is filled
	 */
	private static final String MSG_CACHE_FILE_IS_EMPTY = "The cache file is empty. The given UAS data source will be imported.";

	/**
	 * Message for the log if the cache file is empty
	 */
	private static final String MSG_CACHE_FILE_IS_FILLED = "The cache file is filled and will be imported.";

	/**
	 * Message for the log if the passed resources are the same and an update makes no sense
	 */
	private static final String MSG_SAME_RESOURCES = "The passed URL and file resources are the same. An update was not performed.";

	/**
	 * Constructs a new instance of {@code CachingXmlDataStore} with the given arguments. The given {@code cacheFile}
	 * can be empty or filled with previously cached data in XML format. The file must be writable otherwise an
	 * exception will be thrown.
	 * 
	 * @param cacheFile
	 *            file with cached <em>UAS data</em> in XML format or empty file
	 * @param dataUrl
	 *            URL to <em>UAS data</em>
	 * @param versionUrl
	 *            URL to version information about the given <em>UAS data</em>
	 * @param charset
	 *            the character set in which the data should be read
	 * @return new instance of {@link CachingXmlDataStore}
	 * @throws IllegalArgumentException
	 *             if one of the given arguments is {@code null}
	 * @throws IllegalArgumentException
	 *             if the given cache file can not be read
	 * @throws IllegalStateException
	 *             if no URL can be resolved to the given given file
	 */
	public static CachingXmlDataStore createCachingXmlDataStore(final File cacheFile, final URL dataUrl, final URL versionUrl,
			final Charset charset) {
		if (cacheFile == null) {
			throw new IllegalArgumentException("Argument 'cacheFile' must not be null.");
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

		final DataReader reader = new XmlDataReader();

		final Data data;
		if (!isEmpty(cacheFile)) {
			data = reader.read(UrlUtil.toUrl(cacheFile), charset);
			LOG.debug(MSG_CACHE_FILE_IS_FILLED);
		} else {
			data = reader.read(dataUrl, charset);
			LOG.debug(MSG_CACHE_FILE_IS_EMPTY);
		}

		final CachingXmlDataStore store = new CachingXmlDataStore(data, reader, dataUrl, versionUrl, charset, cacheFile);
		store.refresh(); // update the cache file
		return store;
	}

	/**
	 * Constructs a new instance of {@code CachingXmlDataStore} with the given arguments. The file used to cache the
	 * read in <em>UAS data</em> will be called from {@link CachingXmlDataStore#findOrCreateCacheFile()}. This file may
	 * be empty or filled with previously cached data in XML format. The file must be writable otherwise an exception
	 * will be thrown.
	 * 
	 * @param dataUrl
	 *            URL to <em>UAS data</em>
	 * @param versionUrl
	 *            URL to version information about the given <em>UAS data</em>
	 * @param charset
	 *            the character set in which the data should be read
	 * @return new instance of {@link CachingXmlDataStore}
	 * @throws IllegalArgumentException
	 *             if one of the given arguments is {@code null}
	 * @throws IllegalArgumentException
	 *             if the given cache file can not be read
	 */
	public static CachingXmlDataStore createCachingXmlDataStore(final URL dataUrl, final URL versionUrl, final Charset charset) {
		return createCachingXmlDataStore(findOrCreateCacheFile(), dataUrl, versionUrl, charset);
	}

	/**
	 * Gets the cache file for <em>UAS data</em> in the default temporary-file directory. If no cache file exists, a new
	 * empty file in the default temporary-file directory will be created, using the default prefix and suffix to
	 * generate its name.
	 * 
	 * @return file to cache read in <em>UAS data</em>
	 * @throws IllegalStateException
	 *             if the cache file can not be created
	 */
	public static final File findOrCreateCacheFile() {
		final File file = new File(CACHE_DIR, PREFIX + SUFFIX);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (final IOException e) {
				throw new IllegalStateException("Can not create a cache file.");
			}
		}
		return file;
	}

	/**
	 * Checks if the given file is empty.
	 * 
	 * @param file
	 *            the file that could be empty
	 * @return {@code true} when the file is accessible and empty otherwise {@code false}
	 */
	private static boolean isEmpty(final File file) {
		boolean empty = false;
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(file));
			empty = reader.readLine() == null;
		} catch (final FileNotFoundException e) {
			throw new IllegalStateException("file does not exist");
		} catch (final IOException e) {
			throw new IllegalStateException("file can not be read");
		}
		return empty;
	}

	/**
	 * Reads the content from the given {@link URL} and saves it to the passed file.
	 * 
	 * @param url
	 *            URL to <em>UAS data</em>
	 * @param file
	 *            file in which the entire contents from the given URL can be saved
	 * @param charset
	 *            the character set in which the data should be read
	 * @throws IllegalArgumentException
	 *             if any of the passed arguments is {@code null}
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected synchronized static void readAndSave(final URL url, final File file, final Charset charset) throws IOException {
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' must not be null.");
		}
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' must not be null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException("Argument 'charset' must not be null.");
		}

		if (!url.equals(file.toURI().toURL())) {
			final BufferedReader reader = null;
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(file);
				final String data = UrlUtil.read(url, charset);
				outputStream.write(data.getBytes());
			} finally {
				if (reader != null) {
					reader.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			}
		} else {
			LOG.debug(MSG_SAME_RESOURCES);
		}
	}

	/**
	 * File to cache read in <em>UAS data</em>
	 */
	private final File cacheFile;

	/**
	 * Constructs an {@code CachingXmlDataStore} with the given arguments.
	 * 
	 * @param data
	 *            first <em>UAS data</em> which will be available in the store
	 * @param reader
	 *            data reader to read the given {@code dataUrl}
	 * @param dataUrl
	 *            URL to <em>UAS data</em>
	 * @param versionUrl
	 *            URL to version information about the given <em>UAS data</em>
	 * @param charset
	 *            the character set in which the data should be read
	 * @param cacheFile
	 *            file with cached <em>UAS data</em> in XML format or an empty file
	 * @throws IllegalArgumentException
	 *             if one of the given arguments is {@code null}
	 */
	private CachingXmlDataStore(final Data data, final DataReader reader, final URL dataUrl, final URL versionUrl, final Charset charset,
			final File cacheFile) {
		super(data, reader, dataUrl, versionUrl, charset);
		this.cacheFile = cacheFile;
	}

	@Override
	public synchronized void refresh() {
		try {
			readAndSave(getDataUrl(), cacheFile, getCharset());
		} catch (final IOException e) {
			LOG.warn("Can not read and save UAS data: " + e.getLocalizedMessage(), e);
		}
		setData(getDataReader().read(getDataUrl(), getCharset()));
	}

}
