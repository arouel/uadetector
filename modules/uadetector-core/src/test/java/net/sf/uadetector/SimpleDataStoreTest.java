/*******************************************************************************
 * Copyright 2012 André Rouél
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
package net.sf.uadetector;

import java.io.InputStream;
import java.util.HashSet;
import java.util.TreeMap;

import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;

public class SimpleDataStoreTest {

	private static final String RESOURCE = "uas_test.xml";

	@Test(expected = IllegalArgumentException.class)
	public void construct_data_null() {
		new SimpleDataStore((Data) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_stream_null() {
		new SimpleDataStore((InputStream) null);
	}

	@Test
	public void construct_stream_successful() {
		final InputStream stream = SimpleDataStoreTest.class.getClassLoader().getResourceAsStream(RESOURCE);
		final SimpleDataStore store = new SimpleDataStore(stream);
		Assert.assertEquals("20120509-01", store.getData().getVersion());
	}

	@Test
	public void construct_successful() {
		final Data testData = new Data(new HashSet<Browser>(), new HashSet<OperatingSystem>(), new HashSet<Robot>(0),
				new TreeMap<BrowserPattern, Browser>(), new TreeMap<OperatingSystemPattern, OperatingSystem>(), "");
		final SimpleDataStore store = new SimpleDataStore(testData);
		Assert.assertSame(testData, store.getData());
		store.setData(Data.EMPTY);
		Assert.assertSame(Data.EMPTY, store.getData());
	}

}
