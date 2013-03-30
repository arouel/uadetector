package net.sf.uadetector.datastore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ FileOutputStream.class, UpdateOperationWithCacheFileTask.class })
public class CachingUpdateOperationTaskTest_readAndSave {

	@Test(expected = IOException.class)
	public void testNullCheckBeforeClosing() throws Exception {
		final File temp = File.createTempFile("testfile", ".tmp");
		temp.deleteOnExit();
		PowerMock.expectNiceNew(FileOutputStream.class, EasyMock.anyObject(File.class)).andThrow(new IOException());
		PowerMock.replayAll();
		UpdateOperationWithCacheFileTask.readAndSave(temp, new TestXmlDataStore());
		PowerMock.verifyAll();
		temp.delete();
	}

}
