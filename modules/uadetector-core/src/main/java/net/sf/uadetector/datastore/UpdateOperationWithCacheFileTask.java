package net.sf.uadetector.datastore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
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
	 * @throws IllegalStateException
	 *             if the file can not be deleted
	 */
	protected static File createTemporaryFile(@Nonnull final File file) {
		Check.notNull(file, "file");

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
	protected static void deleteFile(@Nonnull final File file) {
		Check.notNull(file, "file");

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
	private static boolean isEmpty(@Nonnull final File file, @Nonnull final Charset charset) {
		try {
			return FileUtil.isEmpty(file, charset);
		} catch (final IOException e) {
			throw new IllegalStateException("The given file could not be read.");
		}
	}

	/**
	 * Reads the content from the given {@link URL} and saves it to the passed file.
	 * 
	 * @param file
	 *            file in which the entire contents from the given URL can be saved
	 * @param store
	 *            a data store for <em>UAS data</em>
	 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
	 *             if any of the passed arguments is {@code null}
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected static void readAndSave(@Nonnull final File file, @Nonnull final DataStore store) throws IOException {
		Check.notNull(file, "file");
		Check.notNull(store, "store");

		final URL url = store.getDataUrl();
		final Charset charset = store.getCharset();

		final boolean isEqual = url.toExternalForm().equals(UrlUtil.toUrl(file).toExternalForm());
		if (!isEqual) {

			// check if the data can be read in successfully
			final String data = UrlUtil.read(url, charset);
			if (Data.EMPTY.equals(store.getDataReader().read(data))) {
				throw new IllegalStateException("The read in content can not be transformed to an instance of 'Data'.");
			}

			FileOutputStream outputStream = null;
			try {
				final File tempFile = createTemporaryFile(file);

				// write data to temporary file
				outputStream = new FileOutputStream(tempFile);
				outputStream.write(data.getBytes(charset));

				// delete the original file
				Check.stateIsTrue(!file.exists() || file.delete(), "Cannot delete file '%s'.", file.getPath());

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
	 * Renames the given file {@code from} to the new file {@code to}.
	 * 
	 * @param from
	 *            an existing file
	 * @param to
	 *            a new file
	 * 
	 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
	 *             if one of the given arguments is {@code null}
	 * @throws net.sf.qualitycheck.exception.IllegalStateOfArgumentException
	 *             if the file can not be renamed
	 */
	protected static void renameFile(@Nonnull final File from, @Nonnull final File to) {
		Check.notNull(from, "from");
		Check.stateIsTrue(from.exists(), "Argument 'from' must not be an existing file.");
		Check.notNull(to, "to");
		Check.stateIsTrue(from.renameTo(to), "Renaming file from '%s' to '%s' failed.", from.getAbsolutePath(), to.getAbsolutePath());
	}

	/**
	 * File to cache read in <em>UAS data</em>
	 */
	private final File cacheFile;

	/**
	 * The data store for instances that implements {@link net.sf.uadetector.internal.data.Data}
	 */
	private final AbstractRefreshableDataStore store;

	public UpdateOperationWithCacheFileTask(@Nonnull final AbstractRefreshableDataStore dataStore, @Nonnull final File cacheFile) {
		super(dataStore);
		Check.notNull(dataStore, "dataStore");
		Check.notNull(cacheFile, "cacheFile");
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
				readAndSave(cacheFile, store);
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
				readAndSave(cacheFile, store.getFallback());
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
