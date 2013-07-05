package net.sf.uadetector.json.internal.data.deserializer;

import java.util.List;

/**
 * An instance of this class represents the result of an deserialization.
 */
public final class Deserialization<T> {

	private final T data;

	private final List<String> warnings;

	public Deserialization(final T data, final List<String> warnings) {
		this.data = data;
		this.warnings = warnings;
	}

	public T getData() {
		return data;
	}

	/**
	 * Returns a list of warnings that caused during deserialization.
	 * 
	 * @return list of warnings
	 */
	public List<String> getWarnings() {
		return warnings;
	}

}
