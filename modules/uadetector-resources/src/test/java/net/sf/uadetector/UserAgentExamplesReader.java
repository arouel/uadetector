package net.sf.uadetector;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.uadetector.parser.OnlineUserAgentStringParserImpl;

import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class UserAgentExamplesReader {

	/**
	 * Character set of the file
	 */
	private static final String CHARSET = "UTF-8";

	/**
	 * File to read
	 */
	private static final String FILE = "examples/uas_example.csv";

	/**
	 * Default log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OnlineUserAgentStringParserImpl.class);

	public static List<UserAgentExample> read() {
		final InputStream stream = UserAgentStringParserIntegrationTest.class.getClassLoader().getResourceAsStream(FILE);

		CSVParser csvParser = null;
		try {
			csvParser = new CSVParser(new InputStreamReader(stream, CHARSET));
		} catch (final UnsupportedEncodingException e) {
			LOG.warn(e.getLocalizedMessage(), e);
		}

		final List<UserAgentExample> examples = new ArrayList<UserAgentExample>(2000);
		if (csvParser != null) {
			String[] line = null;
			int i = 0;
			do {

				try {
					line = csvParser.getLine();
				} catch (final IOException e) {
					LOG.warn(e.getLocalizedMessage(), e);
					line = null;
				}

				if (line != null) {
					i++;
					if (line.length == 3) {
						examples.add(new UserAgentExample(line[0], line[1], line[2]));
					} else {
						LOG.warn("Can not read operating system example " + i + ", there are too few fields: " + line.length);
					}
				}
			} while (line != null);
		}
		return examples;
	}

}
