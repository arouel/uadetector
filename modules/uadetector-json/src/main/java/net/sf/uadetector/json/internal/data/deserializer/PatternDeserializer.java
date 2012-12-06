package net.sf.uadetector.json.internal.data.deserializer;

import static net.sf.uadetector.json.internal.data.field.SerializablePatternField.FLAGS;
import static net.sf.uadetector.json.internal.data.field.SerializablePatternField.PATTERN;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import net.sf.uadetector.internal.util.RegularExpressionConverter.Flag;
import net.sf.uadetector.json.internal.data.JsonConverter.SerializationOption;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public final class PatternDeserializer extends AbstractDeserializer<Pattern> implements JsonDeserializer<Pattern> {

	public PatternDeserializer(final EnumSet<SerializationOption> options) {
		super(options);
	}

	private void checkPattern(final String pattern, final JsonElement json) {
		if (pattern.isEmpty()) {
			addWarning("The parsed regular expression pattern is empty: " + json);
		}
	}

	@Override
	public Pattern deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {

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
