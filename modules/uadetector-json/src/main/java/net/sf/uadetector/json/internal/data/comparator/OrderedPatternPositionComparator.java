/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.uadetector.json.internal.data.comparator;

import javax.annotation.Nonnull;

import net.sf.uadetector.internal.data.domain.OrderedPattern;
import net.sf.uadetector.internal.util.CompareNullSafe;

public final class OrderedPatternPositionComparator<T extends OrderedPattern<T>> extends CompareNullSafe<T> {

	private static final long serialVersionUID = -1264724926084448870L;

	public static <T extends OrderedPattern<T>> int comparePosition(@Nonnull final T b1, @Nonnull final T b2) {
		final int pos1 = b1.getPosition();
		final int pos2 = b2.getPosition();
		return (pos1 < pos2 ? -1 : (pos1 == pos2 ? 0 : 1));
	}

	@Override
	public int compareType(@Nonnull final T o1, @Nonnull final T o2) {
		return comparePosition(o1, o2);
	}

}
