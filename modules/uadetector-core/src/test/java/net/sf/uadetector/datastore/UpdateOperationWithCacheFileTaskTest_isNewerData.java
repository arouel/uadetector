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

import net.sf.uadetector.internal.data.Data;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class UpdateOperationWithCacheFileTaskTest_isNewerData {

	@Test
	public void isNewerData_differentVersion() {
		final Data older = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL, TestXmlDataStore.VERSION_URL, TestXmlDataStore.DATA_DEF_URL).getData();
		final Data newer = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL_NEWER, TestXmlDataStore.VERSION_URL_NEWER, TestXmlDataStore.DATA_DEF_URL).getData();
		assertThat(UpdateOperationWithCacheFileTask.isNewerData(older, newer)).isTrue();
		assertThat(UpdateOperationWithCacheFileTask.isNewerData(newer, older)).isFalse();
	}

	@Test
	public void isNewerData_sameVersion() {
		final Data older = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL, TestXmlDataStore.VERSION_URL, TestXmlDataStore.DATA_DEF_URL).getData();
		final Data newer = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL, TestXmlDataStore.VERSION_URL, TestXmlDataStore.DATA_DEF_URL).getData();
		assertThat(UpdateOperationWithCacheFileTask.isNewerData(older, newer)).isFalse();
		assertThat(UpdateOperationWithCacheFileTask.isNewerData(newer, older)).isFalse();
	}

}
