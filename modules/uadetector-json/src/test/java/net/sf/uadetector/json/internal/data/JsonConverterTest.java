package net.sf.uadetector.json.internal.data;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.internal.data.Data;
import net.sf.uadetector.internal.util.UrlUtil;
import net.sf.uadetector.json.internal.data.JsonConverter.SerializationOption;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonConverterTest {

	private static final Logger LOG = LoggerFactory.getLogger(JsonConverterTest.class);

	@Test
	public void deserialize_corruptHash_doNotIgnore() throws IOException {
		final URL dataUrl = JsonConverterTest.class.getClassLoader().getResource("samples/uas_corrupt_hash.json");
		final Deserialization<Data> deserialization = JsonConverter.deserialize(UrlUtil.read(dataUrl, DataStore.DEFAULT_CHARSET),
				SerializationOption.HASH_VALIDATING);
		for (String warn : deserialization.getWarnings()) {
			LOG.debug(warn);
		}
		Assert.assertEquals(12, deserialization.getWarnings().size());
		Assert.assertNotSame(Data.EMPTY, deserialization.getData());
	}

	@Test
	public void deserialize_corruptHash_ignoreHash() throws IOException {
		final URL dataUrl = JsonConverterTest.class.getClassLoader().getResource("samples/uas_corrupt_hash.json");
		final Deserialization<Data> deserialization = JsonConverter.deserialize(UrlUtil.read(dataUrl, DataStore.DEFAULT_CHARSET));
		for (String warn : deserialization.getWarnings()) {
			LOG.debug(warn);
		}
		Assert.assertEquals(5, deserialization.getWarnings().size());
		Assert.assertNotSame(Data.EMPTY, deserialization.getData());
	}

	@Test
	public void deserialize_dirtyData() throws IOException {
		final URL dataUrl = JsonConverterTest.class.getClassLoader().getResource("samples/uas_dirty.json");
		final Deserialization<Data> deserialization = JsonConverter.deserialize(UrlUtil.read(dataUrl, DataStore.DEFAULT_CHARSET),
				SerializationOption.HASH_VALIDATING);
		for (final String warning : deserialization.getWarnings()) {
			LOG.debug(warning);
		}
		Assert.assertEquals(0, deserialization.getWarnings().size());
		Assert.assertNotSame(Data.EMPTY, deserialization.getData());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void deserialize_withNullData() {
		JsonConverter.deserialize(null);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<JsonConverter> constructor = JsonConverter.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void serialize_withNullData() {
		JsonConverter.serialize(null);
	}

	@Test
	public void serialize_withNullOptions() {
		final SerializationOption[] options = null;
		JsonConverter.serialize(Data.EMPTY, options);
	}

}
