package net.sf.uadetector.internal.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.datastore.DataStore;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BufferedReader.class, UrlUtil.class })
public class UrlUtilTest_read {

	/**
	 * The character set to read the contents of an URL
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = UrlUtilTest_read.class.getClassLoader().getResource("uas_newer.version");

	@Test(expected = IOException.class)
	public void testNullCheckBeforeClosing() throws Exception {
		PowerMock.expectNiceNew(BufferedReader.class, EasyMock.anyObject(Reader.class)).andThrow(new IOException());
		PowerMock.replayAll();
		UrlUtil.read(VERSION_URL, CHARSET);
		PowerMock.verifyAll();
	}

}
