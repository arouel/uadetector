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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegularExpressionConverter {

	/**
	 * Pattern for PERL style regular expression strings
	 */
	private static final Pattern PERL_STYLE = Pattern.compile("^/.*/((i|m|s|x)*)?$");

	/**
	 * Pattern for PERL style regular expression strings with more fault-tolerance to the modifiers
	 */
	private static final Pattern PERL_STYLE_TOLERANT = Pattern.compile("^/.*/(([A-z])*)?$");

	/**
	 * Converts a PERL style regular expression into Java style.<br>
	 * <br>
	 * The leading and ending slash and the modifiers will be removed. The modifiers will be translated into equivalents
	 * flags of <code>java.util.Pattern</code>. If there are modifiers that are not valid an exception will be thrown.
	 * 
	 * @param regex
	 *            A PERL style regular expression
	 * @return Pattern
	 */
	public static Pattern convertPerlRegexToPattern(final String regex) {
		return convertPerlRegexToPattern(regex, false);
	}

	/**
	 * Converts a PERL style regular expression into Java style.<br>
	 * <br>
	 * The leading and ending slash and the modifiers will be removed.
	 * 
	 * @param regex
	 *            A PERL style regular expression
	 * @param faultTolerant
	 *            Fault-tolerant translating the flags
	 * @return Pattern
	 */
	public static Pattern convertPerlRegexToPattern(final String regex, final boolean faultTolerant) {
		if (regex == null) {
			throw new IllegalArgumentException("Argument 'regex' must not be null.");
		}

		String pattern = regex.trim();
		final Matcher matcher = (faultTolerant) ? PERL_STYLE_TOLERANT.matcher(pattern) : PERL_STYLE.matcher(pattern);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("The given regular expression '" + pattern
					+ "' seems to be not in PERL style or has unsupported modifiers.");
		}

		pattern = pattern.substring(1);
		final int lastIndex = pattern.lastIndexOf('/');
		pattern = pattern.substring(0, lastIndex);

		final int flags = translateModifiers(matcher.group(1));
		return Pattern.compile(pattern, flags);
	}

	/**
	 * Translates PERL style modifiers to a set of {@code Pattern} compatible ones.<br>
	 * <br>
	 * 
	 * @param modifiers
	 *            Modifiers string of a PERL style regular expression
	 * @return A bit mask of modifier flags that may include CASE_INSENSITIVE, MULTILINE, DOTALL and COMMENTS.
	 */
	public static int translateModifiers(final String modifiers) {
		if (modifiers == null) {
			throw new IllegalArgumentException("Argument 'modifiers' must not be null.");
		}

		int flags = 0;
		for (int i = 0; i < modifiers.length(); i++) {
			final char chr = modifiers.charAt(i);
			switch (chr) {
			case 'i':
				flags = flags | Pattern.CASE_INSENSITIVE;
				break;
			case 'm':
				flags = flags | Pattern.MULTILINE;
				break;
			case 's':
				flags = flags | Pattern.DOTALL;
				break;
			case 'x':
				flags = flags | Pattern.COMMENTS;
				break;
			default:
				break;
			}
		}
		return flags;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private RegularExpressionConverter() {
		// This class is not intended to create objects from it.
	}

}
