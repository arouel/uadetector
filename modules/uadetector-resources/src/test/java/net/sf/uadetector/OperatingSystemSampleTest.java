package net.sf.uadetector;

import java.util.List;

import net.sf.uadetector.service.UADetectorServiceFactory;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatingSystemSampleTest {

	/**
	 * Default log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OperatingSystemSampleTest.class);

	/**
	 * Parser to detect informations about an user agent
	 */
	private static final UserAgentStringParser PARSER = UADetectorServiceFactory.getResourceModuleParser();

	@Test
	public void test() {
		final List<OperatingSystemSample> samples = OperatingSystemSampleReader.readAll();
		int i = 0;
		for (final OperatingSystemSample sample : samples) {
			final ReadableUserAgent agent = PARSER.parse(sample.getUserAgentString());

			// comparing the name
			if (!sample.getName().equals(agent.getOperatingSystem().getName())) {
				LOG.info("Naming different: " + sample.getName() + " != " + agent.getOperatingSystem().getName() + " ("
						+ sample.getUserAgentString() + ")");
				Assert.assertEquals(sample.getName(), agent.getOperatingSystem().getName());
			}

			// check for unknown family
			if (OperatingSystemFamily.UNKNOWN == agent.getOperatingSystem().getFamily()) {
				LOG.info("Unknown operating system family found. Please update the enum 'OperatingSystemFamily'.");
			}

			// comparing version number
			if (!sample.getVersion().equals(agent.getOperatingSystem().getVersionNumber())) {
				LOG.info("Versioning different: " + sample.getVersion() + " != " + agent.getOperatingSystem().getVersionNumber() + " ("
						+ sample.getUserAgentString() + ")");
				Assert.assertEquals(sample.getVersion(), agent.getOperatingSystem().getVersionNumber());
			}

			// abort if unknown family
			Assert.assertFalse(OperatingSystemFamily.UNKNOWN == agent.getOperatingSystem().getFamily());

			// save read OS for printing out
			i++;
		}
		LOG.info(i + " operating system samples validated");
	}

}
