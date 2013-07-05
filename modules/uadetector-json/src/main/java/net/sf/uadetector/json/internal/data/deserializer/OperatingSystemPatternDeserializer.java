package net.sf.uadetector.json.internal.data.deserializer;

import static net.sf.uadetector.json.internal.data.field.SerializableOrderedPatternField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableOrderedPatternField.PATTERN;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.json.SerDeOption;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public final class OperatingSystemPatternDeserializer extends AbstractDeserializer<OperatingSystemPattern> implements
		JsonDeserializer<OperatingSystemPattern> {

	private final AtomicInteger counter = new AtomicInteger(0);

	private final Map<String, OperatingSystemPattern> operatingSystemPatterns = new HashMap<String, OperatingSystemPattern>();

	public OperatingSystemPatternDeserializer(final EnumSet<SerDeOption> options) {
		super(options);
	}

	@Override
	public OperatingSystemPattern deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
		String hash = EMPTY_HASH_CODE;

		// deserialize
		Pattern pattern = null;
		for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
			if (PATTERN.getName().equals(entry.getKey())) {
				pattern = context.deserialize(entry.getValue(), Pattern.class);
			} else if (HASH.getName().equals(entry.getKey())) {
				hash = entry.getValue().getAsString();
			}
		}
		final int id = counter.incrementAndGet();

		// create OS pattern
		OperatingSystemPattern osPattern = new OperatingSystemPattern(id, pattern, id);
		try {
			osPattern = new OperatingSystemPattern(id, pattern, id);

			// check hash when option is set
			checkHash(json, hash, osPattern);

			// add pattern to map
			operatingSystemPatterns.put(hash, osPattern);
		} catch (final Exception e) {
			addWarning(e.getLocalizedMessage());
		}

		return osPattern;
	}

	@Nullable
	public OperatingSystemPattern findOperatingSystemPattern(@Nonnull final String hash) {
		Check.notEmpty(hash, "hash");
		final OperatingSystemPattern pattern = operatingSystemPatterns.get(hash);
		if (pattern == null) {
			addWarning("Can not find operating system pattern for hash '" + hash + "'.");
		}
		return pattern;
	}

}
