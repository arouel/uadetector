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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import net.sf.uadetector.internal.data.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This parser checks by every {@code read} call once a day if newer data are remotely available. When newer data are
 * available, they are downloaded, read and replaced by the current one.
 * 
 * @author André Rouél
 */
public final class OnlineUserAgentStringParserImpl extends UserAgentStringParserImpl {

	/**
	 * Character set to read remote data
	 */
	private static final String CHARSET = "UTF-8";

	/**
	 * Default configuration properties
	 */
	private static final String CONFIG_FILE = "uadetector/config.properties";

	/**
	 * Key for the {@code URL} of UAS data in the configuration properties
	 */
	private static final String DATA_URL_KEY = "data.url";

	/**
	 * The default interval to check for updates is once per day
	 */
	private static final long DEFAULT_UPDATE_INTERVAL = 1000 * 60 * 60 * 24; // 1 day

	/**
	 * Default log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OnlineUserAgentStringParserImpl.class);

	/**
	 * Message for the log when an online update check is not necessary because the time interval isn't reached.
	 */
	private static final String MSG_NO_CHECK_NECESSARY = "There is no check necessary because the update interval has not expired.";

	/**
	 * Key for the {@code URL} of UAS version information in the configuration properties
	 */
	private static final String VERSION_URL_KEY = "version.url";

	/**
	 * Reads all configuration properties from <code>config.properties</code> file and returns a {@code Properties}
	 * object.
	 * 
	 * @return Configuration properties
	 * @throws IOException
	 */
	private static Properties readConfigProperties() {
		final Properties properties = new Properties();
		final InputStream stream = OnlineUserAgentStringParserImpl.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
		try {
			properties.load(stream);
		} catch (final IOException e) {
			LOG.warn(e.getLocalizedMessage(), e);
		} finally {
			try {
				stream.close();
			} catch (final IOException e) {
				LOG.warn(e.getLocalizedMessage(), e);
			}
		}
		return properties;
	}

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
	public static String retrieveRemoteVersion(final URL url) throws IOException {
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
	 * The {@code URL} to request the latest UAS data
	 */
	private final URL dataUrl;

	/**
	 * Time of last update check in milliseconds
	 */
	private long lastUpdateCheck;

	/**
	 * Interval to check for updates in milliseconds
	 */
	private long updateInterval = DEFAULT_UPDATE_INTERVAL;

	/**
	 * The {@code URL} to request the latest version information of UAS data
	 */
	private final URL versionUrl;

	/**
	 * Constructs an instance of {@code OnlineUserAgentStringParser}. During construction new UAS data will be queried
	 * online by predefined {@code URL}s.
	 * 
	 * @param stream
	 *            {@code InputStream} with reference data used in fallback case
	 * @throws MalformedURLException
	 *             if on of the read URLs from the default configuration properties is invalid
	 * @throws IllegalArgumentException
	 *             if the given argument {@code stream} is {@code null}
	 */
	public OnlineUserAgentStringParserImpl(final InputStream stream) throws MalformedURLException {
		this(stream, readConfigProperties());
	}

	/**
	 * Constructs an instance of {@code OnlineUserAgentStringParser}. During construction new UAS data will be queried
	 * online by custom {@code URL}s.
	 * 
	 * @param stream
	 *            {@code InputStream} with reference data used in fallback case
	 * @throws IllegalArgumentException
	 *             if the given argument {@code stream} is {@code null}
	 * @throws MalformedURLException
	 *             if on of the read URLs from the default configuration properties is invalid
	 * @throws NullPointerException
	 *             if the given argument {@code configuration} is {@code null}
	 */
	public OnlineUserAgentStringParserImpl(final InputStream stream, final Properties configuration) throws MalformedURLException {
		this(stream, new URL(configuration.getProperty(DATA_URL_KEY)), new URL(configuration.getProperty(VERSION_URL_KEY)));
	}

	/**
	 * Constructs an instance of {@code OnlineUserAgentStringParser}. During construction new UAS data will be queried
	 * online by the given {@code URL}s.
	 * 
	 * @param stream
	 *            {@code InputStream} with reference data used in fallback case
	 * @param dataUrl
	 *            {@code URL} to request the latest UAS data
	 * @param versionUrl
	 *            {@code URL} to request the latest version information of UAS data
	 * @throws IllegalArgumentException
	 *             if one of the given arguments is {@code null}
	 */
	public OnlineUserAgentStringParserImpl(final InputStream stream, final URL dataUrl, final URL versionUrl) {
		super(stream);

		if (dataUrl == null) {
			throw new IllegalArgumentException("Argument 'dataUrl' must not be null.");
		}
		if (versionUrl == null) {
			throw new IllegalArgumentException("Argument 'versionUrl' must not be null.");
		}

		this.dataUrl = dataUrl;
		this.versionUrl = versionUrl;

		// query newer UAS data
		retrieveRemoteData(dataUrl);
	}

	@Override
	protected Data getData() {
		retrieveRemoteData(dataUrl);
		return super.getData();
	}

	/**
	 * Gets the time of the last update check in milliseconds.
	 * 
	 * @return time of the last update check in milliseconds
	 */
	public long getLastUpdateCheck() {
		return lastUpdateCheck;
	}

	/**
	 * Gets the current update interval in milliseconds.
	 * 
	 * @return current update interval in milliseconds
	 */
	public long getUpdateInterval() {
		return updateInterval;
	}

	/**
	 * Fetches the current version information over HTTP and compares it with the last version of the most recently
	 * imported data.
	 * 
	 * @return {@code true} if an update exists, otherwise {@code false}
	 */
	private boolean isUpdateAvailable() {
		boolean result = false;
		if (lastUpdateCheck == 0 || lastUpdateCheck < System.currentTimeMillis() - getUpdateInterval()) {
			try {
				final String version = retrieveRemoteVersion(versionUrl);
				if (version.compareTo(getCurrentVersion()) > 0) {
					LOG.debug("An update is available. Current version is '" + getCurrentVersion() + "' and remote version is '" + version
							+ "'.");
					result = true;
				} else {
					LOG.debug("No update available. Current version is '" + getCurrentVersion() + "'.");
				}
			} catch (final IOException e) {
				LOG.info(e.getLocalizedMessage());
			}
			lastUpdateCheck = System.currentTimeMillis();
		} else {
			LOG.debug(MSG_NO_CHECK_NECESSARY);
		}
		return result;
	}

	/**
	 * Loads the UAS data online.
	 */
	private void retrieveRemoteData(final URL url) {
		if (isUpdateAvailable()) {
			LOG.debug("Reading remote data...");
			setData(getDataReader().read(url));
		}
	}

	/**
	 * Sets a new update interval in milliseconds.
	 * 
	 * @param updateInterval
	 *            update interval in milliseconds
	 */
	public void setUpdateInterval(final long updateInterval) {
		if (updateInterval < 0l) {
			throw new IllegalArgumentException("Update interval must be not less than 0.");
		}
		this.updateInterval = updateInterval;
	}

}
