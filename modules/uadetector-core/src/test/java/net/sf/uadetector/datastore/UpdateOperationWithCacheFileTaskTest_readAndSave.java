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
public class UpdateOperationWithCacheFileTaskTest_readAndSave {

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
