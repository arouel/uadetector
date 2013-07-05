package net.sf.uadetector.json.internal.data.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMessageCollector<T> implements MessageCollector {

	private final List<String> warnings = new ArrayList<String>();

	@Override
	public void addWarning(final String warning) {
		warnings.add(warning);
	}

	@Override
	public List<String> getWarnings() {
		return Collections.unmodifiableList(warnings);
	}

}
