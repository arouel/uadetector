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

import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;

final class BrowserHashCodeBuilder {

	public static String build(final Browser browser) {
		final StringBuilder builder = new StringBuilder();
		builder.append(browser.getFamilyName());
		builder.append(browser.getIcon());
		builder.append(browser.getInfoUrl());
		if (browser.getOperatingSystem() != null) {
			builder.append(OperatingSystemHashCodeBuilder.build(browser.getOperatingSystem()));
		}
		for (final BrowserPattern pattern : browser.getPatterns()) {
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
