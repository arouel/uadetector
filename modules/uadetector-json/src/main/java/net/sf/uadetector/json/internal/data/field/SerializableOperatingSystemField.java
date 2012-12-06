package net.sf.uadetector.json.internal.data.field;

public enum SerializableOperatingSystemField {

	FAMILY("family"),

	HASH("hash"),

	ICON("icon"),

	INFO_URL("info-url"),

	NAME("name"),

	PATTERN_HASHS("operating-system-pattern-hashs"),

	PRODUCER("producer"),

	PRODUCER_URL("producer-url"),

	URL("url");

	private final String name;

	private SerializableOperatingSystemField(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
