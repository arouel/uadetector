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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentFamily;
import net.sf.uadetector.UserAgentType;

public final class Robot {

	public static final class Builder {

		@Nonnull
		private UserAgentFamily family = UserAgentFamily.UNKNOWN;
		@Nonnull
		private String icon = "";
		@Nonnull
		private int id = Integer.MIN_VALUE;
		@Nonnull
		private String infoUrl = "";
		@Nonnull
		private String name = "";
		@Nonnull
		private String producer = "";
		@Nonnull
		private String producerUrl = "";
		@Nonnull
		private String url = "";
		@Nonnull
		private String userAgentString = "";

		public Robot build() {
			return new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
		}

		public UserAgentFamily getFamily() {
			return family;
		}

		public String getIcon() {
			return icon;
		}

		public int getId() {
			return id;
		}

		public String getInfoUrl() {
			return infoUrl;
		}

		public String getName() {
			return name;
		}

		public String getProducer() {
			return producer;
		}

		public String getProducerUrl() {
			return producerUrl;
		}

		public String getUrl() {
			return url;
		}

		public String getUserAgentString() {
			return userAgentString;
		}

		public Builder setFamily(@Nonnull final UserAgentFamily family) {
			Check.notNull(family, "family");

			this.family = family;
			return this;
		}

		public Builder setIcon(@Nonnull final String icon) {
			Check.notNull(icon, "icon");

			this.icon = icon;
			return this;
		}

		public Builder setId(final int id) {
Check.notNegative(id, "id");

			this.id = id;
			return this;
		}

		public Builder setId(@Nonnull final String id) {
			Check.notEmpty(id, "id");

			this.setId(Integer.parseInt(id.trim()));
			return this;
		}

		public Builder setInfoUrl(@Nonnull final String infoUrl) {
			Check.notNull(infoUrl, "infoUrl");

			this.infoUrl = infoUrl;
			return this;
		}

		public Builder setName(@Nonnull final String name) {
			Check.notNull(name, "name");

			this.name = name;
			return this;
		}

		public Builder setProducer(@Nonnull final String producer) {
			Check.notNull(producer, "producer");

			this.producer = producer;
			return this;
		}

		public Builder setProducerUrl(@Nonnull final String producerUrl) {
			Check.notNull(producerUrl, "producerUrl");

			this.producerUrl = producerUrl;
			return this;
		}

		public Builder setUrl(@Nonnull final String url) {
			Check.notNull(url, "url");

			this.url = url;
			return this;
		}

		public Builder setUserAgentString(@Nonnull final String userAgentString) {
			Check.notNull(userAgentString, "userAgentString");

			this.userAgentString = userAgentString;
			return this;
		}

	}

	/**
	 * Identification of the User-Agent type
	 */
	public static final String TYPENAME = "Robot";

	@Nonnull
	private final UserAgentFamily family;
	@Nonnull
	private final String icon;
	@Nonnegative
	private final int id;
	@Nonnull
	private final String infoUrl;
	@Nonnull
	private final String name;
	@Nonnull
	private final String producer;
	@Nonnull
	private final String producerUrl;
	@Nonnull
	private final String url;
	@Nonnull
	private final String userAgentString;

	public Robot(@Nonnull final UserAgentFamily family, @Nonnull final String icon, @Nonnegative final int id,
			@Nonnull final String infoUrl, @Nonnull final String name, @Nonnull final String producer, @Nonnull final String producerUrl,
			@Nonnull final String url, @Nonnull final String userAgentString) {
		Check.notNull(family, "family");
		Check.notNull(icon, "icon");
		Check.notNegative(id, "id");
		Check.notNull(infoUrl, "infoUrl");
		Check.notEmpty(name, "name");
		Check.notNull(producer, "producer");
		Check.notNull(producerUrl, "producerUrl");
		Check.notNull(url, "url");
		Check.notNull(userAgentString, "userAgentString");

		this.family = family;
		this.id = id;
		this.icon = icon;
		this.infoUrl = infoUrl;
		this.name = name;
		this.producer = producer;
		this.producerUrl = producerUrl;
		this.url = url;
		this.userAgentString = userAgentString;
	}

	public void copyTo(@Nonnull final UserAgent.Builder builder) {
		builder.setFamily(family);
		builder.setIcon(icon);
		builder.setName(name);
		builder.setProducer(producer);
		builder.setProducerUrl(producerUrl);
		builder.setUrl(url);
		builder.setType(UserAgentType.ROBOT);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Robot other = (Robot) obj;
		if (!family.equals(other.family)) {
			return false;
		}
		if (!icon.equals(other.icon)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (!infoUrl.equals(other.infoUrl)) {
			return false;
		}
		if (!name.equals(other.name)) {
			return false;
		}
		if (!producer.equals(other.producer)) {
			return false;
		}
		if (!producerUrl.equals(other.producerUrl)) {
			return false;
		}
		if (!url.equals(other.url)) {
			return false;
		}
		if (!userAgentString.equals(other.userAgentString)) {
			return false;
		}
		return true;
	}

	public UserAgentFamily getFamily() {
		return family;
	}

	public String getIcon() {
		return icon;
	}

	public int getId() {
		return id;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public String getName() {
		return name;
	}

	public String getProducer() {
		return producer;
	}

	public String getProducerUrl() {
		return producerUrl;
	}

	public String getUrl() {
		return url;
	}

	public String getUserAgentString() {
		return userAgentString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + family.hashCode();
		result = prime * result + icon.hashCode();
		result = prime * result + id;
		result = prime * result + infoUrl.hashCode();
		result = prime * result + name.hashCode();
		result = prime * result + producer.hashCode();
		result = prime * result + producerUrl.hashCode();
		result = prime * result + url.hashCode();
		result = prime * result + userAgentString.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Robot [family=");
		builder.append(family);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", id=");
		builder.append(id);
		builder.append(", infoUrl=");
		builder.append(infoUrl);
		builder.append(", name=");
		builder.append(name);
		builder.append(", producer=");
		builder.append(producer);
		builder.append(", producerUrl=");
		builder.append(producerUrl);
		builder.append(", url=");
		builder.append(url);
		builder.append(", userAgentString=");
		builder.append(userAgentString);
		builder.append("]");
		return builder.toString();
	}

}
