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

import static org.fest.assertions.Assertions.assertThat;
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
		assertThat(new TestComparator().compare("b1", "b2")).isEqualTo(0);
	}

	@Test
	public void compare_bothNull() {
		assertThat(new TestComparator().compare(null, null)).isEqualTo(0);
	}

	@Test
	public void compare_leftNull() {
		assertThat(new TestComparator().compare(null, "")).isEqualTo(-1);
	}

	@Test
	public void compare_rightNull() {
		assertThat(new TestComparator().compare("", null)).isEqualTo(1);
	}

}
