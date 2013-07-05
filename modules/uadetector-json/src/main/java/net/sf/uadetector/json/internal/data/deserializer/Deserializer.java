package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.uadetector.json.SerDeOption;
import net.sf.uadetector.json.internal.data.util.MessageCollector;

interface Deserializer extends MessageCollector {

	String EMPTY_HASH_CODE = "";

	String MSG_HASH_CODE_DIFFERENCE = "The computed hash code (%s) differs from the original (%s): %s";

	/**
	 * Gets the deserialization options.
	 * 
	 * @return options during deserialization
	 */
	EnumSet<SerDeOption> getOptions();

}
