package net.sf.uadetector.json.internal.data.serializer;

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
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.json.internal.data.hashcodebuilder.HashCodeGenerator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class OperatingSystemSerializer implements JsonSerializer<OperatingSystem> {

	private static List<String> createHashCodeList(final SortedSet<OperatingSystemPattern> patterns) {
		final List<String> hashs = new ArrayList<String>(patterns.size());
		for (final OperatingSystemPattern pattern : patterns) {
			hashs.add(HashCodeGenerator.generate(pattern));
		}
		return hashs;
	}

	@Override
	public JsonElement serialize(final OperatingSystem os, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty(FAMILY.getName(), os.getFamily());
		jsonObj.addProperty(ICON.getName(), os.getIcon());
		jsonObj.addProperty(INFO_URL.getName(), os.getInfoUrl());
		jsonObj.addProperty(NAME.getName(), os.getName());
		jsonObj.add(PATTERN_HASHS.getName(), context.serialize(createHashCodeList(os.getPatternSet())));
		jsonObj.addProperty(PRODUCER.getName(), os.getProducer());
		jsonObj.addProperty(PRODUCER_URL.getName(), os.getProducerUrl());
		jsonObj.addProperty(URL.getName(), os.getUrl());
		jsonObj.addProperty(HASH.getName(), HashCodeGenerator.generate(os));
		return jsonObj;
	}

}
