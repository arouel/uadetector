package net.sf.uadetector;

final class OperatingSystemSample {

	private final OperatingSystemFamily family;
	private final String name;
	private final String userAgentString;
	private final VersionNumber version;

	public OperatingSystemSample(final OperatingSystemFamily family, final String name, final VersionNumber version,
			final String userAgentString) {
		this.family = family;
		this.name = name;
		this.version = version;
		this.userAgentString = userAgentString;
	}

	public OperatingSystemFamily getFamily() {
		return family;
	}

	public String getName() {
		return name;
	}

	public String getUserAgentString() {
		return userAgentString;
	}

	public VersionNumber getVersion() {
		return version;
	}

}
