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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class UpdateOperationWithCacheFileTaskTest_deleteFile {

	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void deleteFile_fileDoesNotExist() {
		final File file = new File("does_not_exist");
		assertThat(file.exists()).isFalse();
		UpdateOperationWithCacheFileTask.deleteFile(file);
		assertThat(file.exists()).isFalse();
	}

	@Test
	public void deleteFile_fileExists() throws IOException {
		final File file = folder.newFile("test_file");
		assertThat(file.exists()).isTrue();
		UpdateOperationWithCacheFileTask.deleteFile(file);
		assertThat(file.exists()).isFalse();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void deleteFile_fileIsNull() {
		UpdateOperationWithCacheFileTask.deleteFile(null);
	}

	@Test
	public void deleteFile_fileNotRemovable() throws IOException {
		final File file = createMock(File.class);
		expect(file.delete()).andReturn(false).anyTimes();
		expect(file.exists()).andReturn(true).anyTimes();
		expect(file.getPath()).andReturn("/path").anyTimes();
		replay(file);

		try {
			UpdateOperationWithCacheFileTask.deleteFile(file);
		} catch (final IllegalStateOfArgumentException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo("Cannot delete file '/path'.");
		}

		verify(file);
	}

}
