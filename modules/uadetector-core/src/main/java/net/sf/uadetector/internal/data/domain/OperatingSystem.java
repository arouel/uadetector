/*******************************************************************************
 * Copyright 2011 André Rouél
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

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.uadetector.UserAgent;

public final class OperatingSystem {

	public static final class Builder {

		private String family = "unknown";

		private String icon = "";

		private int id = Integer.MIN_VALUE;

		private String infoUrl = "";

		private String name = "unknown";

		private final SortedSet<OperatingSystemPattern> patternSet = new TreeSet<OperatingSystemPattern>();

		private String producer = "unknown";

		private String producerUrl = "";

		private String url = "";

		public Builder() {
			// default constructor
		}

		/**
		 * Creates a new instance of a builder with the data of the passed builder.
		 * 
		 * @param builder
		 *            builder containing the data to be copied
		 * @throws IllegalArgumentException
		 *             if the given argument is {@code null}
		 */
		protected Builder(final Builder builder) {
			if (builder == null) {
				throw new IllegalArgumentException("Argument 'builder' must not be null.");
			}

			this.family = builder.family;
			this.icon = builder.icon;
			this.id = builder.id;
			this.infoUrl = builder.infoUrl;
			this.name = builder.name;
			this.patternSet.addAll(builder.patternSet);
			this.producer = builder.producer;
			this.producerUrl = builder.producerUrl;
			this.url = builder.url;
		}

		public Builder addPatternSet(final Set<OperatingSystemPattern> patternSet) {
			if (patternSet == null) {
				throw new IllegalArgumentException("Argument 'patternSet' must not be null.");
			}

			this.patternSet.addAll(patternSet);
			return this;
		}

		public OperatingSystem build() {
			return new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
		}

		/**
		 * Creates a copy (with all its data) of the current builder.
		 * 
		 * @return a new instance of the current builder, never {@code null}
		 */
		public OperatingSystem.Builder copy() {
			return new Builder(this);
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

		public SortedSet<OperatingSystemPattern> getPatternSet() {
			return patternSet;
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

	}

	private final String family;
	private final String icon;
	private final int id;
	private final String infoUrl;
	private final String name;
	private final SortedSet<OperatingSystemPattern> patternSet;
	private final String producer;
	private final String producerUrl;
	private final String url;

	public OperatingSystem(final String family, final String icon, final int id, final String infoUrl, final String name,
			final SortedSet<OperatingSystemPattern> patternSet, final String producer, final String producerUrl, final String url) {

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
		if (patternSet == null) {
			throw new IllegalArgumentException("Argument 'patternSet' must not be null.");
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

		this.family = family;
		this.id = id;
		this.icon = icon;
		this.infoUrl = infoUrl;
		this.name = name;
		this.patternSet = patternSet;
		this.producer = producer;
		this.producerUrl = producerUrl;
		this.url = url;
	}

	public void copyTo(final UserAgent.Builder builder) {
		builder.setOperatingSystem(new net.sf.uadetector.OperatingSystem(family, name, producer, producerUrl, url));
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
		final OperatingSystem other = (OperatingSystem) obj;
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
		if (!patternSet.equals(other.patternSet)) {
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

	public SortedSet<OperatingSystemPattern> getPatternSet() {
		return patternSet;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + family.hashCode();
		result = prime * result + icon.hashCode();
		result = prime * result + id;
		result = prime * result + infoUrl.hashCode();
		result = prime * result + name.hashCode();
		result = prime * result + patternSet.hashCode();
		result = prime * result + producer.hashCode();
		result = prime * result + producerUrl.hashCode();
		result = prime * result + url.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OperatingSystem [family=");
		builder.append(family);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", id=");
		builder.append(id);
		builder.append(", infoUrl=");
		builder.append(infoUrl);
		builder.append(", name=");
		builder.append(name);
		builder.append(", patternSet=");
		builder.append(patternSet);
		builder.append(", producer=");
		builder.append(producer);
		builder.append(", producerUrl=");
		builder.append(producerUrl);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}

}
