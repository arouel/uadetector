package net.sf.uadetector.datastore;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.uadetector.internal.util.FileUtil;

import org.easymock.EasyMock;
import org.easymock.IMockBuilder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CachingUpdateOperationTaskTest {

	/**
	 * The character set to read UAS data
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	/**
	 * URL to retrieve the UAS data as XML
	 */
	private static final URL DATA_URL = CachingUpdateOperationTaskTest.class.getClassLoader().getResource("uas_older.xml");

	/**
	 * Temporary folder to cache <em>UAS data</em> in a file. Created files in this folder are guaranteed to be deleted
	 * when the test method finishes (whether it passes or fails).
	 */
	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test(expected = IllegalArgumentException.class)
	public void construct_cacheFile_isNull() {
		new CachingUpdateOperationTask(new TestXmlDataStore(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_dataStore_isNull() throws IOException {
		new CachingUpdateOperationTask(null, folder.newFile());
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTemporaryFile_null() {
		CachingUpdateOperationTask.createTemporaryFile(null);
	}

	@Test
	public void deleteFile_doesNotExist() throws IOException {
		final File cache = folder.newFile(); // cache file does not exist
		CachingUpdateOperationTask.deleteFile(cache);
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

		CachingUpdateOperationTask.deleteFile(fileMock);

		EasyMock.verify(fileMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteFile_null() {
		CachingUpdateOperationTask.deleteFile(null);
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

		CachingUpdateOperationTask.deleteFile(fileMock);

		EasyMock.verify(fileMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void readAndSave_charset_null() throws IOException {
		final File cache = folder.newFile(); // cache file does not exist
		CachingUpdateOperationTask.readAndSave(DATA_URL, cache, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void readAndSave_dataUrl_null() throws IOException {
		final File cache = folder.newFile(); // cache file does not exist
		CachingUpdateOperationTask.readAndSave(null, cache, CHARSET);
	}

	@Test
	public void readAndSave_deleteAndRenameTempFileTest() throws MalformedURLException, IOException {
		final File cache = folder.newFile(); // cache file does not exist

		// Assert.assertTrue(temp.length() == 0); // does not work on any system
		Assert.assertTrue(FileUtil.isEmpty(cache, DataStore.DEFAULT_CHARSET));

		// file will be created
		CachingUpdateOperationTask.readAndSave(DATA_URL, cache, CHARSET);
		Assert.assertTrue(cache.length() >= 722015);

		// file will be overwritten (delete and rename)
		CachingUpdateOperationTask.readAndSave(DATA_URL, cache, CHARSET);
		Assert.assertTrue(cache.length() >= 722015);
	}

	@Test(expected = IllegalArgumentException.class)
	public void readAndSave_file_null() throws IOException {
		CachingUpdateOperationTask.readAndSave(DATA_URL, null, CHARSET);
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
		CachingUpdateOperationTask.readAndSave(DATA_URL, fileMock, CHARSET);
		Assert.assertTrue(fileMock.length() >= 722015);

		// file will be overwritten (delete and rename)
		CachingUpdateOperationTask.readAndSave(DATA_URL, fileMock, CHARSET);
		Assert.assertTrue(fileMock.length() >= 722015);
	}

	@Test
	public void readAndSave_urlAndFileAreSameResource() throws MalformedURLException, IOException {
		final File resource = folder.newFile(); // cache file does not exist
		CachingUpdateOperationTask.readAndSave(resource.toURI().toURL(), resource, CHARSET);
	}

	@Test(expected = IllegalArgumentException.class)
	public void renameFile_from_null() throws IOException {
		CachingUpdateOperationTask.renameFile(null, folder.newFile());
	}

	@Test(expected = IllegalArgumentException.class)
	public void renameFile_fromFileDoesNotExist() throws MalformedURLException, IOException, SecurityException, NoSuchMethodException {
		final File from = folder.newFile(); // cache file does not exist
		final IMockBuilder<File> builder = EasyMock.createMockBuilder(File.class);
		builder.withConstructor(URI.class);
		builder.withArgs(from.toURI());
		builder.addMockedMethod(File.class.getMethod("exists"));
		final File fileMock = builder.createMock();
		EasyMock.expect(fileMock.exists()).andReturn(false).anyTimes();
		EasyMock.replay(fileMock);

		CachingUpdateOperationTask.renameFile(fileMock, folder.newFile());

		EasyMock.verify(fileMock);
	}

	@Test(expected = IllegalStateException.class)
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

		CachingUpdateOperationTask.renameFile(fileMock, folder.newFile());

		EasyMock.verify(fileMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void renameFile_to_null() throws IOException {
		CachingUpdateOperationTask.renameFile(folder.newFile(), null);
	}

}
