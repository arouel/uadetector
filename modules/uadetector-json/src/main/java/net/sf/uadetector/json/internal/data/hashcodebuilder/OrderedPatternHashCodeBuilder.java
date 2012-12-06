package net.sf.uadetector.json.internal.data.hashcodebuilder;

import net.sf.uadetector.internal.data.domain.OrderedPattern;

final class OrderedPatternHashCodeBuilder {

	public static <T extends OrderedPattern<T>> String build(final OrderedPattern<T> pattern) {
		final StringBuilder builder = new StringBuilder();
		builder.append(pattern.getPattern().pattern());
		builder.append(pattern.getPattern().flags());
		return Sha256CodeBuilder.asHexString(builder.toString());
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private OrderedPatternHashCodeBuilder() {
		// This class is not intended to create objects from it.
	}

}
