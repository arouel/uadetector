/*******************************************************************************
 * Copyright 2012 André Rouél
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
package net.sf.uadetector.internal.data;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class XmlDataHandlerTagTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void constructor_null() {
		XmlDataHandler.Tag.evaluate(null);
	}

	@Test
	public void isTag() {
		XmlDataHandler.Tag.isBrowserOsMappingTag("browser_os");
		XmlDataHandler.Tag.isBrowserPatternTag("browser_reg");
		XmlDataHandler.Tag.isBrowserTag("browser");
		XmlDataHandler.Tag.isBrowserTypeTag("browser_type");
		XmlDataHandler.Tag.isDeviceTag("device");
		XmlDataHandler.Tag.isDevicePatternTag("device_reg");
		XmlDataHandler.Tag.isIdTag("id");
		XmlDataHandler.Tag.isOperatingSystemPatternTag("os_reg");
		XmlDataHandler.Tag.isOperatingSystemTag("os");
		XmlDataHandler.Tag.isRobotTag("robot");
	}

}
