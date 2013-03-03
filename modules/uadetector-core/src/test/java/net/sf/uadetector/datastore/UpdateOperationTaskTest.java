package net.sf.uadetector.datastore;

import org.junit.Test;

public class UpdateOperationTaskTest {

	@Test(expected = IllegalArgumentException.class)
	public void construct_dataStore_isNull() {
		new UpdateOperationTask(null);
	}

}
