/*******************************************************************************
 * Copyright 2013 André Rouél
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.uadetector.datastore.TestXmlDataStore;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.base.Charsets;

public final class XmlDataWriterTest {

	public static String format(final String xml) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		final StringWriter stringWriter = new StringWriter();
		final Document document = XmlDataWriter.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
		final StreamResult xmlOutput = new StreamResult(stringWriter);
		XmlDataWriter.transform(new DOMSource(document.getDocumentElement()), xmlOutput);
		return xmlOutput.getWriter().toString();
	}

	private static String removeIndentation(@Nonnull final String xml) {
		return xml.replaceAll("(?m)^\\s+", "");
	}

	public String formatSimilar(final String xml) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		return format(removeIndentation(xml));
	}

	@Test
	public void test() throws Exception {
		final URL resource = getClass().getClassLoader().getResource("uas_older.xml");
		final String expected = formatSimilar(UrlUtil.read(resource, Charsets.UTF_8)).replaceAll("/si </regstring>", "/si</regstring>");

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XmlDataWriter.write(new TestXmlDataStore().getData(), outputStream);
		final String actual = new String(outputStream.toByteArray(), "UTF-8");

		Assert.assertEquals(expected, formatSimilar(actual));
	}

}
