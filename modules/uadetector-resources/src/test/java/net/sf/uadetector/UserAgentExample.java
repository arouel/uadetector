package net.sf.uadetector;

final class UserAgentExample {

	private final String name;
	private final String type;
	private final String userAgentString;

	public UserAgentExample(final String type, final String name, final String userAgentString) {
		this.type = type;
		this.name = name;
		this.userAgentString = userAgentString;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getUserAgentString() {
		return userAgentString;
	}

}
