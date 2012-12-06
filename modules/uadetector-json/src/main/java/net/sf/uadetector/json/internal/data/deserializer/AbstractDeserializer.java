package net.sf.uadetector.json.internal.data.deserializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import net.sf.uadetector.json.internal.data.JsonConverter.SerializationOption;
import net.sf.uadetector.json.internal.data.hashcodebuilder.HashCodeGenerator;

import com.google.gson.JsonElement;

public abstract class AbstractDeserializer<T> implements Deserializer {

	/**
	 * Options to deserialize JSON into {@link net.sf.uadetector.internal.data.Data}
	 */
	private final EnumSet<SerializationOption> options;

	private final List<String> warnings = new ArrayList<String>();

	public AbstractDeserializer(final EnumSet<SerializationOption> options) {
		this.options = options;
	}

	@Override
	public void addWarning(final String warning) {
		warnings.add(warning);
	}

	/**
	 * Checks that the passed hash is equal to a new computed one if the option
	 * {@code SerializationOption#IGNORE_HASH_CODE} is set.
	 * 
	 * <p>
	 * If the computed hash is not equal to the given one a warning will be added in the list of warnings.
	 * 
	 * @param hash
	 *            Hash in string representation
	 * @param element
	 *            Element for which a new code to be generated
	 */
	public final void checkHash(final JsonElement json, final String hash, final T element) {
		if (getOptions().contains(SerializationOption.HASH_VALIDATING)) {
			final String newHash = HashCodeGenerator.generate(element);
			if (!hash.equals(newHash)) {
				final String warning = String.format(MSG_HASH_CODE_DIFFERENCE, hash, newHash, json);
				addWarning(warning);
			}
		}
	}

	@Override
	public final EnumSet<SerializationOption> getOptions() {
		return options;
	}

	@Override
	public List<String> getWarnings() {
		return Collections.unmodifiableList(warnings);
	}

}
