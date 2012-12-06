package net.sf.uadetector.json.internal.data.hashcodebuilder;

import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;

final class BrowserHashCodeBuilder {

	public static String build(final Browser browser) {
		final StringBuilder builder = new StringBuilder();
		builder.append(UserAgentFamilyHashCodeBuilder.build(browser.getFamily()));
		builder.append(browser.getIcon());
		builder.append(browser.getInfoUrl());
		if (browser.getOperatingSystem() != null) {
			builder.append(OperatingSystemHashCodeBuilder.build(browser.getOperatingSystem()));
		}
		for (final BrowserPattern pattern : browser.getPatternSet()) {
			builder.append(OrderedPatternHashCodeBuilder.build(pattern));
		}
		builder.append(browser.getProducer());
		builder.append(browser.getProducerUrl());
		builder.append(browser.getUrl());
		return Sha256CodeBuilder.asHexString(builder.toString());
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private BrowserHashCodeBuilder() {
		// This class is not intended to create objects from it.
	}

}
