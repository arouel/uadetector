package net.sf.uadetector.json.internal.data.serializer;

/**
 * Schema information about JSON representations of a {@link net.sf.uadetector.internal.data.Data} instances.
 * 
 * @author André Rouél
 */
enum DataJsonFormat {

	VERSION_1_0("1.0", "format");

	private final String key;

	private final String version;

	private DataJsonFormat(final String version, final String key) {
		this.version = version;
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getVersion() {
		return version;
	}

}
