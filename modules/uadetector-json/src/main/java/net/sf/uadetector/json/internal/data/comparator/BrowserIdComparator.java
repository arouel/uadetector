package net.sf.uadetector.json.internal.data.comparator;

import javax.annotation.Nonnull;

import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.util.CompareNullSafe;

public final class BrowserIdComparator extends CompareNullSafe<Browser> {

	private static final long serialVersionUID = -3444490064916486271L;

	public static int compareId(@Nonnull final Browser b1, @Nonnull final Browser b2) {
		final int id1 = b1.getId();
		final int id2 = b2.getId();
		return (id1 < id2 ? -1 : (id1 == id2 ? 0 : 1));
	}

	@Override
	public int compareType(@Nonnull final Browser b1, @Nonnull final Browser b2) {
		return compareId(b1, b2);
	}

}
