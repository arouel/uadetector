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
package net.sf.uadetector.json.internal.data.comparator;

import java.util.regex.Pattern;

import net.sf.uadetector.internal.data.domain.BrowserPattern;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class OrderedPatternPositionComparatorTest {

	private static final BrowserPattern PATTERN_1 = new BrowserPattern(1, Pattern.compile("\\d+"), 1);

	private static final BrowserPattern PATTERN_2 = new BrowserPattern(1, Pattern.compile("\\d+"), 2);

	@Test
	public void compare_bothNull() {
		assertThat(new OrderedPatternPositionComparator<BrowserPattern>().compare(null, null)).isEqualTo(0);
	}

	@Test
	public void compare_equalsBoth() {
		assertThat(new OrderedPatternPositionComparator<BrowserPattern>().compare(PATTERN_1, PATTERN_1)).isEqualTo(0);
		assertThat(new OrderedPatternPositionComparator<BrowserPattern>().compare(PATTERN_2, PATTERN_2)).isEqualTo(0);
	}

	@Test
	public void compare_leftHigher() {
		assertThat(new OrderedPatternPositionComparator<BrowserPattern>().compare(PATTERN_1, PATTERN_2)).isEqualTo(-1);
	}

	@Test
	public void compare_leftNull() {
		assertThat(new OrderedPatternPositionComparator<BrowserPattern>().compare(null, PATTERN_1)).isEqualTo(-1);
	}

	@Test
	public void compare_rightHigher() {
		assertThat(new OrderedPatternPositionComparator<BrowserPattern>().compare(PATTERN_2, PATTERN_1)).isEqualTo(1);
	}

	@Test
	public void compare_rightNull() {
		assertThat(new OrderedPatternPositionComparator<BrowserPattern>().compare(PATTERN_1, null)).isEqualTo(1);
	}

}
