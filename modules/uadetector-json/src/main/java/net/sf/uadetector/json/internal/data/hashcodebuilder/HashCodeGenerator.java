package net.sf.uadetector.json.internal.data.hashcodebuilder;

import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

public final class HashCodeGenerator {

	public static final String EMPTY_HASH_CODE = "";

	/**
	 * Creates a hash code builder for the passed element.
	 * 
	 * @param element
	 *            element for which one a hash code builder should be created
	 * @return a hash code builder
	 */
	public static <T> String generate(final T element) {
		final StringBuilder builder = new StringBuilder(64);
		if (element instanceof Browser) {
			builder.append(BrowserHashCodeBuilder.build((Browser) element));
		} else if (element instanceof BrowserPattern) {
			builder.append(OrderedPatternHashCodeBuilder.build((BrowserPattern) element));
		} else if (element instanceof BrowserType) {
			builder.append(BrowserTypeHashCodeBuilder.build((BrowserType) element));
		} else if (element instanceof OperatingSystem) {
			builder.append(OperatingSystemHashCodeBuilder.build((OperatingSystem) element));
		} else if (element instanceof OperatingSystemPattern) {
			builder.append(OrderedPatternHashCodeBuilder.build((OperatingSystemPattern) element));
		} else if (element instanceof Robot) {
			builder.append(RobotHashCodeBuilder.build((Robot) element));
		} else if (element instanceof UserAgentFamily) {
			builder.append(UserAgentFamilyHashCodeBuilder.build((UserAgentFamily) element));
		}
		return builder.toString();
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private HashCodeGenerator() {
		// This class is not intended to create objects from it.
	}

}
