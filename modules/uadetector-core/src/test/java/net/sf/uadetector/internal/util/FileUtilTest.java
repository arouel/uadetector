package net.sf.uadetector.internal.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.datastore.DataStore;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BufferedReader.class, FileUtil.class })
public class FileUtilTest {

	/**
	 * The character set to read the contents of an URL
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve the version of the UAS data
	 */
	private static final URL VERSION_URL = FileUtilTest.class.getClassLoader().getResource("uas_newer.version");

	@Test
	public void isEmpty_withEmptyFile() throws Exception {
		final File tempEmptyFile = File.createTempFile("testfile", ".tmp");
		tempEmptyFile.deleteOnExit();
		final boolean isEmpty = FileUtil.isEmpty(tempEmptyFile, CHARSET);
		PowerMock.verifyAll();
		Assert.assertTrue(isEmpty);
		tempEmptyFile.delete();
	}

	@Test
	public void isEmpty_withFilledFile() throws Exception {
		final File tempEmptyFile = File.createTempFile("testfile", ".tmp");
		tempEmptyFile.deleteOnExit();
		final FileOutputStream writer = new FileOutputStream(tempEmptyFile);
		writer.write("test".getBytes());
		writer.close();
		final boolean isEmpty = FileUtil.isEmpty(tempEmptyFile, CHARSET);
		PowerMock.verifyAll();
		Assert.assertFalse(isEmpty);
		tempEmptyFile.delete();
	}

	@Test(expected = IOException.class)
	public void testNullCheckBeforeClosing() throws Exception {
		PowerMock.expectNiceNew(BufferedReader.class, EasyMock.anyObject(Reader.class)).andThrow(new IOException());
		PowerMock.replayAll();
		FileUtil.isEmpty(new File(VERSION_URL.toURI()), CHARSET);
		PowerMock.verifyAll();
	}

}
