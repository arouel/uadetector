package net.sf.uadetector.internal.util;

import java.util.concurrent.ThreadFactory;

/**
 * Factory to create daemon threads that runs as a background process and do not blocks an application shutdown
 */
public final class DaemonThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(final Runnable runnable) {
		final Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		return thread;
	}

}
