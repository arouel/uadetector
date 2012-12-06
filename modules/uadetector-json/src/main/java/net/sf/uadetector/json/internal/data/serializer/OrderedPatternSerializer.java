package net.sf.uadetector.json.internal.data.serializer;

import static net.sf.uadetector.json.internal.data.field.SerializableOrderedPatternField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableOrderedPatternField.PATTERN;

import java.lang.reflect.Type;

import net.sf.uadetector.internal.data.domain.OrderedPattern;
import net.sf.uadetector.json.internal.data.hashcodebuilder.HashCodeGenerator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class OrderedPatternSerializer<T extends OrderedPattern<T>> implements JsonSerializer<T> {

	@Override
	public JsonElement serialize(final T pattern, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObj = new JsonObject();
		jsonObj.add(PATTERN.getName(), context.serialize(pattern.getPattern()));
		jsonObj.addProperty(HASH.getName(), HashCodeGenerator.generate(pattern));
		return jsonObj;
	}

}
