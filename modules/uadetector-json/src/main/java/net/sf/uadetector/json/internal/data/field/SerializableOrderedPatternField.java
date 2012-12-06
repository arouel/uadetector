package net.sf.uadetector.json.internal.data.field;

public enum SerializableOrderedPatternField {

	HASH("hash"),

	PATTERN("regex");

	private final String name;

	private SerializableOrderedPatternField(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
