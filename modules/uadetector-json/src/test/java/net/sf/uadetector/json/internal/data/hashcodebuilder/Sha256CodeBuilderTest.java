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

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class Sha256CodeBuilderTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void asHexString_withNullArg() {
		Sha256CodeBuilder.asHexString(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void getMessageDigest_noSuchAlgorithm() {
		Sha256CodeBuilder.getMessageDigest("UNKNOWN-ALGORITHM");
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<Sha256CodeBuilder> constructor = Sha256CodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void toHexString_withTestString() {
		Assert.assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", Sha256CodeBuilder.asHexString("test"));
	}

}
