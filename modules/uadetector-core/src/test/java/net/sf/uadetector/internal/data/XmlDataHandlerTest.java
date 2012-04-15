package net.sf.uadetector.internal.data;

import org.junit.Test;

public class XmlDataHandlerTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructor_null() {
		new XmlDataHandler(null);
	}

}
