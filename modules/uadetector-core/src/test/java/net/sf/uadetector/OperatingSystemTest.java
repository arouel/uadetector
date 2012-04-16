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

import org.junit.Assert;
import org.junit.Test;

public class OperatingSystemTest {

	@Test(expected = IllegalArgumentException.class)
	public void construct_family_null() {
		new OperatingSystem(null, "name", "producer", "producer url", "url");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_name_null() {
		new OperatingSystem("family", null, "producer", "producer url", "url");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_producer_null() {
		new OperatingSystem("family", "name", null, "producer url", "url");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_producerUrl_null() {
		new OperatingSystem("family", "name", "producer", null, "url");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_url_null() {
		new OperatingSystem("family", "name", "producer", "producer url", null);
	}

	@Test
	public void empty() {
		final OperatingSystem os = OperatingSystem.EMPTY;
		Assert.assertEquals("unknown", os.getFamily());
		Assert.assertEquals("unknown", os.getName());
		Assert.assertEquals("", os.getProducer());
		Assert.assertEquals("", os.getProducerUrl());
		Assert.assertEquals("", os.getUrl());
	}

	@Test
	public void equals_differentFamily() {
		final OperatingSystem os1 = new OperatingSystem("family1", "name", "producer", "producer url", "url");
		final OperatingSystem os2 = new OperatingSystem("family2", "name", "producer", "producer url", "url");
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentName() {
		final OperatingSystem os1 = new OperatingSystem("family", "name1", "producer", "producer url", "url");
		final OperatingSystem os2 = new OperatingSystem("family", "name2", "producer", "producer url", "url");
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentProducer() {
		final OperatingSystem os1 = new OperatingSystem("family", "name", "producer1", "producer url", "url");
		final OperatingSystem os2 = new OperatingSystem("family", "name", "producer2", "producer url", "url");
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentProducerUrl() {
		final OperatingSystem os1 = new OperatingSystem("family", "name", "producer", "producer url 1", "url");
		final OperatingSystem os2 = new OperatingSystem("family", "name", "producer", "producer url 2", "url");
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentUrl() {
		final OperatingSystem os1 = new OperatingSystem("family", "name", "producer", "producer url", "url1");
		final OperatingSystem os2 = new OperatingSystem("family", "name", "producer", "producer url", "url2");
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_EMPTY() {
		Assert.assertEquals(OperatingSystem.EMPTY, OperatingSystem.EMPTY);
		Assert.assertSame(OperatingSystem.EMPTY, OperatingSystem.EMPTY);
	}

	@Test
	public void equals_identical() {
		final OperatingSystem os1 = new OperatingSystem("family", "name", "producer", "producer url", "url");
		final OperatingSystem os2 = new OperatingSystem("family", "name", "producer", "producer url", "url");
		Assert.assertEquals(os1, os2);
	}

	@Test
	public void equals_null() {
		Assert.assertFalse(OperatingSystem.EMPTY.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final OperatingSystem os = new OperatingSystem("family", "name", "producer", "producer url", "url");
		Assert.assertFalse(os.equals(123456));
	}

	@Test
	public void testGetters() {
		final OperatingSystem os = new OperatingSystem("family", "name", "producer", "producer url", "url");
		Assert.assertEquals("family", os.getFamily());
		Assert.assertEquals("name", os.getName());
		Assert.assertEquals("producer", os.getProducer());
		Assert.assertEquals("producer url", os.getProducerUrl());
		Assert.assertEquals("url", os.getUrl());
	}

	@Test
	public void testHashCode() {
		final OperatingSystem os1 = new OperatingSystem("family", "name", "producer", "producer url", "url");
		final OperatingSystem os2 = new OperatingSystem("family", "name", "producer", "producer url", "url");
		Assert.assertEquals(os1.hashCode(), os2.hashCode());
	}

	@Test
	public void testHashCode_EMPTY() {
		Assert.assertEquals(OperatingSystem.EMPTY.hashCode(), OperatingSystem.EMPTY.hashCode());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final OperatingSystem ua = new OperatingSystem("f1", "n1", "p1", "pu1", "u1");
		Assert.assertEquals("OperatingSystem [family=f1, name=n1, producer=p1, producerUrl=pu1, url=u1]", ua.toString());
	}

}
