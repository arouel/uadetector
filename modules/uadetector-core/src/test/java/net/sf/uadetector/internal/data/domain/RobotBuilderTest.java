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
package net.sf.uadetector.internal.data.domain;

import org.junit.Assert;
import org.junit.Test;

public class RobotBuilderTest {

	@Test(expected = IllegalArgumentException.class)
	public void setFamily_null() {
		new Robot.Builder().setFamily(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setIcon_null() {
		new Robot.Builder().setIcon(null);
	}

	@Test(expected = NumberFormatException.class)
	public void setId_alphaString() {
		new Robot.Builder().setId("abc");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_emptyString() {
		new Robot.Builder().setId("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_null() {
		new Robot.Builder().setId(null);
	}

	@Test
	public void setId_numericString() {
		final Robot b1 = new Robot.Builder().setId("1").setFamily("f1").setIcon("i1").setInfoUrl("iu1").setName("n1").setProducer("p1")
				.setProducerUrl("pu1").setUrl("u1").build();
		final Robot b2 = new Robot.Builder().setId(1).setFamily("f1").setIcon("i1").setInfoUrl("iu1").setName("n1").setProducer("p1")
				.setProducerUrl("pu1").setUrl("u1").build();
		Assert.assertTrue(b1.equals(b2));
		Assert.assertTrue(b1.hashCode() == b2.hashCode());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_toSmall() {
		new Robot.Builder().setId(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInfoUrl_null() {
		new Robot.Builder().setInfoUrl(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setName_null() {
		new Robot.Builder().setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setProducer_null() {
		new Robot.Builder().setProducer(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setProducerUrl_null() {
		new Robot.Builder().setProducerUrl(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setUrl_null() {
		new Robot.Builder().setUrl(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setUserAgentString_null() {
		new Robot.Builder().setUserAgentString(null);
	}

	@Test
	public void testGetters() throws Exception {
		final Robot.Builder builder = new Robot.Builder();
		Assert.assertSame(builder, builder.setFamily("f1"));
		Assert.assertSame(builder, builder.setIcon("i1"));
		Assert.assertSame(builder, builder.setId(1));
		Assert.assertSame(builder, builder.setInfoUrl("iu1"));
		Assert.assertSame(builder, builder.setName("n1"));
		Assert.assertSame(builder, builder.setProducer("p1"));
		Assert.assertSame(builder, builder.setProducerUrl("pu1"));
		Assert.assertSame(builder, builder.setUrl("u1"));
		Assert.assertSame(builder, builder.setUserAgentString("uas1"));

		Assert.assertEquals("f1", builder.getFamily());
		Assert.assertEquals("i1", builder.getIcon());
		Assert.assertEquals(1, builder.getId());
		Assert.assertEquals("iu1", builder.getInfoUrl());
		Assert.assertEquals("n1", builder.getName());
		Assert.assertEquals("p1", builder.getProducer());
		Assert.assertEquals("pu1", builder.getProducerUrl());
		Assert.assertEquals("u1", builder.getUrl());
		Assert.assertEquals("uas1", builder.getUserAgentString());

		final Robot browser = builder.build();
		Assert.assertEquals("f1", browser.getFamily());
		Assert.assertEquals("i1", browser.getIcon());
		Assert.assertEquals(1, browser.getId());
		Assert.assertEquals("iu1", browser.getInfoUrl());
		Assert.assertEquals("n1", browser.getName());
		Assert.assertEquals("p1", browser.getProducer());
		Assert.assertEquals("pu1", browser.getProducerUrl());
		Assert.assertEquals("u1", browser.getUrl());
	}

}
