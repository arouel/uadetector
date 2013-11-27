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
package net.sf.uadetector.json.internal.data.serializer;

import java.util.EnumSet;
import java.util.regex.Pattern;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.Device;
import net.sf.uadetector.internal.data.domain.DevicePattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.json.SerDeOption;
import net.sf.uadetector.json.internal.data.util.AbstractMessageCollector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Serializers extends AbstractMessageCollector<Data> {

	public static Serialization serialize(final Data data, final EnumSet<SerDeOption> options) {
		final Serializers serializers = new Serializers(options);
		return new Serialization(serializers.serialize(data), serializers.getWarnings());
	}

	private final Gson gson;

	private Serializers(final EnumSet<SerDeOption> options) {
		Check.notNull(options);
		final GsonBuilder gsonBuilder = new GsonBuilder();

		// setup serializers
		gsonBuilder.registerTypeAdapter(Data.class, new DataSerializer());
		gsonBuilder.registerTypeAdapter(Browser.class, new BrowserSerializer());
		gsonBuilder.registerTypeAdapter(BrowserPattern.class, new OrderedPatternSerializer<BrowserPattern>());
		gsonBuilder.registerTypeAdapter(BrowserType.class, new BrowserTypeSerializer());
		gsonBuilder.registerTypeAdapter(OperatingSystem.class, new OperatingSystemSerializer());
		gsonBuilder.registerTypeAdapter(OperatingSystemPattern.class, new OrderedPatternSerializer<OperatingSystemPattern>());
		gsonBuilder.registerTypeAdapter(Robot.class, new RobotSerializer());
		gsonBuilder.registerTypeAdapter(Pattern.class, new PatternSerializer());
		gsonBuilder.registerTypeAdapter(Device.class, new DeviceSerializer());
		gsonBuilder.registerTypeAdapter(DevicePattern.class, new OrderedPatternSerializer<DevicePattern>());

		// some settings
		if (options.contains(SerDeOption.PRETTY_PRINTING)) {
			gsonBuilder.setPrettyPrinting();
		}
		gsonBuilder.disableInnerClassSerialization();

		gson = gsonBuilder.create();
	}

	/**
	 * Deserializes a JSON representation into a new instance of {@link Data}.
	 * 
	 * @param data
	 *            JSON representation of {@code Data}
	 * @return instance of {@code Data}
	 */
	public String serialize(final Data data) {
		Check.notNull(data);
		return gson.toJson(data);
	}

}
