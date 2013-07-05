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

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.uadetector.OperatingSystemFamily;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.VersionNumber;

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
		 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
		 *             if the given argument is {@code null}
		 */
		protected Builder(@Nonnull final Builder builder) {
			Check.notNull(builder, "builder");

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

		@Nonnull
		public Builder addPatternSet(@Nonnull final Set<OperatingSystemPattern> patternSet) {
			Check.notNull(patternSet, "patternSet");

			this.patternSet.addAll(patternSet);
			return this;
		}

		@Nonnull
		public OperatingSystem build() {
			return new OperatingSystem(family, icon, id, infoUrl, name, patternSet, producer, producerUrl, url);
		}

		/**
		 * Creates a copy (with all its data) of the current builder.
		 * 
		 * @return a new instance of the current builder, never {@code null}
		 */
		@Nonnull
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

		@Nonnull
		public Builder setFamily(@Nonnull final String family) {
			Check.notNull(family, "family");

			this.family = family;
			return this;
		}

		@Nonnull
		public Builder setIcon(@Nonnull final String icon) {
			Check.notNull(icon, "icon");

			this.icon = icon;
			return this;
		}

		@Nonnull
		public Builder setId(@Nonnegative final int id) {
			Check.notNegative(id, "id");

			this.id = id;
			return this;
		}

		@Nonnull
		public Builder setId(@Nonnull final String id) {
			Check.notEmpty(id, "id");

			this.setId(Integer.parseInt(id.trim()));
			return this;
		}

		@Nonnull
		public Builder setInfoUrl(@Nonnull final String infoUrl) {
			Check.notNull(infoUrl, "infoUrl");

			this.infoUrl = infoUrl;
			return this;
		}

		@Nonnull
		public Builder setName(@Nonnull final String name) {
			Check.notNull(name, "name");

			this.name = name;
			return this;
		}

		@Nonnull
		public Builder setProducer(@Nonnull final String producer) {
			Check.notNull(producer, "producer");

			this.producer = producer;
			return this;
		}

		@Nonnull
		public Builder setProducerUrl(@Nonnull final String producerUrl) {
			Check.notNull(producerUrl, "producerUrl");

			this.producerUrl = producerUrl;
			return this;
		}

		@Nonnull
		public Builder setUrl(@Nonnull final String url) {
			Check.notNull(url, "url");

			this.url = url;
			return this;
		}

	}

	@Nonnull
	private final String family;
	@Nonnull
	private final String icon;
	@Nonnegative
	private final int id;
	@Nonnull
	private final String infoUrl;
	@Nonnull
	private final String name;
	@Nonnull
	private final SortedSet<OperatingSystemPattern> patternSet;
	@Nonnull
	private final String producer;
	@Nonnull
	private final String producerUrl;

	private final String url;

	public OperatingSystem(@Nonnull final String family, @Nonnull final String icon, @Nonnegative final int id,
			@Nonnull final String infoUrl, @Nonnull final String name, @Nonnull final SortedSet<OperatingSystemPattern> patternSet,
			@Nonnull final String producer, @Nonnull final String producerUrl, @Nonnull final String url) {
		Check.notNull(family, "family");
		Check.notNull(icon, "icon");
		Check.notNegative(id, "id");
		Check.notNull(infoUrl, "infoUrl");
		Check.notNull(name, "name");
		Check.notNull(patternSet, "patternSet");
		Check.notNull(producer, "producer");
		Check.notNull(producerUrl, "producerUrl");
		Check.notNull(url, "url");

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

	/**
	 * Copies all information of the current operating system entry to the given user agent builder.
	 * 
	 * @param builder
	 *            user agent builder
	 */
	public void copyTo(@Nonnull final UserAgent.Builder builder) {
		final OperatingSystemFamily f = OperatingSystemFamily.evaluate(family);
		final VersionNumber version = VersionNumber.parseOperatingSystemVersion(f, builder.getUserAgentString());
		builder.setOperatingSystem(new net.sf.uadetector.OperatingSystem(f, family, icon, name, producer, producerUrl, url, version));
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
