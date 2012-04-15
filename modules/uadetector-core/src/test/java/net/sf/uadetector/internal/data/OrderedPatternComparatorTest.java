package net.sf.uadetector.internal.data;

import java.util.regex.Pattern;

import net.sf.uadetector.internal.data.domain.BrowserPattern;

import org.junit.Assert;
import org.junit.Test;

public class OrderedPatternComparatorTest {

	@Test
	public void compare_differentFlags() {
		final BrowserPattern pattern1 = new BrowserPattern(1, Pattern.compile("[0-9]+", Pattern.MULTILINE), 1);
		final BrowserPattern pattern2 = new BrowserPattern(1, Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE), 1);
		Assert.assertEquals(0, new OrderedPatternComparator<BrowserPattern>().compare(pattern1, pattern2));
	}

	@Test
	public void compare_identical() {
		final BrowserPattern pattern1 = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		final BrowserPattern pattern2 = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		Assert.assertEquals(0, new OrderedPatternComparator<BrowserPattern>().compare(pattern1, pattern2));
	}

	@Test
	public void compare_null_1() {
		new OrderedPatternComparator<BrowserPattern>().compare(null, null);
	}

	@Test
	public void compare_null_2() {
		final BrowserPattern pattern = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		new OrderedPatternComparator<BrowserPattern>().compare(null, pattern);
	}

	@Test
	public void compare_null_3() {
		final BrowserPattern pattern = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		new OrderedPatternComparator<BrowserPattern>().compare(pattern, null);
	}

	@Test
	public void compare_same() {
		final BrowserPattern pattern = new BrowserPattern(1, Pattern.compile("[0-9]+"), 1);
		Assert.assertEquals(0, new OrderedPatternComparator<BrowserPattern>().compare(pattern, pattern));
	}

}
