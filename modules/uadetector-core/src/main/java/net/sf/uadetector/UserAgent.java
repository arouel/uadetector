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

/**
 * {@code UserAgent} is an immutable entity that represents the informations about web-based client applications like
 * Web browsers, search engines or crawlers (spiders) as well as mobile phones, screen readers and braille browsers.
 * 
 * @author André Rouél
 */
public final class UserAgent implements ReadableUserAgent {

	public static final class Builder implements ReadableUserAgent {

		private UserAgentFamily family = EMPTY.family;

		private String icon = EMPTY.icon;

		private String name = EMPTY.name;

		private OperatingSystem operatingSystem = OperatingSystem.EMPTY;

		private String producer = EMPTY.producer;

		private String producerUrl = EMPTY.producerUrl;

		private UserAgentType type = EMPTY.type;

		private String typeName = EMPTY.typeName;

		private String url = EMPTY.url;

		private String userAgentString = "";

		private VersionNumber versionNumber = VersionNumber.UNKNOWN;

		public Builder() {
			// default constructor
		}

		public Builder(final String userAgentString) {
			if (userAgentString == null) {
				throw new IllegalArgumentException("Argument 'userAgentString' must not be null.");
			}
			this.userAgentString = userAgentString;
		}

		public UserAgent build() {
			return new UserAgent(family, icon, name, operatingSystem, producer, producerUrl, type, typeName, url, versionNumber);
		}

		@Override
		public UserAgentFamily getFamily() {
			return family;
		}

