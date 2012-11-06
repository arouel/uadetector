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
package net.sf.uadetector.service;

import java.net.URL;

import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.datastore.AbstractDataStore;
import net.sf.uadetector.datastore.CachingXmlDataStore;
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
	private static final class CachingAndUpdatingParserHolder {
		private static final UserAgentStringParser INSTANCE = new UpdatingUserAgentStringParserImpl(
				CachingXmlDataStore.createCachingXmlDataStore(RESOURCE_MODULE.getData()));
	}

	/**
	 * Holder to load the parser only when it's needed.
	 */
	private static final class OnlineUpdatingParserHolder {
		private static final UserAgentStringParser INSTANCE = new UpdatingUserAgentStringParserImpl(new OnlineXmlDataStore(
				RESOURCE_MODULE.getData()));
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
	 * Data store filled with the <em>UAS data</em> that are shipped with this module (JAR)
	 */
	private static final ResourceModuleXmlDataStore RESOURCE_MODULE = new ResourceModuleXmlDataStore();

	/**
	 * {@link UserAgentStringParser} filled with the <em>UAS data</em> that are shipped with this module (JAR)
	 */
	private static final UserAgentStringParser RESOURCE_MODULE_PARSER = new UserAgentStringParserImpl<ResourceModuleXmlDataStore>(
			RESOURCE_MODULE);

	/**
	 * Returns an implementation of {@link UserAgentStringParser} which checks at regular intervals for new versions of
	 * <em>UAS data</em> (also known as database). When newer data available, it automatically loads and updates it.
	 * Additionally the loaded data are stored in a cache file.
	 * 
	 * <p>
	 * At initialization time the returned parser will be loaded with the <em>UAS data</em> of the cache file. If the
	 * cache file doesn't exist or is empty the data of this module will be loaded. The initialization is started only
	 * when this method is called the first time.
	 * 
	 * <p>
	 * The static class definition {@link CachingAndUpdatingParserHolder} within this factory class is <em>not</em>
	 * initialized until the JVM determines that {@code CachingAndUpdatingParserHolder} must be executed. The static
	 * class {@code CachingAndUpdatingParserHolder} is only executed when the static method
	 * {@code getOnlineUserAgentStringParser} is invoked on the class {@code UADetectorServiceFactory}, and the first
	 * time this happens the JVM will load and initialize the {@code CachingAndUpdatingParserHolder} class.
	 * 
	 * <p>
	 * If during the operation the Internet connection gets lost, then this instance continues to work properly (and
	 * under correct log level settings you will get an corresponding log messages).
	 * 
	 * @return an user agent string parser with updating service
	 */
	public static UserAgentStringParser getCachingAndUpdatingParserHolder() {
		return CachingAndUpdatingParserHolder.INSTANCE;
	}

	/**
	 * Returns an implementation of {@link UserAgentStringParser} which checks at regular intervals for new versions of
	 * <em>UAS data</em> (also known as database). When newer data available, it automatically loads and updates it.
	 * 
	 * <p>
	 * At initialization time the returned parser will be loaded with the <em>UAS data</em> of this module (the shipped
	 * one within the <em>uadetector-resources</em> JAR) and tries to update it. The initialization is started only when
	 * this method is called the first time.
	 * 
	 * <p>
	 * The static class definition {@link OnlineUpdatingParserHolder} within this factory class is <em>not</em>
	 * initialized until the JVM determines that {@code OnlineUpdatingParserHolder} must be executed. The static class
	 * {@code OnlineUpdatingParserHolder} is only executed when the static method {@code getOnlineUserAgentStringParser}
	 * is invoked on the class {@code UADetectorServiceFactory}, and the first time this happens the JVM will load and
	 * initialize the {@code OnlineUpdatingParserHolder} class.
	 * 
	 * <p>
	 * If during the operation the Internet connection gets lost, then this instance continues to work properly (and
	 * under correct log level settings you will get an corresponding log messages).
	 * 
	 * @return an user agent string parser with updating service
	 */
	public static UserAgentStringParser getOnlineUpdatingParser() {
		return OnlineUpdatingParserHolder.INSTANCE;
	}

	/**
	 * Returns an implementation of {@link UserAgentStringParser} with no updating functions. It will be loaded by using
	 * the shipped <em>UAS data</em> (also known as database) of this module. The database is loaded once during
	 * initialization. The initialization is started at class loading of this class ({@code UADetectorServiceFactory}).
	 * 
	 * @return an user agent string parser without updating service
	 */
	public static UserAgentStringParser getResourceModuleParser() {
		return RESOURCE_MODULE_PARSER;
	}

	private UADetectorServiceFactory() {
		// This class is not intended to create objects from it.
	}

}
