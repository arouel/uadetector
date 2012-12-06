package net.sf.uadetector.json.internal.data.serializer;

import static net.sf.uadetector.json.internal.data.field.SerializablePatternField.FLAGS;
import static net.sf.uadetector.json.internal.data.field.SerializablePatternField.PATTERN;

import java.lang.reflect.Type;
import java.util.regex.Pattern;

import net.sf.uadetector.internal.util.RegularExpressionConverter.Flag;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class PatternSerializer implements JsonSerializer<Pattern> {

	@Override
	public JsonElement serialize(final Pattern pattern, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty(FLAGS.getName(), Flag.convertToModifiers(Flag.parse(pattern.flags())));
		jsonObj.addProperty(PATTERN.getName(), pattern.pattern());
		return jsonObj;
	}

}
