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
import java.net.MalformedURLException;

import net.sf.uadetector.parser.OnlineUserAgentStringParserImpl;
import net.sf.uadetector.parser.UserAgentStringParserImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service factory to get preconfigured instances of {@code UserAgentStringParser} implementations.
 * 
 * @author André Rouél
 */
public final class UADetectorServiceFactory {

	private static final class OfflineUserAgentStringParserHolder {
		private static UserAgentStringParser INSTANCE;
		static {
			try {
				INSTANCE = new UserAgentStringParserImpl(readDataIntoStore());
			} catch (final Exception e) {
				LOG.error(e.getLocalizedMessage(), e);
			}

		}
	}

	private static final class OnlineUserAgentStringParserHolder {
		private static UserAgentStringParser INSTANCE;
		static {
			try {
				INSTANCE = new OnlineUserAgentStringParserImpl(readDataIntoStore());
			} catch (final MalformedURLException e) {
				LOG.warn(e.getLocalizedMessage(), e);
			} catch (final Exception e) {
				LOG.error(e.getLocalizedMessage(), e);
			}

		}
	}

	/**
	 * Corresponding default logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UADetectorServiceFactory.class);

	/**
	 * Path where the UAS file is stored for the {@code ClassLoader}
	 */
	private static final String UASDATA = "net/sf/uadetector/resources/uas.xml";

	/**
	 * Gets always the same implementation instance of the interface {@code UserAgentStringParser}. This instance has an
	 * update function so that it checks at regular intervals for new versions of the <em>UAS data</em> (also known as
	 * database). When a newer database is available, it is automatically loaded and updated.<br>
	 * <br>
	 * At initialization time the instance will be loaded with the <em>UAS data</em> of this module (the shipped one
	 * within the <em>uadetector-resources</em> JAR). The initialization is started only when this method is called the
	 * first time.<br>
	 * <br>
	 * The static class definition {@link OnlineUserAgentStringParserHolder} within this factory class is <em>not</em>
	 * initialized until the JVM determines that {@link OnlineUserAgentStringParserHolder} must be executed. The static
	 * class {@code OnlineUserAgentStringParserHolder} is only executed when the static method
	 * {@code getOnlineUserAgentStringParser} is invoked on the class {@code UADetectorServiceFactory}, and the first
	 * time this happens the JVM will load and initialize the {@code OnlineUserAgentStringParserHolder} class.<br>
	 * <br>
	 * If during the operation the Internet connection gets lost, then this instance continues to work properly (and
	 * under correct log level settings you will get an corresponding log messages).
	 * 
	 * @return always the same implementation instance of the interface {@code UserAgentStringParser} and never
	 *         {@code null}
	 */
	public static UserAgentStringParser getOnlineUserAgentStringParser() {
		return OnlineUserAgentStringParserHolder.INSTANCE;
	}

	/**
	 * Gets always the same implementation instance of the interface {@code UserAgentStringParser}. This instance works
	 * offline by using the <em>UAS data</em> (also known as database) of this module. The database is loaded once
	 * during initialization. The initialization is started only when this method is called the first time.<br>
	 * <br>
	 * The static class definition {@link OfflineUserAgentStringParserHolder} within this factory class is <em>not</em>
	 * initialized until the JVM determines that {@link OfflineUserAgentStringParserHolder} must be executed. The static
	 * class {@code OfflineUserAgentStringParserHolder} is only executed when the static method
	 * {@code getUserAgentStringParser} is invoked on the class {@code UADetectorServiceFactory}, and the first time
	 * this happens the JVM will load and initialize the {@code OfflineUserAgentStringParserHolder} class.
	 * 
	 * @return always the same implementation instance of the interface {@code UserAgentStringParser} and never
	 *         {@code null}
	 */
	public static UserAgentStringParser getUserAgentStringParser() {
		return OfflineUserAgentStringParserHolder.INSTANCE;
	}

	private static final DataStore readDataIntoStore() {
		final InputStream stream = UADetectorServiceFactory.class.getClassLoader().getResourceAsStream(UASDATA);
		return new SimpleDataStore(stream);
	}

	private UADetectorServiceFactory() {
		// This class is not intended to create objects from it.
	}

}
