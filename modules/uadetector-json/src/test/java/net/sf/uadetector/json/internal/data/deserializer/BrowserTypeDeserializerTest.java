package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.json.internal.data.Deserializers;
import net.sf.uadetector.json.internal.data.JsonConverter.SerializationOption;

import org.junit.Test;

public class BrowserTypeDeserializerTest {

	@Test
	public void deserialize_emptyJsonObject() {
		Deserializers.deserialize("{}", EnumSet.noneOf(SerializationOption.class), BrowserType.class);
	}

}
