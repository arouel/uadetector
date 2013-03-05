package net.sf.uadetector.internal.util;

import org.junit.Test;

public class DaemonThreadFactoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void construct_threadName_isNull() {
		new DaemonThreadFactory(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_threadName_isEmpty() {
		new DaemonThreadFactory("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_threadName_isEmptyAfterTrimming() {
		new DaemonThreadFactory("  ");
	}

}
