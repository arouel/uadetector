package net.sf.uadetector.datastore;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.DataBlueprint;

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
		final Data data2 = new DataBlueprint().version("test-version").build();
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
