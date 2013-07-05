package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.json.SerDeOption;

import org.junit.Test;

public class BrowserPatternDeserializerTest {

	@Test
	public void deserialize_emptyJsonObject() {
		Deserializers.deserialize("{}", EnumSet.noneOf(SerDeOption.class), BrowserPattern.class);
	}

}
