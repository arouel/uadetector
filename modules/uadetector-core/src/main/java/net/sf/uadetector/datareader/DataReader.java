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
package net.sf.uadetector.datareader;

import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.internal.data.Data;

/**
 * Interface for data readers that reads User-Agent informations for implementations of {@code UserAgentStringParser}.
 * 
 * @author André Rouél
 */
public interface DataReader {

	/**
	 * Reads <em>UAS data</em> from the given {@code URL}.
	 * 
	 * @param url
	 *            the URL where the <em>UAS data</em> can be retrieved
	 * @param charset
	 *            the character set in which the data should be read
	 * @return read in <em>UAS data</em> as {@code Data} instance
	 */
	Data read(final URL url, final Charset charset);

}
