package net.sf.uadetector.internal.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.internal.data.domain.Robot;

public class DataBlueprint {

	public static Data buildEmptyTestData() {
		return buildEmptyTestData("empty-test-data");
	}

	public static Data buildEmptyTestData(final String version) {
		return new Data(new HashSet<Browser>(0), new HashSet<OperatingSystem>(0), new ArrayList<Robot>(0),
				new TreeMap<BrowserPattern, Browser>(), new TreeMap<OperatingSystemPattern, OperatingSystem>(), version);
	}

}
