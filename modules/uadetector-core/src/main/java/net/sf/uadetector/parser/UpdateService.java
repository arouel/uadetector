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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;

import net.sf.uadetector.DataStore;
import net.sf.uadetector.internal.data.DataReader;
import net.sf.uadetector.internal.data.XmlDataReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service to update the UAS data in an {@link DataStore}.
 * 
 * @author André Rouél
 */
final class UpdateService implements Updater, Runnable {

	/**
	 * Defines an empty version string
	 */
	private static final String EMPTY_VERSION = "";

	/**
	 * Corresponding default logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UpdateService.class);

	/**
	 * Message for the log when an online update check is not possible.<br>
	 * <br>
	 * <b>Message sample</b>: Can not check for an updated version. Are you sure you have an established internet
	 * connection?
	 */
	private static final String MSG_NO_UPDATE_CHECK_POSSIBLE = "Can not check for an updated version. Are you sure you have an established internet connection?";

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
	private static String retrieveRemoteVersion(final URL url) throws IOException {
		final InputStream stream = url.openStream();
		final InputStreamReader reader = new InputStreamReader(stream, CHARSET);
		final LineNumberReader lnr = new LineNumberReader(reader);
		final String line = lnr.readLine();
		lnr.close();
		reader.close();
		stream.close();
		return line;
	}

	/**
	 * Data reader to read a UAS file or stream
	 */
	private final DataReader dataReader;

	/**
	 * The {@code URL} to request the latest UAS data
	 */
	private final URL dataUrl;

	/**
	 * Time of last update check in milliseconds
	 */
	private long lastUpdateCheck = 0;

	/**
	 * The data store for instances that implements {@link net.sf.uadetector.internal.data.Data}
	 */
	private final DataStore store;

	/**
	 * The {@code URL} to request the latest version information of UAS data
	 */
	private final URL versionUrl;

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
	 * Message for the log when no update is available.<br>
	 * <br>
	 * <b>Message sample</b>: No update available. Current version is '<em>20120301-01</em>'.<br>
	 * <b>First placeholder</b>: current version
	 */
	private static final String MSG_NO_UPDATE_AVAILABLE = "No update available. Current version is '%s'.";

	public UpdateService(final DataStore store, final URL dataUrl, final URL versionUrl) {
		if (store == null) {
			throw new IllegalArgumentException("Argument 'store' must not be null.");
		}
		if (dataUrl == null) {
			throw new IllegalArgumentException("Argument 'dataUrl' must not be null.");
		}
		if (versionUrl == null) {
			throw new IllegalArgumentException("Argument 'versionUrl' must not be null.");
		}

		this.store = store;
		this.dataUrl = dataUrl;
		this.versionUrl = versionUrl;

		dataReader = new XmlDataReader();
	}

	@Override
	public void call() {
		if (isUpdateAvailable()) {
			LOG.debug("Reading remote data...");
			this.store.setData(dataReader.read(dataUrl));
		}
	}

	/**
	 * Shortcut to get the current version of the UAS data in the {@link DataStore}.
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
	private boolean isUpdateAvailable() {
		boolean result = false;
		String version = EMPTY_VERSION;
		try {
			version = retrieveRemoteVersion(versionUrl);
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
		call();
	}

}
