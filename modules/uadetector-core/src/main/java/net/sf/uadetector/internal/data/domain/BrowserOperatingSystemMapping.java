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

public final class BrowserOperatingSystemMapping {

	public static final class Builder {

		/**
		 * ID of a browser entry
		 */
		private int browserId;

		/**
		 * ID of a operating system entry
		 */
		private int operatingSystemId;

		/**
		 * Build an instance of {@code BrowserOperatingSystemMapping}.
		 * 
		 * @return a new instance of {@code BrowserOperatingSystemMapping}
		 * @throws IllegalArgumentException
		 *             if one of the needed arguments to build an instance of {@code BrowserOperatingSystemMapping} is
		 *             invalid
		 */
		public BrowserOperatingSystemMapping build() {
			return new BrowserOperatingSystemMapping(browserId, operatingSystemId);
		}

		/**
		 * Gets the identification number of a browser entry.
		 * 
		 * @return identification number of a browser entry
		 */
		public int getBrowserId() {
			return browserId;
		}

		/**
		 * Gets the identification number of an operating system entry.
		 * 
		 * @return identification number of an operating system entry
		 */
		public int getOperatingSystemId() {
			return operatingSystemId;
		}

		/**
		 * Sets the identification number of a browser entry.
		 * 
		 * @param browserId
		 *            identification number
		 * @throws IllegalArgumentException
		 *             if the given number is smaller than {@code 0}
		 */
		public Builder setBrowserId(final int browserId) {
			if (browserId < 0) {
				throw new IllegalArgumentException("Argument 'browserId' can not be smaller than 0.");
			}

			this.browserId = browserId;
			return this;
		}

		/**
		 * Sets the identification number of a browser entry via a string.
		 * 
		 * @param browserId
		 *            identification number (as a {@code String)}
		 * @throws IllegalArgumentException
		 *             if the given argument is {@code null}
		 * @throws NumberFormatException
		 *             if the string can not be interpreted as a number
		 * @throws IllegalArgumentException
		 *             if the interpreted number is smaller than {@code 0}
		 */
		public Builder setBrowserId(final String browserId) {
			if (browserId == null) {
				throw new IllegalArgumentException("Argument 'browserId' must not be null.");
			}

			this.setBrowserId(Integer.parseInt(browserId));
			return this;
		}

		/**
		 * Sets the identification number of an operating system entry.
		 * 
		 * @param operatingSystemId
		 *            identification number
		 * @throws IllegalArgumentException
		 *             if the given number is smaller than {@code 0}
		 */
		public Builder setOperatingSystemId(final int operatingSystemId) {
			if (operatingSystemId < 0) {
				throw new IllegalArgumentException("Argument 'operatingSystemId' can not be smaller than 0.");
			}

			this.operatingSystemId = operatingSystemId;
			return this;
		}

		/**
		 * Sets the identification number of an operating system entry via a string.
		 * 
		 * @param operatingSystemId
		 *            identification number (as a {@code String)}
		 * @throws IllegalArgumentException
		 *             if the given argument is {@code null}
		 * @throws NumberFormatException
		 *             if the string can not be interpreted as a number
		 * @throws IllegalArgumentException
		 *             if the interpreted number is smaller than {@code 0}
		 */
		public Builder setOperatingSystemId(final String operatingSystemId) {
			if (operatingSystemId == null) {
				throw new IllegalArgumentException("Argument 'operatingSystemId' must not be null.");
			}

			this.setOperatingSystemId(Integer.parseInt(operatingSystemId));
			return this;
		}

	}

	/**
	 * ID of a browser entry
	 */
	private final int browserId;

	/**
	 * ID of a operating system entry
	 */
	private final int operatingSystemId;

	/**
	 * Constructs an instance of {@code BrowserOperatingSystemMapping}.
	 * 
	 * @throws IllegalArgumentException
	 *             if one of the given arguments is smaller than null
	 */
	public BrowserOperatingSystemMapping(final int browserId, final int operatingSystemId) {
		if (browserId < 0) {
			throw new IllegalArgumentException("Argument 'browserId' can not be smaller than 0.");
		}

		if (operatingSystemId < 0) {
			throw new IllegalArgumentException("Argument 'operatingSystemId' can not be smaller than 0.");
		}

		this.browserId = browserId;
		this.operatingSystemId = operatingSystemId;
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
		final BrowserOperatingSystemMapping other = (BrowserOperatingSystemMapping) obj;
		if (browserId != other.browserId) {
			return false;
		}
		if (operatingSystemId != other.operatingSystemId) {
			return false;
		}
		return true;
	}

	public int getBrowserId() {
		return browserId;
	}

	public int getOperatingSystemId() {
		return operatingSystemId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + browserId;
		result = prime * result + operatingSystemId;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BrowserOperatingSystemMapping [browserId=");
		builder.append(browserId);
		builder.append(", operatingSystemId=");
		builder.append(operatingSystemId);
		builder.append("]");
		return builder.toString();
	}

}
