package net.sf.uadetector.datastore;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.annotation.Nonnull;

import net.sf.uadetector.datareader.DataReader;
import net.sf.uadetector.datareader.XmlDataReader;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class UpdateOperationWithCacheFileTaskTest_call {

	private static class DataStoreWhichFallsBack extends AbstractRefreshableDataStore {

		DataStoreWhichFallsBack(@Nonnull final DataReader reader, @Nonnull final URL dataUrl, @Nonnull final URL versionUrl,
				@Nonnull final Charset charset, final DataStore fallback) {
			super(reader, dataUrl, versionUrl, charset, fallback);
		}

		@Override
		public void refresh() {
			// bypass non-blocking behavior
			getUpdateOperation().call();
		}

	}

	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void call_updateFails_fallbackIsNewer_differentVersion() throws IOException {
		final DataStore fallback = new TestXmlDataStore();

		final DataStoreWhichFallsBack store = new DataStoreWhichFallsBack(new XmlDataReader(),
				NotUpdateableXmlDataStore.DATA_URL_UNREACHABLE, NotUpdateableXmlDataStore.VERSION_URL_UNREACHABLE,
				DataStore.DEFAULT_CHARSET, fallback);

		final UpdateOperationWithCacheFileTask task = new UpdateOperationWithCacheFileTask(store, folder.newFile("cache_file.tmp"));
		Assert.assertSame(fallback.getData(), store.getData());
		Assert.assertFalse(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData()));

		// try to update
		task.call();
		Assert.assertNotSame(fallback.getData(), store.getData());
		Assert.assertTrue(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData()));

		// retry to update
		task.call();
		Assert.assertNotSame(fallback.getData(), store.getData());
		Assert.assertTrue(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData()));
	}

	@Test
	public void call_updateFails_fallbackNotNewer_sameVersion() throws IOException {
		final DataStore fallback = new SimpleXmlDataStore(TestXmlDataStore.DATA_URL, TestXmlDataStore.VERSION_URL);

		final DataStoreWhichFallsBack store = new DataStoreWhichFallsBack(new XmlDataReader(),
				NotUpdateableXmlDataStore.DATA_URL_UNREACHABLE, NotUpdateableXmlDataStore.VERSION_URL_UNREACHABLE,
				DataStore.DEFAULT_CHARSET, fallback);

		final UpdateOperationWithCacheFileTask task = new UpdateOperationWithCacheFileTask(store, folder.newFile("cache_file.tmp"));
		Assert.assertSame(fallback.getData(), store.getData());
		Assert.assertFalse(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData()));

		// try to update
		task.call();
		Assert.assertSame(fallback.getData(), store.getData());
		Assert.assertFalse(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData()));

		// retry to update
		task.call();
		Assert.assertSame(fallback.getData(), store.getData());
		Assert.assertFalse(UpdateOperationWithCacheFileTask.isNewerData(fallback.getData(), store.getData()));
	}

}
