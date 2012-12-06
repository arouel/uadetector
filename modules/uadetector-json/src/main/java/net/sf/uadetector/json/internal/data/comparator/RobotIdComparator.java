package net.sf.uadetector.json.internal.data.comparator;

import javax.annotation.Nonnull;

import net.sf.uadetector.internal.data.domain.Robot;
import net.sf.uadetector.internal.util.CompareNullSafe;

public final class RobotIdComparator extends CompareNullSafe<Robot> {

	private static final long serialVersionUID = 3829859006169779382L;

	public static int compareId(@Nonnull final Robot r1, @Nonnull final Robot r2) {
		final int id1 = r1.getId();
		final int id2 = r2.getId();
		return (id1 < id2 ? -1 : (id1 == id2 ? 0 : 1));
	}

	@Override
	public int compareType(@Nonnull final Robot r1, @Nonnull final Robot r2) {
		return compareId(r1, r2);
	}

}
