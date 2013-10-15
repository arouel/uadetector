package net.sf.uadetector.datastore;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class AbstractUpdateOperationTest {

	@Test
	public void hasUpdate_true() {
		assertThat(AbstractUpdateOperation.hasUpdate("20130310-02", "20130310-01")).isTrue();
		assertThat(AbstractUpdateOperation.hasUpdate("20130310-01", "20130301-01")).isTrue();
		assertThat(AbstractUpdateOperation.hasUpdate("20130310-02", "20120408-10")).isTrue();
	}

	@Test
	public void hasUpdate_false() {
		assertThat(AbstractUpdateOperation.hasUpdate("20130310-02", "20130310-03")).isFalse();
		assertThat(AbstractUpdateOperation.hasUpdate("20130310-01", "20130311-01")).isFalse();
		assertThat(AbstractUpdateOperation.hasUpdate("20110310-02", "20120408-10")).isFalse();
		assertThat(AbstractUpdateOperation.hasUpdate("", "20120408-10")).isFalse();
		assertThat(AbstractUpdateOperation.hasUpdate("<h1>Error - Connect failed:<br /> <u>Too many connections</u></h1>", "20120408-10"))
				.isFalse();
		assertThat(AbstractUpdateOperation.hasUpdate("", "")).isFalse();
		assertThat(AbstractUpdateOperation.hasUpdate("", " ")).isFalse();
		assertThat(AbstractUpdateOperation.hasUpdate(" ", "")).isFalse();
	}

}
