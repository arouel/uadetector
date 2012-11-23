package net.sf.uadetector.internal.util;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Compares two references to each other and {@code null} is assumed to be less than a non-{@code null} value. This
 * class provides the first check for null safe comparison.
 * 
 * @author André Rouél
 */
public abstract class CompareNullSafe<T> implements Comparator<T>, Serializable {

	private static final long serialVersionUID = -704997500621650775L;

	/**
	 * Compares two objects null safe to each other.
	 * 
	 * @param o1
	 *            the first reference
	 * @param o2
	 *            the second reference
	 * @return a negative value if o1 < o2, zero if o1 = o2 and a positive value if o1 > o2
	 */
	@Override
	public int compare(final T o1, final T o2) {
		int result = 0;
		if (o1 == null) {
			if (o2 != null) {
				result = -1;
			}
		} else if (o2 == null) {
			result = 1;
		} else {
			result = compareType(o1, o2);
		}
		return result;
	}

	public abstract int compareType(final T o1, final T o2);

}
