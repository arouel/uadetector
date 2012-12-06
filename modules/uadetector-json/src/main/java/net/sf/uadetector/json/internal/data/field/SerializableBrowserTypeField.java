package net.sf.uadetector.json.internal.data.field;

public enum SerializableBrowserTypeField {

	HASH("hash"),

	NAME("name");

	private final String name;

	private SerializableBrowserTypeField(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
