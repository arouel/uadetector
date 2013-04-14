package net.sf.uadetector.datastore;

import java.util.HashSet;
import java.util.TreeMap;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

import org.junit.Assert;
import org.junit.Test;

public class AbstractRefreshableDataStoreTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void setData_null() {
		final TestXmlDataStore store = new TestXmlDataStore();
		store.setData(null);
	}

	@Test
	public void setData_successful() {
		final TestXmlDataStore store = new TestXmlDataStore();
		final Data data2 = new Data(new HashSet<Browser>(), new HashSet<OperatingSystem>(), new HashSet<Robot>(0),
				new TreeMap<BrowserPattern, Browser>(), new TreeMap<OperatingSystemPattern, OperatingSystem>(), "test-version");
		store.setData(data2);
		Assert.assertSame(data2, store.getData());
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void setData_withEmptyData() {
		final TestXmlDataStore store = new TestXmlDataStore();
		store.setData(Data.EMPTY);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setUpdateOperation_null() {
		final TestXmlDataStore store = new TestXmlDataStore();
		store.setUpdateOperation(null);
	}

}