		@Override
		public String getIcon() {
			return icon;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public OperatingSystem getOperatingSystem() {
			return operatingSystem;
		}

		@Override
		public String getProducer() {
			return producer;
		}

		@Override
		public String getProducerUrl() {
			return producerUrl;
		}

		@Override
		public UserAgentType getType() {
			return type;
		}

		@Override
		public String getTypeName() {
			return typeName;
		}

		@Override
		public String getUrl() {
			return url;
		}

		public String getUserAgentString() {
			return userAgentString;
		}

		@Override
		public VersionNumber getVersionNumber() {
			return versionNumber;
		}

		public Builder setFamily(final UserAgentFamily family) {
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

		public Builder setName(final String name) {
			if (name == null) {
				throw new IllegalArgumentException("Argument 'name' must not be null.");
			}
			this.name = name;
			return this;
		}

		public Builder setOperatingSystem(final OperatingSystem operatingSystem) {
			if (operatingSystem == null) {
				throw new IllegalArgumentException("Argument 'operatingSystem' must not be null.");
			}
			this.operatingSystem = operatingSystem;
			return this;
		}

		public Builder setOperatingSystem(final ReadableOperatingSystem os) {
			if (os == null) {
				throw new IllegalArgumentException("Argument 'os' must not be null.");
			}
			this.operatingSystem = new OperatingSystem(os.getFamily(), os.getFamilyName(), os.getName(), os.getProducer(),
					os.getProducerUrl(), os.getUrl(), os.getVersionNumber());
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

		public Builder setType(final UserAgentType type) {
			if (type == null) {
				throw new IllegalArgumentException("Argument 'type' must not be null.");
			}
			this.type = type;
			this.typeName = type.getName();
			return this;
		}

		public Builder setTypeName(final String typeName) {
			if (typeName == null) {
				throw new IllegalArgumentException("Argument 'typeName' must not be null.");
			}
			this.type = UserAgentType.evaluateByTypeName(typeName);
			this.typeName = typeName;
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

		public Builder setVersionNumber(final VersionNumber versionNumber) {
			if (versionNumber == null) {
				throw new IllegalArgumentException("Argument 'versionNumber' must not be null.");
			}
			this.versionNumber = versionNumber;
			return this;
		}

	}

	public static final UserAgent EMPTY = new UserAgent(UserAgentFamily.UNKNOWN, "", "unknown", OperatingSystem.EMPTY, "", "",
			UserAgentType.UNKNOWN, "", "", VersionNumber.UNKNOWN);

	private final UserAgentFamily family;

	private final String icon;

	private final String name;

	private final OperatingSystem operatingSystem;

	private final String producer;

	private final String producerUrl;

	private final UserAgentType type;

	private final String typeName;

	private final String url;

	private final VersionNumber versionNumber;

	public UserAgent(final UserAgentFamily family, final String icon, final String name, final OperatingSystem operatingSystem,
			final String producer, final String producerUrl, final UserAgentType type, final String typeName, final String url,
			final VersionNumber versionNumber) {

		if (family == null) {
			throw new IllegalArgumentException("Argument 'family' must not be null.");
		}
		if (icon == null) {
			throw new IllegalArgumentException("Argument 'icon' must not be null.");
		}
		if (name == null) {
			throw new IllegalArgumentException("Argument 'name' must not be null.");
		}
		if (operatingSystem == null) {
			throw new IllegalArgumentException("Argument 'operatingSystem' must not be null.");
		}
		if (producer == null) {
			throw new IllegalArgumentException("Argument 'producer' must not be null.");
		}
		if (producerUrl == null) {
			throw new IllegalArgumentException("Argument 'producerUrl' must not be null.");
		}
		if (type == null) {
			throw new IllegalArgumentException("Argument 'type' must not be null.");
		}
		if (typeName == null) {
			throw new IllegalArgumentException("Argument 'typeName' must not be null.");
		}
		if (url == null) {
			throw new IllegalArgumentException("Argument 'url' must not be null.");
		}
		if (versionNumber == null) {
			throw new IllegalArgumentException("Argument 'versionNumber' must not be null.");
		}

		this.family = family;
		this.icon = icon;
		this.name = name;
		this.operatingSystem = operatingSystem;
		this.producer = producer;
		this.producerUrl = producerUrl;
		this.type = type;
		this.typeName = typeName;
		this.url = url;
		this.versionNumber = versionNumber;
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
		final UserAgent other = (UserAgent) obj;
		if (!family.equals(other.family)) {
			return false;
		}
		if (!icon.equals(other.icon)) {
			return false;
		}
		if (!name.equals(other.name)) {
			return false;
		}
		if (!operatingSystem.equals(other.operatingSystem)) {
			return false;
		}
		if (!producer.equals(other.producer)) {
			return false;
		}
		if (!producerUrl.equals(other.producerUrl)) {
			return false;
		}
		if (!type.equals(other.type)) {
			return false;
		}
		if (!typeName.equals(other.typeName)) {
			return false;
		}
		if (!url.equals(other.url)) {
			return false;
		}
		if (!versionNumber.equals(other.versionNumber)) {
			return false;
		}
		return true;
	}

	@Override
	public UserAgentFamily getFamily() {
		return family;
	}

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public OperatingSystem getOperatingSystem() {
		return operatingSystem;
	}

	@Override
	public String getProducer() {
		return producer;
	}

	@Override
	public String getProducerUrl() {
		return producerUrl;
	}

	@Override
	public UserAgentType getType() {
		return type;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public VersionNumber getVersionNumber() {
		return versionNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + family.hashCode();
		result = prime * result + icon.hashCode();
		result = prime * result + name.hashCode();
		result = prime * result + operatingSystem.hashCode();
		result = prime * result + producer.hashCode();
		result = prime * result + producerUrl.hashCode();
		result = prime * result + type.hashCode();
		result = prime * result + typeName.hashCode();
		result = prime * result + url.hashCode();
		result = prime * result + versionNumber.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UserAgent [family=");
		builder.append(family);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", name=");
		builder.append(name);
		builder.append(", operatingSystem=");
		builder.append(operatingSystem);
		builder.append(", producer=");
		builder.append(producer);
		builder.append(", producerUrl=");
		builder.append(producerUrl);
		builder.append(", type=");
		builder.append(type);
		builder.append(", typeName=");
		builder.append(typeName);
		builder.append(", url=");
		builder.append(url);
		builder.append(", versionNumber=");
		builder.append(versionNumber);
		builder.append("]");
		return builder.toString();
	}

}
