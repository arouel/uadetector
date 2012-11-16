package net.sf.uadetector.internal.util;

final class OperatingSystemDetector {

	private static final String OPERATING_SYSTEM_KEY = "os.name";

	public static String getName() {
		return System.getProperty(OPERATING_SYSTEM_KEY);
	}

	public static boolean isLinux() {
		return getName().toLowerCase().contains("linux");
	}

	private OperatingSystemDetector() {
	}

}
