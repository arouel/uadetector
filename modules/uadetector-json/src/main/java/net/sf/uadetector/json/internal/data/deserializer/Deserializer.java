package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;
import java.util.List;

import net.sf.uadetector.json.internal.data.JsonConverter.SerializationOption;

interface Deserializer {

	String EMPTY_HASH_CODE = "";

	String MSG_HASH_CODE_DIFFERENCE = "The computed hash code (%s) differs from the original (%s): %s";

	/**
	 * Adds a warning to the list of warning.
	 * 
	 * @param warning
	 *            a warning
	 */
	void addWarning(final String warning);

	/**
	 * Gets the deserialization options.
	 * 
	 * @return options during deserialization
	 */
	EnumSet<SerializationOption> getOptions();

	/**
	 * Returns a list of warnings that caused during deserialization.
	 * 
	 * @return list of warnings
	 */
	List<String> getWarnings();

}
