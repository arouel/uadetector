package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.json.SerDeOption;

import org.junit.Test;

public class BrowserTypeDeserializerTest {

	@Test
	public void deserialize_emptyJsonObject() {
		Deserializers.deserialize("{}", EnumSet.noneOf(SerDeOption.class), BrowserType.class);
	}

}
