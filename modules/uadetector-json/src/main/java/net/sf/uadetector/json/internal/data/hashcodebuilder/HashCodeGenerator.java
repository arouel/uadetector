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

import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.Device;
import net.sf.uadetector.internal.data.domain.DevicePattern;
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
		} else if (element instanceof Device) {
			builder.append(DeviceHashCodeBuilder.build((Device) element));
		} else if (element instanceof DevicePattern) {
			builder.append(OrderedPatternHashCodeBuilder.build((DevicePattern) element));
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
