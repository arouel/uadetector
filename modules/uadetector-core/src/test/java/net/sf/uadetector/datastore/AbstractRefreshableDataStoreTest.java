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

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataBlueprint;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class AbstractRefreshableDataStoreTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void setData_null() {
		final TestXmlDataStore store = new TestXmlDataStore();
		store.setData(null);
	}

	@Test
	public void setData_successful() {
		final TestXmlDataStore store = new TestXmlDataStore();
		final Data data2 = new DataBlueprint().version("test-version").build();
		store.setData(data2);
		assertThat(store.getData()).isSameAs(data2);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void setData_withEmptyData() {
		final TestXmlDataStore store = new TestXmlDataStore();
		store.setData(Data.EMPTY);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setUpdateOperation_null() {
		final TestXmlDataStore store = new TestXmlDataStore();
		store.setUpdateOperation(null);
	}

}
