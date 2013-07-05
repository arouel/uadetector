package net.sf.uadetector.json.internal.data.serializer;

import java.util.List;

/**
 * An instance of this class represents the result of an serialization.
 */
public final class Serialization {

	private final String json;

	private final List<String> warnings;

	public Serialization(final String json, final List<String> warnings) {
		this.json = json;
		this.warnings = warnings;
	}

	public String getJson() {
		return json;
	}

	/**
	 * Returns a list of warnings that caused during serialization.
	 * 
	 * @return list of warnings
	 */
	public List<String> getWarnings() {
		return warnings;
	}

}
