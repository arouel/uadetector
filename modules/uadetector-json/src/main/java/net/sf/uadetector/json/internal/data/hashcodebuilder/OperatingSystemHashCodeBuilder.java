package net.sf.uadetector.json.internal.data.hashcodebuilder;

import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;

final class OperatingSystemHashCodeBuilder {

	public static String build(final OperatingSystem os) {
		final StringBuilder builder = new StringBuilder();
		builder.append(os.getFamily());
		builder.append(os.getIcon());
		builder.append(os.getInfoUrl());
		builder.append(os.getName());
		for (final OperatingSystemPattern pattern : os.getPatternSet()) {
			builder.append(OrderedPatternHashCodeBuilder.build(pattern));
		}
		builder.append(os.getProducer());
		builder.append(os.getProducerUrl());
		builder.append(os.getUrl());
		return Sha256CodeBuilder.asHexString(builder.toString());
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private OperatingSystemHashCodeBuilder() {
		// This class is not intended to create objects from it.
	}

}
