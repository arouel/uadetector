package net.sf.uadetector.internal.util;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.internal.util.RegularExpressionConverter.Flag;

import org.junit.Assert;
import org.junit.Test;

public class FlagTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void convertToBitmask_null() {
		Flag.convertToBitmask(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void convertToModifiers_null() {
		Flag.convertToModifiers(null);
	}

	@Test
	public void evaluateByCharacter_i() {
		Assert.assertEquals(Flag.CASE_INSENSITIVE, Flag.evaluateByCharacter('i'));
	}

	@Test
	public void evaluateByCharacter_s() {
		Assert.assertEquals(Flag.DOTALL, Flag.evaluateByCharacter('s'));
	}

	@Test
	public void evaluateByCharacter_unknown() {
		Assert.assertEquals(null, Flag.evaluateByCharacter('1'));
	}

	@Test
	public void evaluateByNumber_maxInteger() {
		Assert.assertEquals(null, Flag.evaluateByNumber(Integer.MAX_VALUE));
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void evaluateByNumber_negativeNumber() {
		Flag.evaluateByNumber(-1);
	}

	@Test
	public void evaluateByNumber_UNIX_LINES() {
		Assert.assertEquals(Flag.UNIX_LINES, Flag.evaluateByNumber(1));
	}

	@Test
	public void parse_bitmaskThree() {
		Assert.assertEquals(EnumSet.of(Flag.CASE_INSENSITIVE, Flag.UNIX_LINES), Flag.parse(3));
	}

	@Test
	public void parse_imsx() {
		final Set<Flag> flags = Flag.parse("imsx");
		final EnumSet<Flag> expected = EnumSet.of(Flag.CASE_INSENSITIVE, Flag.DOTALL, Flag.MULTILINE, Flag.COMMENTS);
		Assert.assertTrue(flags.containsAll(expected));
		Assert.assertTrue(expected.containsAll(flags));
	}

	@Test
	public void parse_is() {
		final Set<Flag> flags = Flag.parse("is");
		final EnumSet<Flag> expected = EnumSet.of(Flag.CASE_INSENSITIVE, Flag.DOTALL);
		Assert.assertTrue(flags.containsAll(expected));
		Assert.assertTrue(expected.containsAll(flags));
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void parse_negativeNumber() {
		Flag.parse(-1);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void parse_null() {
		Flag.parse((String) null);
	}

	@Test
	public void parse_si() {
		final Set<Flag> flags = Flag.parse("si");
		final EnumSet<Flag> expected = EnumSet.of(Flag.CASE_INSENSITIVE, Flag.DOTALL);
		Assert.assertTrue(flags.containsAll(expected));
		Assert.assertTrue(expected.containsAll(flags));
	}

	@Test
	public void parse_Si() {
		final Set<Flag> flags = Flag.parse("Si");
		final EnumSet<Flag> expected = EnumSet.of(Flag.CASE_INSENSITIVE);
		Assert.assertTrue(flags.containsAll(expected));
		Assert.assertTrue(expected.containsAll(flags));
	}

	@Test
	public void parse_unknownBigBitmask() {
		Assert.assertEquals(EnumSet.allOf(Flag.class), Flag.parse(Integer.MAX_VALUE));
	}

	@Test
	public void parse_zero() {
		Assert.assertEquals(new HashSet<Flag>(0), Flag.parse(0));
	}

	@Test
	public void parseAndConvert() {
		final String modifiers = "imsx";
		final Set<Flag> convertedOnce = Flag.parse("imsx");
		final EnumSet<Flag> expected = EnumSet.of(Flag.CASE_INSENSITIVE, Flag.DOTALL, Flag.MULTILINE, Flag.COMMENTS);
		final Set<Flag> convertedTwice = Flag.parse(Flag.convertToModifiers(convertedOnce));

		// testing completeness of all flags
		Assert.assertTrue(convertedTwice.containsAll(expected));
		Assert.assertTrue(expected.containsAll(convertedTwice));

		// testing character ordering
		Assert.assertEquals(modifiers, Flag.convertToModifiers(convertedOnce));
		Assert.assertEquals(modifiers, Flag.convertToModifiers(convertedTwice));
	}

}
