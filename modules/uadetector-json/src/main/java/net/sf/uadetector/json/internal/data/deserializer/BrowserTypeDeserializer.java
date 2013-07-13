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

import static net.sf.uadetector.json.internal.data.field.SerializableBrowserTypeField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserTypeField.NAME;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.json.SerDeOption;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public final class BrowserTypeDeserializer extends AbstractDeserializer<BrowserType> implements JsonDeserializer<BrowserType> {

	private final Map<String, BrowserType> browserTypes = new HashMap<String, BrowserType>();

	private final AtomicInteger counter = new AtomicInteger(0);

	public BrowserTypeDeserializer(final EnumSet<SerDeOption> options) {
		super(options);
	}

	@Override
	public BrowserType deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
		String name = null;
		String hash = EMPTY_HASH_CODE;

		// deserialize
		for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
			if (NAME.getName().equals(entry.getKey())) {
				name = entry.getValue().getAsString();
			} else if (HASH.getName().equals(entry.getKey())) {
				hash = entry.getValue().getAsString();
			}
		}

		// create browser type
		BrowserType type = null;
		try {
			type = new BrowserType(counter.incrementAndGet(), name);

			// check hash when option is set
			checkHash(json, hash, type);

			// add pattern to map
			browserTypes.put(hash, type);
		} catch (final Exception e) {
			addWarning(e.getLocalizedMessage());
		}

		return type;
	}

	@Nullable
	public BrowserType findBrowserType(@Nonnull final String hash) {
		Check.notEmpty(hash, "hash");
		final BrowserType browserType = browserTypes.get(hash);
		if (browserType == null) {
			addWarning("Can not find browser type for hash '" + hash + "'.");
		}
		return browserType;
	}

}
