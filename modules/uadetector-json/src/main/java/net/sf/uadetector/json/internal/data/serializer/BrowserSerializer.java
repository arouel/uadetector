package net.sf.uadetector.json.internal.data.serializer;

import static net.sf.uadetector.json.internal.data.field.SerializableBrowserField.BROWSER_TYPE_HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserField.FAMILY;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserField.ICON;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserField.INFO_URL;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserField.OPERATING_SYSTEM_HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserField.PATTERNS;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserField.PRODUCER;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserField.PRODUCER_URL;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserField.URL;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.json.internal.data.hashcodebuilder.HashCodeGenerator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class BrowserSerializer implements JsonSerializer<Browser> {

	static List<String> createHashCodeList(final SortedSet<BrowserPattern> patterns) {
		final List<String> hashs = new ArrayList<String>(patterns.size());
		for (final BrowserPattern pattern : patterns) {
			hashs.add(HashCodeGenerator.generate(pattern));
		}
		return hashs;
	}

	@Override
	public JsonElement serialize(final Browser browser, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty(FAMILY.getName(), browser.getFamilyName());
		jsonObj.addProperty(ICON.getName(), browser.getIcon());
		jsonObj.addProperty(INFO_URL.getName(), browser.getInfoUrl());
		if (browser.getOperatingSystem() != null) {
			jsonObj.addProperty(OPERATING_SYSTEM_HASH.getName(), HashCodeGenerator.generate(browser.getOperatingSystem()));
		}
		jsonObj.add(PATTERNS.getName(), context.serialize(createHashCodeList(browser.getPatterns())));
		jsonObj.addProperty(PRODUCER.getName(), browser.getProducer());
		jsonObj.addProperty(PRODUCER_URL.getName(), browser.getProducerUrl());
		jsonObj.addProperty(BROWSER_TYPE_HASH.getName(), HashCodeGenerator.generate(browser.getType()));
		jsonObj.addProperty(URL.getName(), browser.getUrl());
		jsonObj.addProperty(HASH.getName(), HashCodeGenerator.generate(browser));
		return jsonObj;
	}

}
