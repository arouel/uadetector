package net.sf.uadetector.json.datastore;

import java.net.URL;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class SimpleJsonDataStoreTest {

	private static final URL DATA_URL = SimpleJsonDataStoreTest.class.getClassLoader().getResource("uas.json");

	private static final URL VERSION_URL = SimpleJsonDataStoreTest.class.getClassLoader().getResource("uas.version");

	@Test(expected = IllegalNullArgumentException.class)
	public void construct1_dataIsNull() {
		new SimpleJsonDataStore(null, VERSION_URL);
	}

	@Test
	public void construct1_successful() {
		Assert.assertNotNull(new SimpleJsonDataStore(DATA_URL, VERSION_URL));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct1_versionIsNull() {
		new SimpleJsonDataStore(DATA_URL, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct2_charsetIsNull() {
		new SimpleJsonDataStore(DATA_URL, VERSION_URL, null);
	}

}
