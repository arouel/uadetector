package net.sf.uadetector.json;

import java.net.URL;

import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.datastore.SimpleXmlDataStore;

public final class TestDataStoreFactory {

	public static final URL DATA_URL = TestDataStoreFactory.class.getClassLoader().getResource("samples/uas.xml");

	public static final URL VERSION_URL = TestDataStoreFactory.class.getClassLoader().getResource("samples/uas.version");

	public static DataStore produce() {
		return new SimpleXmlDataStore(DATA_URL, VERSION_URL);
	}

}
