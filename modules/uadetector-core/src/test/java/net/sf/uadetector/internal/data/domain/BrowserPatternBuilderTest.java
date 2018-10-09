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

import java.util.regex.Pattern;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class BrowserPatternBuilderTest {

	@Test(expected = IllegalNegativeArgumentException.class)
	public void build_withoutId() {
		new BrowserPattern.Builder().setPosition(1).setPattern(Pattern.compile("[0-9]+")).build();
	}

	//@Test(expected = IllegalNegativeArgumentException.class)
	public void build_withoutOrder() {
		new BrowserPattern.Builder().setId(1).setPattern(Pattern.compile("[0-9]+")).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void build_withoutPattern() {
		new BrowserPattern.Builder().setId(1).setPosition(1).build();
	}

	@Test
	public void equality() {
		final BrowserPattern pattern1 = new BrowserPattern.Builder().setId(1).setPosition(1).setPattern(Pattern.compile("[0-9]+")).build();
		final BrowserPattern pattern2 = new BrowserPattern.Builder().setId(1).setPosition(1).setPattern(Pattern.compile("[0-9]+")).build();
		assertThat(pattern1.equals(pattern2)).isTrue();
	}

	@Test(expected = NumberFormatException.class)
	public void setId_alphaString() {
		new BrowserPattern.Builder().setId("abc");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void setId_emptyString() {
		new BrowserPattern.Builder().setId("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setId_null() {
		new BrowserPattern.Builder().setId(null);
	}

	@Test
	public void setId_numericString() {
		final BrowserPattern pattern1 = new BrowserPattern.Builder().setId("1").setPosition(1).setPattern(Pattern.compile("[0-9]+"))
				.build();
		final BrowserPattern pattern2 = new BrowserPattern.Builder().setId(1).setPosition(1).setPattern(Pattern.compile("[0-9]+")).build();
		assertThat(pattern1.equals(pattern2)).isTrue();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void setId_toSmall() {
		new BrowserPattern.Builder().setId(-1);
	}

	@Test(expected = NumberFormatException.class)
	public void setOrder_alphaString() {
		new BrowserPattern.Builder().setPosition("abc");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void setOrder_emptyString() {
		new BrowserPattern.Builder().setPosition("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setOrder_null() {
		new BrowserPattern.Builder().setPosition(null);
	}

	//@Test(expected = IllegalNegativeArgumentException.class)
	public void setOrder_toSmall() {
		new BrowserPattern.Builder().setPosition(-1);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setPattern_pattern_null() {
		new BrowserPattern.Builder().setPattern((Pattern) null);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void setPerlRegularExpression_emptyString() {
		new BrowserPattern.Builder().setPerlRegularExpression("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setPerlRegularExpression_nonPerlStyleExpression() {
		new BrowserPattern.Builder().setPerlRegularExpression("abc");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setPerlRegularExpression_string_null() {
		new BrowserPattern.Builder().setPerlRegularExpression((String) null);
	}

	@Test
	public void setPerlRegularExpression_validPerlStyleExpression() {
		new BrowserPattern.Builder().setPerlRegularExpression("/abc/");
		new BrowserPattern.Builder().setPerlRegularExpression("/abc/si");
	}

}
