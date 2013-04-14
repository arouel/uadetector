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

import org.junit.Assert;
import org.junit.Test;

public class UserAgentFamilyTest {

	@Test
	public void evaluate_emptyString() {
		Assert.assertEquals(UserAgentFamily.UNKNOWN, UserAgentFamily.evaluate(""));
	}

	@Test
	public void evaluate_GIANT() {
		Assert.assertEquals(UserAgentFamily.GIANT, UserAgentFamily.evaluate("Giant/2.0"));
		Assert.assertEquals(UserAgentFamily.GIANT, UserAgentFamily.evaluate("Giant/4.9"));
	}

	@Test
	public void evaluate_knownString_CHROME() {
		Assert.assertEquals(UserAgentFamily.CHROME, UserAgentFamily.evaluate("Chrome"));
	}

	@Test
	public void evaluate_knownString_FIREFOX() {
		Assert.assertEquals(UserAgentFamily.FIREFOX, UserAgentFamily.evaluate("Firefox"));
	}

	@Test
	public void evaluate_MAILRU() {
		Assert.assertEquals(UserAgentFamily.MAIL_RU, UserAgentFamily.evaluate("Mail.Ru/1.0"));
		Assert.assertEquals(UserAgentFamily.MAIL_RU, UserAgentFamily.evaluate("Mail.RU_Bot/2.0"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluate_null() {
		UserAgentFamily.evaluate(null);
	}

	@Test
	public void evaluate_unknownString() {
		Assert.assertEquals(UserAgentFamily.UNKNOWN, UserAgentFamily.evaluate("abcdefghijklmnopqrstuvw"));
	}

	@Test
	public void evaluateByName_emptyString() {
		Assert.assertEquals(UserAgentFamily.UNKNOWN, UserAgentFamily.evaluateByName(""));
	}

	@Test
	public void evaluateByName_knownString_YAHOO() {
		Assert.assertEquals(UserAgentFamily.YAHOO, UserAgentFamily.evaluateByName("Yahoo!"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluateByName_null() {
		UserAgentFamily.evaluateByName(null);
	}

	@Test
	public void evaluateByName_unknownString() {
		Assert.assertEquals(UserAgentFamily.UNKNOWN, UserAgentFamily.evaluateByName("abcdefghijklmnopqrstuvw"));
	}

	@Test
	public void evaluateByPattern_emptyString() {
		Assert.assertEquals(UserAgentFamily.UNKNOWN, UserAgentFamily.evaluateByPattern(""));
	}

	@Test
	public void evaluateByPattern_knownString_ZOOMSPIDER() {
		Assert.assertEquals(UserAgentFamily.ZOOMSPIDER, UserAgentFamily.evaluateByPattern("ZoomSpider (ZSEBOT)"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluateByPattern_null() {
		UserAgentFamily.evaluateByPattern(null);
	}

	@Test
	public void evaluateByPattern_unknownString() {
		Assert.assertEquals(UserAgentFamily.UNKNOWN, UserAgentFamily.evaluateByPattern("abcdefghijklmnopqrstuvw"));
	}

}
