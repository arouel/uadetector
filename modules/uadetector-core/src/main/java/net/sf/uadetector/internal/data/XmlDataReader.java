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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sf.uadetector.internal.data.Data.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Reader for the XML data for UASparser from <a
 * href="http://user-agent-string.info/">http://user-agent-string.info</a>.<br>
 * <br>
 * This reader is safe for use by multiple threads.
 * 
 * @author André Rouél
 */
public final class XmlDataReader implements DataReader {

	private static final class XmlParser {

		public static void parse(final InputStream stream, final Builder builder) throws ParserConfigurationException, SAXException,
				IOException {
			final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			final XmlDataHandler handler = new XmlDataHandler(builder);
			parser.parse(stream, handler);
		}

		public static void parse(final String uri, final Builder builder) throws ParserConfigurationException, SAXException, IOException {
			final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			final XmlDataHandler handler = new XmlDataHandler(builder);
			parser.parse(uri, handler);
		}

	}

	/**
	 * Default log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(XmlDataReader.class);

	@Override
	public Data read(final InputStream inputStream) {
		if (inputStream == null) {
			throw new IllegalArgumentException("Argument 'inputStream' must not be null.");
		}
		final Builder builder = new Builder();
		try {
			XmlParser.parse(inputStream, builder);
		} catch (final ParserConfigurationException e) {
			LOG.warn(e.getLocalizedMessage());
		} catch (final SAXException e) {
			LOG.warn(e.getLocalizedMessage());
		} catch (final IOException e) {
			LOG.warn(e.getLocalizedMessage());
		}
		return builder.build();
	}

	/**
	 * Reads the data by an {@code URL}.
	 * 
	 * @param url
	 *            {@code URL} to User-Agent informations
	 * @return read User-Agent data as {@code Data} instance
	 * @throws IllegalArgumentException
	 *             if the given {@code URL} is {@code null}
	 * @throws IllegalArgumentException
	 *             if the given {@code URL} is unreachable
	 */
	@Override
	public Data read(final URL url) {
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' must not be null.");
		}
		final Builder builder = new Builder();
		try {
			XmlParser.parse(url.toExternalForm(), builder);
		} catch (final ParserConfigurationException e) {
			LOG.warn(e.getLocalizedMessage());
		} catch (final SAXException e) {
			LOG.warn(e.getLocalizedMessage());
		} catch (final IOException e) {
			LOG.warn(e.getLocalizedMessage());
		}
		return builder.build();
	}

}
