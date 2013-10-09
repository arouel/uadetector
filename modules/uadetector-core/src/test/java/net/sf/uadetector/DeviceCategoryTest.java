package net.sf.uadetector;

import static org.fest.assertions.Assertions.assertThat;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class DeviceCategoryTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluate_withNull() {
		DeviceCategory.evaluate(null);
	}

	@Test
	public void evaluate_withTablet() {
		assertThat(DeviceCategory.evaluate(DeviceCategory.TABLET.getName())).isEqualTo(DeviceCategory.TABLET);
	}

	@Test
	public void evaluate_withUnknownName() {
		assertThat(DeviceCategory.evaluate("unknown")).isEqualTo(DeviceCategory.UNKNOWN);
		assertThat(DeviceCategory.evaluate("")).isEqualTo(DeviceCategory.UNKNOWN);
	}

}
