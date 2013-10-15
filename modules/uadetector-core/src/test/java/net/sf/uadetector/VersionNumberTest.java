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
package net.sf.uadetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.easymock.EasyMock;
import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class VersionNumberTest {

	@Test
	public void compareTo_differentBugfix() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList("0", "0", "1"));
		final VersionNumber version2 = new VersionNumber(Arrays.asList("0", "0", "2"));
		assertThat(version1.compareTo(version2)).isEqualTo(-1);
		assertThat(version2.compareTo(version1)).isEqualTo(1);
	}

	@Test
	public void compareTo_differentExtension() {
		final VersionNumber version1 = new VersionNumber("1", "12", "1", "pre");
		final VersionNumber version2 = new VersionNumber("1", "12", "1", "");
		assertThat(version1.compareTo(version2) > 0).isTrue();
		assertThat(version2.compareTo(version1) < 0).isTrue();

		final VersionNumber version3 = new VersionNumber("1", "12", "1", "a");
		final VersionNumber version4 = new VersionNumber("1", "12", "1", "b");
		assertThat(version3.compareTo(version4) < 0).isTrue();
		assertThat(version4.compareTo(version3) > 0).isTrue();

		final VersionNumber version5 = new VersionNumber(Arrays.asList("1", "12", "1", "0"));
		final VersionNumber version6 = new VersionNumber("1", "12", "1", "b");
		assertThat(version5.compareTo(version6) > 0).isTrue();
		assertThat(version6.compareTo(version5) < 0).isTrue();

		final VersionNumber version7 = new VersionNumber(Arrays.asList("1", "12", "1", "0"), "a");
		final VersionNumber version8 = new VersionNumber("1", "12", "1", "b");
		assertThat(version7.compareTo(version8) > 0).isTrue();
		assertThat(version8.compareTo(version7) < 0).isTrue();
	}

	@Test
	public void compareTo_differentMajor() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList("1", "0", "1"));
		final VersionNumber version2 = new VersionNumber(Arrays.asList("0", "0", "2"));
		assertThat(version1.compareTo(version2)).isEqualTo(1);
		assertThat(version2.compareTo(version1)).isEqualTo(-1);
	}

	@Test
	public void compareTo_differentMinor() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList("1", "102", "1"));
		final VersionNumber version2 = new VersionNumber(Arrays.asList("1", "12", "1"));
		assertThat(version1.compareTo(version2)).isEqualTo(1);
		assertThat(version2.compareTo(version1)).isEqualTo(-1);
	}

	@Test
	public void compareTo_differentRevisionNumber() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList("11", "0", "0", "1"));
		final VersionNumber version2 = new VersionNumber(Arrays.asList("11", "0", "0"));
		assertThat(version1.compareTo(version2)).isEqualTo(1);
		assertThat(version2.compareTo(version1)).isEqualTo(-1);
	}

	@Test
	public void compareTo_listOfVersions_1() {
		final List<VersionNumber> src = new ArrayList<VersionNumber>();
		src.add(VersionNumber.UNKNOWN);
		src.add(new VersionNumber(Arrays.asList("0", VersionNumber.EMPTY_GROUP, VersionNumber.EMPTY_GROUP)));
		src.add(new VersionNumber("0", "0", "0"));
		src.add(new VersionNumber("0", "8", "1", "-stable"));
		src.add(new VersionNumber("1"));
		src.add(new VersionNumber(Arrays.asList("1", "0", "1")));
		src.add(new VersionNumber(Arrays.asList("1", "99999", null)));
		src.add(new VersionNumber(Arrays.asList("2", VersionNumber.EMPTY_GROUP, VersionNumber.EMPTY_GROUP)));
		src.add(new VersionNumber(Arrays.asList("2", "0", VersionNumber.EMPTY_GROUP)));
		src.add(new VersionNumber(Arrays.asList("2", "0", "0", "21"), "pre"));
		src.add(new VersionNumber(Arrays.asList("11", "0", "0")));
		src.add(new VersionNumber(Arrays.asList("11", "0", "0", "1")));
		src.add(new VersionNumber("99"));
		src.add(new VersionNumber("99", VersionNumber.EMPTY_GROUP));
		src.add(new VersionNumber("100"));

		final List<VersionNumber> scrambled = Arrays.asList(new VersionNumber[src.size()]);
		Collections.copy(scrambled, src);
		Collections.shuffle(scrambled);
		Collections.sort(scrambled);

		assertThat(scrambled).isEqualTo(src);
	}

	@Test
	public void compareTo_listOfVersions_2() {
		final List<VersionNumber> src = new ArrayList<VersionNumber>(18);
		src.add(new VersionNumber(""));
		src.add(new VersionNumber("0"));
		src.add(new VersionNumber("0", "0", "0"));
		src.add(new VersionNumber("0", "0", "00"));
		src.add(new VersionNumber("0", "0", "001"));
		src.add(new VersionNumber("0", "8", "1", "-stable"));
		src.add(new VersionNumber("0", "00", "0"));
		src.add(new VersionNumber("0", "001", "0"));
		src.add(new VersionNumber("1"));
		src.add(new VersionNumber("2", "11", "1"));
		src.add(new VersionNumber("2", "11", "1", "a"));
		src.add(new VersionNumber(Arrays.asList("2", "11", "1", "1")));
		src.add(new VersionNumber(Arrays.asList("2", "11", "1", "1"), "b"));
		src.add(new VersionNumber("00"));
		src.add(new VersionNumber("01"));
		src.add(new VersionNumber("000"));
		src.add(new VersionNumber("001"));
		src.add(new VersionNumber("001", "0", "0"));
		src.add(new VersionNumber("100"));

		final List<VersionNumber> scrambled = Arrays.asList(new VersionNumber[src.size()]);
		Collections.copy(scrambled, src);
		Collections.shuffle(scrambled);
		Collections.sort(scrambled);

		assertThat(scrambled).isEqualTo(src);
	}

	@Test
	public void compareTo_null() {
		final VersionNumber version = new VersionNumber(Arrays.asList("0", "0", "0"));
		assertThat(version.equals(null)).isFalse();
		assertThat(version.compareTo(null)).isEqualTo(-1);
	}

	@Test
	public void compareTo_otherGroupsIsNull() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList("1", "0", "1"));
		final ReadableVersionNumber mockVersion = EasyMock.createStrictMock(ReadableVersionNumber.class);
		EasyMock.expect(mockVersion.getGroups()).andReturn(null);
		EasyMock.replay(mockVersion);

		try {
			assertThat(version1.compareTo(mockVersion)).isEqualTo(1);
		} catch (final IllegalNullArgumentException e) {
			assertThat(e.getLocalizedMessage()).isEqualTo("Argument 'other.getGroups()' must not be null.");
		}

		EasyMock.verify(mockVersion);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void compareTo_otherImplementation_getGroups_null() {
		final ReadableVersionNumber otherImpl = EasyMock.createMock(ReadableVersionNumber.class);
		EasyMock.expect(otherImpl.getGroups()).andReturn(null).anyTimes();
		EasyMock.replay(otherImpl);
		final VersionNumber version = new VersionNumber(Arrays.asList("1", "0", "0"));
		version.compareTo(otherImpl);
	}

	@Test
	public void construct_0() {
		final VersionNumber version = new VersionNumber("0");
		assertThat(version.getMajor()).isEqualTo("0");
		assertThat(version.getMinor()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.getBugfix()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.toVersionString()).isEqualTo("0");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_bugfix_toSmall() {
		new VersionNumber("0", "0", "-1");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_extension_null() {
		new VersionNumber(Arrays.asList("1", "2"), null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_groups_null() {
		new VersionNumber((List<String>) null);
	}

	@Test
	public void construct_groups_null_null_null_0() {
		final VersionNumber v = new VersionNumber(Arrays.asList(null, null, null, "0"));
		assertThat(VersionNumber.UNKNOWN.equals(v)).isFalse();
		assertThat(v.toVersionString()).isEqualTo(VersionNumber.UNKNOWN.toVersionString());
	}

	@Test
	public void construct_hasNullValues() {
		final VersionNumber version = new VersionNumber(Arrays.asList("4"));
		assertThat(version.getMajor()).isEqualTo("4");
		assertThat(version.getMinor()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.getBugfix()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.toVersionString()).isEqualTo("4");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_invalid_version() {
		new VersionNumber(Arrays.asList("-1", VersionNumber.EMPTY_GROUP, "1"));
	}

	@Test
	public void construct_longVersion() {
		final VersionNumber version = new VersionNumber(Arrays.asList("18", "0", "1025", "162"));
		assertThat(version.getMajor()).isEqualTo("18");
		assertThat(version.getMinor()).isEqualTo("0");
		assertThat(version.getBugfix()).isEqualTo("1025");
		assertThat(version.toVersionString()).isEqualTo("18.0.1025.162");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_major_toSmall() {
		new VersionNumber("-1", "0", "0");
	}

	@Test
	public void construct_minimal() {
		final VersionNumber version = new VersionNumber(Arrays.asList("1", VersionNumber.EMPTY_GROUP, VersionNumber.EMPTY_GROUP));
		assertThat(version.getMajor()).isEqualTo("1");
		assertThat(version.getMinor()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.getBugfix()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.toVersionString()).isEqualTo("1");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_minor_toSmall() {
		new VersionNumber("0", "-1", "0");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_minusNumbers() {
		new VersionNumber(Arrays.asList("-1", "-2"));
	}

	@Test
	public void construct_noVersion() {
		final VersionNumber v = new VersionNumber(Arrays.asList(VersionNumber.EMPTY_GROUP, VersionNumber.EMPTY_GROUP,
				VersionNumber.EMPTY_GROUP));
		assertThat(VersionNumber.UNKNOWN.equals(v)).isTrue();
	}

	@Test
	public void construct_sizeOfListToSmall() {
		assertThat(new VersionNumber(Arrays.asList("1", "0")).toVersionString()).isEqualTo("1.0");
	}

	@Test
	public void construct_version_0() {
		final VersionNumber version = new VersionNumber(Arrays.asList("0", VersionNumber.EMPTY_GROUP, "0"));
		assertThat(version.getMajor()).isEqualTo("0");
		assertThat(version.getMinor()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.getBugfix()).isEqualTo("0");
		assertThat(version.toVersionString()).isEqualTo(new VersionNumber("0").toVersionString());
		assertThat(version.toVersionString()).isEqualTo("0");
	}

	@Test
	public void construct_version_0_0_0() {
		final VersionNumber version = new VersionNumber(Arrays.asList("0", "0", "0"));
		assertThat(version.getMajor()).isEqualTo("0");
		assertThat(version.getMinor()).isEqualTo("0");
		assertThat(version.getBugfix()).isEqualTo("0");
		assertThat(version.toVersionString()).isEqualTo("0.0.0");
	}

	@Test
	public void construct_version_0_0_0_001() {
		final VersionNumber version = new VersionNumber(Arrays.asList("0", "0", "0", "001"));
		assertThat(version.getMajor()).isEqualTo("0");
		assertThat(version.getMinor()).isEqualTo("0");
		assertThat(version.getBugfix()).isEqualTo("0");
		assertThat(version.toVersionString()).isEqualTo("0.0.0.001");
	}

	@Test
	public void construct_version_1_2() {
		final VersionNumber version = new VersionNumber(Arrays.asList("1", "2"));
		assertThat(version.getMajor()).isEqualTo("1");
		assertThat(version.getMinor()).isEqualTo("2");
		assertThat(version.getBugfix()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.toVersionString()).isEqualTo("1.2");
	}

	@Test
	public void construct_version_1_2_0() {
		final VersionNumber version = new VersionNumber(Arrays.asList("1", "2", "0"));
		assertThat(version.getMajor()).isEqualTo("1");
		assertThat(version.getMinor()).isEqualTo("2");
		assertThat(version.getBugfix()).isEqualTo("0");
		assertThat(version.toVersionString()).isEqualTo("1.2.0");
	}

	@Test
	public void construct_version_1_2_0_null() {
		final VersionNumber version = new VersionNumber(Arrays.asList("1", "2", "0", null));
		assertThat(version.getMajor()).isEqualTo("1");
		assertThat(version.getMinor()).isEqualTo("2");
		assertThat(version.getBugfix()).isEqualTo("0");
		assertThat(version.toVersionString()).isEqualTo("1.2.0");
	}

	@Test
	public void construct_version_1_2_EMPTY_GROUP() {
		final VersionNumber version = new VersionNumber(Arrays.asList("1", "2", VersionNumber.EMPTY_GROUP));
		assertThat(version.getMajor()).isEqualTo("1");
		assertThat(version.getMinor()).isEqualTo("2");
		assertThat(version.getBugfix()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.toVersionString()).isEqualTo("1.2");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_version_1_2_minus1() {
		new VersionNumber(Arrays.asList("1", "2", "-1"));
	}

	@Test
	public void construct_version_1_2_null() {
		final VersionNumber version = new VersionNumber(Arrays.asList("1", "2", null));
		assertThat(version.getMajor()).isEqualTo("1");
		assertThat(version.getMinor()).isEqualTo("2");
		assertThat(version.getBugfix()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.toVersionString()).isEqualTo("1.2");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_version_1_minus1_1() {
		new VersionNumber(Arrays.asList("1", "-1", "1"));
	}

	@Test
	public void construct_version_1_null_1() {
		final VersionNumber version = new VersionNumber(Arrays.asList("1", null, "1"));
		assertThat(version.getMajor()).isEqualTo("1");
		assertThat(version.getMinor()).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(version.getBugfix()).isEqualTo("1");
		assertThat(version.toVersionString()).isEqualTo("1");
	}

	@Test
	public void equals_differentBugfix() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList("0", "0", "1"));
		final VersionNumber version2 = new VersionNumber(Arrays.asList("0", "0", "2"));
		assertThat(version1.equals(version2)).isFalse();
		assertThat(version2.equals(version1)).isFalse();
		assertThat(version1.hashCode() == version2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentExtension() {
		final VersionNumber version1 = new VersionNumber("1", "0", "0", "-beta5");
		final VersionNumber version2 = new VersionNumber("1", "0", "0", "-stable");
		assertThat(version1.equals(version2)).isFalse();
		assertThat(version2.equals(version1)).isFalse();
		assertThat(version1.hashCode() == version2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentMajor() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList("0", "0", "0"));
		final VersionNumber version2 = new VersionNumber(Arrays.asList("1", "0", "0"));
		assertThat(version1.equals(version2)).isFalse();
		assertThat(version2.equals(version1)).isFalse();
		assertThat(version1.hashCode() == version2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentMinor() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList("0", "0", "0"));
		final VersionNumber version2 = new VersionNumber(Arrays.asList("0", "12", "0"));
		assertThat(version1.equals(version2)).isFalse();
		assertThat(version2.equals(version1)).isFalse();
		assertThat(version1.hashCode() == version2.hashCode()).isFalse();
	}

	@Test
	public void equals_differentRevisionNumber() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList("0", "0", "0"));
		final VersionNumber version2 = new VersionNumber(Arrays.asList("0", "0", "0", "1"));
		assertThat(version1.equals(version2)).isFalse();
		assertThat(version2.equals(version1)).isFalse();
		assertThat(version1.hashCode() == version2.hashCode()).isFalse();
	}

	@Test
	public void equals_identical() {
		final VersionNumber version1 = new VersionNumber("18", "0", "1025", "-stable");
		final VersionNumber version2 = new VersionNumber("18", "0", "1025", "-stable");
		assertThat(version1.equals(version2)).isTrue();
		assertThat(version1.hashCode() == version2.hashCode()).isTrue();
	}

	@Test
	public void equals_null() {
		final VersionNumber version = new VersionNumber(Arrays.asList("0", "0", "0"));
		assertThat(version.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass() {
		final VersionNumber version = new VersionNumber(Arrays.asList("0", "0", "0"));
		assertThat(version.equals("")).isFalse();
	}

	@Test
	public void equals_same() {
		final VersionNumber version = new VersionNumber(Arrays.asList("0", "0", "0"));
		assertThat(version.equals(version)).isTrue();
	}

	@Test
	public void getGroups() {
		final VersionNumber version = new VersionNumber(Arrays.asList("1", "2", "0"));
		assertThat(version.getGroups()).isEqualTo(Arrays.asList("1", "2", "0"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void getGroups_unmodifiable() {
		new VersionNumber(Arrays.asList("1", "2", "0")).getGroups().add("1");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void replaceNullValueWithEmptyGroup_null() {
		VersionNumber.replaceNullValueWithEmptyGroup(null);
	}

	@Test
	public void replaceNullValueWithEmptyGroup_toShort() {
		final List<String> groups1 = VersionNumber.replaceNullValueWithEmptyGroup(Arrays.asList("1"));
		assertThat(groups1).hasSize(3);
		assertThat(groups1.get(0)).isEqualTo("1");
		assertThat(groups1.get(1)).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(groups1.get(2)).isEqualTo(VersionNumber.EMPTY_GROUP);

		final List<String> groups2 = VersionNumber.replaceNullValueWithEmptyGroup(Arrays.asList("1", "2"));
		assertThat(groups2).hasSize(3);
		assertThat(groups2.get(0)).isEqualTo("1");
		assertThat(groups2.get(1)).isEqualTo("2");
		assertThat(groups2.get(2)).isEqualTo(VersionNumber.EMPTY_GROUP);

		final List<String> groups3 = VersionNumber.replaceNullValueWithEmptyGroup(Arrays.asList("1", null));
		assertThat(groups3).hasSize(3);
		assertThat(groups3.get(0)).isEqualTo("1");
		assertThat(groups3.get(1)).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(groups3.get(2)).isEqualTo(VersionNumber.EMPTY_GROUP);
	}

	@Test
	public void replaceNullValueWithEmptyGroup_withNullValues() {
		final List<String> groups = VersionNumber.replaceNullValueWithEmptyGroup(Arrays.asList(null, null, "1"));
		assertThat(groups).hasSize(3);
		assertThat(groups.get(0)).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(groups.get(1)).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(groups.get(2)).isEqualTo("1");
	}

	@Test
	public void replaceNullValueWithEmptyGroup_withoutNullValues() {
		final List<String> groups = VersionNumber.replaceNullValueWithEmptyGroup(Arrays.asList("-1", VersionNumber.EMPTY_GROUP, "1"));
		assertThat(groups).hasSize(3);
		assertThat(groups.get(0)).isEqualTo("-1");
		assertThat(groups.get(1)).isEqualTo(VersionNumber.EMPTY_GROUP);
		assertThat(groups.get(2)).isEqualTo("1");
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final VersionNumber version = new VersionNumber("1", "2", "0", "-stable");
		assertThat(version.toString()).isEqualTo("VersionNumber [groups=[1, 2, 0], extension=-stable]");
	}

}
