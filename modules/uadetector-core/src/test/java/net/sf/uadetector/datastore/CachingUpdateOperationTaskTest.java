package net.sf.uadetector.datastore;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.FileUtil;
import net.sf.uadetector.internal.util.UrlUtil;

import org.easymock.EasyMock;
import org.easymock.IMockBuilder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CachingUpdateOperationTaskTest {

	/**
	 * Temporary folder to cache <em>UAS data</em> in a file. Created files in this folder are guaranteed to be deleted
	 * when the test method finishes (whether it passes or fails).
	 */
	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_cacheFile_isNull() {
		new UpdateOperationWithCacheFileTask(new TestXmlDataStore(), null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_dataStore_isNull() throws IOException {
		new UpdateOperationWithCacheFileTask(null, folder.newFile());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void createTemporaryFile_null() {
		UpdateOperationWithCacheFileTask.createTemporaryFile(null);
	}

	@Test
	public void deleteFile_doesNotExist() throws IOException {
		final File cache = folder.newFile(); // cache file does not exist
		UpdateOperationWithCacheFileTask.deleteFile(cache);
	}

	@Test(expected = IllegalStateException.class)
	public void deleteFile_existsButDeletingFails() throws IOException, SecurityException, NoSuchMethodException {
		final File file = folder.newFile(); // cache file does not exist
		final IMockBuilder<File> builder = EasyMock.createMockBuilder(File.class);
		builder.withConstructor(URI.class);
		builder.withArgs(file.toURI());
		builder.addMockedMethod(File.class.getMethod("exists"));
		builder.addMockedMethod(File.class.getMethod("delete"));
		final File fileMock = builder.createMock();
		EasyMock.expect(fileMock.exists()).andReturn(true).anyTimes();
		EasyMock.expect(fileMock.delete()).andReturn(false).anyTimes();
		EasyMock.replay(fileMock);

		UpdateOperationWithCacheFileTask.deleteFile(fileMock);

		EasyMock.verify(fileMock);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void deleteFile_null() {
		UpdateOperationWithCacheFileTask.deleteFile(null);
	}

	@Test
	public void deleteFile_successful() throws IOException, SecurityException, NoSuchMethodException {
		final File file = folder.newFile(); // cache file does not exist
		final IMockBuilder<File> builder = EasyMock.createMockBuilder(File.class);
		builder.withConstructor(URI.class);
		builder.withArgs(file.toURI());
		builder.addMockedMethod(File.class.getMethod("exists"));
		builder.addMockedMethod(File.class.getMethod("delete"));
		final File fileMock = builder.createMock();
		EasyMock.expect(fileMock.exists()).andReturn(true).anyTimes();
		EasyMock.expect(fileMock.delete()).andReturn(true).anyTimes();
		EasyMock.replay(fileMock);

		UpdateOperationWithCacheFileTask.deleteFile(fileMock);

		EasyMock.verify(fileMock);
	}

	@Test
	public void readAndSave_deleteAndRenameTempFileTest() throws MalformedURLException, IOException {
		final File cache = folder.newFile(); // cache file does not exist

		// Assert.assertTrue(temp.length() == 0); // does not work on any system
		Assert.assertTrue(FileUtil.isEmpty(cache, DataStore.DEFAULT_CHARSET));

		// file will be created
		UpdateOperationWithCacheFileTask.readAndSave(cache, new TestXmlDataStore());
		Assert.assertTrue(cache.length() >= 722015);

		// file will be overwritten (delete and rename)
		UpdateOperationWithCacheFileTask.readAndSave(cache, new TestXmlDataStore());
		Assert.assertTrue(cache.length() >= 722015);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void readAndSave_file_null() throws IOException {
		UpdateOperationWithCacheFileTask.readAndSave(null, new TestXmlDataStore());
	}

	@Test
	public void readAndSave_renamingFailsTest() throws MalformedURLException, IOException {
		final File cache = folder.newFile(); // cache file does not exist
		final IMockBuilder<File> builder = EasyMock.createMockBuilder(File.class);
		builder.withConstructor(URI.class);
		builder.withArgs(cache.toURI());
		final File fileMock = builder.addMockedMethod("renameTo", File.class).createMock();
		EasyMock.expect(fileMock.renameTo(EasyMock.anyObject(File.class))).andReturn(false).anyTimes();
		EasyMock.replay(fileMock);

		// Assert.assertTrue(temp.length() == 0); // does not work on any system
		Assert.assertTrue(FileUtil.isEmpty(fileMock, DataStore.DEFAULT_CHARSET));

		// file will be created
		UpdateOperationWithCacheFileTask.readAndSave(fileMock, new TestXmlDataStore());
		Assert.assertTrue(fileMock.length() >= 722015);

		// file will be overwritten (delete and rename)
		UpdateOperationWithCacheFileTask.readAndSave(fileMock, new TestXmlDataStore());
		Assert.assertTrue(fileMock.length() >= 722015);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void readAndSave_store_null() throws IOException {
		final File cache = folder.newFile(); // cache file does not exist
		UpdateOperationWithCacheFileTask.readAndSave(cache, null);
	}

	@Test
	public void readAndSave_urlAndFileAreSameResource() throws MalformedURLException, IOException {
		final File resource = folder.newFile(); // cache file does not exist
		UpdateOperationWithCacheFileTask.readAndSave(resource, new DataStore() {

			@Override
			public Charset getCharset() {
				return DEFAULT_CHARSET;
			}

			@Override
			public Data getData() {
				return Data.EMPTY;
			}

			@Override
			public DataReader getDataReader() {
				return new XmlDataReader();
			}

			@Override
			public URL getDataUrl() {
				try {
					return resource.toURI().toURL();
				} catch (final MalformedURLException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public URL getVersionUrl() {
				return UrlUtil.build(DEFAULT_VERSION_URL);
			}
		});
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void renameFile_from_null() throws IOException {
		UpdateOperationWithCacheFileTask.renameFile(null, folder.newFile());
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void renameFile_fromFileDoesNotExist() throws MalformedURLException, IOException, SecurityException, NoSuchMethodException {
		final File from = folder.newFile(); // cache file does not exist
		final IMockBuilder<File> builder = EasyMock.createMockBuilder(File.class);
		builder.withConstructor(URI.class);
		builder.withArgs(from.toURI());
		builder.addMockedMethod(File.class.getMethod("exists"));
		final File fileMock = builder.createMock();
		EasyMock.expect(fileMock.exists()).andReturn(false).anyTimes();
		EasyMock.replay(fileMock);

		UpdateOperationWithCacheFileTask.renameFile(fileMock, folder.newFile());

		EasyMock.verify(fileMock);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void renameFile_removingOrphanedFileTest() throws MalformedURLException, IOException, SecurityException, NoSuchMethodException {
		final File from = folder.newFile(); // cache file does not exist
		final IMockBuilder<File> builder = EasyMock.createMockBuilder(File.class);
		builder.withConstructor(URI.class);
		builder.withArgs(from.toURI());
		builder.addMockedMethod(File.class.getMethod("exists"));
		builder.addMockedMethod(File.class.getMethod("renameTo", File.class));
		final File fileMock = builder.createMock();
		// final File fileMock = builder.addMockedMethod("exists").addMockedMethod("renameTo", File.class).createMock();
		EasyMock.expect(fileMock.exists()).andReturn(true).anyTimes();
		EasyMock.expect(fileMock.renameTo(EasyMock.anyObject(File.class))).andReturn(false).anyTimes();
		EasyMock.replay(fileMock);

		UpdateOperationWithCacheFileTask.renameFile(fileMock, folder.newFile());

		EasyMock.verify(fileMock);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void renameFile_to_null() throws IOException {
		UpdateOperationWithCacheFileTask.renameFile(folder.newFile(), null);
	}

}
