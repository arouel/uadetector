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

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;

public class VersionNumberTest {

	@Test
	public void compareTo_aListOfVersions() {
		final List<VersionNumber> versions = new ArrayList<VersionNumber>();
		versions.add(new VersionNumber(Arrays.asList(11, 0, 0, 1)));
		versions.add(new VersionNumber(Arrays.asList(11, 0, 0)));
		versions.add(new VersionNumber(Arrays.asList(0, VersionNumber.EMPTY_GROUP, VersionNumber.EMPTY_GROUP)));
		versions.add(new VersionNumber(0, 0, 0));
		versions.add(new VersionNumber(99, VersionNumber.EMPTY_GROUP));
		versions.add(new VersionNumber(Arrays.asList(1, 0, 1)));
		versions.add(new VersionNumber(Arrays.asList(1, 99999, null)));
		versions.add(new VersionNumber(Arrays.asList(2, 0, VersionNumber.EMPTY_GROUP)));
		versions.add(new VersionNumber(Arrays.asList(2, VersionNumber.EMPTY_GROUP, VersionNumber.EMPTY_GROUP)));
		versions.add(new VersionNumber(1));
		versions.add(new VersionNumber(99));
		versions.add(new VersionNumber(100));
		versions.add(VersionNumber.UNKNOWN);

		Collections.sort(versions);

		Assert.assertEquals("", versions.get(0).toVersionString());
		Assert.assertEquals("0", versions.get(1).toVersionString());
		Assert.assertEquals("0.0.0", versions.get(2).toVersionString());
		Assert.assertEquals("1", versions.get(3).toVersionString());
		Assert.assertEquals("1.0.1", versions.get(4).toVersionString());
		Assert.assertEquals("1.99999", versions.get(5).toVersionString());
		Assert.assertEquals("2", versions.get(6).toVersionString());
		Assert.assertEquals("2.0", versions.get(7).toVersionString());
		Assert.assertEquals("11.0.0", versions.get(8).toVersionString());
		Assert.assertEquals("11.0.0.1", versions.get(9).toVersionString());
		Assert.assertEquals("99", versions.get(10).toVersionString());
		Assert.assertEquals("99", versions.get(11).toVersionString());
		Assert.assertEquals("100", versions.get(12).toVersionString());
	}

