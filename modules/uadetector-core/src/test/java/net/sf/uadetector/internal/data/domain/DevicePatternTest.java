package net.sf.uadetector.internal.data.domain;

import static org.fest.assertions.Assertions.assertThat;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public final class DevicePatternTest {

	private static final class Blueprint {

		private int id = 123456789;

		private Pattern pattern = Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE);

		private int position = 987654321;

		public Blueprint() {
			// default constructor
		}

		@Nonnull
		public DevicePattern build() {
			return new DevicePattern(id, pattern, position);
		}

		public Blueprint id(final int id) {
			this.id = id;
			return this;
		}

		public Blueprint pattern(final Pattern pattern) {
			this.pattern = pattern;
			return this;
		}

		public Blueprint position(final int position) {
			this.position = position;
			return this;
		}

	}

	@Test
	public void blueprintEqualsBuilder() {
		final DevicePattern blueprint = new Blueprint().build();
		final DevicePattern.Builder builder = new DevicePattern.Builder();
		builder.setId(123456789);
		builder.setPattern(Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE));
		builder.setPosition(987654321);
		final DevicePattern obj = builder.build();
		assertThat(obj).isEqualTo(blueprint);

	}

	@Test
	public void compareTo_different_ID() {
		final DevicePattern a = new Blueprint().id(987).build();
		final DevicePattern b = new Blueprint().id(123).build();
		assertThat(a.compareTo(b) > 0).isTrue();
		assertThat(b.compareTo(a) < 0).isTrue();
	}

	@Test
	public void compareTo_different_PATTERN() {
		final DevicePattern a = new Blueprint().pattern(Pattern.compile("")).build();
		final DevicePattern b = new Blueprint().pattern(Pattern.compile("^1")).build();
		assertThat(a.compareTo(b) < 0).isTrue();
		assertThat(b.compareTo(a) > 0).isTrue();
	}

	@Test
	public void compareTo_different_PATTERN_FLAGS() {
		final DevicePattern a = new Blueprint().pattern(Pattern.compile("[a-z]+")).build();
		final DevicePattern b = new Blueprint().pattern(Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE)).build();
		assertThat(a.compareTo(b) < 0).isTrue();
		assertThat(b.compareTo(a) > 0).isTrue();
	}

	@Test
	public void compareTo_different_POSITION() {
		final DevicePattern a = new Blueprint().position(127).build();
		final DevicePattern b = new Blueprint().position(255).build();
		assertThat(a.compareTo(b) < 0).isTrue();
		assertThat(b.compareTo(a) > 0).isTrue();
	}

	@Test
	public void compareTo_identical() {
		final DevicePattern a = new Blueprint().build();
		final DevicePattern b = new Blueprint().build();
		assertThat(a.compareTo(b) == 0).isTrue();
	}

	@Test
	public void compareTo_null() {
		final DevicePattern a = new Blueprint().build();
		assertThat(a.compareTo(null) < 0).isTrue();
	}

	@Test
	public void compareTo_same() {
		final DevicePattern a = new Blueprint().build();
		assertThat(a.compareTo(a) == 0).isTrue();
	}

	@Test
	public void equals_different_ID() {
		final DevicePattern a = new Blueprint().id(987).build();
		final DevicePattern b = new Blueprint().id(123).build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PATTERN() {
		final DevicePattern a = new Blueprint().pattern(Pattern.compile("")).build();
		final DevicePattern b = new Blueprint().pattern(Pattern.compile("^1")).build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_PATTERN_FLAGS() {
		final DevicePattern a = new Blueprint().pattern(Pattern.compile("[a-z]+")).build();
		final DevicePattern b = new Blueprint().pattern(Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE)).build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_POSITION() {
		final DevicePattern a = new Blueprint().position(127).build();
		final DevicePattern b = new Blueprint().position(255).build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_identical() {
		final DevicePattern a = new Blueprint().build();
		final DevicePattern b = new Blueprint().build();
		assertThat(b).isEqualTo(a);
		assertThat(a.hashCode() == b.hashCode()).isTrue();
	}

	@Test
	public void equals_null() {
		final DevicePattern a = new Blueprint().build();
		assertThat(a.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass() {
		final DevicePattern a = new Blueprint().build();
		assertThat(a.equals("")).isFalse();
	}

	@Test
	public void equals_same() {
		final DevicePattern a = new Blueprint().build();
		assertThat(a).isEqualTo(a);
		assertThat(a).isSameAs(a);
		assertThat(a.hashCode() == a.hashCode()).isTrue();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void precondition_ID() {
		new Blueprint().id(-1).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PATTERN() {
		new Blueprint().pattern(null).build();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void precondition_POSITION() {
		new Blueprint().position(-1).build();
	}

	@Test
	public void testToString() {
		// reduces only some noise in coverage report
		assertThat(new Blueprint().build().toString()).isEqualTo("DevicePattern [id=123456789, pattern=[a-z]+, position=987654321]");
	}

}
