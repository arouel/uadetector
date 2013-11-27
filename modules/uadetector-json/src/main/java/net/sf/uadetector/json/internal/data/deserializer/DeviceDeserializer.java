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

import static net.sf.uadetector.json.internal.data.field.SerializableDeviceField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableDeviceField.ICON;
import static net.sf.uadetector.json.internal.data.field.SerializableDeviceField.INFO_URL;
import static net.sf.uadetector.json.internal.data.field.SerializableDeviceField.NAME;
import static net.sf.uadetector.json.internal.data.field.SerializableDeviceField.PATTERNS;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.internal.data.domain.Device;
import net.sf.uadetector.internal.data.domain.DevicePattern;
import net.sf.uadetector.json.SerDeOption;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public final class DeviceDeserializer extends AbstractDeserializer<Device> implements JsonDeserializer<Device> {

	private final AtomicInteger counter = new AtomicInteger(0);

	private final DevicePatternDeserializer devicePatternDeserializer;

	private final Map<String, Device> devices = new HashMap<String, Device>();

	public DeviceDeserializer(final EnumSet<SerDeOption> options, final DevicePatternDeserializer devicePatternDeserializer) {
		super(options);
		this.devicePatternDeserializer = Check.notNull(devicePatternDeserializer, "devicePatternDeserializer");
	}

	@Override
	public Device deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
		String hash = EMPTY_HASH_CODE;

		// deserialize
		final Device.Builder b = new Device.Builder();
		b.setId(counter.incrementAndGet());
		for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
			if (HASH.getName().equals(entry.getKey())) {
				hash = entry.getValue().getAsString();
			} else if (ICON.getName().equals(entry.getKey())) {
				b.setIcon(entry.getValue().getAsString());
			} else if (INFO_URL.getName().equals(entry.getKey())) {
				b.setInfoUrl(entry.getValue().getAsString());
			} else if (NAME.getName().equals(entry.getKey())) {
				b.setName(entry.getValue().getAsString());
			} else if (PATTERNS.getName().equals(entry.getKey())) {
				final SortedSet<DevicePattern> patterns = new TreeSet<DevicePattern>();
				for (final JsonElement patternHash : entry.getValue().getAsJsonArray()) {
					final DevicePattern pattern = devicePatternDeserializer.findDevicePattern(patternHash.getAsString());
					if (pattern != null) {
						patterns.add(pattern);
					}
				}
				b.setPatterns(patterns);
			}
		}

		// create device
		Device d = null;
		try {
			// build
			d = b.build();

			// check hash when option is set
			checkHash(json, hash, d);

			// add pattern to map
			devices.put(hash, d);
		} catch (final Exception e) {
			addWarning(e.getLocalizedMessage());
		}

		return d;
	}

	@Nullable
	public Device findDevice(@Nonnull final String hash) {
		Check.notEmpty(hash, "hash");
		final Device d = devices.get(hash);
		if (d == null) {
			addWarning("Can not find device for hash '" + hash + "'.");
		}
		return d;
	}

}
