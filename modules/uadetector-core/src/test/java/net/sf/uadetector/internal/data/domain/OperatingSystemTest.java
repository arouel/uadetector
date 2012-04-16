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
package net.sf.uadetector.internal.data.domain;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.uadetector.UserAgent;

import org.junit.Assert;
import org.junit.Test;

public class OperatingSystemTest {

	@Test(expected = IllegalArgumentException.class)
	public void construct_family_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String family = null;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_icon_null() {
		final int id = 1;
		final String icon = null;
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String family = "family";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_id_toSmall() {
		final int id = -1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String family = "family";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_infoUrl_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = null;
		final String name = "name";
		final String url = "url";
		final String family = "family";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_name_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final String name = null;
		final String family = "family";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_patternSet_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String family = "family";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = null;
		new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_producer_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String family = "family";
		final String producerUrl = "producer url";
		final String producer = null;
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_producerUrl_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String family = "family";
		final String producerUrl = null;
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test
	public void construct_successful() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String family = "family";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_url_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = null;
		final String family = "family";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
	}

	@Test
	public void copyTo_successful() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final UserAgent.Builder builder = new UserAgent.Builder();
		os.copyTo(builder);
		Assert.assertEquals(os.getFamily(), builder.getOperatingSystem().getFamily());
		Assert.assertEquals(os.getName(), builder.getOperatingSystem().getName());
		Assert.assertEquals(os.getProducer(), builder.getOperatingSystem().getProducer());
		Assert.assertEquals(os.getProducerUrl(), builder.getOperatingSystem().getProducerUrl());
		Assert.assertEquals(os.getUrl(), builder.getOperatingSystem().getUrl());
	}

	@Test
	public void equals_differentFamily() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f2", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentIcon() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f1", "i2", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentId() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 2, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentInfoUrl() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 1, "iu2", "n1", osPatternSet, "p1", "pu1", "u1");
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentName() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 1, "iu1", "n2", osPatternSet, "p1", "pu1", "u1");
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentPatternSet() {
		final SortedSet<OperatingSystemPattern> patternSet1 = new TreeSet<OperatingSystemPattern>();
		patternSet1.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));
		final SortedSet<OperatingSystemPattern> patternSet2 = new TreeSet<OperatingSystemPattern>();
		patternSet2.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 2));
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", patternSet1, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", patternSet2, "p1", "pu1", "u1");
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
		Assert.assertFalse(os1.equals(os2));
		Assert.assertFalse(os2.equals(os1));
	}

	@Test
	public void equals_differentProducer() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p2", "pu1", "u1");
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentProducerUrl() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu2", "u1");
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_differentUrl() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u2");
		Assert.assertFalse(os1.hashCode() == os2.hashCode());
		Assert.assertFalse(os1.equals(os2));
	}

	@Test
	public void equals_identical_withIdenticalPatternSet() {
		final SortedSet<OperatingSystemPattern> patternSet1 = new TreeSet<OperatingSystemPattern>();
		patternSet1.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 2));
		final SortedSet<OperatingSystemPattern> patternSet2 = new TreeSet<OperatingSystemPattern>();
		patternSet2.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 2));
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", patternSet1, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", patternSet2, "p1", "pu1", "u1");
		Assert.assertTrue(os1.hashCode() == os2.hashCode());
		Assert.assertTrue(os1.equals(os2));
	}

	@Test
	public void equals_identical_withSamePatternSet() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os1 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final OperatingSystem os2 = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		Assert.assertTrue(os1.hashCode() == os2.hashCode());
		Assert.assertTrue(os1.equals(os2));
	}

	@Test
	public void equals_null() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		Assert.assertFalse(os.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		final String otherClass = "";
		Assert.assertFalse(os.equals(otherClass));
	}

	@Test
	public void equals_same() {
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", osPatternSet, "p1", "pu1", "u1");
		Assert.assertTrue(os.equals(os));
		Assert.assertTrue(os.hashCode() == os.hashCode());
	}

	@Test
	public void testGetters() {
		final int id = 12354;
		final String icon = "bunt.png";
		final String infoUrl = "http://programming-motherfucker.com/";
		final String name = "Programming, Motherfucker";
		final String url = "http://user-agent-string.info/";
		final String family = "Learn Code The Hard Way";
		final String producerUrl = "https://github.com/before";
		final String producer = "Our Values";
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem b = new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
		Assert.assertEquals("Learn Code The Hard Way", b.getFamily());
		Assert.assertEquals("bunt.png", b.getIcon());
		Assert.assertEquals(12354, b.getId());
		Assert.assertEquals("http://programming-motherfucker.com/", b.getInfoUrl());
		Assert.assertEquals("Programming, Motherfucker", b.getName());
		Assert.assertSame(patternSet, b.getPatternSet());
		Assert.assertEquals("Our Values", b.getProducer());
		Assert.assertEquals("https://github.com/before", b.getProducerUrl());
		Assert.assertEquals("http://user-agent-string.info/", b.getUrl());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();
		final OperatingSystem os = new OperatingSystem("f1", "i1", 1, "iu1", "n1", patternSet, "p1", "pu1", "u1");
		Assert.assertEquals(
				"OperatingSystem [family=f1, icon=i1, id=1, infoUrl=iu1, name=n1, patternSet=[], producer=p1, producerUrl=pu1, url=u1]",
				os.toString());
	}

}
