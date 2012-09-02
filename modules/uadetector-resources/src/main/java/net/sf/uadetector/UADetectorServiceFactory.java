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

import java.net.URL;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.datastore.AbstractDataStore;
import net.sf.uadetector.datastore.OnlineXmlDataStore;
import net.sf.uadetector.parser.UpdatingUserAgentStringParserImpl;
import net.sf.uadetector.parser.UserAgentStringParserImpl;

/**
 * Service factory to get preconfigured instances of {@code UserAgentStringParser} implementations.
 * 
 * @author André Rouél
 */
public final class UADetectorServiceFactory {

	/**
	 * Holder to load the parser only when it's needed.
	 */
	private static final class OfflineUserAgentStringParserHolder {
		private static UserAgentStringParser INSTANCE = new UserAgentStringParserImpl<ResourceModuleXmlDataStore>(
				new ResourceModuleXmlDataStore());
	}

	/**
	 * Holder to load the parser only when it's needed.
	 */
	private static final class OnlineUserAgentStringParserHolder {
		private static UserAgentStringParser INSTANCE = new UpdatingUserAgentStringParserImpl(new OnlineXmlDataStore());
	}

	/**
	 * A simple implementation to store <em>UAS data</em> delivered in this module (called <em>uadetector-resource</em>)
	 * only in the heap space.
	 * 
	 * @author André Rouél
	 */
	public static final class ResourceModuleXmlDataStore extends AbstractDataStore {

		/**
		 * The default data reader to read in <em>UAS data</em> in XML format
		 */
		private static final DataReader DEFAULT_DATA_READER = new XmlDataReader();

		/**
		 * Path where the UAS data file is stored for the {@code ClassLoader}
		 */
		private static final String PATH = "net/sf/uadetector/resources";

		/**
		 * {@link URL} to the UAS data delivered in this module
		 */
		public static final URL UAS_DATA = ResourceModuleXmlDataStore.class.getClassLoader().getResource(PATH + "/uas.xml");

		/**
		 * {@link URL} to the version information of the delivered UAS data in this module
		 */
		public static final URL UAS_VERSION = ResourceModuleXmlDataStore.class.getClassLoader().getResource(PATH + "/uas.version");

		/**
		 * Constructs an {@code ResourceModuleXmlDataStore} by reading <em>UAS data</em> by the specified URL
		 * {@link UADetectorServiceFactory#UAS_DATA} (in XML format).
		 */
		public ResourceModuleXmlDataStore() {
			super(DEFAULT_DATA_READER, UAS_DATA, UAS_VERSION, DEFAULT_CHARSET);
		}

	}

	/**
	 * Gets always the same implementation instance of the interface {@code UserAgentStringParser}. This instance has an
	 * update function so that it checks at regular intervals for new versions of the <em>UAS data</em> (also known as
	 * database). When a newer database is available, it is automatically loaded and updated.<br>
	 * <br>
	 * At initialization time the instance will be loaded with the <em>UAS data</em> of this module (the shipped one
	 * within the <em>uadetector-resources</em> JAR). The initialization is started only when this method is called the
	 * first time.<br>
	 * <br>
	 * The static class definition {@link UpdatingUserAgentStringParserHolder} within this factory class is <em>not</em>
	 * initialized until the JVM determines that {@link UpdatingUserAgentStringParserHolder} must be executed. The static
	 * class {@code UpdatingUserAgentStringParserHolder} is only executed when the static method
	 * {@code getOnlineUserAgentStringParser} is invoked on the class {@code UADetectorServiceFactory}, and the first
	 * time this happens the JVM will load and initialize the {@code UpdatingUserAgentStringParserHolder} class.<br>
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

	private UADetectorServiceFactory() {
		// This class is not intended to create objects from it.
	}

}
