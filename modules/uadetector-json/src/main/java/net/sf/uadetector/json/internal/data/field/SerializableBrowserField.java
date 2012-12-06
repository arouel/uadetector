package net.sf.uadetector.json.internal.data.field;

public enum SerializableBrowserField {

	FAMILY("family"),

	HASH("hash"),

	ICON("icon"),

	INFO_URL("info-url"),

	OPERATING_SYSTEM_HASH("operating-system-hash"),

	PATTERNS("browser-pattern-hashs"),

	PRODUCER("producer"),

	PRODUCER_URL("producer-url"),

	BROWSER_TYPE_HASH("browser-type-hash"),

	URL("url");

	private final String name;

	private SerializableBrowserField(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
