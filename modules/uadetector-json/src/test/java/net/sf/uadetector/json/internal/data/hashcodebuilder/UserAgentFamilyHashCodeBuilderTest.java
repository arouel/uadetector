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

import net.sf.uadetector.UserAgentFamily;

import org.junit.Assert;
import org.junit.Test;

public class UserAgentFamilyHashCodeBuilderTest {

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<UserAgentFamilyHashCodeBuilder> constructor = UserAgentFamilyHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void test() {
		final String hash1 = UserAgentFamilyHashCodeBuilder.build(UserAgentFamily.CAMINO);
		final String hash2 = UserAgentFamilyHashCodeBuilder.build(UserAgentFamily.CAMINO);
		Assert.assertEquals(hash1, hash2);
		Assert.assertEquals("565bfb6dc1b336d2f42c7d27e12e3f8a75fc8441580002b3fa5e5431e90b1a52", hash2);
	}

}
