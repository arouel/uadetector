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

import net.sf.uadetector.UserAgent;

import org.junit.Assert;
import org.junit.Test;

public class RobotTest {

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
		final String userAgentString = "I'm a robot";
		new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
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
		final String userAgentString = "I'm a robot";
		new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
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
		final String userAgentString = "I'm a robot";
		new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
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
		final String userAgentString = "I'm a robot";
		new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
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
		final String userAgentString = "I'm a robot";
		new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
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
		final String userAgentString = "I'm a robot";
		new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
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
		final String userAgentString = "I'm a robot";
		new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
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
		final String userAgentString = "I'm a robot";
		new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
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
		final String userAgentString = "I'm a robot";
		new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_userAgentString_null() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String family = "family";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final String userAgentString = null;
		new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
	}

	@Test
	public void copyTo_successful() {
		final Robot robot = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final UserAgent.Builder builder = new UserAgent.Builder();
		robot.copyTo(builder);
		Assert.assertEquals(robot.getFamily(), builder.getFamily());
		Assert.assertEquals(robot.getName(), builder.getName());
		Assert.assertEquals(robot.getProducer(), builder.getProducer());
		Assert.assertEquals(robot.getProducerUrl(), builder.getProducerUrl());
		Assert.assertEquals(robot.getUrl(), builder.getUrl());
		Assert.assertNotNull(builder.getOperatingSystem());
	}

	@Test
	public void equals_differentFamily() {
		final Robot robot1 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Robot robot2 = new Robot("f2", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		Assert.assertFalse(robot1.hashCode() == robot2.hashCode());
		Assert.assertFalse(robot1.equals(robot2));
	}

	@Test
	public void equals_differentIcon() {
		final Robot robot1 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Robot robot2 = new Robot("f1", "i2", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		Assert.assertFalse(robot1.hashCode() == robot2.hashCode());
		Assert.assertFalse(robot1.equals(robot2));
	}

	@Test
	public void equals_differentId() {
		final Robot robot1 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Robot robot2 = new Robot("f1", "i1", 2, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		Assert.assertFalse(robot1.hashCode() == robot2.hashCode());
		Assert.assertFalse(robot1.equals(robot2));
	}

	@Test
	public void equals_differentInfoUrl() {
		final Robot robot1 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Robot robot2 = new Robot("f1", "i1", 1, "iu2", "n1", "p1", "pu1", "u1", "uas1");
		Assert.assertFalse(robot1.hashCode() == robot2.hashCode());
		Assert.assertFalse(robot1.equals(robot2));
	}

	@Test
	public void equals_differentName() {
		final Robot robot1 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Robot robot2 = new Robot("f1", "i1", 1, "iu1", "n2", "p1", "pu1", "u1", "uas1");
		Assert.assertFalse(robot1.hashCode() == robot2.hashCode());
		Assert.assertFalse(robot1.equals(robot2));
	}

	@Test
	public void equals_differentProducer() {
		final Robot robot1 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Robot robot2 = new Robot("f1", "i1", 1, "iu1", "n1", "p2", "pu1", "u1", "uas1");
		Assert.assertFalse(robot1.hashCode() == robot2.hashCode());
		Assert.assertFalse(robot1.equals(robot2));
	}

	@Test
	public void equals_differentProducerUrl() {
		final Robot robot1 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Robot robot2 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu2", "u1", "uas1");
		Assert.assertFalse(robot1.hashCode() == robot2.hashCode());
		Assert.assertFalse(robot1.equals(robot2));
	}

	@Test
	public void equals_differentUrl() {
		final Robot robot1 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Robot robot2 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u2", "uas1");
		Assert.assertFalse(robot1.hashCode() == robot2.hashCode());
		Assert.assertFalse(robot1.equals(robot2));
	}

	@Test
	public void equals_differentUserAgentString() {
		final Robot robot1 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Robot robot2 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas2");
		Assert.assertFalse(robot1.hashCode() == robot2.hashCode());
		Assert.assertFalse(robot1.equals(robot2));
		Assert.assertFalse(robot2.equals(robot1));
	}

	@Test
	public void equals_identical() {
		final Robot robot1 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final Robot robot2 = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		Assert.assertTrue(robot1.hashCode() == robot2.hashCode());
		Assert.assertTrue(robot1.equals(robot2));
	}

	@Test
	public void equals_null() {
		final Robot robot = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		Assert.assertFalse(robot.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Robot robot = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		final String otherClass = "";
		Assert.assertFalse(robot.equals(otherClass));
	}

	@Test
	public void equals_same() {
		final Robot robot = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		Assert.assertTrue(robot.equals(robot));
		Assert.assertTrue(robot.hashCode() == robot.hashCode());
	}

	@Test
	public void testGetters() {
		final int id = 12354;
		final String icon = "bunt.png";
		final String infoUrl = "http://programming-motherfucker.com/";
		final String name = "Programming, Motherfucker";
		final String family = "Learn Code The Hard Way";
		final String producerUrl = "https://github.com/before";
		final String producer = "Our Values";
		final String url = "http://user-agent-string.info/";
		final String userAgentString = "I'm a robot";
		final Robot b = new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
		Assert.assertEquals("Learn Code The Hard Way", b.getFamily());
		Assert.assertEquals("bunt.png", b.getIcon());
		Assert.assertEquals(12354, b.getId());
		Assert.assertEquals("http://programming-motherfucker.com/", b.getInfoUrl());
		Assert.assertEquals("Programming, Motherfucker", b.getName());
		Assert.assertEquals("Our Values", b.getProducer());
		Assert.assertEquals("https://github.com/before", b.getProducerUrl());
		Assert.assertEquals("http://user-agent-string.info/", b.getUrl());
		Assert.assertEquals("I'm a robot", b.getUserAgentString());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final Robot robot = new Robot("f1", "i1", 1, "iu1", "n1", "p1", "pu1", "u1", "uas1");
		Assert.assertEquals(
				"Robot [family=f1, icon=i1, id=1, infoUrl=iu1, name=n1, producer=p1, producerUrl=pu1, url=u1, userAgentString=uas1]",
				robot.toString());
	}

}
