package net.sf.uadetector.json.internal.data.hashcodebuilder;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * Corresponding default logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Sha256CodeBuilder.class);

	/**
	 * Message for the log if the requested algorithm can not be found
	 */
	private static final String MSG_NO_SUCH_ALGORITHM = String.format(
			"The cryptographic algorithm '%s' is not available in this environment.", ALGORITHM);

	@Nonnull
	public static String asHexString(final String content) {
		Check.notNull(content, "content");
		final MessageDigest md = getMessageDigest(ALGORITHM);
		final StringBuffer hexString = new StringBuffer(HASH_CODE_LENGTH);
		md.update(content.getBytes(CHARSET));
		final byte byteData[] = md.digest();
		for (final byte element : byteData) {
			final String hex = Integer.toHexString(0xff & element);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	@Nonnull
	static MessageDigest getMessageDigest(final String algorithm) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			LOG.warn(MSG_NO_SUCH_ALGORITHM, e);
		} finally {
			Check.stateIsTrue(md != null, MSG_NO_SUCH_ALGORITHM);
		}
		return md;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private Sha256CodeBuilder() {
		// This class is not intended to create objects from it.
	}

}
