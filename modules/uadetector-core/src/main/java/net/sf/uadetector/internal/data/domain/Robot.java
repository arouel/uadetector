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

import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentType;

public final class Robot {

	public static final class Builder {

		private String family = "";
		private String icon = "";
		private int id = Integer.MIN_VALUE;
		private String infoUrl = "";
		private String name;
		private String producer = "";
		private String producerUrl = "";
		private String url = "";
		private String userAgentString = "";

		public Robot build() {
			return new Robot(family, icon, id, infoUrl, name, producer, producerUrl, url, userAgentString);
		}

		public String getFamily() {
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

		public Builder setFamily(final String family) {
			if (family == null) {
				throw new IllegalArgumentException("Argument 'family' must not be null.");
			}

			this.family = family;
			return this;
		}

		public Builder setIcon(final String icon) {
			if (icon == null) {
				throw new IllegalArgumentException("Argument 'icon' must not be null.");
			}

			this.icon = icon;
			return this;
		}

		public Builder setId(final int id) {
			if (id < 0) {
				throw new IllegalArgumentException("Argument 'id' must not be smaller than 0.");
			}

			this.id = id;
			return this;
		}

		public Builder setId(final String id) {
			if (id == null) {
				throw new IllegalArgumentException("Argument 'id' must not be null.");
			}

			this.setId(Integer.parseInt(id));
			return this;
		}

		public Builder setInfoUrl(final String infoUrl) {
			if (infoUrl == null) {
				throw new IllegalArgumentException("Argument 'infoUrl' must not be null.");
			}

			this.infoUrl = infoUrl;
			return this;
		}

		public Builder setName(final String name) {
			if (name == null) {
				throw new IllegalArgumentException("Argument 'name' must not be null.");
			}

			this.name = name;
			return this;
		}

		public Builder setProducer(final String producer) {
			if (producer == null) {
				throw new IllegalArgumentException("Argument 'producer' must not be null.");
			}

			this.producer = producer;
			return this;
		}

		public Builder setProducerUrl(final String producerUrl) {
			if (producerUrl == null) {
				throw new IllegalArgumentException("Argument 'producerUrl' must not be null.");
			}

			this.producerUrl = producerUrl;
			return this;
		}

		public Builder setUrl(final String url) {
			if (url == null) {
				throw new IllegalArgumentException("Argument 'url' must not be null.");
			}

			this.url = url;
			return this;
		}

		public Builder setUserAgentString(final String userAgentString) {
			if (userAgentString == null) {
				throw new IllegalArgumentException("Argument 'userAgentString' must not be null.");
			}

			this.userAgentString = userAgentString;
			return this;
		}

	}

	/**
	 * Identification of the User-Agent type
	 */
	public static final String TYPENAME = "Robot";

	private final String family;
	private final String icon;
	private final int id;
	private final String infoUrl;
	private final String name;
	private final String producer;
	private final String producerUrl;
	private final String url;
	private final String userAgentString;

	public Robot(final String family, final String icon, final int id, final String infoUrl, final String name, final String producer,
			final String producerUrl, final String url, final String userAgentString) {

		if (family == null) {
			throw new IllegalArgumentException("Argument 'family' must not be null.");
		}
		if (icon == null) {
			throw new IllegalArgumentException("Argument 'icon' must not be null.");
		}
		if (id < 0) {
			throw new IllegalArgumentException("Argument 'id' must not be smaller than 0.");
		}
		if (infoUrl == null) {
			throw new IllegalArgumentException("Argument 'infoUrl' must not be null.");
		}
		if (name == null) {
			throw new IllegalArgumentException("Argument 'name' must not be null.");
		}
		if (producer == null) {
			throw new IllegalArgumentException("Argument 'producer' must not be null.");
		}
		if (producerUrl == null) {
			throw new IllegalArgumentException("Argument 'producerUrl' must not be null.");
		}
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' must not be null.");
		}
		if (userAgentString == null) {
			throw new IllegalArgumentException("Argument 'userAgentString' must not be null.");
		}

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

	public void copyTo(final UserAgent.Builder builder) {
		builder.setFamily(family);
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

	public String getFamily() {
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
