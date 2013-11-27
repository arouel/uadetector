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

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.uadetector.ReadableDeviceCategory.Category;
import net.sf.uadetector.internal.data.domain.Device;
import net.sf.uadetector.internal.data.domain.DevicePattern;

import org.junit.Test;

public class DeviceHashCodeBuilderTest {

	protected static final Device create() {
		final String name = "device name";
		final int id = 1;
		final Category category = Category.PERSONAL_COMPUTER;
		final String icon = "icon";
		final String infoUrl = "info url";
		final SortedSet<DevicePattern> patterns = new TreeSet<DevicePattern>();
		patterns.add(new DevicePattern(1, Pattern.compile("[0-9]"), 1));
		patterns.add(new DevicePattern(2, Pattern.compile("[a-z]"), 2));
		return new Device(name, id, category, icon, infoUrl, patterns);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<DeviceHashCodeBuilder> constructor = DeviceHashCodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void test() {
		final String hash1 = DeviceHashCodeBuilder.build(create());
		final String hash2 = DeviceHashCodeBuilder.build(create());
		assertThat(hash2).isEqualTo(hash1);
		assertThat(hash1).isEqualTo("22645788fb39dc0ad16514b73e0f913921cd875fb68d5928ee9258a7a4d2b80c");
	}

}
