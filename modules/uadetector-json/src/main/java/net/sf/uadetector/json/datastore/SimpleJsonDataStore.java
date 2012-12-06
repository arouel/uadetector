package net.sf.uadetector.json.datastore;

import java.net.URL;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datastore.AbstractDataStore;
import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.json.datareader.JsonDataReader;

/**
 * This is the simplest implementation of a {@link DataStore}. It initialize the store by reading the <em>UAS data</em>
 * via the passed URL and store it only in the Java heap space.
 * 
 * <p>
 * The given resource must have a valid JSON format with UTF-8 charset. Every entity must be valid against his own
 * checksum (in SHA-256).
 * 
 * @author André Rouél
 */
public final class SimpleJsonDataStore extends AbstractDataStore implements DataStore {

	/**
	 * The default data reader to read in <em>UAS data</em> in JSON format
	 */
	private static final DataReader DEFAULT_DATA_READER = new JsonDataReader();

	/**
	 * Constructs an {@code SimpleJsonDataStore} by reading <em>UAS data</em> by the specified default URL
	 * {@link DataStore#DEFAULT_DATA_URL} (in JSON format).
	 * 
	 * @param dataUrl
	 *            URL to <em>UAS data</em>
	 * @param versionUrl
	 *            URL to version information about the given <em>UAS data</em>
	 */
	public SimpleJsonDataStore(final URL dataUrl, final URL versionUrl) {
		super(DEFAULT_DATA_READER, Check.notNull(dataUrl), Check.notNull(versionUrl), DEFAULT_CHARSET);
	}

	/**
	 * Constructs an {@code SimpleJsonDataStore} by reading <em>UAS data</em> by the specified default URL
	 * {@link DataStore#DEFAULT_DATA_URL} (in JSON format).
	 * 
	 * @param dataUrl
	 *            URL to <em>UAS data</em>
	 * @param versionUrl
	 *            URL to version information about the given <em>UAS data</em>
	 * @param fallback
	 *            <em>UAS data</em> as fallback in case the data on the specified resource can not be read correctly
	 */
	public SimpleJsonDataStore(final URL dataUrl, final URL versionUrl, final Data fallback) {
		super(DEFAULT_DATA_READER, Check.notNull(dataUrl), Check.notNull(versionUrl), DEFAULT_CHARSET, Check.notNull(fallback));
	}

}
