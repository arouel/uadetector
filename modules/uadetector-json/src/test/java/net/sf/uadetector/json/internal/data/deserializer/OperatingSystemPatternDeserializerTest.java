package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.json.internal.data.Option;

import org.junit.Test;

public class OperatingSystemPatternDeserializerTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void deserialize_emptyJsonObject() {
		Deserializers.deserialize("{}", EnumSet.noneOf(Option.class), OperatingSystemPattern.class);
	}

}
