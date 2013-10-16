package net.sf.uadetector.internal.data.domain;

import static org.fest.assertions.Assertions.assertThat;

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
			return new Device(name, id, category, icon, infoUrl, patterns);
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
		assertThat(obj).isEqualTo(blueprint);
	}

	@Test
	public void equals_different_CATEGORY() {
		final Device a = new Blueprint().category(Category.SMARTPHONE).build();
		final Device b = new Blueprint().category(Category.TABLET).build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_ICON() {
		final Device a = new Blueprint().icon("icn").build();
		final Device b = new Blueprint().icon("ic").build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_ID() {
		final Device a = new Blueprint().id(749).build();
		final Device b = new Blueprint().id(372).build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_INFOURL() {
		final Device a = new Blueprint().infoUrl("info1").build();
		final Device b = new Blueprint().infoUrl("info2").build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_NAME() {
		final Device a = new Blueprint().name("test1").build();
		final Device b = new Blueprint().name("test2").build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
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
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_identical() {
		final Device a = new Blueprint().build();
		final Device b = new Blueprint().build();
		assertThat(b).isEqualTo(a);
		assertThat(a.hashCode() == b.hashCode()).isTrue();
	}

	@Test
	public void equals_null() {
		final Device a = new Blueprint().build();
		assertThat(a.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass() {
		final Device a = new Blueprint().build();
		assertThat(a.equals("")).isFalse();
	}

	@Test
	public void equals_same() {
		final Device a = new Blueprint().build();
		assertThat(a).isEqualTo(a);
		assertThat(a).isSameAs(a);
		assertThat(a.hashCode() == a.hashCode()).isTrue();
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

		assertThat(b.getName()).isEqualTo("test");
		assertThat(b.getIcon()).isEqualTo("icon");
		assertThat(b.getId()).isEqualTo(2);
		assertThat(b.getInfoUrl()).isEqualTo("info");
		assertThat(b.getPatterns()).isEqualTo(patterns);

		final Device device = b.build();
		assertThat(device.getName()).isEqualTo("test");
		assertThat(device.getIcon()).isEqualTo("icon");
		assertThat(device.getId()).isEqualTo(2);
		assertThat(device.getInfoUrl()).isEqualTo("info");
		assertThat(device.getPatterns()).isEqualTo(patterns);
		assertThat(device.getCategory()).isEqualTo(Category.UNKNOWN);

		final Device.Builder c = new Device.Builder(device);
		assertThat(c.getName()).isEqualTo("test");
		assertThat(c.getIcon()).isEqualTo("icon");
		assertThat(c.getId()).isEqualTo(2);
		assertThat(c.getInfoUrl()).isEqualTo("info");
		assertThat(c.getPatterns()).isEqualTo(patterns);
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		assertThat(new Blueprint().build().toString())
				.isEqualTo(
						"Device [icon=logo.png, id=987, infoUrl=info-url, name=device-name, patterns=[DevicePattern [id=1, pattern=Enterprise, position=1701]]]");
	}

}
