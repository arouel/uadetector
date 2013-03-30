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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.exception.CanNotOpenStreamException;
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

		private static final String MSG_NOT_PARSED_AS_EXPECTED = "The UAS data has not been parsed as expected.";

		public static void parse(final InputStream stream, final Builder builder) throws ParserConfigurationException, SAXException,
				IOException {
			final SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			final SAXParser parser = factory.newSAXParser();
			final XmlDataHandler handler = new XmlDataHandler(builder);
			parser.parse(stream, handler);
			validate(handler);
		}

		protected static void validate(final XmlDataHandler handler) {
			if (handler.hasError() || handler.hasWarning()) {
				throw new IllegalStateException(MSG_NOT_PARSED_AS_EXPECTED);
			}
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
	 * written to a log. The termination of the read operation will not lead to a program termination and in this case
	 * this method returns {@link Data#EMPTY}.
	 * 
	 * @param inputStream
	 *            an input stream for reading <em>UAS data</em>
	 * @param charset
	 *            the character set in which the data should be read
	 * @return read in <em>UAS data</em> as {@code Data} instance
	 * @throws IllegalArgumentException
	 *             if any of the given arguments is {@code null}
	 * @throws net.sf.uadetector.exception.CanNotOpenStreamException
	 *             if no stream to the given {@code URL} can be established
	 */
	protected static Data readXml(final InputStream inputStream, final Charset charset) {
		if (inputStream == null) {
			throw new IllegalArgumentException("Argument 'inputStream' must not be null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException("Argument 'charset' must not be null.");
		}

		final Builder builder = new Builder();
		boolean hasErrors = false;
		try {
			XmlParser.parse(inputStream, builder);
		} catch (final ParserConfigurationException e) {
			hasErrors = true;
			LOG.warn(e.getLocalizedMessage());
		} catch (final SAXException e) {
			hasErrors = true;
			LOG.warn(e.getLocalizedMessage());
		} catch (final IOException e) {
			hasErrors = true;
			LOG.warn(e.getLocalizedMessage());
		} catch (final IllegalStateException e) {
			hasErrors = true;
			LOG.warn(e.getLocalizedMessage());
		} catch (final Exception e) {
			hasErrors = true;
			LOG.warn(e.getLocalizedMessage(), e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (final IOException e) {
					LOG.warn(e.getLocalizedMessage(), e);
				}
			}
		}

		return hasErrors ? Data.EMPTY : builder.build();
	}

	/**
	 * Reads the <em>UAS data</em> in XML format from the given string.
	 * 
	 * @param data
	 *            <em>UAS data</em> as string
	 * @return read in User-Agent data as {@code Data} instance otherwise {@link Data#EMPTY}
	 * 
	 * @throws IllegalArgumentException
	 *             if any of the given argument is {@code null}
	 */
	@Override
	public Data read(final String data) {
		if (data == null) {
			throw new IllegalArgumentException("Argument 'data' must not be null.");
		}

		return readXml(new ByteArrayInputStream(data.getBytes(DataStore.DEFAULT_CHARSET)), DataStore.DEFAULT_CHARSET);
	}

	/**
	 * Reads the <em>UAS data</em> in XML format based on the given URL.
	 * 
	 * @param url
	 *            {@code URL} to User-Agent informations
	 * @param charset
	 *            the character set in which the data should be read
	 * @return read in User-Agent data as {@code Data} instance otherwise {@link Data#EMPTY}
	 * 
	 * @throws IllegalArgumentException
	 *             if any of the given arguments is {@code null}
	 */
	@Override
	public Data read(final URL url, final Charset charset) {
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' must not be null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException("Argument 'charset' must not be null.");
		}

		Data data = Data.EMPTY;
		try {
			data = readXml(UrlUtil.open(url), charset);
		} catch (final CanNotOpenStreamException e) {
			LOG.warn(e.getLocalizedMessage());
		}

		return data;
	}

}
