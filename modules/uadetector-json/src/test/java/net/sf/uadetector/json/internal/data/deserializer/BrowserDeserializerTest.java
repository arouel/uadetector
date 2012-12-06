package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.json.internal.data.Deserializers;
import net.sf.uadetector.json.internal.data.JsonConverter.SerializationOption;

import org.junit.Test;

public class BrowserDeserializerTest {

	//@Test(expected = IllegalArgumentException.class)
	@Test
	public void deserialize_emptyJsonObject() {
		Deserializers.deserialize("{}", EnumSet.noneOf(SerializationOption.class), Browser.class);
	}

}
