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

import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentFamily;

public final class Browser {

	public static final class Builder {

		private UserAgentFamily family = UserAgentFamily.UNKNOWN;
		private String icon = "";
		private int id = Integer.MIN_VALUE;
		private String infoUrl = "";
		private OperatingSystem operatingSystem;
		private SortedSet<BrowserPattern> patternSet = new TreeSet<BrowserPattern>();
		private String producer = "";
		private String producerUrl = "";
		private BrowserType type;
		private int typeId = Integer.MIN_VALUE;
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
			this.operatingSystem = builder.operatingSystem;
			this.patternSet = builder.patternSet;
			this.producer = builder.producer;
			this.producerUrl = builder.producerUrl;
			this.type = builder.type;
			this.typeId = builder.typeId;
			this.url = builder.url;
		}

		@Nonnull
		public Browser build() {
			return new Browser(id, type, family, url, producer, producerUrl, icon, infoUrl, patternSet, operatingSystem);
		}

		/**
		 * Creates a copy (with all its data) of the current builder.
		 * 
		 * @return a new instance of the current builder, never {@code null}
		 */
		@Nonnull
		public Builder copy() {
			return new Builder(this);
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

		public OperatingSystem getOperatingSystem() {
			return operatingSystem;
		}

		public SortedSet<BrowserPattern> getPatternSet() {
			return patternSet;
		}

		public String getProducer() {
			return producer;
		}

		public String getProducerUrl() {
			return producerUrl;
		}

		public BrowserType getType() {
			return type;
		}

		public int getTypeId() {
			return typeId;
		}

		public String getUrl() {
			return url;
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
		public Builder setOperatingSystem(@Nonnull final OperatingSystem operatingSystem) {
			Check.notNull(operatingSystem, "operatingSystem");

			this.operatingSystem = operatingSystem;
			return this;
		}

		@Nonnull
		public Builder setPatternSet(@Nonnull final SortedSet<BrowserPattern> patternSet) {
			Check.notNull(patternSet, "patternSet");

			this.patternSet = patternSet;
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

		/**
		 * Sets the browser type.
		 * 
		 * @param type
		 *            A browser type
		 * @throws IllegalNullArgumentException
		 *             if the given argument is {@code null}
		 */
		@Nonnull
		public Builder setType(@Nonnull final BrowserType type) {
			Check.notNull(type, "type");

			this.type = type;
			return this;
		}

		@Nonnull
		public Builder setTypeId(@Nonnegative final int typeId) {
			Check.notNegative(typeId, "typeId");

			this.typeId = typeId;
			return this;
		}

		@Nonnull
		public Builder setTypeId(@Nonnull final String typeId) {
			Check.notEmpty(typeId, "typeId");

			setTypeId(Integer.parseInt(typeId.trim()));
			return this;
		}

		@Nonnull
		public Builder setUrl(@Nonnull final String url) {
			Check.notNull(url, "url");

			this.url = url;
			return this;
		}

	}

	private final UserAgentFamily family;
	private final String icon;
	private final int id;
	private final String infoUrl;
	private final OperatingSystem operatingSystem;
	private final SortedSet<BrowserPattern> patternSet;
	private final String producer;
	private final String producerUrl;
	private final BrowserType type;
	private final String url;

	public Browser(@Nonnegative final int id, @Nonnull final BrowserType type, @Nonnull final UserAgentFamily family,
			@Nonnull final String url, @Nonnull final String producer, @Nonnull final String producerUrl, @Nonnull final String icon,
			@Nonnull final String infoUrl, @Nonnull final SortedSet<BrowserPattern> patternSet,
			@Nonnull final OperatingSystem operatingSystem) {
		Check.notNull(family, "family");
		Check.notNull(icon, "icon");
		Check.notNegative(id, "id");
		Check.notNull(infoUrl, "infoUrl");
		Check.notNull(patternSet, "patternSet");
		Check.notNull(producer, "producer");
		Check.notNull(producerUrl, "producerUrl");
		Check.notNull(type, "type");
		Check.notNull(url, "url");

		this.family = family;
		this.icon = icon;
		this.id = id;
		this.infoUrl = infoUrl;
		this.operatingSystem = operatingSystem;
		this.patternSet = patternSet;
		this.producer = producer;
		this.producerUrl = producerUrl;
		this.type = type;
		this.url = url;
	}

	/**
	 * Copy values from itself to a <code>UserAgentInfo.Builder</code>.
	 */
	public void copyTo(@Nonnull final UserAgent.Builder builder) {
		builder.setFamily(family);
		builder.setIcon(icon);
		builder.setName(family.getName());
		builder.setProducer(producer);
		builder.setProducerUrl(producerUrl);
		builder.setTypeName(type.getName());
		builder.setUrl(url);
		if (operatingSystem != null) {
			operatingSystem.copyTo(builder);
		}
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
		final Browser other = (Browser) obj;
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
		if (operatingSystem == null) {
			if (other.operatingSystem != null) {
				return false;
			}
		} else if (!operatingSystem.equals(other.operatingSystem)) {
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
		if (!type.equals(other.type)) {
			return false;
		}
		if (!url.equals(other.url)) {
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

	public OperatingSystem getOperatingSystem() {
		return operatingSystem;
	}

	public SortedSet<BrowserPattern> getPatternSet() {
		return patternSet;
	}

	public String getProducer() {
		return producer;
	}

	public String getProducerUrl() {
		return producerUrl;
	}

	public BrowserType getType() {
		return type;
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
		result = prime * result + ((operatingSystem == null) ? 0 : operatingSystem.hashCode());
		result = prime * result + patternSet.hashCode();
		result = prime * result + producer.hashCode();
		result = prime * result + producerUrl.hashCode();
		result = prime * result + type.hashCode();
		result = prime * result + url.hashCode();
		return result;
	}

	@Nonnull
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Browser [family=");
		builder.append(family);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", id=");
		builder.append(id);
		builder.append(", infoUrl=");
		builder.append(infoUrl);
		builder.append(", operatingSystem=");
		builder.append(operatingSystem);
		builder.append(", patternSet=");
		builder.append(patternSet);
		builder.append(", producer=");
		builder.append(producer);
		builder.append(", producerUrl=");
		builder.append(producerUrl);
		builder.append(", type=");
		builder.append(type);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}

}