	@Test
	public void compareTo_differentBugfix() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList(0, 0, 1));
		final VersionNumber version2 = new VersionNumber(Arrays.asList(0, 0, 2));
		Assert.assertEquals(-1, version1.compareTo(version2));
		Assert.assertEquals(1, version2.compareTo(version1));
	}

	@Test
	public void compareTo_differentMajor() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList(1, 0, 1));
		final VersionNumber version2 = new VersionNumber(Arrays.asList(0, 0, 2));
		Assert.assertEquals(1, version1.compareTo(version2));
		Assert.assertEquals(-1, version2.compareTo(version1));
	}

	@Test
	public void compareTo_differentMinor() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList(1, 102, 1));
		final VersionNumber version2 = new VersionNumber(Arrays.asList(1, 12, 2));
		Assert.assertEquals(1, version1.compareTo(version2));
		Assert.assertEquals(-1, version2.compareTo(version1));
	}

	@Test
	public void compareTo_differentRevisionNumber() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList(11, 0, 0, 1));
		final VersionNumber version2 = new VersionNumber(Arrays.asList(11, 0, 0));
		Assert.assertEquals(1, version1.compareTo(version2));
		Assert.assertEquals(-1, version2.compareTo(version1));
	}

	@Test
	public void compareTo_null() {
		final VersionNumber version = new VersionNumber(Arrays.asList(0, 0, 0));
		Assert.assertFalse(version.equals(null));
		Assert.assertEquals(-1, version.compareTo(null));
	}

	@Test
	public void compareTo_otherImplementation_getGroups_null() {
		final ReadableVersionNumber otherImpl = EasyMock.createMock(ReadableVersionNumber.class);
		EasyMock.expect(otherImpl.getGroups()).andReturn(null).anyTimes();
		EasyMock.replay(otherImpl);
		final VersionNumber version = new VersionNumber(Arrays.asList(1, 0, 0));
		Assert.assertEquals(-1, version.compareTo(otherImpl));
	}

	@Test
	public void construct_0() {
		final VersionNumber version = new VersionNumber(0);
		Assert.assertEquals(0, version.getMajor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getMinor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getBugfix());
		Assert.assertEquals("0", version.toVersionString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_bugfix_toSmall() {
		new VersionNumber(0, 0, -1);
	}

	@Test
	public void construct_groups_null_null_null_0() {
		final VersionNumber v = new VersionNumber(Arrays.asList(null, null, null, 0));
		Assert.assertFalse(VersionNumber.UNKNOWN.equals(v));
		Assert.assertEquals(VersionNumber.UNKNOWN.toVersionString(), v.toVersionString());
	}

	@Test
	public void construct_hasNullValues() {
		final VersionNumber version = new VersionNumber(Arrays.asList(4));
		Assert.assertEquals(4, version.getMajor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getMinor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getBugfix());
		Assert.assertEquals("4", version.toVersionString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_invalid_version() {
		new VersionNumber(Arrays.asList(-1, VersionNumber.EMPTY_GROUP, 1));
	}

	@Test
	public void construct_longVersion() {
		final VersionNumber version = new VersionNumber(Arrays.asList(18, 0, 1025, 162));
		Assert.assertEquals(18, version.getMajor());
		Assert.assertEquals(0, version.getMinor());
		Assert.assertEquals(1025, version.getBugfix());
		Assert.assertEquals("18.0.1025.162", version.toVersionString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_major_toSmall() {
		new VersionNumber(-1, 0, 0);
	}

	@Test
	public void construct_minimal() {
		final VersionNumber version = new VersionNumber(Arrays.asList(1, VersionNumber.EMPTY_GROUP, VersionNumber.EMPTY_GROUP));
		Assert.assertEquals(1, version.getMajor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getMinor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getBugfix());
		Assert.assertEquals("1", version.toVersionString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_minor_toSmall() {
		new VersionNumber(0, -1, 0);
	}

	@Test
	public void construct_noVersion() {
		final VersionNumber v = new VersionNumber(Arrays.asList(VersionNumber.EMPTY_GROUP, VersionNumber.EMPTY_GROUP,
				VersionNumber.EMPTY_GROUP));
		Assert.assertTrue(VersionNumber.UNKNOWN.equals(v));
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_null() {
		new VersionNumber(null);
	}

	@Test
	public void construct_sizeOfListToSmall() {
		Assert.assertEquals("1.0", new VersionNumber(Arrays.asList(1, 0)).toVersionString());
	}

	@Test
	public void construct_version_0() {
		final VersionNumber version = new VersionNumber(Arrays.asList(0, VersionNumber.EMPTY_GROUP, 0));
		Assert.assertEquals(0, version.getMajor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getMinor());
		Assert.assertEquals(0, version.getBugfix());
		Assert.assertEquals(new VersionNumber(0).toVersionString(), version.toVersionString());
		Assert.assertEquals("0", version.toVersionString());
	}

	@Test
	public void construct_version_0_0_0() {
		final VersionNumber version = new VersionNumber(Arrays.asList(0, 0, 0));
		Assert.assertEquals(0, version.getMajor());
		Assert.assertEquals(0, version.getMinor());
		Assert.assertEquals(0, version.getBugfix());
		Assert.assertEquals("0.0.0", version.toVersionString());
	}

	@Test
	public void construct_version_0_0_0_1() {
		final VersionNumber version = new VersionNumber(Arrays.asList(0, 0, 0, 001));
		Assert.assertEquals(0, version.getMajor());
		Assert.assertEquals(0, version.getMinor());
		Assert.assertEquals(0, version.getBugfix());
		Assert.assertEquals("0.0.0.1", version.toVersionString());
	}

	@Test
	public void construct_version_1_2() {
		final VersionNumber version = new VersionNumber(Arrays.asList(1, 2));
		Assert.assertEquals(1, version.getMajor());
		Assert.assertEquals(2, version.getMinor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getBugfix());
		Assert.assertEquals("1.2", version.toVersionString());
	}

	@Test
	public void construct_version_1_2_0() {
		final VersionNumber version = new VersionNumber(Arrays.asList(1, 2, 0));
		Assert.assertEquals(1, version.getMajor());
		Assert.assertEquals(2, version.getMinor());
		Assert.assertEquals(0, version.getBugfix());
		Assert.assertEquals("1.2.0", version.toVersionString());
	}

	@Test
	public void construct_version_1_2_0_null() {
		final VersionNumber version = new VersionNumber(Arrays.asList(1, 2, 0, null));
		Assert.assertEquals(1, version.getMajor());
		Assert.assertEquals(2, version.getMinor());
		Assert.assertEquals(0, version.getBugfix());
		Assert.assertEquals("1.2.0", version.toVersionString());
	}

	@Test
	public void construct_version_1_2_EMPTY_GROUP() {
		final VersionNumber version = new VersionNumber(Arrays.asList(1, 2, VersionNumber.EMPTY_GROUP));
		Assert.assertEquals(1, version.getMajor());
		Assert.assertEquals(2, version.getMinor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getBugfix());
		Assert.assertEquals("1.2", version.toVersionString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_version_1_2_minus1() {
		new VersionNumber(Arrays.asList(1, 2, -1));
	}

	@Test
	public void construct_version_1_2_null() {
		final VersionNumber version = new VersionNumber(Arrays.asList(1, 2, null));
		Assert.assertEquals(1, version.getMajor());
		Assert.assertEquals(2, version.getMinor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getBugfix());
		Assert.assertEquals("1.2", version.toVersionString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_version_1_minus1_1() {
		new VersionNumber(Arrays.asList(1, -1, 1));
	}

	@Test
	public void construct_version_1_null_1() {
		final VersionNumber version = new VersionNumber(Arrays.asList(1, null, 1));
		Assert.assertEquals(1, version.getMajor());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, version.getMinor());
		Assert.assertEquals(1, version.getBugfix());
		Assert.assertEquals("1", version.toVersionString());
	}

	@Test
	public void equals_differentBugfix() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList(0, 0, 1));
		final VersionNumber version2 = new VersionNumber(Arrays.asList(0, 0, 2));
		Assert.assertFalse(version1.equals(version2));
		Assert.assertFalse(version2.equals(version1));
		Assert.assertFalse(version1.hashCode() == version2.hashCode());
	}

	@Test
	public void equals_differentMajor() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList(0, 0, 0));
		final VersionNumber version2 = new VersionNumber(Arrays.asList(1, 0, 0));
		Assert.assertFalse(version1.equals(version2));
		Assert.assertFalse(version2.equals(version1));
		Assert.assertFalse(version1.hashCode() == version2.hashCode());
	}

	@Test
	public void equals_differentMinor() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList(0, 0, 0));
		final VersionNumber version2 = new VersionNumber(Arrays.asList(0, 12, 0));
		Assert.assertFalse(version1.equals(version2));
		Assert.assertFalse(version2.equals(version1));
		Assert.assertFalse(version1.hashCode() == version2.hashCode());
	}

	@Test
	public void equals_differentRevisionNumber() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList(0, 0, 0));
		final VersionNumber version2 = new VersionNumber(Arrays.asList(0, 0, 0, 1));
		Assert.assertFalse(version1.equals(version2));
		Assert.assertFalse(version2.equals(version1));
		Assert.assertFalse(version1.hashCode() == version2.hashCode());
	}

	@Test
	public void equals_identical() {
		final VersionNumber version1 = new VersionNumber(Arrays.asList(18, 0, 1025, 162));
		final VersionNumber version2 = new VersionNumber(Arrays.asList(18, 0, 1025, 162));
		Assert.assertTrue(version1.equals(version2));
		Assert.assertTrue(version1.hashCode() == version2.hashCode());
	}

	@Test
	public void equals_null() {
		final VersionNumber version = new VersionNumber(Arrays.asList(0, 0, 0));
		Assert.assertFalse(version.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final VersionNumber version = new VersionNumber(Arrays.asList(0, 0, 0));
		Assert.assertFalse(version.equals(""));
	}

	@Test
	public void equals_same() {
		final VersionNumber version = new VersionNumber(Arrays.asList(0, 0, 0));
		Assert.assertTrue(version.equals(version));
	}

	@Test
	public void getGroups() {
		final VersionNumber version = new VersionNumber(Arrays.asList(1, 2, 0));
		Assert.assertEquals(Arrays.asList(1, 2, 0), version.getGroups());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void getGroups_unmodifiable() {
		new VersionNumber(Arrays.asList(1, 2, 0)).getGroups().add(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void replaceNullValueWithEmptyGroup_null() {
		VersionNumber.replaceNullValueWithEmptyGroup(null);
	}

	@Test
	public void replaceNullValueWithEmptyGroup_toShort() {
		final List<Integer> groups1 = VersionNumber.replaceNullValueWithEmptyGroup(Arrays.asList(1));
		Assert.assertEquals(3, groups1.size());
		Assert.assertEquals(1, groups1.get(0).intValue());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, groups1.get(1).intValue());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, groups1.get(2).intValue());

		final List<Integer> groups2 = VersionNumber.replaceNullValueWithEmptyGroup(Arrays.asList(1, 2));
		Assert.assertEquals(3, groups2.size());
		Assert.assertEquals(1, groups2.get(0).intValue());
		Assert.assertEquals(2, groups2.get(1).intValue());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, groups2.get(2).intValue());

		final List<Integer> groups3 = VersionNumber.replaceNullValueWithEmptyGroup(Arrays.asList(1, null));
		Assert.assertEquals(3, groups3.size());
		Assert.assertEquals(1, groups3.get(0).intValue());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, groups3.get(1).intValue());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, groups3.get(2).intValue());
	}

	@Test
	public void replaceNullValueWithEmptyGroup_withNullValues() {
		final List<Integer> groups = VersionNumber.replaceNullValueWithEmptyGroup(Arrays.asList(null, null, 1));
		Assert.assertEquals(3, groups.size());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, groups.get(0).intValue());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, groups.get(1).intValue());
		Assert.assertEquals(1, groups.get(2).intValue());
	}

	@Test
	public void replaceNullValueWithEmptyGroup_withoutNullValues() {
		final List<Integer> groups = VersionNumber.replaceNullValueWithEmptyGroup(Arrays.asList(-1, VersionNumber.EMPTY_GROUP, 1));
		Assert.assertEquals(3, groups.size());
		Assert.assertEquals(-1, groups.get(0).intValue());
		Assert.assertEquals(VersionNumber.EMPTY_GROUP, groups.get(1).intValue());
		Assert.assertEquals(1, groups.get(2).intValue());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		final VersionNumber version = new VersionNumber(Arrays.asList(1, 2, 0));
		Assert.assertEquals("VersionNumber [groups=[1, 2, 0]]", version.toString());
	}

}
