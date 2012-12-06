package net.sf.uadetector.json.internal.data.comparator;

import javax.annotation.Nonnull;

import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.util.CompareNullSafe;

public final class OperatingSystemIdComparator extends CompareNullSafe<OperatingSystem> {

	private static final long serialVersionUID = -4818486187666820059L;

	public static int compareId(@Nonnull final OperatingSystem b1, @Nonnull final OperatingSystem b2) {
		final int id1 = b1.getId();
		final int id2 = b2.getId();
		return (id1 < id2 ? -1 : (id1 == id2 ? 0 : 1));
	}

	@Override
	public int compareType(@Nonnull final OperatingSystem b1, @Nonnull final OperatingSystem b2) {
		return compareId(b1, b2);
	}

}
