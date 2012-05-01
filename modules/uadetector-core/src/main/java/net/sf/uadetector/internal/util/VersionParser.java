package net.sf.uadetector.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.uadetector.VersionNumber;

/**
 * This class is used to detect version information within strings.
 * 
 * @author André Rouél
 */
public final class VersionParser {

	/**
	 * Regular expression to analyze a version number separated by a dot
	 */
	private static final Pattern VERSIONNUMBER = Pattern.compile("((\\d+)((\\.\\d+)+)?)");

	/**
	 * Regular expression to analyze a version number separated by a dot with suffix
	 */
	private static final Pattern VERSIONNUMBER_WITH_SUFFIX = Pattern.compile(VERSIONNUMBER.pattern() + "((\\s|\\-|\\.|\\[|\\]|\\w+)+)?");

	/**
	 * Regular expression to analyze segments of a version string, consisting of prefix, numeric groups and suffix
	 */
	private static final Pattern VERSIONSTRING = Pattern.compile("^" + VERSIONNUMBER_WITH_SUFFIX.pattern());

	/**
	 * Interprets a string with version information. The last version number in the string will be searched and
	 * processed.
	 * 
	 * @param text
	 *            string with version information
	 * @return an object of {@code VersionNumber}, never {@code null}
	 */
	public static VersionNumber parseLastVersionNumber(final String text) {
		if (text == null) {
			throw new IllegalArgumentException("Argument 'text' must not be null.");
		}

		final Matcher matcher = VERSIONNUMBER_WITH_SUFFIX.matcher(text);
		String[] split = null;
		String ext = null;
		while (matcher.find()) {
			split = matcher.group(1).split("\\.");
			ext = matcher.group(5);
		}

		final String extension = ext == null ? VersionNumber.EMPTY_EXTENSION : trimRight(ext);

		return split == null ? VersionNumber.UNKNOWN : new VersionNumber(Arrays.asList(split), extension);
	}

	/**
	 * Interprets a string with version information. The first found group will be taken and processed.
	 * 
	 * @param version
	 *            version as string
	 * @return an object of {@code VersionNumber}, never {@code null}
	 */
	public static VersionNumber parseVersion(final String version) {
		if (version == null) {
			throw new IllegalArgumentException("Argument 'version' must not be null.");
		}

		VersionNumber result = new VersionNumber(new ArrayList<String>(0), version);
		final Matcher matcher = VERSIONSTRING.matcher(version);
		if (matcher.find()) {
			final List<String> groups = Arrays.asList(matcher.group(1).split("\\."));
			final String extension = matcher.group(5) == null ? VersionNumber.EMPTY_EXTENSION : trimRight(matcher.group(5));
			result = new VersionNumber(groups, extension);
		}

		return result;
	}

	/**
	 * Trims the whitespace at the end of the given string.
	 * 
	 * @param text
	 *            string to trim
	 * @return trimmed string
	 */
	private static String trimRight(final String text) {
		return text.replaceAll("\\s+$", "");
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private VersionParser() {
		// This class is not intended to create objects from it.
	}

}
