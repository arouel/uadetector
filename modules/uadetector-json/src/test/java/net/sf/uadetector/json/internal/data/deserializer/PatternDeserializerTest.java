package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;
import java.util.regex.Pattern;

import net.sf.uadetector.json.SerDeOption;

import org.junit.Test;

public class PatternDeserializerTest {

	@Test
	public void deserialize_emptyJsonObject() {
		Deserializers.deserialize("{}", EnumSet.noneOf(SerDeOption.class), Pattern.class);
	}

}
