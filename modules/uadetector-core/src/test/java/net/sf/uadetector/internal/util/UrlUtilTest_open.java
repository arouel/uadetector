package net.sf.uadetector.internal.util;

import java.io.IOException;
import java.net.URL;

import net.sf.uadetector.exception.CanNotOpenStreamException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UrlUtil.class)
public class UrlUtilTest_open {

	@Test(expected = CanNotOpenStreamException.class)
	public void open_withIOException() throws IOException {
		// The following line makes a bug in EasyMock 3.1 visible. For example on OS X the output will be
		// 'EasyMock.DISABLE_CLASS_MOCKING: false' and on Linux it is 'EasyMock.DISABLE_CLASS_MOCKING: true'
		EasyMock.setEasyMockProperty(EasyMock.DISABLE_CLASS_MOCKING, Boolean.FALSE.toString());
		System.out.println("EasyMock.DISABLE_CLASS_MOCKING: " + EasyMock.getEasyMockProperty(EasyMock.DISABLE_CLASS_MOCKING));

		final URL url = PowerMock.createMock(URL.class);
		EasyMock.expect(url.openStream()).andThrow(new IOException());
		PowerMock.replayAll();
		UrlUtil.open(url);
		PowerMock.verifyAll();
	}

}
