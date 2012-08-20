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

import net.sf.uadetector.SimpleDataStore;
import net.sf.uadetector.internal.data.Data;

import org.junit.Assert;
import org.junit.Test;

public class UpdateServiceTest {

	private final URL DATA_URL = this.getClass().getClassLoader().getResource("uas_newer.xml");

	private final URL VERSION_URL = this.getClass().getClassLoader().getResource("uas_newer.version");

	@Test
	public void call() throws MalformedURLException {
		final UpdateService service = new UpdateService(new SimpleDataStore(Data.EMPTY), DATA_URL, VERSION_URL);
		service.call();
	}

	@Test
	public void call_notReachable() throws MalformedURLException {
		final URL notReachableUrl = new URL("http://localhost:17171");
		final UpdateService service = new UpdateService(new SimpleDataStore(Data.EMPTY), notReachableUrl, notReachableUrl);
		service.call();
	}

	@Test
	public void callTriple() throws MalformedURLException {
		final UpdateService service = new UpdateService(new SimpleDataStore(Data.EMPTY), DATA_URL, VERSION_URL);
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
	public void callTwice() throws MalformedURLException {
		final UpdateService service = new UpdateService(new SimpleDataStore(Data.EMPTY), DATA_URL, VERSION_URL);
		Assert.assertEquals(0, service.getLastUpdateCheck());
		final long startTime = System.currentTimeMillis();
		service.call();
		Assert.assertTrue(service.getLastUpdateCheck() >= startTime);
		final long nextTime = System.currentTimeMillis();
		service.call();
		Assert.assertTrue(service.getLastUpdateCheck() >= nextTime);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_dataUrl_null() throws MalformedURLException {
		new UpdateService(new SimpleDataStore(Data.EMPTY), null, VERSION_URL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_store_null() throws MalformedURLException {
		new UpdateService(null, DATA_URL, VERSION_URL);
	}

	@Test
	public void construct_successful() throws MalformedURLException {
		new UpdateService(new SimpleDataStore(Data.EMPTY), DATA_URL, VERSION_URL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_versionUrl_null() throws MalformedURLException {
		new UpdateService(new SimpleDataStore(Data.EMPTY), DATA_URL, null);
	}

	@Test
	public void getLastUpdateCheck() throws MalformedURLException {
		final UpdateService service = new UpdateService(new SimpleDataStore(Data.EMPTY), DATA_URL, VERSION_URL);
		Assert.assertEquals(0, service.getLastUpdateCheck());
		final long startTime = System.currentTimeMillis();
		service.call();
		Assert.assertTrue(service.getLastUpdateCheck() >= startTime);
	}

}
