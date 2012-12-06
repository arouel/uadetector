package net.sf.uadetector.json.internal.data.field;

public enum SerializableDataField {

	BROWSERPATTERNS("browser-patterns"),

	BROWSERS("browsers"),

	BROWSERTYPES("browser-types"),

	OPERATINGSYSTEMPATTERNS("operating-system-patterns"),

	OPERATINGSYSTEMS("operating-systems"),

	ROBOTS("robots"),

	VERSION("version");

	private final String name;

	private SerializableDataField(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
