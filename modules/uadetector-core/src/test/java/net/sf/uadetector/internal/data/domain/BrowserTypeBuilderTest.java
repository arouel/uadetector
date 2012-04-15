package net.sf.uadetector.internal.data.domain;

import junit.framework.Assert;

import net.sf.uadetector.internal.data.domain.BrowserType.Builder;

import org.junit.Test;

public class BrowserTypeBuilderTest {

	@Test(expected = IllegalArgumentException.class)
	public void setId_toSmall() {
		Builder b = new BrowserType.Builder();
		b.setId(-1);
	}

	@Test
	public void setId_byString_valid() {
		Builder b = new BrowserType.Builder();
		b.setId("1");
	}

	@Test(expected = NumberFormatException.class)
	public void setId_byString_invalid() {
		Builder b = new BrowserType.Builder();
		b.setId("abc");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_byString_toSmall() {
		Builder b = new BrowserType.Builder();
		b.setId("-1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setId_null() {
		Builder b = new BrowserType.Builder();
		b.setId(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setName_null() {
		Builder b = new BrowserType.Builder();
		b.setName(null);
	}

	@Test
	public void testSettersAndGetters() {
		Builder b = new BrowserType.Builder();
		b.setId(1);
		b.setName("Test");
		BrowserType i = b.build();
		Assert.assertEquals(1, i.getId());
		Assert.assertEquals("Test", i.getName());
	}

}
