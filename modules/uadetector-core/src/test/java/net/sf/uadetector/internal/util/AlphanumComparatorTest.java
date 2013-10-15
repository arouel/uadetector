/*******************************************************************************
 * Copyright 2012 André Rouél
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class AlphanumComparatorTest {

	@Test
	public void compare_0_0() {
		final String charA = "0";
		final String charB = "0";
		assertThat(new AlphanumComparator().compare(charA, charB)).isEqualTo(0);
	}

	@Test
	public void compare_0_0000() {
		final String charA = "0";
		final String charB = "0000";
		assertThat(new AlphanumComparator().compare(charA, charB)).isEqualTo(-3);
		assertThat(new AlphanumComparator().compare(charB, charA)).isEqualTo(3);
	}

	@Test
	public void compare_00001_00001() {
		final String charA = "00001";
		final String charB = "00001";
		assertThat(new AlphanumComparator().compare(charA, charB)).isEqualTo(0);
	}

	@Test
	public void compare_02_2() {
		final String charA = "02";
		final String charB = "2";
		assertThat(new AlphanumComparator().compare(charA, charB)).isEqualTo(1);
		assertThat(new AlphanumComparator().compare(charB, charA)).isEqualTo(-1);
	}

	@Test
	public void compare_02_20() {
		final String charA = "02";
		final String charB = "20";
		assertThat(new AlphanumComparator().compare(charA, charB)).isEqualTo(-2);
		assertThat(new AlphanumComparator().compare(charB, charA)).isEqualTo(2);
	}

	@Test
	public void compare_0A_0000A() {
		final String charA = "0A";
		final String charB = "0000A";
		assertThat(new AlphanumComparator().compare(charA, charB)).isEqualTo(-3);
		assertThat(new AlphanumComparator().compare(charB, charA)).isEqualTo(3);
	}

	@Test
	public void compare_differentFlags() {
		final String version1 = "0";
		final String version2 = "00";
		assertThat(new AlphanumComparator().compare(version1, version2)).isEqualTo(-1);
		assertThat(new AlphanumComparator().compare(version2, version1)).isEqualTo(1);
	}

	@Test
	public void compare_identical() {
		final String version1 = "000-12";
		final String version2 = "000-12";
		assertThat(new AlphanumComparator().compare(version1, version2)).isEqualTo(0);
	}

	@Test
	public void compare_list() {
		final List<String> src = new ArrayList<String>(18);
		src.add("0");
		src.add("1");
		src.add("00");
		src.add("01");
		src.add("02");
		src.add("10");
		src.add("11");
		src.add("12");
		src.add("000");
		src.add("001");
		src.add("002");
		src.add("100");
		src.add("101");
		src.add("102");
		src.add("103");
		src.add("104");
		src.add("0002");
		src.add("1002");
		src.add("1056");

		final List<String> scrambled = Arrays.asList(src.toArray(new String[] {}));
		Collections.copy(scrambled, src);
		Collections.shuffle(scrambled);
		Collections.sort(scrambled, new AlphanumComparator());

		assertThat(scrambled).isEqualTo(src);
	}

	@Test
	public void compare_null_1() {
		new AlphanumComparator().compare(null, null);
	}

	@Test
	public void compare_null_2() {
		final String version = "1";
		new AlphanumComparator().compare(null, version);
	}

	@Test
	public void compare_null_3() {
		final String version = "2";
		new AlphanumComparator().compare(version, null);
	}

	@Test
	public void compare_same() {
		final String version = "4";
		assertThat(new AlphanumComparator().compare(version, version)).isEqualTo(0);
	}

	@Test
	public void compareDigits_001_xyz() {
		final String charA = "001";
		final String charB = "xyz";
		assertThat(AlphanumComparator.compareDigits(charA, charB)).isEqualTo(-72);
		assertThat(AlphanumComparator.compareDigits(charB, charA)).isEqualTo(72);
	}

	@Test
	public void compareDigits_1_00() {
		final String charA = "1";
		final String charB = "00";
		assertThat(AlphanumComparator.compareDigits(charA, charB)).isEqualTo(-1);
		assertThat(AlphanumComparator.compareDigits(charB, charA)).isEqualTo(1);
	}

	@Test
	public void compareDigits_abc_xyz() {
		final String charA = "abc";
		final String charB = "xyz";
		assertThat(AlphanumComparator.compareDigits(charA, charB)).isEqualTo(-23);
		assertThat(AlphanumComparator.compareDigits(charB, charA)).isEqualTo(23);
	}

}
