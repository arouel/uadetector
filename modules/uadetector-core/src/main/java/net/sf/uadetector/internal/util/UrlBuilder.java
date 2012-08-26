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
package net.sf.uadetector.internal.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is used to create a new instance of {@link URL} from a string representation of an {@code URL}. It is
 * intended to encapsulate the checked exception {@link MalformedURLException} during the construction of an URL.
 * 
 * @author André Rouél
 */
public class UrlBuilder {

	/**
	 * Creates an {@code URL} instance from the given {@code String} representation.<br>
	 * <br>
	 * This method tunnels a {@link MalformedURLException} by an {@link IllegalArgumentException}.
	 * 
	 * @param url
	 *            {@code String} representation of an {@code URL}
	 * @return new {@code URL} instance
	 * @throws IllegalArgumentException
	 *             if the string representation of the given URL is invalid and a {@link MalformedURLException} occurs
	 */
	public static final URL build(final String url) {
		URL ret = null;
		try {
			ret = new URL(url);
		} catch (final MalformedURLException e) {
			throw new IllegalArgumentException("The given string is not a valid URL: " + url, e);
		}
		return ret;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private UrlBuilder() {
		// This class is not intended to create objects from it.
	}

}
