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
package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

final class Sha256CodeBuilder {

	/**
	 * Name for registered security providers for SHA-256
	 */
	private static final String ALGORITHM = "SHA-256";

	/**
	 * Character set to encodes a string into a sequence of bytes (and to using not the platform's default charset)
	 */
	private static final Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * Length of a hexadecimal SHA-256 message digest
	 */
	private static final int HASH_CODE_LENGTH = 64;

	/**
	 * 8-bit maximum numeric value in range to add when converting a given integer to a string representation in base 16
	 */
	private static final int MAX_BYTE = 0xFF;

	/**
	 * Message for the log if the requested algorithm can not be found
	 */
	private static final String MSG_NO_SUCH_ALGORITHM = "The cryptographic algorithm '%s' is not available in this environment.";

	@Nonnull
	public static String asHexString(final String content) {
		Check.notNull(content, "content");
		final MessageDigest md = getMessageDigest(ALGORITHM);
		final StringBuffer hexString = new StringBuffer(HASH_CODE_LENGTH);
		md.update(content.getBytes(CHARSET));
		final byte byteData[] = md.digest();
		for (final byte element : byteData) {
			final String hex = Integer.toHexString(MAX_BYTE & element);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	@Nonnull
	static MessageDigest getMessageDigest(final String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException(String.format(MSG_NO_SUCH_ALGORITHM, ALGORITHM), e);
		}
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private Sha256CodeBuilder() {
		// This class is not intended to create objects from it.
	}

}
