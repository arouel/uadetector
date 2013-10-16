package net.sf.uadetector.internal.data.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.ReadableDeviceCategory.Category;

import org.junit.Test;

public final class DeviceTest {

	private static final class Blueprint {

		private String icon = "logo.png";

		private int id = 987;

		private String infoUrl = "info-url";

		private String name = "device-name";

		private Category category = Category.UNKNOWN;

		private SortedSet<DevicePattern> patterns = new TreeSet<DevicePattern>();

		public Blueprint() {
			patterns.add(new DevicePattern(1, Pattern.compile("Enterprise"), 1701));
		}

		@Nonnull
		public Device build() {
			return new Device(icon, id, infoUrl, name, patterns, category);
		}

		public Blueprint category(final Category category) {
			this.category = category;
			return this;
		}

		public Blueprint icon(final String icon) {
			this.icon = icon;
			return this;
		}

		public Blueprint id(final int id) {
			this.id = id;
			return this;
		}

		public Blueprint infoUrl(final String infoUrl) {
			this.infoUrl = infoUrl;
			return this;
		}

		public Blueprint name(final String name) {
			this.name = name;
			return this;
		}

		public Blueprint patterns(final SortedSet<DevicePattern> patterns) {
			this.patterns = patterns;
			return this;
		}

	}

	@Test
	public void blueprintEqualsBuilder() {
		final Device.Builder builder = new Device.Builder();
		builder.setIcon("logo.png");
		builder.setId(987);
		builder.setInfoUrl("info-url");
		builder.setName("device-name");
		final SortedSet<DevicePattern> patterns = new TreeSet<DevicePattern>();
		patterns.add(new DevicePattern(1, Pattern.compile("Enterprise"), 1701));
		builder.setPatterns(patterns);
		final Device obj = builder.build();

		final Device blueprint = new Blueprint().build();
		assertEquals(blueprint, obj);
	}

	@Test
	public void equals_different_CATEGORY() {
		final Device a = new Blueprint().category(Category.SMARTPHONE).build();
		final Device b = new Blueprint().category(Category.TABLET).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_ICON() {
		final Device a = new Blueprint().icon("icn").build();
		final Device b = new Blueprint().icon("ic").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_ID() {
		final Device a = new Blueprint().id(749).build();
		final Device b = new Blueprint().id(372).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_INFOURL() {
		final Device a = new Blueprint().infoUrl("info1").build();
		final Device b = new Blueprint().infoUrl("info2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_NAME() {
		final Device a = new Blueprint().name("test1").build();
		final Device b = new Blueprint().name("test2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_PATTERNS() {
		final SortedSet<DevicePattern> patterns1 = new TreeSet<DevicePattern>();
		patterns1.add(new DevicePattern(1, Pattern.compile("Enterprise"), 1701));
		final Device a = new Blueprint().patterns(patterns1).build();
		final SortedSet<DevicePattern> patterns2 = new TreeSet<DevicePattern>();
		patterns2.add(new DevicePattern(1, Pattern.compile("Enterprise"), 1701));
		patterns2.add(new DevicePattern(2, Pattern.compile("Voyager"), 74656));
		final Device b = new Blueprint().patterns(patterns2).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final Device a = new Blueprint().build();
		final Device b = new Blueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Device a = new Blueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Device a = new Blueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Device a = new Blueprint().build();
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_ICON() {
		new Blueprint().icon(null).build();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void precondition_ID() {
		new Blueprint().id(-1).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_INFOURL() {
		new Blueprint().infoUrl(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_NAME() {
		new Blueprint().name(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PATTERNS() {
		new Blueprint().patterns(null).build();
	}

	@Test
	public void testGetters() {
		final Device.Builder b = new Device.Builder();
		b.setName("test");
		b.setIcon("icon");
		b.setId(2);
		b.setInfoUrl("info");
		final SortedSet<DevicePattern> patterns = new TreeSet<DevicePattern>();
		patterns.add(new DevicePattern(21, Pattern.compile(""), 42));
		b.setPatterns(patterns);

		assertEquals("test", b.getName());
		assertEquals("icon", b.getIcon());
		assertEquals(2, b.getId());
		assertEquals("info", b.getInfoUrl());
		assertEquals(patterns, b.getPatterns());

		final Device device = b.build();
		assertEquals("test", device.getName());
		assertEquals("icon", device.getIcon());
		assertEquals(2, device.getId());
		assertEquals("info", device.getInfoUrl());
		assertEquals(patterns, device.getPatterns());
		assertEquals(Category.UNKNOWN, device.getCategory());

		final Device.Builder c = new Device.Builder(device);
		assertEquals("test", c.getName());
		assertEquals("icon", c.getIcon());
		assertEquals(2, c.getId());
		assertEquals("info", c.getInfoUrl());
		assertEquals(patterns, c.getPatterns());
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		assertEquals(
				"Device [icon=logo.png, id=987, infoUrl=info-url, name=device-name, patterns=[DevicePattern [id=1, pattern=Enterprise, position=1701]]]",
				new Blueprint().build().toString());
	}

}
