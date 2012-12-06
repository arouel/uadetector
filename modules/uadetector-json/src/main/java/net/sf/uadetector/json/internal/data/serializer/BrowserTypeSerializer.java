package net.sf.uadetector.json.internal.data.serializer;

import static net.sf.uadetector.json.internal.data.field.SerializableBrowserTypeField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserTypeField.NAME;

import java.lang.reflect.Type;

import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.json.internal.data.hashcodebuilder.HashCodeGenerator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class BrowserTypeSerializer implements JsonSerializer<BrowserType> {

	@Override
	public JsonElement serialize(final BrowserType type, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty(NAME.getName(), type.getName());
		jsonObj.addProperty(HASH.getName(), HashCodeGenerator.generate(type));
		return jsonObj;
	}

}
