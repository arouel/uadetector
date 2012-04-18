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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The {@code VersionNumber} class represents the version number of an operating system or User-Agent.<br>
 * <br>
 * A {@code VersionNumber} object is immutable, their values cannot be changed after creation.
 * 
 * @author André Rouél
 */
public final class VersionNumber implements ReadableVersionNumber {

	/**
	 * Empty group or category of a version number
	 */
	public static final int EMPTY_GROUP = Integer.MIN_VALUE;

	/**
	 * Defines an empty or not set version number
	 */
	public static final VersionNumber UNKNOWN = new VersionNumber(EMPTY_GROUP);

	/**
	 * Separator between numeric groups of a version number
	 */
	private static final char SEPARATOR = '.';

	/**
	 * Minimum number of numeric group a version number
	 */
	private static final int MIN_GROUP_SIZE = 3;

	/**
	 * Replaces all {@code null} values in the given list of groups with {@code VersionNumber#EMPTY_GROUP}.
	 * 
	 * @param groups
	 *            list of numbers of a version number
	 * @return a new list of groups without {@code null} values
	 */
	public static List<Integer> replaceNullValueWithEmptyGroup(final List<Integer> groups) {
		if (groups == null) {
			throw new IllegalArgumentException("Argument 'groups' must not be null.");
		}

		final List<Integer> result = new ArrayList<Integer>(groups.size());
		for (final Integer group : groups) {
			if (group == null) {
				result.add(EMPTY_GROUP);
			} else {
				result.add(group);
			}
		}
		for (int i = result.size(); i < MIN_GROUP_SIZE; i++) {
			result.add(EMPTY_GROUP);
		}
		return result;
	}

	/**
	 * Converts the given list of numbers in a version string. The groups of the version number will be separated by a
	 * dot.
	 * 
	 * @param groups
	 *            list of numbers of a version number
	 * @return a formated version string
	 */
	private static String toVersionString(final List<Integer> groups) {
		final StringBuilder builder = new StringBuilder(6);
		int count = 0;
		for (final Integer category : groups) {
			if (category < 0) {
				break;
			} else {
				if (count > 0) {
					builder.append(SEPARATOR);
				}
				builder.append(category);
			}
			count++;
		}
		return builder.toString();
	}

	/**
	 * Groups, segments or categories of the version number
	 */
	private final List<Integer> groups;

	/**
	 * Constructs a {@code VersionNumber} with the given major number and without a minor and bugfix number.
	 * 
	 * @param major
	 *            major group of the version number
	 * @throws IllegalArgumentException
	 *             if the major segment is smaller than 0 and not empty
	 */
	public VersionNumber(final int major) {
		this(major, EMPTY_GROUP);
	}

	/**
	 * Constructs a {@code VersionNumber} with the given major, minor number and without a bugfix number.
	 * 
	 * @param major
	 *            major group of the version number
	 * @param minor
	 *            minor group of the version number
	 * @throws IllegalArgumentException
	 *             if the major or minor segment is smaller than 0 and not empty
	 */
	public VersionNumber(final int major, final int minor) {
		this(major, minor, EMPTY_GROUP);
	}

	/**
	 * Constructs a {@code VersionNumber} with the given major, minor and bugfix number.
	 * 
	 * @param major
	 *            major group of the version number
	 * @param minor
	 *            minor group of the version number
	 * @param bugfix
	 *            bugfix group of the version number
	 * @throws IllegalArgumentException
	 *             if the major, minor or bugfix segment is smaller than 0 and not empty
	 */
	public VersionNumber(final int major, final int minor, final int bugfix) {
		this(Arrays.asList(major, minor, bugfix));
	}

	/**
	 * Constructs a {@code VersionNumber} with the given numeric groups, such as major, minor and bugfix number.
	 * 
	 * @param groups
	 *            list of numbers of a version number
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 * @throws IllegalArgumentException
	 *             if one of the segments of the version number is smaller than 0 and not empty
	 */
	public VersionNumber(final List<Integer> groups) {
		if (groups == null) {
			throw new IllegalArgumentException("Argument 'groups' must not be null.");
		}

		final List<Integer> segments = replaceNullValueWithEmptyGroup(groups);
		int i = 0;
		for (final Integer segment : segments) {
			if (segment != EMPTY_GROUP && segment < 0) {
				throw new IllegalArgumentException("The segment on position " + i + " in the list must be EMPTY_GROUP, 0 or greater.");
			}
			i++;
		}

		this.groups = segments;
	}

	/**
	 * Compares this version number with the specified version number for order. Returns a negative integer, zero, or a
	 * positive integer as this version number is less than, equal to, or greater than the specified version number.
	 * 
	 * @return a negative integer, zero, or a positive integer as this version number is less than, equal to, or greater
	 *         than the specified version number.
	 */
	@Override
	public int compareTo(final ReadableVersionNumber other) {
		int result = 0;
		if (other == null) {
			result = -1;
		} else if (other.getGroups() == null) {
			result = -1;
		} else {
			final int length = groups.size() < other.getGroups().size() ? groups.size() : other.getGroups().size();
			int g1;
			int g2;
			for (int i = 0; i < length; i++) {
				g1 = Integer.valueOf(groups.get(i));
				g2 = Integer.valueOf(other.getGroups().get(i));
				if (g1 > g2) {
					result = 1;
					break;
				}
				if (g1 < g2) {
					result = -1;
					break;
				}
			}
			if (result == 0) {
				result = groups.size() > other.getGroups().size() ? 1 : groups.size() < other.getGroups().size() ? -1 : 0;
			}
		}
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this version number.
	 * 
	 * @return {@code true} if the given version number is equal to this one
	 */
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
		final VersionNumber other = (VersionNumber) obj;
		if (!groups.equals(other.groups)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the bugfix category of the version number.
	 */
	@Override
	public int getBugfix() {
		return groups.get(2);
	}

	/**
	 * Get all groups (or categories) of this version number. The first element in the list is the major category,
	 * followed by the minor and bugfix segment of the version number.<br>
	 * <br>
	 * The returned list of the version number segments is immutable.
	 * 
	 * @return an unmodifiable view of the of the version number groups
	 */
	@Override
	public List<Integer> getGroups() {
		return Collections.unmodifiableList(groups);
	}

	/**
	 * Gets the major category of the version number.
	 */
	@Override
	public int getMajor() {
		return groups.get(0);
	}

	/**
	 * Gets the major category of the version number.
	 */
	@Override
	public int getMinor() {
		return groups.get(1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + groups.hashCode();
		return result;
	}

	/**
	 * Returns a string representation of the version number.
	 * 
	 * @return a string representation of this version number
	 */
	@Override
	public String toString() {
		return "VersionNumber [groups=" + groups + "]";
	}

	/**
	 * Gets this version number as string.
	 * 
	 * @return numeric groups as dot separated version number string
	 */
	@Override
	public String toVersionString() {
		return toVersionString(groups);
	}

}
