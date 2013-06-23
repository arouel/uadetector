package net.sf.uadetector.internal.util;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Nullable;

import net.sf.uadetector.exception.CannotCloseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is intended to provide utility methods to close {@link Closeable} instances.
 * 
 * @author André Rouél
 */
public final class Closeables {

	private static final Logger LOG = LoggerFactory.getLogger(Closeables.class);

	/**
	 * Closes a {@link Closeable} and swallows an occurring {@link IOException} if argument {@code swallowIOException}
	 * is {@code true}, otherwise the {@code IOException} will be thrown.
	 * <p>
	 * This method does nothing if a null reference is passed as {@code closeable}.
	 * 
	 * @param closeable
	 *            the {@code Closeable} object to be closed, or {@code null}
	 * @param swallowIOException
	 *            {@code true} if an occurring {@code IOException} should be swallowed and logged or {@code false} to
	 *            throw it
	 * 
	 * @throws IOException
	 *             if the given {@code closeable} cannot be closed
	 */
	public static void close(@Nullable final Closeable closeable, final boolean swallowIOException) throws IOException {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (final IOException e) {
				if (!swallowIOException) {
					throw e;
				}
				LOG.warn(e.getLocalizedMessage(), e);
			}
		}
	}

	/**
	 * Closes a {@link Closeable} and swallows an occurring {@link IOException} if argument {@code swallowIOException}
	 * is {@code true}, otherwise the {@code IOException} will be converted into a runtime exception.
	 * <p>
	 * This method does nothing if a null reference is passed as {@code closeable}.
	 * 
	 * @param closeable
	 *            the {@code Closeable} object to be closed, or {@code null}
	 * @param swallowIOException
	 *            {@code true} if an occurring {@code IOException} should be swallowed and logged or {@code false} to
	 *            throw a {@code CannotCloseException}
	 * 
	 * @throws CannotCloseException
	 *             if the given {@code closeable} cannot be closed
	 */
	public static void closeAndConvert(@Nullable final Closeable closeable, final boolean swallowIOException) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (final IOException e) {
				if (!swallowIOException) {
					throw new CannotCloseException(e.getLocalizedMessage(), e);
				}
				LOG.warn(e.getLocalizedMessage(), e);
			}
		}
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private Closeables() {
		// This class is not intended to create objects from it.
	}

}
