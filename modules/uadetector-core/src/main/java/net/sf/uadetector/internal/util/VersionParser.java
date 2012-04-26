package net.sf.uadetector.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.uadetector.VersionNumber;

public final class VersionParser {

	/**
	 * Regular expression groups to analyze a version number separated by a dot
	 */
	private static final Pattern VERSIONNUMBER = Pattern.compile("((\\d+)((\\.\\d+)+)?)");

	/**
	 * Regular expression to analyze segments of a version string, consisting of prefix, numeric groups and suffix
	 */
	private static final Pattern VERSIONSTRING = Pattern.compile("^" + VERSIONNUMBER.pattern() + "(.*)");

	/**
	 * Interprets a User-Agent string with version information. The last version number in the string will be searched
	 * and processed.
	 * 
	 * @param userAgentString
	 *            User-Agent string with version information
	 * @return an object of {@code VersionNumber}, never {@code null}
	 */
	public static VersionNumber parseLastVersionNumber(final String userAgentString) {
		if (userAgentString == null) {
			throw new IllegalArgumentException("Argument 'version' must not be null.");
		}

		final Matcher matcher = VERSIONNUMBER.matcher(userAgentString);
		String[] split = null;
		while (matcher.find()) {
			split = matcher.group().split("\\.");
		}

		return split == null ? VersionNumber.UNKNOWN : new VersionNumber(Arrays.asList(split));
	}

	/**
	 * Interprets a string with version information.
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
			final String ext = matcher.group(5);
			result = new VersionNumber(groups, ext);
		}

		return result;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private VersionParser() {
		// This class is not intended to create objects from it.
	}

}
