package net.sf.uadetector.datastore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.exception.CanNotOpenStreamException;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.FileUtil;
import net.sf.uadetector.internal.util.UrlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class UpdateOperationWithCacheFileTask extends AbstractUpdateOperation {

	/**
	 * Corresponding default logger of this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UpdateOperationWithCacheFileTask.class);

	/**
	 * Message for the log when issues occur during reading of or writing to the cache file.
	 */
	private static final String MSG_CACHE_FILE_ISSUES = "Issues occured during reading of or writing to the cache file: %s";

	/**
	 * Message for the log if the passed resources are the same and an update makes no sense
	 */
	private static final String MSG_SAME_RESOURCES = "The passed URL and file resources are the same. An update was not performed.";

	/**
	 * Creates a temporary file near the passed file. The name of the given one will be used and the suffix ".temp" will
	 * be added.
	 * 
	 * @param file
	 *            file in which the entire contents from the given URL can be saved
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected static File createTemporaryFile(final File file) {// throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' must not be null.");
		}

		final File tempFile = new File(file.getParent(), file.getName() + ".temp");

		// remove orphaned temporary file
		deleteFile(tempFile);

		return tempFile;
	}

	/**
	 * Removes the given file.
	 * 
	 * @param file
	 *            an existing file
	 * @throws IllegalStateException
	 *             if the file can not be deleted
	 */
	protected static void deleteFile(final File file) {
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' must not be null.");
		}

		if (file.exists() && !file.delete()) {
			throw new IllegalStateException("Passed file can not be removed: " + file.getAbsolutePath());
		}
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
	protected static void readAndSave(final URL url, final File file, final Charset charset) throws IOException {
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' must not be null.");
		}
		if (file == null) {
			throw new IllegalArgumentException("Argument 'file' must not be null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException("Argument 'charset' must not be null.");
		}

		final boolean isEqual = url.toExternalForm().equals(UrlUtil.toUrl(file).toExternalForm());
		if (!isEqual) {
			FileOutputStream outputStream = null;
			try {
				final File tempFile = createTemporaryFile(file);

				// write data to temporary file
				outputStream = new FileOutputStream(tempFile);
				final String data = UrlUtil.read(url, charset);
				outputStream.write(data.getBytes(charset));

				// delete the original file
				file.delete();

				// rename the new file to the original one
				renameFile(tempFile, file);

			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (final IOException e) {
						LOG.warn("The output stream could not be closed.");
					}
				}
			}
		} else {
			LOG.debug(MSG_SAME_RESOURCES);
		}
	}

	/**
	 * Removes the given file.
	 * 
	 * @param from
	 *            an existing file
	 * @param to
	 *            a new file
	 * @throws IllegalStateException
	 *             if the file can not be renamed
	 */
	protected static void renameFile(final File from, final File to) {
		if (from == null) {
			throw new IllegalArgumentException("Argument 'from' must not be null.");
		}
		if (!from.exists()) {
			throw new IllegalArgumentException("Argument 'from' must not be an existing file.");
		}
		if (to == null) {
			throw new IllegalArgumentException("Argument 'to' must not be null.");
		}

		if (!from.renameTo(to)) {
			throw new IllegalStateException("Renaming file from '" + from.getAbsolutePath() + "' to '" + to.getAbsolutePath() + "' failed.");
		}
	}

	/**
	 * File to cache read in <em>UAS data</em>
	 */
	private final File cacheFile;

	/**
	 * The data store for instances that implements {@link net.sf.uadetector.internal.data.Data}
	 */
	private final AbstractRefreshableDataStore store;

	public UpdateOperationWithCacheFileTask(final AbstractRefreshableDataStore dataStore, final File cacheFile) {
		super(dataStore);
		if (dataStore == null) {
			throw new IllegalArgumentException("Argument 'dataStore' must not be null.");
		}
		if (cacheFile == null) {
			throw new IllegalArgumentException("Argument 'cacheFile' must not be null.");
		}
		store = dataStore;
		this.cacheFile = cacheFile;
	}

	@Override
	public void call() {
		readDataIfNewerAvailable();
	}

	private boolean isCacheFileEmpty() {
		return isEmpty(cacheFile, store.getCharset());
	}

	private void readDataIfNewerAvailable() {
		try {
			if (isUpdateAvailable() || isCacheFileEmpty()) {
				readAndSave(store.getDataUrl(), cacheFile, store.getCharset());
				store.setData(store.getDataReader().read(cacheFile.toURI().toURL(), store.getCharset()));
			}
		} catch (final CanNotOpenStreamException e) {
			LOG.warn(String.format(RefreshableDataStore.MSG_URL_NOT_READABLE, e.getLocalizedMessage()));
			readFallbackData();
		} catch (final IllegalArgumentException e) {
			LOG.warn(RefreshableDataStore.MSG_FAULTY_CONTENT + " " + e.getLocalizedMessage());
			readFallbackData();
		} catch (final RuntimeException e) {
			LOG.warn(RefreshableDataStore.MSG_FAULTY_CONTENT, e);
			readFallbackData();
		} catch (final IOException e) {
			LOG.warn(String.format(MSG_CACHE_FILE_ISSUES, e.getLocalizedMessage()), e);
			readFallbackData();
		}
	}

	private void readFallbackData() {
		LOG.info("Reading fallback data...");
		try {
			if (isCacheFileEmpty()) {
				readAndSave(store.getFallback().getDataUrl(), cacheFile, store.getFallback().getCharset());
				final Data data = store.getDataReader().read(cacheFile.toURI().toURL(), store.getCharset());
				if (data.getVersion().compareTo(store.getData().getVersion()) > 0) {
					store.setData(data);
				}
			}
		} catch (final CanNotOpenStreamException e) {
			LOG.warn(String.format(RefreshableDataStore.MSG_URL_NOT_READABLE, e.getLocalizedMessage()));
		} catch (final IllegalArgumentException e) {
			LOG.warn(RefreshableDataStore.MSG_FAULTY_CONTENT + " " + e.getLocalizedMessage());
		} catch (final RuntimeException e) {
			LOG.warn(RefreshableDataStore.MSG_FAULTY_CONTENT, e);
		} catch (final IOException e) {
			LOG.warn(String.format(MSG_CACHE_FILE_ISSUES, e.getLocalizedMessage()), e);
		}
	}

}
