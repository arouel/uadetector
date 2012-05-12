package net.sf.uadetector;

final class OperatingSystemExample {

	private final String name;
	private final String userAgentString;

	public OperatingSystemExample(final String name, final String userAgentString) {
		this.name = name;
		this.userAgentString = userAgentString;
	}

	public String getName() {
		return name;
	}

	public String getUserAgentString() {
		return userAgentString;
	}

}
