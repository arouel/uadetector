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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.sf.uadetector.datastore.DataStore;

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
	 * Default configuration properties
	 */
	private static final String CONFIG_FILE = "uadetector/config.properties";

	/**
	 * Key for the {@code URL} of UAS data in the configuration properties
	 */
	private static final String DATA_URL_KEY = "data.url";

	/**
	 * Default log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OnlineUserAgentStringParserImpl.class);

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
	 *             if the properties file can not be loaded
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
	 * The {@code URL} to request the latest UAS data
	 */
	private final URL dataUrl;

	/**
	 * Interval to check for updates in milliseconds
	 */
	private long updateInterval = Updater.DEFAULT_UPDATE_INTERVAL;

	/**
	 * The {@code URL} to request the latest version information of UAS data
	 */
	private final URL versionUrl;

	/**
	 * Current update task of {@link OnlineUserAgentStringParserImpl#scheduler}
	 */
	private ScheduledFuture<?> currentUpdateTask;

	/**
	 * {@link ScheduledExecutorService} to schedule commands to update the UAS data in defined intervals
	 */
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	/**
	 * Current update service which will be triggered by the {@link OnlineUserAgentStringParserImpl#scheduler}
	 */
	private UpdateService updateService;

	/**
	 * Constructs an instance of {@code OnlineUserAgentStringParser}. During construction new UAS data will be queried
	 * online by predefined {@code URL}s.
	 * 
	 * @param store
	 *            {@code DataStore} with reference UAS data used in fallback case
	 * @throws MalformedURLException
	 *             if on of the read URLs from the default configuration properties is invalid
	 * @throws IllegalArgumentException
	 *             if the given argument {@code stream} is {@code null}
	 */
	public OnlineUserAgentStringParserImpl(final DataStore store) throws MalformedURLException {
		this(store, readConfigProperties());
	}

	/**
	 * Constructs an instance of {@code OnlineUserAgentStringParser}. During construction new UAS data will be queried
	 * online by custom {@code URL}s.
	 * 
	 * @param store
	 *            {@code DataStore} with reference UAS data used in fallback case
	 * @throws IllegalArgumentException
	 *             if the given argument {@code stream} is {@code null}
	 * @throws MalformedURLException
	 *             if on of the read URLs from the default configuration properties is invalid
	 * @throws NullPointerException
	 *             if the given argument {@code configuration} is {@code null}
	 */
	public OnlineUserAgentStringParserImpl(final DataStore store, final Properties configuration) throws MalformedURLException {
		this(store, new URL(configuration.getProperty(DATA_URL_KEY)), new URL(configuration.getProperty(VERSION_URL_KEY)));
	}

	/**
	 * Constructs an instance of {@code OnlineUserAgentStringParser}. During construction new UAS data will be queried
	 * online by the given {@code URL}s.
	 * 
	 * @param store
	 *            {@code DataStore} with reference UAS data used in fallback case
	 * @param dataUrl
	 *            {@code URL} to request the latest UAS data
	 * @param versionUrl
	 *            {@code URL} to request the latest version information of UAS data
	 * @throws IllegalArgumentException
	 *             if one of the given arguments is {@code null}
	 */
	public OnlineUserAgentStringParserImpl(final DataStore store, final URL dataUrl, final URL versionUrl) {
		super(store);

		if (dataUrl == null) {
			throw new IllegalArgumentException("Argument 'dataUrl' must not be null.");
		}
		if (versionUrl == null) {
			throw new IllegalArgumentException("Argument 'versionUrl' must not be null.");
		}

		this.dataUrl = dataUrl;
		this.versionUrl = versionUrl;

		// query newer UAS data
		setUpUpdateService();
		updateService.call();
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
	 * Gets the current {@link UpdateService} of this parser.
	 * 
	 * @return current update service of this parser
	 */
	public Updater getUpdater() {
		return updateService;
	}

	/**
	 * Sets a new update interval in milliseconds.<br>
	 * <br>
	 * When a new update interval is set, the old update service is removed and a new one will be set.
	 * 
	 * @param updateInterval
	 *            update interval in milliseconds
	 * @throws IllegalArgumentException
	 *             if the given value is less than 0
	 */
	public void setUpdateInterval(final long updateInterval) {
		if (updateInterval < 0l) {
			throw new IllegalArgumentException("Update interval must be not less than 0.");
		}
		this.updateInterval = updateInterval;
		setUpUpdateService();
	}

	/**
	 * Set up a new update service to get newer UAS data
	 */
	private void setUpUpdateService() {
		if (currentUpdateTask != null) {
			currentUpdateTask.cancel(false);
		}
		updateService = new UpdateService(getDataStore(), dataUrl, versionUrl);
		currentUpdateTask = scheduler.scheduleWithFixedDelay(updateService, 0, updateInterval, TimeUnit.MILLISECONDS);
	}

}
