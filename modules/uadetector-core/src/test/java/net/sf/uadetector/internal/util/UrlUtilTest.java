/*******************************************************************************
 * Copyright 2012 André Rouél
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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.uadetector.datastore.DataStore;
import net.sf.uadetector.exception.CanNotOpenStreamException;

import org.junit.Assert;
import org.junit.Test;

public class UrlUtilTest {

	/**
	 * The character set to read the contents of an URL
	 */
	private static final Charset CHARSET = DataStore.DEFAULT_CHARSET;

	@Test(expected = IllegalStateOfArgumentException.class)
	public void build_invalid_url() throws Exception {
		UrlUtil.build("i'm invalid");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void build_null() throws Exception {
		UrlUtil.build(null);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<UrlUtil> constructor = UrlUtil.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void open_null() throws Exception {
		UrlUtil.open(null);
	}

	@Test(expected = CanNotOpenStreamException.class)
	public void open_unreachable_url() throws MalformedURLException {
		final String unreachable = "http://unreachable.local";
		UrlUtil.open(new URL(unreachable));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void read_charset_null() throws IOException {
		UrlUtil.read(new URL("http://localhost"), null);
	}

	@Test(expected = CanNotOpenStreamException.class)
	public void read_unreachable_url() throws IOException {
		final String unreachable = "http://unreachable.local";
		UrlUtil.read(new URL(unreachable), CHARSET);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void read_url_null() throws IOException {
		UrlUtil.read(null, CHARSET);
	}

	@Test
	public void toUrl_empty() {
		final URL url = UrlUtil.toUrl(new File("doesn't exist"));
		Assert.assertNotNull(url);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void toUrl_null() {
		UrlUtil.toUrl(null);
	}

}
