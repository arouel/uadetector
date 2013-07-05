package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.json.internal.data.Option;

import org.junit.Test;

public class BrowserTypeDeserializerTest {

	@Test
	public void deserialize_emptyJsonObject() {
		Deserializers.deserialize("{}", EnumSet.noneOf(Option.class), BrowserType.class);
	}

}
