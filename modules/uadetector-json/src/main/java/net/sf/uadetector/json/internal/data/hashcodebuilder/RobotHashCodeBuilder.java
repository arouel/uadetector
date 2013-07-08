package net.sf.uadetector.json.internal.data.hashcodebuilder;

import net.sf.uadetector.internal.data.domain.Robot;

final class RobotHashCodeBuilder {

	public static String build(final Robot robot) {
		final StringBuilder builder = new StringBuilder();
		builder.append(UserAgentFamilyHashCodeBuilder.build(robot.getFamily()));
		builder.append(robot.getFamilyName());
		builder.append(robot.getIcon());
		builder.append(robot.getInfoUrl());
		builder.append(robot.getName());
		builder.append(robot.getProducer());
		builder.append(robot.getProducerUrl());
		builder.append(robot.getUserAgentString());
		return Sha256CodeBuilder.asHexString(builder.toString());
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private RobotHashCodeBuilder() {
		// This class is not intended to create objects from it.
	}

}
