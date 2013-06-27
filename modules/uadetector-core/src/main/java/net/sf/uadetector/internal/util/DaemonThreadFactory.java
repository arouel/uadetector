package net.sf.uadetector.internal.util;

import java.util.concurrent.ThreadFactory;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

/**
 * Factory to create daemon threads that runs as a background process and do not blocks an application shutdown
 */
public final class DaemonThreadFactory implements ThreadFactory {

	/**
	 * Name of a new thread
	 */
	@Nonnull
	private final String threadName;

	/**
	 * Creates a new {@code DaemonThreadFactory} which creates itself threads with the specified name.
	 * 
	 * @param threadName
	 *            name of a thread to be created
	 */
	public DaemonThreadFactory(@Nonnull final String threadName) {
		Check.notNull(threadName, "threadName");
		Check.notEmpty(threadName.trim(), "threadName");
		this.threadName = threadName;
	}

	@Override
	public Thread newThread(@Nonnull final Runnable runnable) {
		final Thread thread = new Thread(runnable);
		thread.setName(threadName);
		thread.setDaemon(true);
		return thread;
	}

}
