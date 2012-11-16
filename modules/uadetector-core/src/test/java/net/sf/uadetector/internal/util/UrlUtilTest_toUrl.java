package net.sf.uadetector.internal.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import org.easymock.EasyMock;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ File.class, URI.class })
public class UrlUtilTest_toUrl {

	private static final Logger LOG = LoggerFactory.getLogger(UrlUtilTest_toUrl.class);

	@Before
	public void doNotRunOnLinux() {
		final boolean isLinux = OperatingSystemDetector.isLinux();
		if (isLinux) {
			LOG.info("This unit test will be ignored due to a bug in EasyMock <= 3.1, in the class mocking feature under GNU/Linux.");
		}
	}

	@Test(expected = IllegalStateException.class)
	public void read() throws Exception {
		Assume.assumeTrue(!OperatingSystemDetector.isLinux());
		final URI uri = PowerMock.createPartialMock(URI.class, "toURL");
		EasyMock.expect(uri.toURL()).andThrow(new MalformedURLException());
		final File file = PowerMock.createMock(File.class);
		EasyMock.expect(file.toURI()).andReturn(uri);
		PowerMock.replayAll();
		UrlUtil.toUrl(file);
		PowerMock.verifyAll();
	}

}
