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
package net.sf.uadetector.internal.data;

import java.io.Serializable;
import java.util.Comparator;

public final class OrderedPatternComparator<T extends Comparable<T>> implements Comparator<T>, Serializable {

	private static final long serialVersionUID = -3161079380603225797L;

	@Override
	public int compare(final T b1, final T b2) {
		int result = 0;
		if (b1 == null) {
			if (b2 != null) {
				result = -1;
			}
		} else {
			if (b2 == null) {
				result = 1;
			} else {
				result = b1.compareTo(b2);
			}
		}
		return result;
	}

}
