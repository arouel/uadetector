package net.sf.uadetector.datastore;

import net.sf.uadetector.internal.data.Data;

final class UpdateOperationTask extends AbstractUpdateOperation {

	/**
	 * The data store for instances that implements {@link net.sf.uadetector.internal.data.Data}
	 */
	private final AbstractRefreshableDataStore store;

	public UpdateOperationTask(final AbstractRefreshableDataStore dataStore) {
		super(dataStore);
		store = dataStore;
	}

	@Override
	public void call() {
		if (isUpdateAvailable()) {
			final Data data = store.getDataReader().read(store.getDataUrl(), store.getCharset());
			store.setData(data);
		}
	}

}
