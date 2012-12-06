package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.lang.reflect.Constructor;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class Sha256CodeBuilderTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void asHexString_withNullArg() {
		Sha256CodeBuilder.asHexString(null);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void getMessageDigest_noSuchAlgorithm() {
		Sha256CodeBuilder.getMessageDigest("UNKNOWN-ALGORITHM");
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<Sha256CodeBuilder> constructor = Sha256CodeBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void toHexString_withTestString() {
		Assert.assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", Sha256CodeBuilder.asHexString("test"));
	}

}
