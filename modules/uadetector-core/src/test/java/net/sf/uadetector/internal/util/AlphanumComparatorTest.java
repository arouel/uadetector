package net.sf.uadetector.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class AlphanumComparatorTest {

	@Test
	public void compare_0_0() {
		final String charA = "0";
		final String charB = "0";
		Assert.assertEquals(0, new AlphanumComparator().compare(charA, charB));
	}

	@Test
	public void compare_0_0000() {
		final String charA = "0";
		final String charB = "0000";
		Assert.assertEquals(-3, new AlphanumComparator().compare(charA, charB));
		Assert.assertEquals(3, new AlphanumComparator().compare(charB, charA));
	}

	@Test
	public void compare_00001_00001() {
		final String charA = "00001";
		final String charB = "00001";
		Assert.assertEquals(0, new AlphanumComparator().compare(charA, charB));
	}

	@Test
	public void compare_02_2() {
		final String charA = "02";
		final String charB = "2";
		Assert.assertEquals(1, new AlphanumComparator().compare(charA, charB));
		Assert.assertEquals(-1, new AlphanumComparator().compare(charB, charA));
	}

	@Test
	public void compare_02_20() {
		final String charA = "02";
		final String charB = "20";
		Assert.assertEquals(-2, new AlphanumComparator().compare(charA, charB));
		Assert.assertEquals(2, new AlphanumComparator().compare(charB, charA));
	}

	@Test
	public void compare_0A_0000A() {
		final String charA = "0A";
		final String charB = "0000A";
		Assert.assertEquals(-3, new AlphanumComparator().compare(charA, charB));
		Assert.assertEquals(3, new AlphanumComparator().compare(charB, charA));
	}

	@Test
	public void compare_differentFlags() {
		final String version1 = "0";
		final String version2 = "00";
		Assert.assertEquals(-1, new AlphanumComparator().compare(version1, version2));
		Assert.assertEquals(1, new AlphanumComparator().compare(version2, version1));
	}

	@Test
	public void compare_identical() {
		final String version1 = "000-12";
		final String version2 = "000-12";
		Assert.assertEquals(0, new AlphanumComparator().compare(version1, version2));
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

		Assert.assertEquals(src, scrambled);
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
		Assert.assertEquals(0, new AlphanumComparator().compare(version, version));
	}

	@Test
	public void compareDigits_001_xyz() {
		final String charA = "001";
		final String charB = "xyz";
		Assert.assertEquals(-72, AlphanumComparator.compareDigits(charA, charB));
		Assert.assertEquals(72, AlphanumComparator.compareDigits(charB, charA));
	}

	@Test
	public void compareDigits_1_00() {
		final String charA = "1";
		final String charB = "00";
		Assert.assertEquals(-1, AlphanumComparator.compareDigits(charA, charB));
		Assert.assertEquals(1, AlphanumComparator.compareDigits(charB, charA));
	}

	@Test
	public void compareDigits_abc_xyz() {
		final String charA = "abc";
		final String charB = "xyz";
		Assert.assertEquals(-23, AlphanumComparator.compareDigits(charA, charB));
		Assert.assertEquals(23, AlphanumComparator.compareDigits(charB, charA));
	}

}
