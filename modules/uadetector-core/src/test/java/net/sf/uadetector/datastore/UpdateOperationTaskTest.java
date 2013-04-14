package net.sf.uadetector.datastore;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class UpdateOperationTaskTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_dataStore_isNull() {
		new UpdateOperationTask(null);
	}

}
