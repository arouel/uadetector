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
package net.sf.uadetector.json.datareader;

import java.net.URL;
import java.nio.charset.Charset;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.json.internal.data.JsonConverter;
import net.sf.uadetector.json.internal.data.Option;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reader for <em>UAS data</em> in JSON representation.
 * 
 * <p>
 * This reader is safe when used concurrently by multiple threads.
 * 
 * @author André Rouél
 */
public final class JsonDataReader implements DataReader {

	/**
	 * Corresponding default logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(JsonDataReader.class);

	/**
	 * Reads the <em>UAS data</em> in JSON format via the given URL.
	 * <p>
	 * When during the reading errors occur which lead to a termination of the read operation, the information will be
	 * written to a log. The termination of the read operation will not lead to a program termination and in this case
	 * this method returns {@link Data#EMPTY}.
	 * 
	 * @param url
	 *            {@code URL} to User-Agent informations
	 * @param charset
	 *            the character set in which the data should be read
	 * @return read in content as {@code Data} instance otherwise {@link Data#EMPTY}
	 * 
	 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
	 *             if any of the given arguments is {@code null}
	 */
	@Override
	public Data read(final URL url, final Charset charset) {
		Check.notNull(url, "url");
		Check.notNull(charset, "charset");
		try {
			final String json = UrlUtil.read(url, charset);
			return read(json);
		} catch (Exception e) {
			LOG.warn(e.getLocalizedMessage(), e);
		}
		return Data.EMPTY;
	}

	/**
	 * Reads the <em>UAS data</em> in JSON format from the given string.
	 * <p>
	 * When during the reading errors occur which lead to a termination of the read operation, the information will be
	 * written to a log. The termination of the read operation will not lead to a program termination and in this case
	 * this method returns {@link Data#EMPTY}.
	 * 
	 * @param url
	 *            {@code URL} to User-Agent informations
	 * @param charset
	 *            the character set in which the data should be read
	 * @return read in content as {@code Data} instance otherwise {@link Data#EMPTY}
	 * 
	 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
	 *             if any of the given arguments is {@code null}
	 */
	@Override
	public Data read(final String json) {
		Check.notNull(json, "json");
		try {
			return JsonConverter.deserialize(json, Option.HASH_VALIDATING).getData();
		} catch (Exception e) {
			LOG.warn(e.getLocalizedMessage(), e);
		}
		return Data.EMPTY;
	}

}
