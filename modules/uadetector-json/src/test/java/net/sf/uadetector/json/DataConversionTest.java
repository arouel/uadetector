package net.sf.uadetector.json;

import java.io.IOException;
import java.net.URISyntaxException;

import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.json.internal.data.JsonConverter;
import net.sf.uadetector.json.internal.data.serializer.Serialization;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataConversionTest {

	private static final Logger LOG = LoggerFactory.getLogger(DataConversionTest.class);

	public static String humanReadableByteCount(final long bytes, final boolean si) {
		final int unit = si ? 1000 : 1024;
		if (bytes < unit) {
			return bytes + " B";
		}
		final int exp = (int) (Math.log(bytes) / Math.log(unit));
		final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	@Test
	public void testConversionViceVersa() throws IOException, URISyntaxException {
		final SerDeOption options = SerDeOption.PRETTY_PRINTING;
		final DataStore store = TestDataStoreFactory.produce();
		Serialization serialization = JsonConverter.serialize(store.getData(), options);
		Assert.assertEquals(0, serialization.getWarnings().size());
		final String json = serialization.getJson();
		final Data parsedData = JsonConverter.deserialize(json).getData();

		// can not be equals, because the IDs are different
		Assert.assertFalse(store.getData().equals(parsedData));

		// must be equals, because the IDs are identical after conversion
		Serialization serialization2 = JsonConverter.serialize(store.getData(), options);
		Assert.assertEquals(0, serialization2.getWarnings().size());
		final String expectedJson = serialization2.getJson();
		final Data expectedData = JsonConverter.deserialize(expectedJson).getData();
		Assert.assertEquals(expectedJson, json);
		Assert.assertEquals(expectedData, parsedData);

		// print some JSON
		LOG.info(json);

		// print some stats
		LOG.info(store.getData().toStats());
		LOG.info(parsedData.toStats());

		// print size comparison
		final String uasDataAsXml = UrlUtil.read(TestDataStoreFactory.DATA_URL, DataStore.DEFAULT_CHARSET);
		LOG.info("size XML: " + humanReadableByteCount(uasDataAsXml.getBytes().length, false));
		LOG.info("size JSON: " + humanReadableByteCount(JsonConverter.serialize(store.getData()).getJson().getBytes().length, false));
	}

}
