package net.sf.uadetector.json.internal.data.deserializer;

import java.util.EnumSet;

import net.sf.uadetector.json.SerDeOption;
import net.sf.uadetector.json.internal.data.hashcodebuilder.HashCodeGenerator;
import net.sf.uadetector.json.internal.data.util.AbstractMessageCollector;

import com.google.gson.JsonElement;

abstract class AbstractDeserializer<T> extends AbstractMessageCollector<T> implements Deserializer {

	/**
	 * Options to deserialize JSON into {@link net.sf.uadetector.internal.data.Data}
	 */
	private final EnumSet<SerDeOption> options;

	public AbstractDeserializer(final EnumSet<SerDeOption> options) {
		this.options = options;
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
		if (getOptions().contains(SerDeOption.HASH_VALIDATING)) {
			final String newHash = HashCodeGenerator.generate(element);
			if (!hash.equals(newHash)) {
				final String warning = String.format(MSG_HASH_CODE_DIFFERENCE, hash, newHash, json);
				addWarning(warning);
			}
		}
	}

	@Override
	public final EnumSet<SerDeOption> getOptions() {
		return options;
	}

}
