package net.sf.uadetector.json.internal.data.deserializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.json.SerDeOption;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Immutable
public final class Deserializers extends AbstractDeserializer<Data> {

	public static Deserialization<Data> deserialize(final String json, final EnumSet<SerDeOption> options) {
		return deserialize(json, options, Data.class);
	}

	public static <T> Deserialization<T> deserialize(final String json, final EnumSet<SerDeOption> options, final Class<T> classOfT) {
		final Deserializers deserializers = new Deserializers(options);
		return new Deserialization<T>(deserializers.deserialize(json, classOfT), deserializers.getWarnings());
	}

	private final BrowserDeserializer browserDeserializer;

	private final BrowserPatternDeserializer browserPatternDeserializer;

	private final BrowserTypeDeserializer browserTypeDeserializer;

	private final DataDeserializer dataDeserializer;

	private final Gson gson;

	private final OperatingSystemDeserializer operatingSystemDeserializer;

	private final OperatingSystemPatternDeserializer operatingSystemPatternDeserializer;

	private final PatternDeserializer patternDeserializer;

	private final RobotDeserializer robotDeserializer;

	private Deserializers(final EnumSet<SerDeOption> options) {
		super(Check.notNull(options));
		dataDeserializer = new DataDeserializer(options);
		browserPatternDeserializer = new BrowserPatternDeserializer(options);
		browserTypeDeserializer = new BrowserTypeDeserializer(options);
		operatingSystemPatternDeserializer = new OperatingSystemPatternDeserializer(options);
		operatingSystemDeserializer = new OperatingSystemDeserializer(options, operatingSystemPatternDeserializer);
		browserDeserializer = new BrowserDeserializer(options, browserPatternDeserializer, browserTypeDeserializer,
				operatingSystemDeserializer);
		robotDeserializer = new RobotDeserializer(options);
		patternDeserializer = new PatternDeserializer(options);

		// setup deserializers
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Data.class, dataDeserializer);
		gsonBuilder.registerTypeAdapter(Browser.class, browserDeserializer);
		gsonBuilder.registerTypeAdapter(BrowserPattern.class, browserPatternDeserializer);
		gsonBuilder.registerTypeAdapter(BrowserType.class, browserTypeDeserializer);
		gsonBuilder.registerTypeAdapter(OperatingSystem.class, operatingSystemDeserializer);
		gsonBuilder.registerTypeAdapter(OperatingSystemPattern.class, operatingSystemPatternDeserializer);
		gsonBuilder.registerTypeAdapter(Robot.class, robotDeserializer);
		gsonBuilder.registerTypeAdapter(Pattern.class, patternDeserializer);
		gson = gsonBuilder.create();
	}

	/**
	 * Deserializes a JSON representation into a new instance of {@link Data}.
	 * 
	 * @param json
	 *            JSON representation of {@code Data}
	 * @return instance of {@code Data}
	 */
	public <T> T deserialize(final String json, final Class<T> classOfT) {
		Check.notNull(json);
		Check.notNull(classOfT);
		return gson.fromJson(json, classOfT);
	}

	public BrowserDeserializer getBrowserDeserializer() {
		return browserDeserializer;
	}

	public BrowserPatternDeserializer getBrowserPatternDeserializer() {
		return browserPatternDeserializer;
	}

	public BrowserTypeDeserializer getBrowserTypeDeserializer() {
		return browserTypeDeserializer;
	}

	public DataDeserializer getDataDeserializer() {
		return dataDeserializer;
	}

	public OperatingSystemDeserializer getOperatingSystemDeserializer() {
		return operatingSystemDeserializer;
	}

	public OperatingSystemPatternDeserializer getOperatingSystemPatternDeserializer() {
		return operatingSystemPatternDeserializer;
	}

	public PatternDeserializer getPatternDeserializer() {
		return patternDeserializer;
	}

	public RobotDeserializer getRobotDeserializer() {
		return robotDeserializer;
	}

	@Override
	public List<String> getWarnings() {
		final List<String> warnings = new ArrayList<String>();
		// over all warnings
		warnings.addAll(patternDeserializer.getWarnings());

		// order should depend on deserialization steps (ordering of level one fields in JSON)
		warnings.addAll(operatingSystemPatternDeserializer.getWarnings());
		warnings.addAll(operatingSystemDeserializer.getWarnings());
		warnings.addAll(browserTypeDeserializer.getWarnings());
		warnings.addAll(browserPatternDeserializer.getWarnings());
		warnings.addAll(browserDeserializer.getWarnings());
		warnings.addAll(robotDeserializer.getWarnings());

		// warnings during data building
		warnings.addAll(dataDeserializer.getWarnings());

		return Collections.unmodifiableList(warnings);
	}

}
