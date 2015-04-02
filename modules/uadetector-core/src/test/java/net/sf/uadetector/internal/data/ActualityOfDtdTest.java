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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;
import net.sf.uadetector.datastore.DataStore;

import org.junit.Test;

public class ActualityOfDtdTest {

	@Test
	public void checkDtdActuality() throws IOException {
		final String currentDefinition = XmlDataHandler.UASDATA_DEF;
		final URL definitionUrl = new URL(DataStore.DEFAULT_DATA_DEF_URL);

		// read DTD online
		final InputStream onlineStream = definitionUrl.openStream();
		final InputStreamReader onlineReader = new InputStreamReader(onlineStream);
		final String onlineDtd = read(onlineReader);
		onlineReader.close();
		onlineStream.close();

		// read DTD local
		final InputStreamReader localReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(currentDefinition));
		final String localDtd = read(localReader);
		localReader.close();

		assertThat(localDtd).isEqualTo(onlineDtd);
	}

	private String read(final Reader reader) throws IOException {
		final StringBuilder buffer = new StringBuilder();
		String nextLine = null;
		final BufferedReader bufferedReader = new BufferedReader(reader);
		do {
			nextLine = bufferedReader.readLine();
			if (nextLine != null) {
				buffer.append(nextLine);
			}
		} while (nextLine != null);
		bufferedReader.close();
		return buffer.toString();
	}

}
