package net.sf.uadetector.json.internal.data.comparator;

import javax.annotation.Nonnull;

import net.sf.uadetector.internal.data.domain.OrderedPattern;
import net.sf.uadetector.internal.util.CompareNullSafe;

public final class OrderedPatternPositionComparator<T extends OrderedPattern<T>> extends CompareNullSafe<T> {

	private static final long serialVersionUID = -1264724926084448870L;

	public static <T extends OrderedPattern<T>> int comparePosition(@Nonnull final T b1, @Nonnull final T b2) {
		final int pos1 = b1.getPosition();
		final int pos2 = b2.getPosition();
		return (pos1 < pos2 ? -1 : (pos1 == pos2 ? 0 : 1));
	}

	@Override
	public int compareType(@Nonnull final T o1, @Nonnull final T o2) {
		return comparePosition(o1, o2);
	}

}
