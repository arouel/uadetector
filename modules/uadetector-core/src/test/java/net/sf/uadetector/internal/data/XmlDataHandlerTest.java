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

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.datastore.TestXmlDataStore;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlDataHandlerTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void constructor_null() {
		new XmlDataHandler(null, TestXmlDataStore.DATA_DEF_URL);
	}

	/**
	 * This test reduces only some coverage noise.
	 */
	@Test
	public void logParsingIssue() {
		final String message = "msg";
		final String publicId = "public id";
		final String systemId = "system id";
		final int lineNumber = 123;
		final int columnNumber = 345;
		final IllegalArgumentException iae = new IllegalArgumentException();
		final SAXParseException e = new SAXParseException(message, publicId, systemId, lineNumber, columnNumber, iae);
		XmlDataHandler.logParsingIssue("Warning", e);
	}

	@Test
	public void resolveEntity_knownUrl() throws IOException, SAXException {
		final XmlDataHandler handler = new XmlDataHandler(new DataBuilder(), TestXmlDataStore.DATA_DEF_URL);
		final InputSource input = handler.resolveEntity("publicId is irrelevant", DataStore.DEFAULT_DATA_DEF_URL);
		assertThat(input).isNotNull();
	}

	@Test(expected = SAXException.class)
	public void resolveEntity_unknownUrl() throws IOException, SAXException {
		final XmlDataHandler handler = new XmlDataHandler(new DataBuilder(), TestXmlDataStore.DATA_DEF_URL);
		handler.resolveEntity("publicId unknown", "systemId unknown");
	}

}
