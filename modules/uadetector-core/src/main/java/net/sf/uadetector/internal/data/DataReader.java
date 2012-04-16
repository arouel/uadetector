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
package net.sf.uadetector.internal.data;

import java.io.InputStream;
import java.net.URL;

/**
 * Interface for data readers that reads User-Agent informations for implementations of {@code UserAgentStringParser}.
 * 
 * @author André Rouél
 */
public interface DataReader {

	/**
	 * Reads the data of an {@code InputStream}.
	 * 
	 * @param inputStream
	 *            {@code InputStream} with User-Agent informations
	 * @return read User-Agent data as {@code Data} instance
	 */
	Data read(final InputStream inputStream);

	/**
	 * Reads the data by an {@code URL}.
	 * 
	 * @param url
	 *            {@code URL} to User-Agent informations
	 * @return read User-Agent data as {@code Data} instance
	 */
	Data read(final URL url);

}
