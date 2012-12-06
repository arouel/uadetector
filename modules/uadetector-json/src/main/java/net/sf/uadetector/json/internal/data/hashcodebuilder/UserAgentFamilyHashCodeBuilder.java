package net.sf.uadetector.json.internal.data.hashcodebuilder;

import net.sf.uadetector.UserAgentFamily;

final class UserAgentFamilyHashCodeBuilder {

	public static String build(final UserAgentFamily family) {
		return Sha256CodeBuilder.asHexString(family.name());
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private UserAgentFamilyHashCodeBuilder() {
		// This class is not intended to create objects from it.
	}

}
