package net.sf.uadetector.json.internal.data.field;

public enum SerializablePatternField {

	FLAGS("flags"),

	PATTERN("pattern");

	private final String name;

	private SerializablePatternField(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
