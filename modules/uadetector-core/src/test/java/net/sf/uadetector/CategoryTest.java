package net.sf.uadetector;

import static org.fest.assertions.Assertions.assertThat;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.ReadableDeviceCategory.Category;

import org.junit.Test;

public class CategoryTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluate_withNull() {
		Category.evaluate(null);
	}

	@Test
	public void evaluate_withTablet() {
		assertThat(Category.evaluate(Category.TABLET.getName())).isEqualTo(Category.TABLET);
	}

	@Test
	public void evaluate_withUnknownName() {
		assertThat(Category.evaluate("unknown")).isEqualTo(Category.UNKNOWN);
		assertThat(Category.evaluate("")).isEqualTo(Category.UNKNOWN);
	}

}
