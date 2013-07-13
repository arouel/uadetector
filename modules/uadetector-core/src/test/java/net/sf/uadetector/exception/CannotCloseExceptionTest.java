/*******************************************************************************
 * Copyright 2013 André Rouél
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
		final String expected = String.format(CannotCloseException.MESSAGE_WITH_INFO, info);
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
		final String expected = String.format(CannotCloseException.MESSAGE_WITH_INFO, info);
		Assert.assertEquals(expected, e.getMessage());
	}

}
