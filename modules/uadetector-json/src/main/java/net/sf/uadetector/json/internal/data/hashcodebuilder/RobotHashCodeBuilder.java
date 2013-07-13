/*******************************************************************************
 * Copyright 2013 André Rouél
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
