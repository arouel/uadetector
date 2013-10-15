/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.lang.reflect.Constructor;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class OperatingSystemHashCodeBuilderTest {

	protected static final OperatingSystem create() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String name = "name";
		final String url = "url";
		final String family = "f1";
		final String producerUrl = "producer url";
		final String producer = "producer";
		final SortedSet<OperatingSystemPattern> patterns = new TreeSet<OperatingSystemPattern>();
		patterns.add(new OperatingSystemPattern(1, Pattern.compile("[0-9]"), 1));
		patterns.add(new OperatingSystemPattern(2, Pattern.compile("[a-z]"), 2));
		return new OperatingSystem(id, name, family, infoUrl, patterns, producer, producerUrl, url, icon);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<OperatingSystemHashCodeBuilder> constructor = OperatingSystemHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void test() {
		final String hash1 = OperatingSystemHashCodeBuilder.build(create());
		final String hash2 = OperatingSystemHashCodeBuilder.build(create());
		assertThat(hash2).isEqualTo(hash1);
		assertThat(hash2).isEqualTo("cff51b9b31579a45cc4982ed536ef7ca162fce780024582d1f14782ba900b0e9");
	}

}
