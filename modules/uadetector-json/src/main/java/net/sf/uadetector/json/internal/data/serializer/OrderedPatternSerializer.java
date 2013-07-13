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
package net.sf.uadetector.json.internal.data.serializer;

import static net.sf.uadetector.json.internal.data.field.SerializableOrderedPatternField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableOrderedPatternField.PATTERN;

import java.lang.reflect.Type;

import net.sf.uadetector.internal.data.domain.OrderedPattern;
import net.sf.uadetector.json.internal.data.hashcodebuilder.HashCodeGenerator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class OrderedPatternSerializer<T extends OrderedPattern<T>> implements JsonSerializer<T> {

	@Override
	public JsonElement serialize(final T pattern, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObj = new JsonObject();
		jsonObj.add(PATTERN.getName(), context.serialize(pattern.getPattern()));
		jsonObj.addProperty(HASH.getName(), HashCodeGenerator.generate(pattern));
		return jsonObj;
	}

}
