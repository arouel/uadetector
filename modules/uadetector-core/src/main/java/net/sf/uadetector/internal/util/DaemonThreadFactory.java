package net.sf.uadetector.internal.util;

import java.util.concurrent.ThreadFactory;

/**
 * Factory to create daemon threads that runs as a background process and do not blocks an application shutdown
 */
public final class DaemonThreadFactory implements ThreadFactory {

	/**
	 * Name of a new thread
	 */
	private final String threadName;

	public DaemonThreadFactory(final String threadName) {
		if (threadName == null) {
			throw new IllegalArgumentException("Argument 'threadName' must not be null.");
		}
		if (threadName.trim().isEmpty()) {
			throw new IllegalArgumentException("Argument 'threadName' must not be empty.");
		}
		this.threadName = threadName;
	}

	@Override
	public Thread newThread(final Runnable runnable) {
		final Thread thread = new Thread(runnable);
		thread.setName(threadName);
		thread.setDaemon(true);
		return thread;
	}

}
