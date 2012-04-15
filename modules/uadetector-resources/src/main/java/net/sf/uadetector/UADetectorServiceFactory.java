/*******************************************************************************
 * Copyright 2011 André Rouél
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

import net.sf.uadetector.internal.parser.OnlineUserAgentStringParserImpl;
import net.sf.uadetector.internal.parser.UserAgentStringParserImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service factory to create instance of {@code UserAgentStringParser} implementations.
 * 
 * @author André Rouél
 */
public final class UADetectorServiceFactory {

	private static final class OfflineUserAgentStringParserHolder {
		public static UserAgentStringParser INSTANCE;
		static {
			try {
				final InputStream stream = UADetectorServiceFactory.class.getClassLoader().getResourceAsStream(UASDATA);
				INSTANCE = new UserAgentStringParserImpl(stream);
			} catch (final Exception e) {
				LOG.error(e.getLocalizedMessage(), e);
			}

		}
	}

	private static final class OnlineUserAgentStringParserHolder {
		public static UserAgentStringParser INSTANCE;
		static {
			try {
				final InputStream stream = UADetectorServiceFactory.class.getClassLoader().getResourceAsStream(UASDATA);
				INSTANCE = new OnlineUserAgentStringParserImpl(stream);
			} catch (final MalformedURLException e) {
				LOG.warn(e.getLocalizedMessage(), e);
			} catch (final Exception e) {
				LOG.error(e.getLocalizedMessage(), e);
			}

		}
	}

	private static final Logger LOG = LoggerFactory.getLogger(UADetectorServiceFactory.class);

	private static final String UASDATA = "net/sf/uadetector/resources/uas.xml";

	public static UserAgentStringParser getUserAgentStringParser() {
		return OfflineUserAgentStringParserHolder.INSTANCE;
	}

	public static UserAgentStringParser getOnlineUserAgentStringParser() {
		return OnlineUserAgentStringParserHolder.INSTANCE;
	}

	private UADetectorServiceFactory() {
		// This class is not intended to create objects from it.
	}

}
