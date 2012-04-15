package net.sf.uadetector.internal.data;

import org.junit.Test;

public class XmlDataHandlerTagTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructor_null() {
		XmlDataHandler.Tag.evaluate(null);
	}

}
