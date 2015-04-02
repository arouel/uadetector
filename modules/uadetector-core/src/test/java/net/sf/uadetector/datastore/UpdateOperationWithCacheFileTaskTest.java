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
import static org.fest.assertions.Assertions.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class UpdateOperationWithCacheFileTaskTest {

	/**
	 * Temporary folder to cache <em>UAS data</em> in a file. Created files in this folder are guaranteed to be deleted
	 * when the test method finishes (whether it passes or fails).
	 */
	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void call_cacheContainsUnexpectedData_willBeOverriddenIfUpdateAvailable() throws MalformedURLException, IOException {
		final File cache = folder.newFile("test.cache");
		Files.write("unexpected data".getBytes(Charsets.UTF_8), cache);
		assertThat(cache.exists()).isTrue();

		// file will be created
		final UpdateOperationWithCacheFileTask task = new UpdateOperationWithCacheFileTask(new TestXmlDataStore(), cache);
		task.call();
		assertThat(cache.length() >= 722015).isTrue();

		// file will be overwritten (delete and rename)
		task.call();
		assertThat(cache.length() >= 722015).isTrue();
	}

	@Test
	public void call_cacheContainsUnexpectedData_willNotBeOverriddenIfNoUpdateAvailable() throws MalformedURLException, IOException {
		final File cache = folder.newFile("test.cache");
		final String unexpectedContent = "unexpected data";
		Files.write(unexpectedContent.getBytes(Charsets.UTF_8), cache);
		assertThat(cache.exists()).isTrue();

		// file will be created
		final UpdateOperationWithCacheFileTask task = new UpdateOperationWithCacheFileTask(new NotUpdateableXmlDataStore(), cache);
		task.call();
		assertThat(cache.length()).isEqualTo(unexpectedContent.getBytes(Charsets.UTF_8).length);

		// file will be overwritten (delete and rename)
		task.call();
		assertThat(cache.length()).isEqualTo(unexpectedContent.getBytes(Charsets.UTF_8).length);
	}

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
	public void readAndSave_cacheFileDoesExist() throws MalformedURLException, IOException {
		final File cache = new File(folder.getRoot(), "test.cache"); // cache file does not exist
		cache.createNewFile();
		assertThat(cache.exists()).isTrue();

		// file will be created
		UpdateOperationWithCacheFileTask.readAndSave(cache, new TestXmlDataStore());
		assertThat(cache.length() >= 722015).isTrue();

		// file will be overwritten (delete and rename)
		UpdateOperationWithCacheFileTask.readAndSave(cache, new TestXmlDataStore());
		assertThat(cache.length() >= 722015).isTrue();
	}

	@Test
	public void readAndSave_cacheFileDoesNotExist() throws MalformedURLException, IOException {
		final File cache = new File(folder.getRoot(), "test.cache"); // cache file does not exist
		assertThat(cache.exists()).isFalse();

		// file will be created
		UpdateOperationWithCacheFileTask.readAndSave(cache, new TestXmlDataStore());
		assertThat(cache.length() >= 722015).isTrue();

		// file will be overwritten (delete and rename)
		UpdateOperationWithCacheFileTask.readAndSave(cache, new TestXmlDataStore());
		assertThat(cache.length() >= 722015).isTrue();
	}

	@Test
	public void readAndSave_deleteAndRenameTempFileTest() throws MalformedURLException, IOException {
		final File cache = folder.newFile(); // cache file does not exist

		// assertThat(temp.length() == 0).isTrue(); // does not work on any system
		assertThat(FileUtil.isEmpty(cache, DataStore.DEFAULT_CHARSET)).isTrue();

		// file will be created
		UpdateOperationWithCacheFileTask.readAndSave(cache, new TestXmlDataStore());
		assertThat(cache.length() >= 722015).isTrue();

		// file will be overwritten (delete and rename)
		UpdateOperationWithCacheFileTask.readAndSave(cache, new TestXmlDataStore());
		assertThat(cache.length() >= 722015).isTrue();
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

		// assertThat(temp.length() == 0).isTrue(); // does not work on any system
		assertThat(FileUtil.isEmpty(fileMock, DataStore.DEFAULT_CHARSET)).isTrue();

		// file will be created
		UpdateOperationWithCacheFileTask.readAndSave(fileMock, new TestXmlDataStore());
		assertThat(fileMock.length() >= 722015).isTrue();

		// file will be overwritten (delete and rename)
		UpdateOperationWithCacheFileTask.readAndSave(fileMock, new TestXmlDataStore());
		assertThat(fileMock.length() >= 722015).isTrue();
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
			public URL getDataDefUrl() {
			  return UrlUtil.build(DEFAULT_DATA_DEF_URL);
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
