package net.sf.uadetector.json.internal.data.serializer;

import static net.sf.uadetector.json.internal.data.field.SerializableDataField.BROWSERPATTERNS;
import static net.sf.uadetector.json.internal.data.field.SerializableDataField.BROWSERS;
import static net.sf.uadetector.json.internal.data.field.SerializableDataField.BROWSERTYPES;
import static net.sf.uadetector.json.internal.data.field.SerializableDataField.OPERATINGSYSTEMPATTERNS;
import static net.sf.uadetector.json.internal.data.field.SerializableDataField.OPERATINGSYSTEMS;
import static net.sf.uadetector.json.internal.data.field.SerializableDataField.ROBOTS;
import static net.sf.uadetector.json.internal.data.field.SerializableDataField.VERSION;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.json.internal.data.comparator.BrowserIdComparator;
import net.sf.uadetector.json.internal.data.comparator.BrowserTypeIdComparator;
import net.sf.uadetector.json.internal.data.comparator.OperatingSystemIdComparator;
import net.sf.uadetector.json.internal.data.comparator.OrderedPatternPositionComparator;
import net.sf.uadetector.json.internal.data.comparator.RobotIdComparator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class DataSerializer implements JsonSerializer<Data> {

	private static Set<BrowserType> findAllBrowserTypes(final Set<Browser> browsers) {
		final Set<BrowserType> types = new HashSet<BrowserType>(browsers.size());
		for (final Browser browser : browsers) {
			types.add(browser.getType());
		}
		return types;
	}

	@Override
	public JsonElement serialize(final Data data, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObj = new JsonObject();
		jsonObj.add(DataJsonFormat.VERSION_1_0.getKey(), context.serialize(DataJsonFormat.VERSION_1_0.getVersion()));
		jsonObj.add(VERSION.getName(), context.serialize(data.getVersion()));

		final List<OperatingSystemPattern> osPatterns = new ArrayList<OperatingSystemPattern>(data.getPatternOsMap().keySet());
		Collections.sort(osPatterns, new OrderedPatternPositionComparator<OperatingSystemPattern>());
		jsonObj.add(OPERATINGSYSTEMPATTERNS.getName(), context.serialize(osPatterns));

		final List<OperatingSystem> systems = new ArrayList<OperatingSystem>(data.getOperatingSystems());
		Collections.sort(systems, new OperatingSystemIdComparator());
		jsonObj.add(OPERATINGSYSTEMS.getName(), context.serialize(systems));

		final List<BrowserType> browserTypes = new ArrayList<BrowserType>(findAllBrowserTypes(data.getBrowsers()));
		Collections.sort(browserTypes, new BrowserTypeIdComparator());
		jsonObj.add(BROWSERTYPES.getName(), context.serialize(browserTypes));

		final List<BrowserPattern> browserPatterns = new ArrayList<BrowserPattern>(data.getPatternBrowserMap().keySet());
		Collections.sort(browserPatterns, new OrderedPatternPositionComparator<BrowserPattern>());
		jsonObj.add(BROWSERPATTERNS.getName(), context.serialize(browserPatterns));

		final List<Browser> browsers = new ArrayList<Browser>(data.getBrowsers());
		Collections.sort(browsers, new BrowserIdComparator());
		jsonObj.add(BROWSERS.getName(), context.serialize(browsers));

		final List<Robot> robots = new ArrayList<Robot>(data.getRobots());
		Collections.sort(robots, new RobotIdComparator());
		jsonObj.add(ROBOTS.getName(), context.serialize(robots));
		return jsonObj;
	}

}
