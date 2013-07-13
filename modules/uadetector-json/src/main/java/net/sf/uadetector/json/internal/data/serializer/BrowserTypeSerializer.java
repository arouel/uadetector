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

import static net.sf.uadetector.json.internal.data.field.SerializableBrowserTypeField.HASH;
import static net.sf.uadetector.json.internal.data.field.SerializableBrowserTypeField.NAME;

import java.lang.reflect.Type;

import net.sf.uadetector.internal.data.domain.BrowserType;
import net.sf.uadetector.json.internal.data.hashcodebuilder.HashCodeGenerator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public final class BrowserTypeSerializer implements JsonSerializer<BrowserType> {

	@Override
	public JsonElement serialize(final BrowserType type, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty(NAME.getName(), type.getName());
		jsonObj.addProperty(HASH.getName(), HashCodeGenerator.generate(type));
		return jsonObj;
	}

}
