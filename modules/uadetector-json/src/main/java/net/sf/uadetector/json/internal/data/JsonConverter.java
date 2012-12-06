package net.sf.uadetector.json.internal.data;

import java.util.Arrays;
import java.util.EnumSet;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.internal.data.Data;

/**
 * Converts a instance of {@link Data} into JSON representation and back.
 * 
 * @author André Rouél
 */
public final class JsonConverter {

	/**
	 * Options to serialize {@link Data}
	 */
	public enum SerializationOption {
		HASH_VALIDATING, PRETTY_PRINTING
	}

	/**
	 * Representation of no special serialization options
	 */
	private static final EnumSet<SerializationOption> WITHOUT_SPECIAL_OPTIONS = EnumSet.noneOf(SerializationOption.class);

	/**
	 * Converts an array of options to an {@code EnumSet}.
	 * 
	 * @param options
	 *            array of options to convert
	 * @return {@code EnumSet} of options
	 */
	private static EnumSet<SerializationOption> convertOptions(final SerializationOption... options) {
		final EnumSet<SerializationOption> opts;
		if (options != null) {
			opts = EnumSet.copyOf(Arrays.asList(options));
		} else {
			opts = EnumSet.noneOf(SerializationOption.class);
		}
		return opts;
	}

	/**
	 * Deserializes a JSON representation into a new instance of {@link Data}.
	 * 
	 * @param json
	 *            JSON representation of {@code Data}
	 * @return instance of {@code Data}
	 */
	public static Deserialization<Data> deserialize(final String json) {
		Check.notNull(json);
		return deserialize(json, WITHOUT_SPECIAL_OPTIONS);
	}

	/**
	 * Deserializes a JSON representation into a new instance of {@link Data}.
	 * 
	 * @param json
	 *            JSON representation of {@code Data}
	 * @param options
	 *            options for deserializing into {@code Data} (e.g. hash code validating)
	 * @return instance of {@code Data}
	 */
	public static Deserialization<Data> deserialize(final String json, final EnumSet<SerializationOption> options) {
		Check.notNull(json);
		Check.notNull(options);
		return Deserializers.deserialize(json, options);
	}

	/**
	 * Deserializes a JSON representation into a new instance of {@link Data}.
	 * 
	 * @param json
	 *            JSON representation of {@code Data}
	 * @param options
	 *            options for deserializing into {@code Data} (e.g. hash code validating)
	 * @return instance of {@code Data}
	 */
	public static Deserialization<Data> deserialize(final String json, final SerializationOption... options) {
		Check.notNull(json);
		Check.notNull(options);
		return deserialize(json, convertOptions(options));
	}

	/**
	 * Serializes an instance of {@link Data} into a JSON representation.
	 * 
	 * @param data
	 *            instance of {@code Data}
	 * @return JSON representation of the given {@code Data}
	 */
	public static Serialization serialize(final Data data) {
		Check.notNull(data);
		return serialize(data, EnumSet.noneOf(SerializationOption.class));
	}

	/**
	 * Serializes an instance of {@link Data} into a JSON representation.
	 * 
	 * @param data
	 *            instance of {@code Data}
	 * @param options
	 *            options to serialize into JSON (e.g. pretty printing)
	 * @return JSON representation of the given {@code Data}
	 */
	public static Serialization serialize(final Data data, final EnumSet<SerializationOption> options) {
		Check.notNull(data);
		Check.notNull(options);
		return Serializers.serialize(data, options);
	}

	/**
	 * Serializes an instance of {@link Data} into a JSON representation.
	 * 
	 * @param data
	 *            instance of {@code Data}
	 * @param options
	 *            options to serialize into JSON (e.g. pretty printing)
	 * @return JSON representation of the given {@code Data}
	 */
	public static Serialization serialize(final Data data, final SerializationOption... options) {
		Check.notNull(data);
		return serialize(data, convertOptions(options));
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private JsonConverter() {
		// This class is not intended to create objects from it.
	}

}
