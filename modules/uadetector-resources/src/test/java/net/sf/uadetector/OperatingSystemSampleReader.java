package net.sf.uadetector;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import net.sf.uadetector.parser.VersionParser;

import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class OperatingSystemSampleReader {

	/**
	 * Character set of the file
	 */
	private static final String CHARSET = "UTF-8";

	/**
	 * Default log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OperatingSystemSampleReader.class);

	private static List<OperatingSystemSample> read(final String file) {
		final InputStream stream = UserAgentStringParserIntegrationTest.class.getClassLoader().getResourceAsStream(file);

		CSVParser csvParser = null;
		try {
			csvParser = new CSVParser(new InputStreamReader(stream, CHARSET));
		} catch (final UnsupportedEncodingException e) {
			LOG.warn(e.getLocalizedMessage(), e);
		}

		final List<OperatingSystemSample> examples = new ArrayList<OperatingSystemSample>();
		if (csvParser != null) {
			String[] line = null;
			int i = 0;
			do {

				try {
					line = csvParser.getLine();
				} catch (final IOException e) {
					line = null;
					LOG.warn(e.getLocalizedMessage(), e);
				}

				if (line != null) {
					i++;
					if (line.length == 4) {
						final OperatingSystemFamily family = OperatingSystemFamily.valueOf(line[0]);
						final String name = line[1];
						final VersionNumber version = VersionParser.parseVersion(line[2]);
						final String userAgent = line[3];
						examples.add(new OperatingSystemSample(family, name, version, userAgent));
					} else {
						LOG.warn("Can not read operating system example " + i + ", there are too few fields: " + line.length);
					}
				}
			} while (line != null);
		}
		return examples;
	}

	public static List<OperatingSystemSample> readAll() {
		final List<OperatingSystemSample> examples = new ArrayList<OperatingSystemSample>();
		examples.addAll(read("samples/ANDROID.csv"));
		examples.addAll(read("samples/BADA.csv"));
		examples.addAll(read("samples/BSD.csv"));
		examples.addAll(read("samples/IOS.csv"));
		examples.addAll(read("samples/JVM.csv"));
		examples.addAll(read("samples/MAC_OS.csv"));
		examples.addAll(read("samples/OS_X.csv"));
		examples.addAll(read("samples/SYMBIAN.csv"));
		examples.addAll(read("samples/WEBOS.csv"));
		examples.addAll(read("samples/WINDOWS.csv"));
		return examples;
	}

}
