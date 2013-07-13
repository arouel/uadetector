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
package net.sf.uadetector.internal.util;

import java.io.Closeable;
import java.io.IOException;

import net.sf.uadetector.exception.CannotCloseException;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

public class CloseablesTest {

	private Closeable mockCloseable;

	@Test
	public void close_withClosable_butSwallowIOException_closeSuccessful() throws IOException {
		final boolean swallowException = true;
		final boolean expectThrown = false;
		setupCloseableMock(expectThrown);
		doClose(mockCloseable, swallowException, expectThrown);
	}

	@Test
	public void close_withClosable_butSwallowIOException_throwIOException() throws IOException {
		final boolean swallowException = true;
		final boolean expectThrown = true;
		setupCloseableMock(expectThrown);
		doClose(mockCloseable, swallowException, expectThrown);
	}

	@Test
	public void close_withClosable_dontSwallowIOException_closeSuccessful() throws IOException {
		final boolean swallowException = false;
		final boolean expectThrown = false;
		setupCloseableMock(expectThrown);
		doClose(mockCloseable, swallowException, expectThrown);
	}

	@Test
	public void close_withClosable_dontSwallowIOException_throwIOException() throws IOException {
		final boolean swallowException = false;
		final boolean expectThrown = true;
		setupCloseableMock(expectThrown);
		doClose(mockCloseable, swallowException, expectThrown);
	}

	@Test
	public void close_withNull_swallow() throws IOException {
		Closeables.close(null, true);
	}

	@Test
	public void close_withNull_swallowNot() throws IOException {
		Closeables.close(null, false);
	}

	@Test
	public void closeAndConvert_withClosable_dontSwallowIOException_eatCheckedException() throws IOException {
		final boolean swallowException = false;
		final boolean expectThrown = true;
		setupCloseableMock(expectThrown);
		doCloseAndConvert(mockCloseable, swallowException, expectThrown);
	}

	@Test
	public void closeAndConvert_withClosable_swallowIOException_closeSuccessful() throws IOException {
		final boolean swallowException = true;
		final boolean expectThrown = true;
		setupCloseableMock(expectThrown);
		doCloseAndConvert(mockCloseable, swallowException, expectThrown);
	}

	@Test
	public void closeAndConvert_withNull_swallow() {
		Closeables.closeAndConvert(null, true);
	}

	@Test
	public void closeAndConvert_withNull_swallowNot() {
		Closeables.closeAndConvert(null, false);
	}

	private void doClose(final Closeable closeable, final boolean swallowException, final boolean expectThrown) {
		try {
			Closeables.close(closeable, swallowException);
			if (expectThrown && !swallowException) {
				Assert.fail("An IOException must be thrown.");
			}
		} catch (final IOException e) {
			if (!expectThrown) {
				Assert.fail("An IOException must be thrown.");
			}
		}
		EasyMock.verify(closeable);
	}

	private void doCloseAndConvert(final Closeable closeable, final boolean swallowException, final boolean expectThrown) {
		try {
			Closeables.closeAndConvert(closeable, swallowException);
			if (expectThrown && !swallowException) {
				Assert.fail("A CannotCloseException must be thrown.");
			}
		} catch (final CannotCloseException e) {
			if (!expectThrown) {
				Assert.fail("A CannotCloseException must be thrown.");
			} else {
				Assert.assertNotNull(e.getCause());
			}
		}
		EasyMock.verify(closeable);
	}

	private void setupCloseableMock(final boolean throwIOException) throws IOException {
		mockCloseable = EasyMock.createStrictMock(Closeable.class);
		mockCloseable.close();
		if (throwIOException) {
			EasyMock.expectLastCall().andThrow(new IOException("I/O test error (this is not an issue)"));
		}
		EasyMock.replay(mockCloseable);
	}

}
