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

import static net.sf.uadetector.json.internal.data.field.SerializableDeviceField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableDeviceField.ICON;
import static net.sf.uadetector.json.internal.data.field.SerializableDeviceField.INFO_URL;
import static net.sf.uadetector.json.internal.data.field.SerializableDeviceField.NAME;
import static net.sf.uadetector.json.internal.data.field.SerializableDeviceField.PATTERNS;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import net.sf.uadetector.internal.data.domain.Device;
import net.sf.uadetector.internal.data.domain.DevicePattern;
import net.sf.uadetector.json.internal.data.hashcodebuilder.HashCodeGenerator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class DeviceSerializer implements JsonSerializer<Device> {

	static List<String> createHashCodeList(final SortedSet<DevicePattern> patterns) {
		final List<String> hashs = new ArrayList<String>(patterns.size());
		for (final DevicePattern pattern : patterns) {
			hashs.add(HashCodeGenerator.generate(pattern));
		}
		return hashs;
	}

	@Override
	public JsonElement serialize(final Device device, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty(ICON.getName(), device.getIcon());
		jsonObj.addProperty(INFO_URL.getName(), device.getInfoUrl());
		jsonObj.addProperty(NAME.getName(), device.getName());
		jsonObj.add(PATTERNS.getName(), context.serialize(createHashCodeList(device.getPatterns())));
		jsonObj.addProperty(HASH.getName(), HashCodeGenerator.generate(device));
		return jsonObj;
	}

}
