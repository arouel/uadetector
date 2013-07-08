package net.sf.uadetector.json.internal.data.deserializer;

import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.FAMILY_NAME;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.ICON;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.INFO_URL;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.NAME;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.PRODUCER;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.PRODUCER_URL;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.USER_AGENT_STRING;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.json.SerDeOption;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public final class RobotDeserializer extends AbstractDeserializer<Robot> implements JsonDeserializer<Robot> {

	private final AtomicInteger counter = new AtomicInteger(0);

	public RobotDeserializer(final EnumSet<SerDeOption> options) {
		super(options);
	}

	@Override
	public Robot deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
		String hash = EMPTY_HASH_CODE;

		// deserialize
		final Robot.Builder b = new Robot.Builder();
		b.setId(counter.incrementAndGet());
		for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
			if (FAMILY_NAME.getName().equals(entry.getKey())) {
				b.setFamilyName(entry.getValue().getAsString());
			} else if (HASH.getName().equals(entry.getKey())) {
				hash = entry.getValue().getAsString();
			} else if (ICON.getName().equals(entry.getKey())) {
				b.setIcon(entry.getValue().getAsString());
			} else if (INFO_URL.getName().equals(entry.getKey())) {
				b.setInfoUrl(entry.getValue().getAsString());
			} else if (NAME.getName().equals(entry.getKey())) {
				b.setName(entry.getValue().getAsString());
			} else if (PRODUCER.getName().equals(entry.getKey())) {
				b.setProducer(entry.getValue().getAsString());
			} else if (PRODUCER_URL.getName().equals(entry.getKey())) {
				b.setProducerUrl(entry.getValue().getAsString());
			} else if (USER_AGENT_STRING.getName().equals(entry.getKey())) {
				b.setUserAgentString(entry.getValue().getAsString());
			}
		}

		// create robot entry
		Robot robot = null;
		try {
			robot = b.build();

			// check hash when option is set
			checkHash(json, hash, robot);
		} catch (final Exception e) {
			addWarning(e.getLocalizedMessage());
		}

		return robot;
	}

}
