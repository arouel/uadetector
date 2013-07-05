package net.sf.uadetector.json.internal.data;

import java.util.Arrays;
import java.util.EnumSet;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.json.internal.data.deserializer.Deserializers;
import net.sf.uadetector.json.internal.data.serializer.Serialization;
import net.sf.uadetector.json.internal.data.serializer.Serializers;

/**
 * Converts a instance of {@link Data} into JSON representation and back.
 * 
 * @author André Rouél
 */
public final class JsonConverter {

	/**
	 * Representation of no special serialization options
	 */
	private static final EnumSet<Option> WITHOUT_SPECIAL_OPTIONS = EnumSet.noneOf(Option.class);

	/**
	 * Converts an array of options to an {@code EnumSet}.
	 * 
	 * @param options
	 *            array of options to convert
	 * @return {@code EnumSet} of options
	 */
	private static EnumSet<Option> convertOptions(final Option... options) {
		final EnumSet<Option> opts;
		if (options != null) {
			opts = EnumSet.copyOf(Arrays.asList(options));
		} else {
			opts = EnumSet.noneOf(Option.class);
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
	public static Deserialization<Data> deserialize(final String json, final EnumSet<Option> options) {
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
	public static Deserialization<Data> deserialize(final String json, final Option... options) {
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
		return serialize(data, EnumSet.noneOf(Option.class));
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
	public static Serialization serialize(final Data data, final EnumSet<Option> options) {
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
	public static Serialization serialize(final Data data, final Option... options) {
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
