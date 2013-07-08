package net.sf.uadetector.json.internal.data.serializer;

import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.FAMILY_NAME;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.ICON;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.INFO_URL;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.NAME;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.PRODUCER;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.PRODUCER_URL;
import static net.sf.uadetector.json.internal.data.field.SerializableRobotField.USER_AGENT_STRING;

import java.lang.reflect.Type;

import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.json.internal.data.hashcodebuilder.HashCodeGenerator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class RobotSerializer implements JsonSerializer<Robot> {

	@Override
	public JsonElement serialize(final Robot robot, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty(FAMILY_NAME.getName(), robot.getFamilyName());
		jsonObj.addProperty(ICON.getName(), robot.getIcon());
		jsonObj.addProperty(INFO_URL.getName(), robot.getInfoUrl());
		jsonObj.addProperty(NAME.getName(), robot.getName());
		jsonObj.addProperty(PRODUCER.getName(), robot.getProducer());
		jsonObj.addProperty(PRODUCER_URL.getName(), robot.getProducerUrl());
		jsonObj.addProperty(USER_AGENT_STRING.getName(), robot.getUserAgentString());
		jsonObj.addProperty(HASH.getName(), HashCodeGenerator.generate(robot));
		return jsonObj;
	}

}
