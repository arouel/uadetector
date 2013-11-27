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

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Constructor;

import net.sf.uadetector.UserAgentFamily;

import org.junit.Test;

public class HashCodeGeneratorTest {

	@Test
	public void generate_forBrowser() {
		final String hash = HashCodeGenerator.generate(BrowserHashCodeBuilderTest.create());
		assertThat(hash.isEmpty()).isFalse();
	}

	@Test
	public void generate_forBrowserPattern() {
		final String hash = HashCodeGenerator.generate(OrderedPatternHashCodeBuilderTest.createBrowserPattern());
		assertThat(hash.isEmpty()).isFalse();
	}

	@Test
	public void generate_forBrowserType() {
		final String hash = HashCodeGenerator.generate(BrowserTypeHashCodeBuilderTest.create());
		assertThat(hash.isEmpty()).isFalse();
	}

	@Test
	public void generate_forDevice() {
		final String hash = HashCodeGenerator.generate(DeviceHashCodeBuilderTest.create());
		assertThat(hash.isEmpty()).isFalse();
	}

	@Test
	public void generate_forOperatingSystem() {
		final String hash = HashCodeGenerator.generate(OperatingSystemHashCodeBuilderTest.create());
		assertThat(hash.isEmpty()).isFalse();
	}

	@Test
	public void generate_forOperatingSystemPattern() {
		final String hash = HashCodeGenerator.generate(OrderedPatternHashCodeBuilderTest.createOperatingSystemPattern());
		assertThat(hash.isEmpty()).isFalse();
	}

	@Test
	public void generate_forUserAgentFamily() {
		final String hash = HashCodeGenerator.generate(UserAgentFamily.FIREFOX);
		assertThat(hash.isEmpty()).isFalse();
	}

	@Test
	public void generate_UNKNOWN() {
		final String hash = HashCodeGenerator.generate("unknown type");
		assertThat(hash.isEmpty()).isTrue();
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<BrowserTypeHashCodeBuilder> constructor = BrowserTypeHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

}
