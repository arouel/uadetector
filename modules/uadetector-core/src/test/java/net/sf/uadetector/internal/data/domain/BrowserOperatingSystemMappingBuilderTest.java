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
package net.sf.uadetector.internal.data.domain;

import static org.fest.assertions.Assertions.assertThat;
import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.internal.data.domain.BrowserOperatingSystemMapping.Builder;

import org.junit.Test;

public class BrowserOperatingSystemMappingBuilderTest {

	@Test(expected = NumberFormatException.class)
	public void setBrowserId_abcString() {
		new BrowserOperatingSystemMapping.Builder().setBrowserId("abc");
	}

	@Test(expected = NumberFormatException.class)
	public void setBrowserId_emptyString() {
		new BrowserOperatingSystemMapping.Builder().setBrowserId("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setBrowserId_null() {
		new BrowserOperatingSystemMapping.Builder().setBrowserId(null);
	}

	@Test
	public void setBrowserId_successful() {
		final Builder b = new BrowserOperatingSystemMapping.Builder().setBrowserId("12345");
		assertThat(b.getBrowserId()).isEqualTo(12345);
		b.setBrowserId(98765);
		assertThat(b.getBrowserId()).isEqualTo(98765);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void setBrowserId_toSmall() {
		new BrowserOperatingSystemMapping.Builder().setBrowserId(-1);
	}

	@Test(expected = NumberFormatException.class)
	public void setOperatingSystemId_abcString() {
		new BrowserOperatingSystemMapping.Builder().setOperatingSystemId("abc");
	}

	@Test(expected = NumberFormatException.class)
	public void setOperatingSystemId_emptyString() {
		new BrowserOperatingSystemMapping.Builder().setOperatingSystemId("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setOperatingSystemId_null() {
		new BrowserOperatingSystemMapping.Builder().setOperatingSystemId(null);
	}

	@Test
	public void setOperatingSystemId_successful() {
		final Builder b = new BrowserOperatingSystemMapping.Builder().setOperatingSystemId("12345");
		assertThat(b.getOperatingSystemId()).isEqualTo(12345);
		b.setOperatingSystemId(98765);
		assertThat(b.getOperatingSystemId()).isEqualTo(98765);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void setOperatingSystemId_toSmall() {
		new BrowserOperatingSystemMapping.Builder().setOperatingSystemId(-1);
	}

	@Test
	public void testGetters() {
		final BrowserOperatingSystemMapping.Builder builder = new BrowserOperatingSystemMapping.Builder();
		assertThat(builder.setBrowserId(1)).isSameAs(builder);
		assertThat(builder.setBrowserId("2")).isSameAs(builder);
		assertThat(builder.setOperatingSystemId(3)).isSameAs(builder);
		assertThat(builder.setOperatingSystemId("4")).isSameAs(builder);

		assertThat(builder.getBrowserId()).isEqualTo(2);
		assertThat(builder.getOperatingSystemId()).isEqualTo(4);

		final BrowserOperatingSystemMapping m = builder.build();
		assertThat(m.getBrowserId()).isEqualTo(2);
		assertThat(m.getOperatingSystemId()).isEqualTo(4);
	}

}
