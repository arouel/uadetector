package net.sf.uadetector.exception;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class CanNotOpenStreamExceptionTest {

	@Test
	public void construct_cause_null() {
		new CanNotOpenStreamException((Throwable) null);
	}

	@Test
	public void construct_cause_successful() {
		final IOException tunneled = new IOException();
		final CanNotOpenStreamException e = new CanNotOpenStreamException(tunneled);
		final String expected = CanNotOpenStreamException.DEFAULT_MESSAGE;
		Assert.assertEquals(expected, e.getMessage());
		Assert.assertSame(tunneled, e.getCause());
	}

	@Test
	public void construct_default_successful() {
		new CanNotOpenStreamException();
	}

	@Test
	public void construct_url_cause_null() {
		new CanNotOpenStreamException((String) null, null);
	}

	@Test
	public void construct_url_cause_successful() {
		final String url = "http://localhost";
		final IOException tunneled = new IOException();
		final CanNotOpenStreamException e = new CanNotOpenStreamException(url, tunneled);
		final String expected = String.format(CanNotOpenStreamException.MESSAGE_WITH_URL, url);
		Assert.assertEquals(expected, e.getMessage());
		Assert.assertSame(tunneled, e.getCause());
	}

	@Test
	public void construct_url_null() {
		new CanNotOpenStreamException((String) null);
	}

	@Test
	public void construct_url_successful() {
		final String url = "http://localhost";
		final CanNotOpenStreamException e = new CanNotOpenStreamException(url);
		final String expected = String.format(CanNotOpenStreamException.MESSAGE_WITH_URL, url);
		Assert.assertEquals(expected, e.getMessage());
	}

}
