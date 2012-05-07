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

/**
 * Defines an user agent.<br>
 * <br>
 * An user agent is a client program with which a network service can be used. The user agent is the interface to
 * representing contents and taking orders of an user. Examples of user agents are web browsers, email programs, news
 * reader and web crawlers.<br>
 * <br>
 * The implementation of this interface may be mutable or immutable. This interface only gives access to retrieve data,
 * never to change it.
 * 
 * @author André Rouél
 */
public interface ReadableUserAgent {

	/**
	 * Gets the name of the user agent.
	 * 
	 * @return name of the user agent
	 */
	@Deprecated
	String getFamily();

	/**
	 * Gets the name of the user agent.
	 * 
	 * @return name of the user agent
	 */
	String getName();

	/**
	 * Gets the operating system on which the user agent is running.
	 * 
	 * @return the running operating system
	 */
	OperatingSystem getOperatingSystem();

	/**
	 * Returns the manufacturer of the user agent.
	 * 
	 * @return the manufacturer
	 */
	String getProducer();

	/**
	 * Returns the URL to the main website of the manufacturer of the user agent.
	 * 
	 * @return URL to the website of the manufacturer
	 */
	String getProducerUrl();

	/**
	 * Returns the type of user agents, for example, mobile browser or email client.
	 * 
	 * @return the type of user agents
	 */
	String getType();

	/**
	 * Returns the URL to the product or information page of the user agent.
	 * 
	 * @return the URL to the product page
	 */
	String getUrl();

	/**
	 * Gets the version number of an user agent.
	 * 
	 * @return version number of an user agent
	 */
	VersionNumber getVersionNumber();

}
