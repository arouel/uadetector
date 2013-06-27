package net.sf.uadetector.datastore;

import javax.annotation.Nonnegative;

/**
 * Defines an update operation which will be executed within a executor service in background.
 * 
 * @author André Rouél
 */
public interface UpdateOperation extends Runnable {

	/**
	 * The default interval to check for updates is once per day
	 */
	@Nonnegative
	long DEFAULT_UPDATE_INTERVAL = 1000 * 60 * 60 * 24;

	/**
	 * This function checks whether updated UAS data are available and updates silently the data in a
	 * {@link net.sf.uadetector.datastore.DataStore}.
	 */
	void call();

	/**
	 * Gets the time of the last update check in milliseconds.
	 * 
	 * @return time of the last update check in milliseconds
	 */
	@Nonnegative
	long getLastUpdateCheck();

	/**
	 * Shuts down the corresponding background executor.
	 */
	void shutdown();

}
