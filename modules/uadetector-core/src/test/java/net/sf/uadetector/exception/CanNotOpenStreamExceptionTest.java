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
