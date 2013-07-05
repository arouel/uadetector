package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.json.SerDeOption;

import org.junit.Test;

public class OperatingSystemDeserializerTest {

	@Test
	public void deserialize_emptyJsonObject() {
		Deserializers.deserialize("{}", EnumSet.noneOf(SerDeOption.class), OperatingSystem.class);
	}

}
