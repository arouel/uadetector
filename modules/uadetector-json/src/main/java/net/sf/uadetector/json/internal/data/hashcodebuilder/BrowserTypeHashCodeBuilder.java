package net.sf.uadetector.json.internal.data.hashcodebuilder;

import net.sf.uadetector.internal.data.domain.BrowserType;

final class BrowserTypeHashCodeBuilder {

	public static String build(final BrowserType type) {
		return Sha256CodeBuilder.asHexString(type.getName());
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private BrowserTypeHashCodeBuilder() {
		// This class is not intended to create objects from it.
	}

}
