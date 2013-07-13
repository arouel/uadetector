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
package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.json.SerDeOption;

import org.junit.Test;

public class OperatingSystemPatternDeserializerTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void deserialize_emptyJsonObject() {
		Deserializers.deserialize("{}", EnumSet.noneOf(SerDeOption.class), OperatingSystemPattern.class);
	}

}
