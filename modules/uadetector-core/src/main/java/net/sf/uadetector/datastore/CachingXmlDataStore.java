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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.FileUtil;
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
public final class CachingXmlDataStore extends AbstractRefreshableDataStore {

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
	 * The prefix string to be used in generating the cache file's name; must be at least three characters long
	 */
	private static final String PREFIX = "uas";

	/**
	 * The suffix string to be used in generating the cache file's name; may be {@code null}, in which case the suffix "
	 * {@code .tmp}" will be used
	 */
	private static final String SUFFIX = ".xml";

	/**
	 * Constructs a new instance of {@code CachingXmlDataStore} with the given arguments. The given {@code cacheFile}
	 * can be empty or filled with previously cached data in XML format. The file must be writable otherwise an
	 * exception will be thrown.
	 * 
	 * @param fallback
	 *            <em>UAS data</em> as fallback in case the data on the specified resource can not be read correctly
	 * @return new instance of {@link CachingXmlDataStore}
	 * @throws IllegalArgumentException
	 *             if one of the given arguments is {@code null}
	 * @throws IllegalArgumentException
	 *             if the given cache file can not be read
	 * @throws IllegalStateException
	 *             if no URL can be resolved to the given given file
	 */
	public static CachingXmlDataStore createCachingXmlDataStore(final DataStore fallback) {
		return createCachingXmlDataStore(findOrCreateCacheFile(), fallback);
	}

	/**
	 * Constructs a new instance of {@code CachingXmlDataStore} with the given arguments. The given {@code cacheFile}
	 * can be empty or filled with previously cached data in XML format. The file must be writable otherwise an
	 * exception will be thrown.
	 * 
	 * @param cacheFile
	 *            file with cached <em>UAS data</em> in XML format or empty file
	 * @param fallback
	 *            <em>UAS data</em> as fallback in case the data on the specified resource can not be read correctly
	 * @return new instance of {@link CachingXmlDataStore}
	 * @throws IllegalArgumentException
	 *             if one of the given arguments is {@code null}
	 * @throws IllegalArgumentException
	 *             if the given cache file can not be read
	 * @throws IllegalStateException
	 *             if no URL can be resolved to the given given file
	 */
	public static CachingXmlDataStore createCachingXmlDataStore(final File cacheFile, final DataStore fallback) {
		return createCachingXmlDataStore(cacheFile, UrlUtil.build(DEFAULT_DATA_URL), UrlUtil.build(DEFAULT_VERSION_URL), DEFAULT_CHARSET,
				fallback);
	}

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
	 * @param fallback
	 *            <em>UAS data</em> as fallback in case the data on the specified resource can not be read correctly
	 * @return new instance of {@link CachingXmlDataStore}
	 * @throws IllegalArgumentException
	 *             if one of the given arguments is {@code null}
	 * @throws IllegalArgumentException
	 *             if the given cache file can not be read
	 * @throws IllegalStateException
	 *             if no URL can be resolved to the given given file
	 */
	public static CachingXmlDataStore createCachingXmlDataStore(final File cacheFile, final URL dataUrl, final URL versionUrl,
			final Charset charset, final DataStore fallback) {
		if (cacheFile == null) {
			throw new IllegalArgumentException("Argument 'cacheFile' must not be null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException("Argument 'charset' must not be null.");
		}
		if (dataUrl == null) {
			throw new IllegalArgumentException("Argument 'dataUrl' must not be null.");
		}
		if (fallback == null) {
			throw new IllegalArgumentException("Argument 'fallback' must not be null.");
		}
		if (versionUrl == null) {
			throw new IllegalArgumentException("Argument 'versionUrl' must not be null.");
		}

		final DataReader reader = new XmlDataReader();

		final Data data;
		if (!isEmpty(cacheFile, charset)) {
			data = reader.read(UrlUtil.toUrl(cacheFile), charset);
			LOG.debug(MSG_CACHE_FILE_IS_FILLED);
		} else {
			data = fallback.getData();
			LOG.debug(MSG_CACHE_FILE_IS_EMPTY);
		}

		final CachingXmlDataStore store = new CachingXmlDataStore(data, reader, dataUrl, versionUrl, charset, cacheFile, fallback);
		// update the cache file (non-blocking in background)
		//store.refresh();
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
	 * @param fallback
	 *            <em>UAS data</em> as fallback in case the data on the specified resource can not be read correctly
	 * @return new instance of {@link CachingXmlDataStore}
	 * @throws IllegalArgumentException
	 *             if one of the given arguments is {@code null}
	 * @throws IllegalArgumentException
	 *             if the given cache file can not be read
	 */
	public static CachingXmlDataStore createCachingXmlDataStore(final URL dataUrl, final URL versionUrl, final Charset charset,
			final DataStore fallback) {
		return createCachingXmlDataStore(findOrCreateCacheFile(), dataUrl, versionUrl, charset, fallback);
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
	public static File findOrCreateCacheFile() {
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
	 * @throws IllegalStateException
	 *             if an I/O error occurs
	 */
	private static boolean isEmpty(final File file, final Charset charset) {
		try {
			return FileUtil.isEmpty(file, charset);
		} catch (final IOException e) {
			throw new IllegalStateException("The given file could not be read.");
		}
	}

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
			final File cacheFile, final DataStore fallback) {
		super(reader, dataUrl, versionUrl, charset, fallback);
		setUpdateOperation(new CachingUpdateOperationTask(this, cacheFile));
	}

}
