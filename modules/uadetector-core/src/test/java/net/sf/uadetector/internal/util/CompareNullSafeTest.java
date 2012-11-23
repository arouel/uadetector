package net.sf.uadetector.internal.util;

import org.junit.Assert;
import org.junit.Test;

public class CompareNullSafeTest {

	private static class TestComparator extends CompareNullSafe<String> {
		private static final long serialVersionUID = 1L;

		@Override
		public int compareType(final String o1, final String o2) {
			return 0;
		}
	}

	@Test
	public void compare_bothNotNull() {
		Assert.assertEquals(0, new TestComparator().compare("b1", "b2"));
	}

	@Test
	public void compare_bothNull() {
		Assert.assertEquals(0, new TestComparator().compare(null, null));
	}

	@Test
	public void compare_leftNull() {
		Assert.assertEquals(-1, new TestComparator().compare(null, ""));
	}

	@Test
	public void compare_rightNull() {
		Assert.assertEquals(1, new TestComparator().compare("", null));
	}

}
