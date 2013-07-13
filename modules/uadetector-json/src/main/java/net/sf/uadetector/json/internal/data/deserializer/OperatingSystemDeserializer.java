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

import static net.sf.uadetector.json.internal.data.field.SerializableOperatingSystemField.FAMILY;
import static net.sf.uadetector.json.internal.data.field.SerializableOperatingSystemField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableOperatingSystemField.ICON;
import static net.sf.uadetector.json.internal.data.field.SerializableOperatingSystemField.INFO_URL;
import static net.sf.uadetector.json.internal.data.field.SerializableOperatingSystemField.NAME;
import static net.sf.uadetector.json.internal.data.field.SerializableOperatingSystemField.PATTERN_HASHS;
import static net.sf.uadetector.json.internal.data.field.SerializableOperatingSystemField.PRODUCER;
import static net.sf.uadetector.json.internal.data.field.SerializableOperatingSystemField.PRODUCER_URL;
import static net.sf.uadetector.json.internal.data.field.SerializableOperatingSystemField.URL;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.json.SerDeOption;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public final class OperatingSystemDeserializer extends AbstractDeserializer<OperatingSystem> implements JsonDeserializer<OperatingSystem> {

	private final AtomicInteger counter = new AtomicInteger(0);

	private final OperatingSystemPatternDeserializer operatingSystemPatternDeserializer;

	private final Map<String, OperatingSystem> operatingSystems = new HashMap<String, OperatingSystem>();

	public OperatingSystemDeserializer(final EnumSet<SerDeOption> options,
			final OperatingSystemPatternDeserializer operatingSystemPatternDeserializer) {
		super(options);
		this.operatingSystemPatternDeserializer = Check.notNull(operatingSystemPatternDeserializer, "operatingSystemPatternDeserializer");
	}

	@Override
	public OperatingSystem deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
		String hash = EMPTY_HASH_CODE;

		// deserialize
		final OperatingSystem.Builder b = new OperatingSystem.Builder();
		b.setId(counter.incrementAndGet());
		for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
			if (FAMILY.getName().equals(entry.getKey())) {
				b.setFamily(entry.getValue().getAsString());
			} else if (HASH.getName().equals(entry.getKey())) {
				hash = entry.getValue().getAsString();
			} else if (ICON.getName().equals(entry.getKey())) {
				b.setIcon(entry.getValue().getAsString());
			} else if (INFO_URL.getName().equals(entry.getKey())) {
				b.setInfoUrl(entry.getValue().getAsString());
			} else if (NAME.getName().equals(entry.getKey())) {
				b.setName(entry.getValue().getAsString());
			} else if (PATTERN_HASHS.getName().equals(entry.getKey())) {
				final Set<OperatingSystemPattern> patternSet = new HashSet<OperatingSystemPattern>();
				for (final JsonElement patternHash : entry.getValue().getAsJsonArray()) {
					final OperatingSystemPattern osPattern = operatingSystemPatternDeserializer.findOperatingSystemPattern(patternHash
							.getAsString());
					if (osPattern != null) {
						patternSet.add(osPattern);
					}
				}
				b.addPatterns(patternSet);
			} else if (PRODUCER.getName().equals(entry.getKey())) {
				b.setProducer(entry.getValue().getAsString());
			} else if (PRODUCER_URL.getName().equals(entry.getKey())) {
				b.setProducerUrl(entry.getValue().getAsString());
			} else if (URL.getName().equals(entry.getKey())) {
				b.setUrl(entry.getValue().getAsString());
			}
		}

		// create operating system
		OperatingSystem os = null;
		try {
			// build
			os = b.build();

			// check hash when option is set
			checkHash(json, hash, os);

			// add pattern to map
			operatingSystems.put(hash, os);
		} catch (final Exception e) {
			addWarning(e.getLocalizedMessage());
		}

		return os;
	}

	@Nullable
	public OperatingSystem findOperatingSystem(@Nonnull final String hash) {
		Check.notEmpty(hash, "hash");
		final OperatingSystem os = operatingSystems.get(hash);
		if (os == null) {
			addWarning("Can not find operating system for hash '" + hash + "'.");
		}
		return os;
	}

}
