package net.sf.uadetector.json.internal.data.util;

import java.util.List;

public interface MessageCollector {

	/**
	 * Adds a warning to the list of warning.
	 * 
	 * @param warning
	 *            a warning
	 */
	void addWarning(final String warning);

	/**
	 * Returns a list of warnings that caused during deserialization.
	 * 
	 * @return list of warnings
	 */
	List<String> getWarnings();

}
