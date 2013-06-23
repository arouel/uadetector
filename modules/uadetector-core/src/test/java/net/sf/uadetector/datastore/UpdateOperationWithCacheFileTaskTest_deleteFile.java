package net.sf.uadetector.datastore;

import java.io.File;
import java.io.IOException;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ File.class })
@PowerMockIgnore({ "com.*", "org.*" })
public class UpdateOperationWithCacheFileTaskTest_deleteFile {

	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void deleteFile_fileDoesNotExist() {
		final File file = new File("does_not_exist");
		Assert.assertFalse(file.exists());
		UpdateOperationWithCacheFileTask.deleteFile(file);
		Assert.assertFalse(file.exists());
	}

	@Test
	public void deleteFile_fileExists() throws IOException {
		final File file = folder.newFile("test_file");
		Assert.assertTrue(file.exists());
		UpdateOperationWithCacheFileTask.deleteFile(file);
		Assert.assertFalse(file.exists());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void deleteFile_fileIsNull() {
		UpdateOperationWithCacheFileTask.deleteFile(null);
	}

	@Test
	public void deleteFile_fileNotRemovable() throws IOException {
		final File file = PowerMock.createMock(File.class);
		EasyMock.expect(file.delete()).andReturn(false).anyTimes();
		EasyMock.expect(file.exists()).andReturn(true).anyTimes();
		EasyMock.expect(file.getPath()).andReturn("/path").anyTimes();
		PowerMock.replay(file);

		try {
			UpdateOperationWithCacheFileTask.deleteFile(file);
		} catch (final IllegalStateOfArgumentException e) {
			Assert.assertEquals("Cannot delete file '/path'.", e.getLocalizedMessage());
		}

		PowerMock.verify(file);
	}

}
