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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.Data.Builder;
import net.sf.uadetector.internal.data.XmlDataHandler;
import net.sf.uadetector.internal.util.UrlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Reader for the XML data for UASparser from <a
 * href="http://user-agent-string.info/">http://user-agent-string.info</a>.<br>
 * <br>
 * This reader is safe when used concurrently by multiple threads.
 * 
 * @author André Rouél
 */
public final class XmlDataReader implements DataReader {

	protected static final class XmlParser {

		public static void parse(final InputStream stream, final Builder builder) throws ParserConfigurationException, SAXException,
				IOException {
			final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			final XmlDataHandler handler = new XmlDataHandler(builder);
			parser.parse(stream, handler);
		}

		private XmlParser() {
			// This class is not intended to create objects from it.
		}

	}

	/**
	 * Corresponding default logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(XmlDataReader.class);

	/**
	 * Reads the <em>UAS data</em> in XML format based on the given URL.<br>
	 * <br>
	 * When during the reading errors occur which lead to a termination of the read operation, the information will be
	 * written to a log. The termination of the read operation will not lead to a program termination.
	 * 
	 * @param url
	 *            {@code URL} to User-Agent informations
	 * @param charset
	 *            the character set in which the data should be read
	 * @return read User-Agent data as {@code Data} instance
	 * @throws IllegalArgumentException
	 *             if any of the given arguments is {@code null}
	 * @throws net.sf.uadetector.exception.CanNotOpenStreamException
	 *             if no stream to the given {@code URL} can be established
	 */
	protected static Data readXml(final URL url, final Charset charset) {
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' must not be null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException("Argument 'charset' must not be null.");
		}

		final Builder builder = new Builder();
		try {
			XmlParser.parse(UrlUtil.open(url), builder);
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
	 * Reads the <em>UAS data</em> in XML format based on the given URL.
	 * 
	 * @param url
	 *            {@code URL} to User-Agent informations
	 * @param charset
	 *            the character set in which the data should be read
	 * @return read User-Agent data as {@code Data} instance
	 * @throws IllegalArgumentException
	 *             if any of the given arguments is {@code null}
	 * @throws CanNotOpenStreamException
	 *             if no stream to the given {@code URL} can be established
	 */
	@Override
	public Data read(final URL url, final Charset charset) {
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' must not be null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException("Argument 'charset' must not be null.");
		}

		return readXml(url, charset);
	}

}
