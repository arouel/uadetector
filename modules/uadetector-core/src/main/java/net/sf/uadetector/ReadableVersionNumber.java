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

import java.util.List;

/**
 * Defines a version number of an operating system or User-Agent.<br>
 * <br>
 * The implementation of this interface may be mutable or immutable. This interface only gives access to retrieve data,
 * never to change it.
 * 
 * @author André Rouél
 */
public interface ReadableVersionNumber extends Comparable<ReadableVersionNumber> {

	/**
	 * Gets the bugfix category of the version number.
	 * 
	 * @return bugfix segment
	 */
	int getBugfix();

	/**
	 * Get all groups (or categories) of this version number. The first element in the list is the major category,
	 * followed by the minor and bugfix segment of the version number.<br>
	 * <br>
	 * The returned list of the version number segments should be immutable.
	 * 
	 * @return a list of segments of the version number
	 */
	List<Integer> getGroups();

	/**
	 * Gets the major category of the version number.
	 * 
	 * @return major segment
	 */
	int getMajor();

	/**
	 * Gets the minor category of the version number.
	 * 
	 * @return minor segment
	 */
	int getMinor();

	/**
	 * Gets this version number as string.
	 * 
	 * @return numeric groups as dot separated version string
	 */
	String toVersionString();

}
