package net.sf.uadetector.exception;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class CannotCloseExceptionTest {

	@Test
	public void construct_cause_null() {
		new CannotCloseException((Throwable) null);
	}

	@Test
	public void construct_cause_successful() {
		final IOException tunneled = new IOException();
		final CannotCloseException e = new CannotCloseException(tunneled);
		final String expected = CannotCloseException.DEFAULT_MESSAGE;
		Assert.assertEquals(expected, e.getMessage());
		Assert.assertSame(tunneled, e.getCause());
	}

	@Test
	public void construct_default_successful() {
		new CannotCloseException();
	}

	@Test
	public void construct_url_cause_null() {
		new CannotCloseException((String) null, null);
	}

	@Test
	public void construct_url_cause_successful() {
		final String info = "a message which describes the cause";
		final IOException tunneled = new IOException();
		final CannotCloseException e = new CannotCloseException(info, tunneled);
		final String expected = String.format(CannotCloseException.MESSAGE_WITH_URL, info);
		Assert.assertEquals(expected, e.getMessage());
		Assert.assertSame(tunneled, e.getCause());
	}

	@Test
	public void construct_url_null() {
		new CannotCloseException((String) null);
	}

	@Test
	public void construct_url_successful() {
		final String info = "a message which describes the cause";
		final CannotCloseException e = new CannotCloseException(info);
		final String expected = String.format(CannotCloseException.MESSAGE_WITH_URL, info);
		Assert.assertEquals(expected, e.getMessage());
	}

}
