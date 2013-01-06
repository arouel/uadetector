package net.sf.uadetector.datareader;

import net.sf.uadetector.datareader.XmlDataReader.XmlParser;
import net.sf.uadetector.internal.data.XmlDataHandler;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(XmlDataHandler.class)
public class XmlParserTest {

	@Test(expected = IllegalStateException.class)
	public void validate_hasError() {
		final XmlDataHandler mock = PowerMock.createMock(XmlDataHandler.class);
		EasyMock.expect(mock.hasError()).andReturn(true).anyTimes();
		EasyMock.expect(mock.hasWarning()).andReturn(false).anyTimes();
		PowerMock.replay(mock);

		XmlParser.validate(mock);

		PowerMock.verify(mock);
	}

	@Test(expected = IllegalStateException.class)
	public void validate_hasErrorAndWarning() {
		final XmlDataHandler mock = PowerMock.createMock(XmlDataHandler.class);
		EasyMock.expect(mock.hasError()).andReturn(true).anyTimes();
		EasyMock.expect(mock.hasWarning()).andReturn(true).anyTimes();
		PowerMock.replay(mock);

		XmlParser.validate(mock);

		PowerMock.verify(mock);
	}

	@Test(expected = IllegalStateException.class)
	public void validate_hasWarning() {
		final XmlDataHandler mock = PowerMock.createMock(XmlDataHandler.class);
		EasyMock.expect(mock.hasError()).andReturn(false).anyTimes();
		EasyMock.expect(mock.hasWarning()).andReturn(true).anyTimes();
		PowerMock.replay(mock);

		XmlParser.validate(mock);

		PowerMock.verify(mock);
	}

	@Test
	public void validate_noErrorAndWarning() {
		final XmlDataHandler mock = PowerMock.createMock(XmlDataHandler.class);
		EasyMock.expect(mock.hasError()).andReturn(false).anyTimes();
		EasyMock.expect(mock.hasWarning()).andReturn(false).anyTimes();
		PowerMock.replay(mock);

		XmlParser.validate(mock);

		PowerMock.verify(mock);
	}

}
