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

import net.sf.uadetector.internal.data.domain.BrowserType;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class BrowserTypeHashCodeBuilderTest {

	protected static final BrowserType create() {
		return new BrowserType(1, "Browser");
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<BrowserTypeHashCodeBuilder> constructor = BrowserTypeHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void test() {
		final String hash1 = BrowserTypeHashCodeBuilder.build(create());
		final String hash2 = BrowserTypeHashCodeBuilder.build(create());
		assertThat(hash2).isEqualTo(hash1);
		assertThat(hash2).isEqualTo("d31de1a5c5c8ba2a210a167cf0d0dc2425c57ea7525f4b73a4b7ab934af79dfc");
	}

}
