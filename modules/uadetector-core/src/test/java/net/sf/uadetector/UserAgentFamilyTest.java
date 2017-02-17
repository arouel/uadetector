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

import static org.fest.assertions.Assertions.assertThat;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class UserAgentFamilyTest {

	@Test
	public void evaluate_emptyString() {
		assertThat(UserAgentFamily.evaluate("")).isEqualTo(UserAgentFamily.UNKNOWN);
	}

	@Test
	public void evaluate_GIANT() {
		assertThat(UserAgentFamily.evaluate("Giant/2.0")).isEqualTo(UserAgentFamily.GIANT);
		assertThat(UserAgentFamily.evaluate("Giant/4.9")).isEqualTo(UserAgentFamily.GIANT);
	}

	@Test
	public void evaluate_knownString_CHROME() {
		assertThat(UserAgentFamily.evaluate("Chrome")).isEqualTo(UserAgentFamily.CHROME);
	}

	@Test
	public void evaluate_knownString_FIREFOX() {
		assertThat(UserAgentFamily.evaluate("Firefox")).isEqualTo(UserAgentFamily.FIREFOX);
	}

	@Test
	public void evaluate_knownString_ANDROID_BROWSER() {
		assertThat(UserAgentFamily.evaluate("Android browser")).isEqualTo(UserAgentFamily.ANDROID_BROWSER);
	}

	@Test
	public void evaluate_MAILRU() {
		assertThat(UserAgentFamily.evaluate("Mail.Ru/1.0")).isEqualTo(UserAgentFamily.MAIL_RU);
		assertThat(UserAgentFamily.evaluate("Mail.RU_Bot/2.0")).isEqualTo(UserAgentFamily.MAIL_RU);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluate_null() {
		UserAgentFamily.evaluate(null);
	}

	@Test
	public void evaluate_unknownString() {
		assertThat(UserAgentFamily.evaluate("abcdefghijklmnopqrstuvw")).isEqualTo(UserAgentFamily.UNKNOWN);
	}

	@Test
	public void evaluateByName_emptyString() {
		assertThat(UserAgentFamily.evaluateByName("")).isEqualTo(UserAgentFamily.UNKNOWN);
	}

	@Test
	public void evaluateByName_knownString_YAHOO() {
		assertThat(UserAgentFamily.evaluateByName("Yahoo!")).isEqualTo(UserAgentFamily.YAHOO);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluateByName_null() {
		UserAgentFamily.evaluateByName(null);
	}

	@Test
	public void evaluateByName_unknownString() {
		assertThat(UserAgentFamily.evaluateByName("abcdefghijklmnopqrstuvw")).isEqualTo(UserAgentFamily.UNKNOWN);
	}

	@Test
	public void evaluateByPattern_emptyString() {
		assertThat(UserAgentFamily.evaluateByPattern("")).isEqualTo(UserAgentFamily.UNKNOWN);
	}

	@Test
	public void evaluateByPattern_knownString_ZOOMSPIDER() {
		assertThat(UserAgentFamily.evaluateByPattern("ZoomSpider (ZSEBOT)")).isEqualTo(UserAgentFamily.ZOOMSPIDER);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluateByPattern_null() {
		UserAgentFamily.evaluateByPattern(null);
	}

	@Test
	public void evaluateByPattern_unknownString() {
		assertThat(UserAgentFamily.evaluateByPattern("abcdefghijklmnopqrstuvw")).isEqualTo(UserAgentFamily.UNKNOWN);
	}

}
