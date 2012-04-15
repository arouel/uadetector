package net.sf.uadetector.internal.data.domain;

import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

public class BrowserPatternBuilderTest {

	@Test(expected = IllegalArgumentException.class)
	public void build_withoutId() {
		new BrowserPattern.Builder().setPosition(1).setPattern(Pattern.compile("[0-9]+")).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void build_withoutOrder() {
		new BrowserPattern.Builder().setId(1).setPattern(Pattern.compile("[0-9]+")).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void build_withoutPattern() {
		new BrowserPattern.Builder().setId(1).setPosition(1).build();
	}

	@Test
	public void equality() {
		final BrowserPattern pattern1 = new BrowserPattern.Builder().setId(1).setPosition(1).setPattern(Pattern.compile("[0-9]+")).build();
		final BrowserPattern pattern2 = new BrowserPattern.Builder().setId(1).setPosition(1).setPattern(Pattern.compile("[0-9]+")).build();
		Assert.assertTrue(pattern1.equals(pattern2));
	}

	@Test(expected = NumberFormatException.class)
	public void setId_alphaString() {
		new BrowserPattern.Builder().setId("abc");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_emptyString() {
		new BrowserPattern.Builder().setId("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_null() {
		new BrowserPattern.Builder().setId(null);
	}

	@Test
	public void setId_numericString() {
		final BrowserPattern pattern1 = new BrowserPattern.Builder().setId("1").setPosition(1).setPattern(Pattern.compile("[0-9]+"))
				.build();
		final BrowserPattern pattern2 = new BrowserPattern.Builder().setId(1).setPosition(1).setPattern(Pattern.compile("[0-9]+")).build();
		Assert.assertTrue(pattern1.equals(pattern2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_toSmall() {
		new BrowserPattern.Builder().setId(-1);
	}

	@Test(expected = NumberFormatException.class)
	public void setOrder_alphaString() {
		new BrowserPattern.Builder().setPosition("abc");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setOrder_emptyString() {
		new BrowserPattern.Builder().setPosition("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setOrder_null() {
		new BrowserPattern.Builder().setPosition(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setOrder_toSmall() {
		new BrowserPattern.Builder().setPosition(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setPattern_pattern_null() {
		new BrowserPattern.Builder().setPattern((Pattern) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setPerlRegularExpression_emptyString() {
		new BrowserPattern.Builder().setPerlRegularExpression("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setPerlRegularExpression_nonPerlStyleExpression() {
		new BrowserPattern.Builder().setPerlRegularExpression("abc");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setPerlRegularExpression_string_null() {
		new BrowserPattern.Builder().setPerlRegularExpression((String) null);
	}

	@Test
	public void setPerlRegularExpression_validPerlStyleExpression() {
		new BrowserPattern.Builder().setPerlRegularExpression("/abc/");
		new BrowserPattern.Builder().setPerlRegularExpression("/abc/si");
	}

}
