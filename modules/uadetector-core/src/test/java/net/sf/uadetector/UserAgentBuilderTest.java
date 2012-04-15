/*******************************************************************************
 * Copyright 2011 André Rouél
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

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

public class UserAgentBuilderTest {

	@Test
	public void build_custom() {
		final OperatingSystem os = new OperatingSystem("family", "name", "producer", "producer url", "url");

		final UserAgent.Builder b = new UserAgent.Builder();
		b.setFamily("f1").setName("n1").setOperatingSystem(os).setProducer("p1").setProducerUrl("pu1").setType("t1").setUrl("u1");
		Assert.assertEquals("f1", b.getFamily());
		Assert.assertEquals("n1", b.getName());
		Assert.assertEquals(os, b.getOperatingSystem());
		Assert.assertEquals("p1", b.getProducer());
		Assert.assertEquals("pu1", b.getProducerUrl());
		Assert.assertEquals("t1", b.getType());
		Assert.assertEquals("u1", b.getUrl());

		final UserAgent ua = b.build();
		Assert.assertNotNull(ua);
		Assert.assertEquals("f1", ua.getFamily());
		Assert.assertEquals("n1", ua.getName());
		Assert.assertEquals(os, ua.getOperatingSystem());
		Assert.assertEquals("p1", ua.getProducer());
		Assert.assertEquals("pu1", ua.getProducerUrl());
		Assert.assertEquals("t1", ua.getType());
		Assert.assertEquals("u1", ua.getUrl());
	}

	@Test
	public void build_empty() {
		final UserAgent.Builder b = new UserAgent.Builder();
		Assert.assertEquals(UserAgent.EMPTY.getFamily(), b.getFamily());
		Assert.assertEquals(UserAgent.EMPTY.getName(), b.getName());
		Assert.assertEquals(UserAgent.EMPTY.getOperatingSystem(), b.getOperatingSystem());
		Assert.assertEquals(UserAgent.EMPTY.getProducer(), b.getProducer());
		Assert.assertEquals(UserAgent.EMPTY.getProducerUrl(), b.getProducerUrl());
		Assert.assertEquals(UserAgent.EMPTY.getType(), b.getType());
		Assert.assertEquals(UserAgent.EMPTY.getUrl(), b.getUrl());
		Assert.assertEquals(UserAgent.EMPTY, b.build());

		final UserAgent ua = b.build();
		Assert.assertEquals(UserAgent.EMPTY.getFamily(), ua.getFamily());
		Assert.assertEquals(UserAgent.EMPTY.getName(), ua.getName());
		Assert.assertEquals(UserAgent.EMPTY.getOperatingSystem(), ua.getOperatingSystem());
		Assert.assertEquals(UserAgent.EMPTY.getProducer(), ua.getProducer());
		Assert.assertEquals(UserAgent.EMPTY.getProducerUrl(), ua.getProducerUrl());
		Assert.assertEquals(UserAgent.EMPTY.getType(), ua.getType());
		Assert.assertEquals(UserAgent.EMPTY.getUrl(), ua.getUrl());
		Assert.assertEquals(UserAgent.EMPTY, b.build());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setFamily_null() {
		new UserAgent.Builder().setFamily(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setName_null() {
		new UserAgent.Builder().setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setOperatingSystem_null() {
		new UserAgent.Builder().setOperatingSystem((OperatingSystem) null);
	}

	@Test
	public void setOperatingSystem_ReadableOperatingSystem() {
		final ReadableOperatingSystem os = EasyMock.createMock(ReadableOperatingSystem.class);
		EasyMock.expect(os.getFamily()).andReturn("f1").anyTimes();
		EasyMock.expect(os.getName()).andReturn("n1").anyTimes();
		EasyMock.expect(os.getProducer()).andReturn("p1").anyTimes();
		EasyMock.expect(os.getProducerUrl()).andReturn("pu1").anyTimes();
		EasyMock.expect(os.getUrl()).andReturn("u1").anyTimes();
		EasyMock.replay(os);
		new UserAgent.Builder().setOperatingSystem(os);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setOperatingSystem_ReadableOperatingSystem_invalidFamily() {
		final ReadableOperatingSystem os = EasyMock.createMock(ReadableOperatingSystem.class);
		EasyMock.expect(os.getFamily()).andReturn(null).anyTimes();
		EasyMock.expect(os.getName()).andReturn("n1").anyTimes();
		EasyMock.expect(os.getProducer()).andReturn("p1").anyTimes();
		EasyMock.expect(os.getProducerUrl()).andReturn("pu1").anyTimes();
		EasyMock.expect(os.getUrl()).andReturn("u1").anyTimes();
		EasyMock.replay(os);
		new UserAgent.Builder().setOperatingSystem(os);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setOperatingSystem_ReadableOperatingSystem_null() {
		new UserAgent.Builder().setOperatingSystem((ReadableOperatingSystem) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setProducer_null() {
		new UserAgent.Builder().setProducer(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setProducerUrl_null() {
		new UserAgent.Builder().setProducerUrl(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setType_null() {
		new UserAgent.Builder().setType(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setUrl_null() {
		new UserAgent.Builder().setUrl(null);
	}

}
