package net.sf.uadetector.internal.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ File.class, URI.class })
public class UrlUtilTest_toUrl {

	@Test(expected = IllegalStateException.class)
	public void read() throws Exception {
		final URI uri = PowerMock.createPartialMock(URI.class, "toURL");
		EasyMock.expect(uri.toURL()).andThrow(new MalformedURLException());
		final File file = PowerMock.createMock(File.class);
		EasyMock.expect(file.toURI()).andReturn(uri);
		PowerMock.replayAll();
		UrlUtil.toUrl(file);
		PowerMock.verifyAll();
	}

}
