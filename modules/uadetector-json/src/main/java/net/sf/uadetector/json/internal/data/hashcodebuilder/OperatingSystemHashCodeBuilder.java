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

import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;

final class OperatingSystemHashCodeBuilder {

	public static String build(final OperatingSystem os) {
		final StringBuilder builder = new StringBuilder();
		builder.append(os.getFamily());
		builder.append(os.getIcon());
		builder.append(os.getInfoUrl());
		builder.append(os.getName());
		for (final OperatingSystemPattern pattern : os.getPatterns()) {
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
