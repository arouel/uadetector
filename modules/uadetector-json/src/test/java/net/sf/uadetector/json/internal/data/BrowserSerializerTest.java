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
package net.sf.uadetector.json.internal.data;

import java.lang.reflect.Type;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.internal.data.domain.Browser;
import net.sf.uadetector.internal.data.domain.BrowserPattern;
import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.internal.data.domain.OperatingSystem;
import net.sf.uadetector.internal.data.domain.OperatingSystemPattern;
import net.sf.uadetector.json.internal.data.serializer.BrowserSerializer;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;

public class BrowserSerializerTest {

	public static Browser create() {
		final int id = 1;
		final String icon = "icon";
		final String infoUrl = "info url";
		final String url = "url";
		final UserAgentFamily family = UserAgentFamily.FIREFOX;
		final String producerUrl = "producer url";
		final String producer = "producer";
		final BrowserType type = new BrowserType(1, "Browser");
		final SortedSet<OperatingSystemPattern> osPatternSet = new TreeSet<OperatingSystemPattern>();
		osPatternSet.add(new OperatingSystemPattern(1, Pattern.compile("12345"), 123));
		osPatternSet.add(new OperatingSystemPattern(2, Pattern.compile("82378"), 987));
		final OperatingSystem operatingSystem = new OperatingSystem(1, "n1", "f1", "iu1", osPatternSet, "p1", "pu1", "u1", "i1");
		final SortedSet<BrowserPattern> patterns = new TreeSet<BrowserPattern>();
		return new Browser(id, family, family.getName(), patterns, type, operatingSystem, icon, infoUrl, producer, producerUrl, url);
	}

	@Test
	public void test() {
		final Browser browser = create();
		final Type typeOfSrc = null;
		final JsonSerializationContext context = new JsonSerializationContext() {

			@Override
			public JsonElement serialize(final Object src) {
				return new Gson().toJsonTree(src);
			}

			@Override
			public JsonElement serialize(final Object src, final Type typeOfSrc) {
				return new Gson().toJsonTree(src, typeOfSrc);
			}

		};
		final JsonElement e1 = new BrowserSerializer().serialize(browser, typeOfSrc, context);
		final JsonElement e2 = new BrowserSerializer().serialize(browser, typeOfSrc, context);
		Assert.assertEquals(e1.toString(), e2.toString());
	}

}
