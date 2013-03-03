package net.sf.uadetector.datastore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ FileOutputStream.class, CachingUpdateOperationTask.class })
public class CachingUpdateOperationTaskTest_readAndSave {

	/**
	 * The character set to read the contents of an URL
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = CachingUpdateOperationTaskTest_readAndSave.class.getClassLoader().getResource("uas_older.xml");

	@Test(expected = IOException.class)
	public void testNullCheckBeforeClosing() throws Exception {
		final File temp = File.createTempFile("testfile", ".tmp");
		temp.deleteOnExit();
		PowerMock.expectNiceNew(FileOutputStream.class, EasyMock.anyObject(File.class)).andThrow(new IOException());
		PowerMock.replayAll();
		CachingUpdateOperationTask.readAndSave(DATA_URL, temp, CHARSET);
		PowerMock.verifyAll();
		temp.delete();
	}

}
