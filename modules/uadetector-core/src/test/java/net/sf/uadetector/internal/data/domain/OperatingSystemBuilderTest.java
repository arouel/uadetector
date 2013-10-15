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
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class OperatingSystemBuilderTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void addPatterns_null() {
		new OperatingSystem.Builder().addPatterns(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_builder_builderIsNull() {
		new OperatingSystem.Builder((OperatingSystem.Builder) null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_builder_osIsNull() {
		new OperatingSystem.Builder((OperatingSystem) null);
	}

	@Test
	public void construct_copy_successful() {
		final Set<OperatingSystemPattern> patterns = new HashSet<OperatingSystemPattern>();
		patterns.add(new OperatingSystemPattern(2, Pattern.compile("[0-9]+"), 2));
		patterns.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));

		final OperatingSystem.Builder original = new OperatingSystem.Builder();
		original.setFamily("f1");
		original.setIcon("i1");
		original.setId(1);
		original.setInfoUrl("iu1");
		original.setName("n1");
		original.addPatterns(patterns);
		original.setProducer("p1");
		original.setProducerUrl("pu1");
		original.setUrl("u1");

		final OperatingSystem.Builder copy1 = new OperatingSystem.Builder(original);
		assertThat(copy1.getFamily()).isEqualTo("f1");
		assertThat(copy1.getIcon()).isEqualTo("i1");
		assertThat(copy1.getId()).isEqualTo(1);
		assertThat(copy1.getInfoUrl()).isEqualTo("iu1");
		assertThat(copy1.getName()).isEqualTo("n1");
		assertThat(copy1.getPatterns()).hasSize(2);
		assertThat(copy1.getPatterns()).isEqualTo(patterns);
		assertThat(copy1.getProducer()).isEqualTo("p1");
		assertThat(copy1.getProducerUrl()).isEqualTo("pu1");
		assertThat(copy1.getUrl()).isEqualTo("u1");

		final OperatingSystem.Builder copy2 = original.copy();
		assertThat(copy2.getFamily()).isEqualTo("f1");
		assertThat(copy2.getIcon()).isEqualTo("i1");
		assertThat(copy2.getId()).isEqualTo(1);
		assertThat(copy2.getInfoUrl()).isEqualTo("iu1");
		assertThat(copy2.getName()).isEqualTo("n1");
		assertThat(copy2.getPatterns()).hasSize(2);
		assertThat(copy2.getPatterns()).isEqualTo(patterns);
		assertThat(copy2.getProducer()).isEqualTo("p1");
		assertThat(copy2.getProducerUrl()).isEqualTo("pu1");
		assertThat(copy2.getUrl()).isEqualTo("u1");

		final OperatingSystem.Builder copy3 = new OperatingSystem.Builder(original.build());
		assertThat(copy3.getFamily()).isEqualTo("f1");
		assertThat(copy3.getIcon()).isEqualTo("i1");
		assertThat(copy3.getId()).isEqualTo(1);
		assertThat(copy3.getInfoUrl()).isEqualTo("iu1");
		assertThat(copy3.getName()).isEqualTo("n1");
		assertThat(copy3.getPatterns()).hasSize(2);
		assertThat(copy3.getPatterns()).isEqualTo(patterns);
		assertThat(copy3.getProducer()).isEqualTo("p1");
		assertThat(copy3.getProducerUrl()).isEqualTo("pu1");
		assertThat(copy3.getUrl()).isEqualTo("u1");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setFamily_null() {
		new OperatingSystem.Builder().setFamily(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setIcon_null() {
		new OperatingSystem.Builder().setIcon(null);
	}

	@Test(expected = NumberFormatException.class)
	public void setId_alphaString() {
		new OperatingSystem.Builder().setId("abc");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void setId_emptyString() {
		new OperatingSystem.Builder().setId("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setId_null() {
		new OperatingSystem.Builder().setId(null);
	}

	@Test
	public void setId_numericString() {
		final OperatingSystem b1 = new OperatingSystem.Builder().setId("1").setFamily("f1").setIcon("i1").setInfoUrl("iu1")
				.setProducer("p1").setProducerUrl("pu1").setUrl("u1").build();
		final OperatingSystem b2 = new OperatingSystem.Builder().setId(1).setFamily("f1").setIcon("i1").setInfoUrl("iu1").setProducer("p1")
				.setProducerUrl("pu1").setUrl("u1").build();
		assertThat(b1.equals(b2)).isTrue();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void setId_toSmall() {
		new OperatingSystem.Builder().setId(-1);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setInfoUrl_null() {
		new OperatingSystem.Builder().setInfoUrl(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setName_null() {
		new OperatingSystem.Builder().setName(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setPatterns_null() {
		new OperatingSystem.Builder().setPatterns(null);
	}

	@Test
	public void setPatterns_successful() {
		final SortedSet<OperatingSystemPattern> patterns = new TreeSet<OperatingSystemPattern>();
		patterns.add(new OperatingSystemPattern(1, Pattern.compile("d"), 2));
		patterns.add(new OperatingSystemPattern(4, Pattern.compile("g"), 8));
		final OperatingSystem.Builder builder = new OperatingSystem.Builder().setPatterns(patterns);
		assertThat(builder.getPatterns()).isNotSameAs(patterns);
		assertThat(builder.getPatterns()).isEqualTo(patterns);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setProducer_null() {
		new OperatingSystem.Builder().setProducer(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setProducerUrl_null() {
		new OperatingSystem.Builder().setProducerUrl(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setUrl_null() {
		new OperatingSystem.Builder().setUrl(null);
	}

	@Test
	public void testGetters() throws Exception {
		final Set<OperatingSystemPattern> patterns = new HashSet<OperatingSystemPattern>();
		patterns.add(new OperatingSystemPattern(2, Pattern.compile("[0-9]+"), 2));
		patterns.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));

		final OperatingSystem.Builder builder = new OperatingSystem.Builder();
		assertThat(builder.setFamily("f1")).isSameAs(builder);
		assertThat(builder.setIcon("i1")).isSameAs(builder);
		assertThat(builder.setId(1)).isSameAs(builder);
		assertThat(builder.setInfoUrl("iu1")).isSameAs(builder);
		assertThat(builder.setName("n1")).isSameAs(builder);
		assertThat(builder.addPatterns(patterns)).isSameAs(builder);
		assertThat(builder.setProducer("p1")).isSameAs(builder);
		assertThat(builder.setProducerUrl("pu1")).isSameAs(builder);
		assertThat(builder.setUrl("u1")).isSameAs(builder);

		assertThat(builder.getFamily()).isEqualTo("f1");
		assertThat(builder.getIcon()).isEqualTo("i1");
		assertThat(builder.getId()).isEqualTo(1);
		assertThat(builder.getInfoUrl()).isEqualTo("iu1");
		assertThat(builder.getName()).isEqualTo("n1");
		assertThat(builder.getPatterns()).isEqualTo(patterns);
		assertThat(builder.getProducer()).isEqualTo("p1");
		assertThat(builder.getProducerUrl()).isEqualTo("pu1");
		assertThat(builder.getUrl()).isEqualTo("u1");

		final OperatingSystem os = builder.build();
		assertThat(os.getFamily()).isEqualTo("f1");
		assertThat(os.getIcon()).isEqualTo("i1");
		assertThat(os.getId()).isEqualTo(1);
		assertThat(os.getInfoUrl()).isEqualTo("iu1");
		assertThat(os.getName()).isEqualTo("n1");
		assertThat(os.getProducer()).isEqualTo("p1");
		assertThat(os.getProducerUrl()).isEqualTo("pu1");
		assertThat(os.getUrl()).isEqualTo("u1");
	}

}
