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
package net.sf.uadetector;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.easymock.EasyMock;
import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class UserAgentBuilderTest {

	@Test
	public void build_custom() {
		final OperatingSystem os = new OperatingSystem(OperatingSystemFamily.UNKNOWN, "family", "icon", "name", "producer", "producer url",
				"url", new VersionNumber("1"));

		final UserAgent.Builder b = new UserAgent.Builder();
		assertThat(b.setFamily(UserAgentFamily.CHROMIUM)).isSameAs(b);
		assertThat(b.setIcon("i1")).isSameAs(b);
		assertThat(b.setName("n1")).isSameAs(b);
		assertThat(b.setOperatingSystem(os)).isSameAs(b);
		assertThat(b.setProducer("p1")).isSameAs(b);
		assertThat(b.setProducerUrl("pu1")).isSameAs(b);
		assertThat(b.setType(UserAgentType.BROWSER)).isSameAs(b);
		assertThat(b.setTypeName("t1")).isSameAs(b);
		assertThat(b.setUrl("u1")).isSameAs(b);
		assertThat(b.setVersionNumber(VersionParser.parseVersion("1.0.0"))).isSameAs(b);

		assertThat(b.getFamily()).isEqualTo(UserAgentFamily.CHROMIUM);
		assertThat(b.getIcon()).isEqualTo("i1");
		assertThat(b.getName()).isEqualTo("n1");
		assertThat(b.getOperatingSystem()).isEqualTo(os);
		assertThat(b.getProducer()).isEqualTo("p1");
		assertThat(b.getProducerUrl()).isEqualTo("pu1");
		assertThat(b.getTypeName()).isEqualTo("t1");
		assertThat(b.getUrl()).isEqualTo("u1");
		assertThat(b.getVersionNumber().toVersionString()).isEqualTo("1.0.0");

		final UserAgent ua = b.build();
		assertThat(ua).isNotNull();
		assertThat(ua.getFamily()).isEqualTo(UserAgentFamily.CHROMIUM);
		assertThat(ua.getIcon()).isEqualTo("i1");
		assertThat(ua.getName()).isEqualTo("n1");
		assertThat(ua.getOperatingSystem()).isEqualTo(os);
		assertThat(ua.getProducer()).isEqualTo("p1");
		assertThat(ua.getProducerUrl()).isEqualTo("pu1");
		assertThat(ua.getTypeName()).isEqualTo("t1");
		assertThat(ua.getUrl()).isEqualTo("u1");
		assertThat(ua.getVersionNumber().toVersionString()).isEqualTo("1.0.0");
	}

	@Test
	public void build_empty() {
		final UserAgent.Builder b = new UserAgent.Builder();
		assertThat(b.getFamily()).isEqualTo(UserAgent.EMPTY.getFamily());
		assertThat(b.getIcon()).isEqualTo(UserAgent.EMPTY.getIcon());
		assertThat(b.getName()).isEqualTo(UserAgent.EMPTY.getName());
		assertThat(b.getOperatingSystem()).isEqualTo(UserAgent.EMPTY.getOperatingSystem());
		assertThat(b.getProducer()).isEqualTo(UserAgent.EMPTY.getProducer());
		assertThat(b.getProducerUrl()).isEqualTo(UserAgent.EMPTY.getProducerUrl());
		assertThat(b.getType()).isEqualTo(UserAgent.EMPTY.getType());
		assertThat(b.getTypeName()).isEqualTo(UserAgent.EMPTY.getTypeName());
		assertThat(b.getUrl()).isEqualTo(UserAgent.EMPTY.getUrl());
		assertThat(b.getVersionNumber()).isEqualTo(VersionNumber.UNKNOWN);
		assertThat(b.build()).isEqualTo(UserAgent.EMPTY);

		final UserAgent ua = b.build();
		assertThat(ua.getFamily()).isEqualTo(UserAgent.EMPTY.getFamily());
		assertThat(ua.getIcon()).isEqualTo(UserAgent.EMPTY.getIcon());
		assertThat(ua.getName()).isEqualTo(UserAgent.EMPTY.getName());
		assertThat(ua.getOperatingSystem()).isEqualTo(UserAgent.EMPTY.getOperatingSystem());
		assertThat(ua.getProducer()).isEqualTo(UserAgent.EMPTY.getProducer());
		assertThat(ua.getProducerUrl()).isEqualTo(UserAgent.EMPTY.getProducerUrl());
		assertThat(ua.getType()).isEqualTo(UserAgent.EMPTY.getType());
		assertThat(ua.getTypeName()).isEqualTo(UserAgent.EMPTY.getTypeName());
		assertThat(ua.getUrl()).isEqualTo(UserAgent.EMPTY.getUrl());
		assertThat(ua.getVersionNumber()).isEqualTo(VersionNumber.UNKNOWN);
		assertThat(b.build()).isEqualTo(UserAgent.EMPTY);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_null() {
		new UserAgent.Builder(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setFamily_null() {
		new UserAgent.Builder().setFamily(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setIcon_null() {
		new UserAgent.Builder().setIcon(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setName_null() {
		new UserAgent.Builder().setName(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setOperatingSystem_null() {
		new UserAgent.Builder().setOperatingSystem((OperatingSystem) null);
	}

	@Test
	public void setOperatingSystem_ReadableOperatingSystem() {
		final ReadableOperatingSystem os = EasyMock.createMock(ReadableOperatingSystem.class);
		EasyMock.expect(os.getFamily()).andReturn(OperatingSystemFamily.LINUX).anyTimes();
		EasyMock.expect(os.getFamilyName()).andReturn("f1").anyTimes();
		EasyMock.expect(os.getIcon()).andReturn("i1").anyTimes();
		EasyMock.expect(os.getName()).andReturn("n1").anyTimes();
		EasyMock.expect(os.getProducer()).andReturn("p1").anyTimes();
		EasyMock.expect(os.getProducerUrl()).andReturn("pu1").anyTimes();
		EasyMock.expect(os.getUrl()).andReturn("u1").anyTimes();
		EasyMock.expect(os.getVersionNumber()).andReturn(new VersionNumber("1", "0")).anyTimes();
		EasyMock.replay(os);
		new UserAgent.Builder().setOperatingSystem(os);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setOperatingSystem_ReadableOperatingSystem_invalidFamily() {
		final ReadableOperatingSystem os = EasyMock.createMock(ReadableOperatingSystem.class);
		EasyMock.expect(os.getFamily()).andReturn(OperatingSystemFamily.LINUX).anyTimes();
		EasyMock.expect(os.getFamilyName()).andReturn(null).anyTimes();
		EasyMock.expect(os.getIcon()).andReturn("i1").anyTimes();
		EasyMock.expect(os.getName()).andReturn("n1").anyTimes();
		EasyMock.expect(os.getProducer()).andReturn("p1").anyTimes();
		EasyMock.expect(os.getProducerUrl()).andReturn("pu1").anyTimes();
		EasyMock.expect(os.getUrl()).andReturn("u1").anyTimes();
		EasyMock.expect(os.getVersionNumber()).andReturn(new VersionNumber("1", "0")).anyTimes();
		EasyMock.replay(os);
		new UserAgent.Builder().setOperatingSystem(os);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setOperatingSystem_ReadableOperatingSystem_null() {
		new UserAgent.Builder().setOperatingSystem((ReadableOperatingSystem) null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setProducer_null() {
		new UserAgent.Builder().setProducer(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setProducerUrl_null() {
		new UserAgent.Builder().setProducerUrl(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setType_null() {
		new UserAgent.Builder().setType(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setTypeName_null() {
		new UserAgent.Builder().setTypeName(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setUrl_null() {
		new UserAgent.Builder().setUrl(null);
	}

	@Test
	public void setUserAgentString() {
		final String userAgentString = "a user agent string";
		final UserAgent.Builder b = new UserAgent.Builder().setUserAgentString(userAgentString);
		assertThat(b.getUserAgentString()).isEqualTo(userAgentString);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setUserAgentString_null() {
		new UserAgent.Builder().setUserAgentString(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void setVersionNumber_null() {
		new UserAgent.Builder().setVersionNumber(null);
	}

}
