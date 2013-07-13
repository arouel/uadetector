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

import static net.sf.uadetector.json.internal.data.field.SerializablePatternField.FLAGS;
import static net.sf.uadetector.json.internal.data.field.SerializablePatternField.PATTERN;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import net.sf.uadetector.internal.util.RegularExpressionConverter.Flag;
import net.sf.uadetector.json.SerDeOption;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public final class PatternDeserializer extends AbstractDeserializer<Pattern> implements JsonDeserializer<Pattern> {

	public PatternDeserializer(final EnumSet<SerDeOption> options) {
		super(options);
	}

	private void checkPattern(final String pattern, final JsonElement json) {
		if (pattern.isEmpty()) {
			addWarning("The parsed regular expression pattern is empty: " + json);
		}
	}

	@Override
	public Pattern deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {

		// deserialize
		String flags = "";
		String expression = "";
		for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
			if (FLAGS.getName().equals(entry.getKey())) {
				flags = entry.getValue().getAsString();
			} else if (PATTERN.getName().equals(entry.getKey())) {
				expression = entry.getValue().getAsString();
			}
		}

		// check expression
		checkPattern(expression, json);

		// create pattern
		Pattern pattern = null;
		try {
			pattern = Pattern.compile(expression, Flag.convertToBitmask(Flag.parse(flags)));
		} catch (final Exception e) {
			addWarning(e.getLocalizedMessage());
		}

		return pattern;
	}

}
