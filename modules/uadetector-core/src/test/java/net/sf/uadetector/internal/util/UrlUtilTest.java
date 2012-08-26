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

import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.uadetector.exception.CanNotOpenStreamException;

import org.junit.Test;

public class UrlUtilTest {

	@Test(expected = IllegalArgumentException.class)
	public void build_invalid_url() throws Exception {
		UrlUtil.build("i'm invalid");
	}

	@Test(expected = IllegalArgumentException.class)
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

	@Test(expected = IllegalArgumentException.class)
	public void open_null() throws Exception {
		UrlUtil.open(null);
	}

	@Test(expected = CanNotOpenStreamException.class)
	public void open_unreachable_url() throws MalformedURLException {
		final String unreachable = "http://unreachable,local";
		UrlUtil.open(new URL(unreachable));
	}

}
