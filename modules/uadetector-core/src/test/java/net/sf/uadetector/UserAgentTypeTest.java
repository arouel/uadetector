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
package net.sf.uadetector;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;

public class UserAgentTypeTest {

	@Test
	public void evaluateByType_emptyString() {
		Assert.assertEquals(UserAgentType.UNKNOWN, UserAgentType.evaluateByTypeName(""));
	}

	@Test
	public void evaluateByType_knownString_BROWSER() {
		Assert.assertEquals(UserAgentType.BROWSER, UserAgentType.evaluateByTypeName("Browser"));
	}

	@Test
	public void evaluateByType_knownString_ROBOT() {
		Assert.assertEquals(UserAgentType.ROBOT, UserAgentType.evaluateByTypeName(Robot.TYPENAME));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluateByType_null() {
		UserAgentType.evaluateByTypeName(null);
	}

	@Test
	public void evaluateByType_unknownString() {
		Assert.assertEquals(UserAgentType.UNKNOWN, UserAgentType.evaluateByTypeName("abcdefghijklmnopqrstuvw"));
	}

}
