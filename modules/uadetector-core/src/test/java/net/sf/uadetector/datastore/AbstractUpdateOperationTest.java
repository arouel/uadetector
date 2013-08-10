package net.sf.uadetector.datastore;

import org.junit.Assert;
import org.junit.Test;

public class AbstractUpdateOperationTest {

	@Test
	public void hasUpdate_true() {
		Assert.assertTrue(AbstractUpdateOperation.hasUpdate("20130310-02", "20130310-01"));
		Assert.assertTrue(AbstractUpdateOperation.hasUpdate("20130310-01", "20130301-01"));
		Assert.assertTrue(AbstractUpdateOperation.hasUpdate("20130310-02", "20120408-10"));
	}
	
	@Test
	public void hasUpdate_false() {
		Assert.assertFalse(AbstractUpdateOperation.hasUpdate("20130310-02", "20130310-03"));
		Assert.assertFalse(AbstractUpdateOperation.hasUpdate("20130310-01", "20130311-01"));
		Assert.assertFalse(AbstractUpdateOperation.hasUpdate("20110310-02", "20120408-10"));
		Assert.assertFalse(AbstractUpdateOperation.hasUpdate("", "20120408-10"));
		Assert.assertFalse(AbstractUpdateOperation.hasUpdate("<h1>Error - Connect failed:<br /> <u>Too many connections</u></h1>", "20120408-10"));
		Assert.assertFalse(AbstractUpdateOperation.hasUpdate("", ""));
		Assert.assertFalse(AbstractUpdateOperation.hasUpdate("", " "));
		Assert.assertFalse(AbstractUpdateOperation.hasUpdate(" ", ""));
	}

}
