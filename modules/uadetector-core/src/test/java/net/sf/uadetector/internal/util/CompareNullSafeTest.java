/*******************************************************************************
 * Copyright 2013 André Rouél
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
