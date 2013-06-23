package net.sf.uadetector.datastore;

import net.sf.uadetector.internal.data.Data;

import org.junit.Assert;
import org.junit.Test;

public class UpdateOperationWithCacheFileTaskTest_isNewerData {

	@Test
	public void isNewerData_differentVersion() {
		final Data older = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL, TestXmlDataStore.VERSION_URL).getData();
		final Data newer = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL_NEWER, TestXmlDataStore.VERSION_URL_NEWER).getData();
		Assert.assertTrue(UpdateOperationWithCacheFileTask.isNewerData(older, newer));
		Assert.assertFalse(UpdateOperationWithCacheFileTask.isNewerData(newer, older));
	}

	@Test
	public void isNewerData_sameVersion() {
		final Data older = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL, TestXmlDataStore.VERSION_URL).getData();
		final Data newer = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL, TestXmlDataStore.VERSION_URL).getData();
		Assert.assertFalse(UpdateOperationWithCacheFileTask.isNewerData(older, newer));
		Assert.assertFalse(UpdateOperationWithCacheFileTask.isNewerData(newer, older));
	}

}
