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
import java.util.regex.Pattern;

import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class OrderedPatternHashCodeBuilderTest {

	protected static final BrowserPattern createBrowserPattern() {
		return new BrowserPattern(1, Pattern.compile("[0-9]+"), 123);
	}

	protected static final OperatingSystemPattern createOperatingSystemPattern() {
		return new OperatingSystemPattern(3, Pattern.compile("[A-Z]+", Pattern.CASE_INSENSITIVE), 987);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<OrderedPatternHashCodeBuilder> constructor = OrderedPatternHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testWithBrowserPattern() {
		final String hash1 = OrderedPatternHashCodeBuilder.build(createBrowserPattern());
		final String hash2 = OrderedPatternHashCodeBuilder.build(createBrowserPattern());
		assertThat(hash2).isEqualTo(hash1);
		assertThat(hash2).isEqualTo("f0a6e328afe4c0d5c8d75852ba614af9a71e9ce8c8405bd36bb56a45943a700a");
	}

	@Test
	public void testWithOperatingSystemPattern() {
		final String hash1 = OrderedPatternHashCodeBuilder.build(createOperatingSystemPattern());
		final String hash2 = OrderedPatternHashCodeBuilder.build(createOperatingSystemPattern());
		assertThat(hash2).isEqualTo(hash1);
		assertThat(hash2).isEqualTo("7ab26ea8a1080050329c5a0426eb9bfb4c43ea45d84b29cfaf343583e49e1632");
	}

}
