package net.sf.uadetector.datastore;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.uadetector.internal.util.DaemonThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractUpdateOperation implements UpdateOperation {

	/**
	 * Defines an empty version string
	 */
	private static final String EMPTY_VERSION = "";

	/**
	 * Corresponding default logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractUpdateOperation.class);

	/**
	 * Message for the log when no update is available.<br>
	 * <br>
	 * <b>Message sample</b>: No update available. Current version is '<em>20120301-01</em>'.<br>
	 * <b>First placeholder</b>: current version
	 */
	private static final String MSG_NO_UPDATE_AVAILABLE = "No update available. Current version is '%s'.";

	/**
	 * Message for the log when an online update check is not possible.<br>
	 * <br>
	 * <b>Message sample</b>: Can not check for an updated version. Are you sure you have an established internet
	 * connection?
	 */
	private static final String MSG_NO_UPDATE_CHECK_POSSIBLE = "Can not check for an updated version. Are you sure you have an established internet connection?";

	/**
	 * Message for the log when an exception occur during the update check.<br>
	 * <br>
	 * <b>Message sample</b>: Can not check for an updated version: <em>java.net.ConnectException</em>:
	 * <em>Connection refused</em><br>
	 * <b>First placeholder</b>: class name of exception<br>
	 * <b>Second placeholder</b>: exception message
	 */
	private static final String MSG_NO_UPDATE_CHECK_POSSIBLE__DEBUG = "Can not check for an updated version: %s: %s";

	/**
	 * Message for the log when an update is available.<br>
	 * <br>
	 * <b>Message sample</b>: An update is available. Current version is '<em>20120301-01</em>' and remote version is '
	 * <em>20120401-01</em>'.<br>
	 * <b>First placeholder</b>: current version<br>
	 * <b>Second placeholder</b>: new remote version
	 */
	private static final String MSG_UPDATE_AVAILABLE = "An update is available. Current version is '%s' and remote version is '%s'.";

	/**
	 * Reads the current User-Agent data version from <a
	 * href="http://user-agent-string.info">http://user-agent-string.info</a>.
	 * 
	 * @param url
	 *            a URL which the version information can be loaded
	 * @return a version string or {@code null}
	 * @throws IOException
	 *             if an I/O exception occurs
	 */
	private static String retrieveRemoteVersion(final URL url, final Charset charset) throws IOException {
		final InputStream stream = url.openStream();
		final InputStreamReader reader = new InputStreamReader(stream, charset);
		final LineNumberReader lnr = new LineNumberReader(reader);
		final String line = lnr.readLine();
		lnr.close();
		reader.close();
		stream.close();
		return line;
	}

	/**
	 * {@link ExecutorService} to run the update operation of the UAS data in background
	 */
	private final ExecutorService executorService = Executors.newSingleThreadExecutor(new DaemonThreadFactory());

	/**
	 * Time of last update check in milliseconds
	 */
	private long lastUpdateCheck = 0;

	/**
	 * The data store for instances that implements {@link net.sf.uadetector.internal.data.Data}
	 */
	private final RefreshableDataStore store;

	public AbstractUpdateOperation(final RefreshableDataStore dataStore) {
		if (dataStore == null) {
			throw new IllegalArgumentException("Argument 'dataStore' must not be null.");
		}
		store = dataStore;
	}

	/**
	 * Shortcut to get the current version of the UAS data in the {@link net.sf.uadetector.datastore.DataStore}.
	 * 
	 * @return current version of UAS data
	 */
	private String getCurrentVersion() {
		return store.getData().getVersion();
	}

	/**
	 * Gets the time of the last update check in milliseconds.
	 * 
	 * @return time of the last update check in milliseconds
	 */
	@Override
	public long getLastUpdateCheck() {
		return lastUpdateCheck;
	}

	/**
	 * Fetches the current version information over HTTP and compares it with the last version of the most recently
	 * imported data.
	 * 
	 * @return {@code true} if an update exists, otherwise {@code false}
	 */
	protected boolean isUpdateAvailable() {
		boolean result = false;
		String version = EMPTY_VERSION;
		try {
			version = retrieveRemoteVersion(store.getVersionUrl(), store.getCharset());
		} catch (final IOException e) {
			LOG.info(MSG_NO_UPDATE_CHECK_POSSIBLE);
			if (LOG.isDebugEnabled()) {
				LOG.debug(String.format(MSG_NO_UPDATE_CHECK_POSSIBLE__DEBUG, e.getClass().getName(), e.getLocalizedMessage()));
			}
		}
		if (version.compareTo(getCurrentVersion()) > 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(String.format(MSG_UPDATE_AVAILABLE, getCurrentVersion(), version));
			}
			result = true;
		} else if (LOG.isDebugEnabled()) {
			LOG.debug(String.format(MSG_NO_UPDATE_AVAILABLE, getCurrentVersion()));
		}
		lastUpdateCheck = System.currentTimeMillis();
		return result;
	}

	@Override
	public void run() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				call();
			}
		});
	}

}
