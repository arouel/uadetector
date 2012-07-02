package net.sf.uadetector.internal.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;

public class ActualityOfDtdTest {

	@Test
	public void checkDtdActuality() throws IOException {
		final String currentDefinition = XmlDataHandler.UASDATA_DEF;
		final URL definitionUrl = new URL(XmlDataHandler.UASDATA_DEF_URL);

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

		Assert.assertEquals(onlineDtd, localDtd);
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
