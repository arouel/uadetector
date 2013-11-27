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

import static net.sf.uadetector.json.internal.data.field.SerializableOrderedPatternField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableOrderedPatternField.PATTERN;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.internal.data.domain.DevicePattern;
import net.sf.uadetector.json.SerDeOption;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public final class DevicePatternDeserializer extends AbstractDeserializer<DevicePattern> implements JsonDeserializer<DevicePattern> {

	private final Map<String, DevicePattern> devicePatterns = new HashMap<String, DevicePattern>();

	private final AtomicInteger counter = new AtomicInteger(0);

	public DevicePatternDeserializer(final EnumSet<SerDeOption> options) {
		super(options);
	}

	@Override
	public DevicePattern deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
		String hash = EMPTY_HASH_CODE;
		Pattern pattern = null;

		// deserialize
		for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
			if (PATTERN.getName().equals(entry.getKey())) {
				pattern = context.deserialize(entry.getValue(), Pattern.class);
			} else if (HASH.getName().equals(entry.getKey())) {
				hash = entry.getValue().getAsString();
			}
		}
		final int id = counter.incrementAndGet();

		// create device pattern
		DevicePattern devicePattern = null;
		try {
			devicePattern = new DevicePattern(id, pattern, id);

			// check hash when option is set
			checkHash(json, hash, devicePattern);

			// add pattern to map
			devicePatterns.put(hash, devicePattern);
		} catch (final Exception e) {
			addWarning(e.getLocalizedMessage());
		}

		return devicePattern;
	}

	@Nullable
	public DevicePattern findDevicePattern(@Nonnull final String hash) {
		Check.notEmpty(hash, "hash");
		final DevicePattern devicePattern = devicePatterns.get(hash);
		if (devicePattern == null) {
			addWarning("Can not find device pattern for hash '" + hash + "'.");
		}
		return devicePattern;
	}

}
