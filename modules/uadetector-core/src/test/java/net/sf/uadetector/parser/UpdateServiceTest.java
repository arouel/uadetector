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
package net.sf.uadetector.parser;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.uadetector.datastore.AbstractDataStore;
import net.sf.uadetector.datastore.TestXmlDataStore;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.XmlDataReader;

import org.junit.Assert;
import org.junit.Test;

public class UpdateServiceTest {

	private static class TestEmptyDataStore extends AbstractDataStore {
		protected TestEmptyDataStore() {
			super(Data.EMPTY, new XmlDataReader(), DATA_URL, VERSION_URL);
		}
	}

	private static final URL DATA_URL = UpdateServiceTest.class.getClassLoader().getResource("uas_newer.xml");

	private static final URL VERSION_URL = UpdateServiceTest.class.getClassLoader().getResource("uas_newer.version");

	@Test
	public void call() {
		final UpdateService service = new UpdateService(new TestXmlDataStore(), DATA_URL, VERSION_URL);
		service.call();
	}

	@Test
	public void call_notReachable() throws MalformedURLException {
		final URL notReachableUrl = new URL("http://localhost:17171");
		final UpdateService service = new UpdateService(new TestXmlDataStore(), notReachableUrl, notReachableUrl);
		service.call();
	}

	@Test
	public void call_withEmptyData() {
		final UpdateService service = new UpdateService(new TestEmptyDataStore(), DATA_URL, VERSION_URL);
		service.call();
	}

	@Test
	public void callTriple() {
		final UpdateService service = new UpdateService(new TestXmlDataStore(), DATA_URL, VERSION_URL);
		Assert.assertEquals(0, service.getLastUpdateCheck());
		final long startTime = System.currentTimeMillis();
		service.call();
		Assert.assertTrue(service.getLastUpdateCheck() >= startTime);
		final long nextTime = System.currentTimeMillis();
		service.call();
		Assert.assertTrue(service.getLastUpdateCheck() >= nextTime);
		final long lastTime = System.currentTimeMillis();
		service.call();
		Assert.assertTrue(service.getLastUpdateCheck() >= lastTime);
	}

	@Test
	public void callTwice() {
		final UpdateService service = new UpdateService(new TestXmlDataStore(), DATA_URL, VERSION_URL);
		Assert.assertEquals(0, service.getLastUpdateCheck());
		final long startTime = System.currentTimeMillis();
		service.call();
		Assert.assertTrue(service.getLastUpdateCheck() >= startTime);
		final long nextTime = System.currentTimeMillis();
		service.call();
		Assert.assertTrue(service.getLastUpdateCheck() >= nextTime);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_dataUrl_null() {
		new UpdateService(new TestXmlDataStore(), null, VERSION_URL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_store_null() {
		new UpdateService(null, DATA_URL, VERSION_URL);
	}

	@Test
	public void construct_successful() {
		new UpdateService(new TestXmlDataStore(), DATA_URL, VERSION_URL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_versionUrl_null() {
		new UpdateService(new TestXmlDataStore(), DATA_URL, null);
	}

	@Test
	public void getLastUpdateCheck() {
		final UpdateService service = new UpdateService(new TestXmlDataStore(), DATA_URL, VERSION_URL);
		Assert.assertEquals(0, service.getLastUpdateCheck());
		final long startTime = System.currentTimeMillis();
		service.call();
		Assert.assertTrue(service.getLastUpdateCheck() >= startTime);
	}

	@Test
	public void run() {
		final UpdateService service = new UpdateService(new TestXmlDataStore(), DATA_URL, VERSION_URL);
		service.run();
	}

}
