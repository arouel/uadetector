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

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class OperatingSystemBuilderTest {

	@Test(expected = IllegalArgumentException.class)
	public void construct_builder_null() {
		new OperatingSystem.Builder(null);
	}

	@Test
	public void construct_copy_successful() {
		final Set<OperatingSystemPattern> patternSet = new HashSet<OperatingSystemPattern>();
		patternSet.add(new OperatingSystemPattern(2, Pattern.compile("[0-9]+"), 2));
		patternSet.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));

		final OperatingSystem.Builder original = new OperatingSystem.Builder();
		original.setFamily("f1");
		original.setIcon("i1");
		original.setId(1);
		original.setInfoUrl("iu1");
		original.setName("n1");
		original.addPatternSet(patternSet);
		original.setProducer("p1");
		original.setProducerUrl("pu1");
		original.setUrl("u1");

		final OperatingSystem.Builder copy1 = new OperatingSystem.Builder(original);
		Assert.assertEquals("f1", copy1.getFamily());
		Assert.assertEquals("i1", copy1.getIcon());
		Assert.assertEquals(1, copy1.getId());
		Assert.assertEquals("iu1", copy1.getInfoUrl());
		Assert.assertEquals("n1", copy1.getName());
		Assert.assertEquals(2, copy1.getPatternSet().size());
		Assert.assertEquals(patternSet, copy1.getPatternSet());
		Assert.assertEquals("p1", copy1.getProducer());
		Assert.assertEquals("pu1", copy1.getProducerUrl());
		Assert.assertEquals("u1", copy1.getUrl());

		final OperatingSystem.Builder copy2 = original.copy();
		Assert.assertEquals("f1", copy2.getFamily());
		Assert.assertEquals("i1", copy2.getIcon());
		Assert.assertEquals(1, copy2.getId());
		Assert.assertEquals("iu1", copy2.getInfoUrl());
		Assert.assertEquals("n1", copy2.getName());
		Assert.assertEquals(2, copy2.getPatternSet().size());
		Assert.assertEquals(patternSet, copy2.getPatternSet());
		Assert.assertEquals("p1", copy2.getProducer());
		Assert.assertEquals("pu1", copy2.getProducerUrl());
		Assert.assertEquals("u1", copy2.getUrl());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setFamily_null() {
		new OperatingSystem.Builder().setFamily(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setIcon_null() {
		new OperatingSystem.Builder().setIcon(null);
	}

	@Test(expected = NumberFormatException.class)
	public void setId_alphaString() {
		new OperatingSystem.Builder().setId("abc");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_emptyString() {
		new OperatingSystem.Builder().setId("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_null() {
		new OperatingSystem.Builder().setId(null);
	}

	@Test
	public void setId_numericString() {
		final OperatingSystem b1 = new OperatingSystem.Builder().setId("1").setFamily("f1").setIcon("i1").setInfoUrl("iu1")
				.setProducer("p1").setProducerUrl("pu1").setUrl("u1").build();
		final OperatingSystem b2 = new OperatingSystem.Builder().setId(1).setFamily("f1").setIcon("i1").setInfoUrl("iu1").setProducer("p1")
				.setProducerUrl("pu1").setUrl("u1").build();
		Assert.assertTrue(b1.equals(b2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_toSmall() {
		new OperatingSystem.Builder().setId(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInfoUrl_null() {
		new OperatingSystem.Builder().setInfoUrl(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setName_null() {
		new OperatingSystem.Builder().setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setPatternSet_null() {
		new OperatingSystem.Builder().addPatternSet(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setProducer_null() {
		new OperatingSystem.Builder().setProducer(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setProducerUrl_null() {
		new OperatingSystem.Builder().setProducerUrl(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setUrl_null() {
		new OperatingSystem.Builder().setUrl(null);
	}

	@Test
	public void testGetters() throws Exception {
		final Set<OperatingSystemPattern> patternSet = new HashSet<OperatingSystemPattern>();
		patternSet.add(new OperatingSystemPattern(2, Pattern.compile("[0-9]+"), 2));
		patternSet.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));

		final OperatingSystem.Builder builder = new OperatingSystem.Builder();
		Assert.assertSame(builder, builder.setFamily("f1"));
		Assert.assertSame(builder, builder.setIcon("i1"));
		Assert.assertSame(builder, builder.setId(1));
		Assert.assertSame(builder, builder.setInfoUrl("iu1"));
		Assert.assertSame(builder, builder.setName("n1"));
		Assert.assertSame(builder, builder.addPatternSet(patternSet));
		Assert.assertSame(builder, builder.setProducer("p1"));
		Assert.assertSame(builder, builder.setProducerUrl("pu1"));
		Assert.assertSame(builder, builder.setUrl("u1"));

		Assert.assertEquals("f1", builder.getFamily());
		Assert.assertEquals("i1", builder.getIcon());
		Assert.assertEquals(1, builder.getId());
		Assert.assertEquals("iu1", builder.getInfoUrl());
		Assert.assertEquals("n1", builder.getName());
		Assert.assertEquals(patternSet, builder.getPatternSet());
		Assert.assertEquals("p1", builder.getProducer());
		Assert.assertEquals("pu1", builder.getProducerUrl());
		Assert.assertEquals("u1", builder.getUrl());

		final OperatingSystem os = builder.build();
		Assert.assertEquals("f1", os.getFamily());
		Assert.assertEquals("i1", os.getIcon());
		Assert.assertEquals(1, os.getId());
		Assert.assertEquals("iu1", os.getInfoUrl());
		Assert.assertEquals("n1", os.getName());
		Assert.assertEquals("p1", os.getProducer());
		Assert.assertEquals("pu1", os.getProducerUrl());
		Assert.assertEquals("u1", os.getUrl());
	}

}
