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
package net.sf.uadetector.internal.util;

import java.lang.reflect.Constructor;
import java.util.regex.Pattern;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class RegularExpressionConverterTest {

	@Test
	public void convertPerlRegexToPattern_andBack() {
		final String perlStyleRegex = "/^test[a-z0-9]\\w+$/si";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		assertThat(RegularExpressionConverter.convertPatternToPerlRegex(pattern)).isEqualTo(perlStyleRegex);
	}

	@Test
	public void convertPerlRegexToPattern_faultTolerantModifierRegexConverting_NintendoDS() {
		final String perlStyleRegex = "/Nintendo DS/Si";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex, true);
		final Pattern expected = Pattern.compile("Nintendo DS", Pattern.CASE_INSENSITIVE);
		assertThat(pattern.flags()).isEqualTo(expected.flags());
	}

	@Test(expected = IllegalArgumentException.class)
	public void convertPerlRegexToPattern_NintendoDS() {
		final String perlStyleRegex = "/Nintendo DS/Si";
		RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void convertPerlRegexToPattern_null() {
		RegularExpressionConverter.convertPerlRegexToPattern(null);
	}

	@Test
	public void convertPerlRegexToPattern_regexWithApparentlyIntersection() {
		final String perlStyleRegex = "/test/([0-9a-zA-Z.\\-+]+)/s";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		final Pattern expected = Pattern.compile("test/([0-9a-zA-Z.\\-+]+)", Pattern.DOTALL);
		assertThat(pattern.flags()).isEqualTo(expected.flags());
		assertThat(pattern.pattern()).isEqualTo(expected.pattern());
	}

	@Test
	public void convertPerlRegexToPattern_regexWithModifiers() {
		final String perlStyleRegex = "/\\s*[a-zA-Z0-9]*/im";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		final Pattern expected = Pattern.compile("\\s*[a-zA-Z0-9]*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		assertThat(pattern.flags()).isEqualTo(expected.flags());
		assertThat(pattern.pattern()).isEqualTo(expected.pattern());
	}

	@Test
	public void convertPerlRegexToPattern_regexWithWhitespace() {
		final String perlStyleRegex = "/\\s*/ [a-zA-Z0-9]* /im ";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		final Pattern expected = Pattern.compile("\\s*/ [a-zA-Z0-9]* ", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		assertThat(pattern.flags()).isEqualTo(expected.flags());
		assertThat(pattern.pattern()).isEqualTo(expected.pattern());
	}

	@Test
	public void convertPerlRegexToPattern_simpleRegex() {
		final String perlStyleRegex = "/\\s*[a-zA-Z0-9]*/";
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		final Pattern expected = Pattern.compile("\\s*[a-zA-Z0-9]*");
		assertThat(pattern.flags()).isEqualTo(expected.flags());
	}

	@Test
	public void convertPerlRegexToPattern_swiftfoxRegex() {
		final String perlStyleRegex = "/mozilla.*rv:[0-9\\.]+.*gecko\\/[0-9]+.*firefox\\/([0-9a-z\\+\\-\\.]+).*swiftfox/si";
		RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex);
		final Pattern pattern = RegularExpressionConverter.convertPerlRegexToPattern(perlStyleRegex, true);
		final Pattern expected = Pattern.compile("mozilla.*rv:[0-9\\.]+.*gecko\\/[0-9]+.*firefox\\/([0-9a-z\\+\\-\\.]+).*swiftfox",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		assertThat(pattern.flags()).isEqualTo(expected.flags());
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<RegularExpressionConverter> constructor = RegularExpressionConverter.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

}
