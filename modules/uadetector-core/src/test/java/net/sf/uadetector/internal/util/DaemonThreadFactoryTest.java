package net.sf.uadetector.internal.util;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class DaemonThreadFactoryTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_threadName_isNull() {
		new DaemonThreadFactory(null);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_threadName_isEmpty() {
		new DaemonThreadFactory("");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_threadName_isEmptyAfterTrimming() {
		new DaemonThreadFactory("  ");
	}

}
