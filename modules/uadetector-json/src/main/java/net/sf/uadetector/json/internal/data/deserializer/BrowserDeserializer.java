package net.sf.uadetector.json.internal.data.deserializer;

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
import java.util.EnumSet;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.json.internal.data.JsonConverter.SerializationOption;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public final class BrowserDeserializer extends AbstractDeserializer<Browser> implements JsonDeserializer<Browser> {

	private final BrowserPatternDeserializer browserPatternDeserializer;

	private final BrowserTypeDeserializer browserTypeDeserializer;

	private final AtomicInteger counter = new AtomicInteger(0);

	private final OperatingSystemDeserializer operatingSystemDeserializer;

	public BrowserDeserializer(final EnumSet<SerializationOption> options, final BrowserPatternDeserializer browserPatternDeserializer,
			final BrowserTypeDeserializer browserTypeDeserializer, final OperatingSystemDeserializer operatingSystemDeserializer) {
		super(options);
		this.browserPatternDeserializer = Check.notNull(browserPatternDeserializer, "browserPatternDeserializer");
		this.browserTypeDeserializer = Check.notNull(browserTypeDeserializer, "browserTypeDeserializer");
		this.operatingSystemDeserializer = Check.notNull(operatingSystemDeserializer, "operatingSystemDeserializer");
	}

	@Override
	public Browser deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
		String hash = EMPTY_HASH_CODE;
		final Browser.Builder b = new Browser.Builder();
		b.setId(counter.incrementAndGet());

		// deserialize
		for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
			if (FAMILY.getName().equals(entry.getKey())) {
				b.setFamily(UserAgentFamily.valueOf(entry.getValue().getAsString()));
			} else if (HASH.getName().equals(entry.getKey())) {
				hash = entry.getValue().getAsString();
			} else if (ICON.getName().equals(entry.getKey())) {
				b.setIcon(entry.getValue().getAsString());
			} else if (INFO_URL.getName().equals(entry.getKey())) {
				b.setInfoUrl(entry.getValue().getAsString());
			} else if (OPERATING_SYSTEM_HASH.getName().equals(entry.getKey())) {
				final OperatingSystem os = operatingSystemDeserializer.findOperatingSystem(entry.getValue().getAsString());
				if (os != null) {
					b.setOperatingSystem(os);
				}
			} else if (PATTERNS.getName().equals(entry.getKey())) {
				final SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
				for (final JsonElement element : entry.getValue().getAsJsonArray()) {
					final BrowserPattern browserPattern = browserPatternDeserializer.findBrowserPattern(element.getAsString());
					if (browserPattern != null) {
						patternSet.add(browserPattern);
					}
				}
				b.setPatternSet(patternSet);
			} else if (PRODUCER.getName().equals(entry.getKey())) {
				b.setProducer(entry.getValue().getAsString());
			} else if (PRODUCER_URL.getName().equals(entry.getKey())) {
				b.setProducerUrl(entry.getValue().getAsString());
			} else if (BROWSER_TYPE_HASH.getName().equals(entry.getKey())) {
				final BrowserType type = browserTypeDeserializer.findBrowserType(entry.getValue().getAsString());
				if (type != null) {
					b.setType(type);
				}
			} else if (URL.getName().equals(entry.getKey())) {
				b.setUrl(entry.getValue().getAsString());
			}
		}

		// create a browser entry
		Browser browser = null;
		try {
			browser = b.build();

			// check hash when option is set
			checkHash(json, hash, browser);
		} catch (final Exception e) {
			final String msg = String.format("Can not create browser entry: %s", e.getLocalizedMessage());
			addWarning(msg);
		}

		return browser;
	}

}
