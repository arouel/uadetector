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

import org.junit.Assert;
import org.junit.Test;

public class UserAgentTest {

	@Test(expected = IllegalArgumentException.class)
	public void construct_family_null() {
		new UserAgent(null, "name", OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_name_null() {
		new UserAgent("family", null, OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_os_null() {
		new UserAgent("family", "name", null, "producer", "producer url", "type", "url");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_producer_null() {
		new UserAgent("family", "name", OperatingSystem.EMPTY, null, "producer url", "type", "url");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_producerUrl_null() {
		new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", null, "type", "url");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_type_null() {
		new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", null, "url");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_url_null() {
		new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type", null);
	}

	@Test
	public void empty() {
		final UserAgent ua = UserAgent.EMPTY;
		Assert.assertEquals("unknown", ua.getFamily());
		Assert.assertEquals("unknown", ua.getName());
		Assert.assertEquals("", ua.getProducer());
		Assert.assertEquals("", ua.getProducerUrl());
		Assert.assertEquals("", ua.getUrl());
	}

	@Test
	public void equals_differentFamily() {
		final UserAgent ua1 = new UserAgent("family1", "name", OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
		final UserAgent ua2 = new UserAgent("family2", "name", OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
		Assert.assertFalse(ua1.equals(ua2));
	}

	@Test
	public void equals_differentName() {
		final UserAgent ua1 = new UserAgent("family", "name1", OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
		final UserAgent ua2 = new UserAgent("family", "name2", OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
		Assert.assertFalse(ua1.equals(ua2));
	}

	@Test
	public void equals_differentOperatingSystem() {
		final OperatingSystem os1 = new OperatingSystem("family", "name1", "producer", "producer url", "url");
		final OperatingSystem os2 = new OperatingSystem("family", "name2", "producer", "producer url", "url");
		final UserAgent ua1 = new UserAgent("family", "name", os1, "producer", "producer url", "type", "url");
		final UserAgent ua2 = new UserAgent("family", "name", os2, "producer", "producer url", "type", "url");
		Assert.assertFalse(ua1.equals(ua2));
	}

	@Test
	public void equals_differentProducer() {
		final UserAgent ua1 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer1", "producer url", "type", "url");
		final UserAgent ua2 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer2", "producer url", "type", "url");
		Assert.assertFalse(ua1.equals(ua2));
	}

	@Test
	public void equals_differentProducerUrl() {
		final UserAgent ua1 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url 1", "type", "url");
		final UserAgent ua2 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url 2", "type", "url");
		Assert.assertFalse(ua1.equals(ua2));
	}

	@Test
	public void equals_differentType() {
		final UserAgent ua1 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type1", "url");
		final UserAgent ua2 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type2", "url");
		Assert.assertFalse(ua1.equals(ua2));
	}

	@Test
	public void equals_differentUrl() {
		final UserAgent ua1 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type", "url1");
		final UserAgent ua2 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type", "url2");
		Assert.assertFalse(ua1.equals(ua2));
	}

	@Test
	public void equals_EMPTY() {
		Assert.assertEquals(UserAgent.EMPTY, UserAgent.EMPTY);
		Assert.assertSame(UserAgent.EMPTY, UserAgent.EMPTY);
	}

	@Test
	public void equals_identical() {
		final UserAgent ua1 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
		final UserAgent ua2 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
		Assert.assertEquals(ua1, ua2);
	}

	@Test
	public void equals_null() {
		Assert.assertFalse(UserAgent.EMPTY.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final UserAgent ua = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type1", "url");
		Assert.assertFalse(ua.equals(OperatingSystem.EMPTY));
	}

	@Test
	public void testGetters() {
		final UserAgent ua = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
		Assert.assertEquals("family", ua.getFamily());
		Assert.assertEquals("name", ua.getName());
		Assert.assertEquals(OperatingSystem.EMPTY, ua.getOperatingSystem());
		Assert.assertEquals("producer", ua.getProducer());
		Assert.assertEquals("producer url", ua.getProducerUrl());
		Assert.assertEquals("type", ua.getType());
		Assert.assertEquals("url", ua.getUrl());
	}

	@Test
	public void testHashCode() {
		final UserAgent ua1 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
		final UserAgent ua2 = new UserAgent("family", "name", OperatingSystem.EMPTY, "producer", "producer url", "type", "url");
		Assert.assertEquals(ua1.hashCode(), ua2.hashCode());
	}

	@Test
	public void testHashCode_EMPTY() {
		Assert.assertEquals(UserAgent.EMPTY.hashCode(), UserAgent.EMPTY.hashCode());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final UserAgent ua = new UserAgent("f1", "n1", OperatingSystem.EMPTY, "p1", "pu1", "t1", "u1");
		Assert.assertEquals(
				"UserAgent [family=f1, name=n1, operatingSystem=OperatingSystem [family=unknown, name=unknown, producer=, producerUrl=, url=], producer=p1, producerUrl=pu1, type=t1, url=u1]",
				ua.toString());
	}

}
