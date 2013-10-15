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
package net.sf.uadetector.internal.util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class ExecutorServicesTest {

	private static final class SleepingRunnable implements Runnable {

		private final long duration;

		private final int id;

		private final List<Integer> pool;

		public SleepingRunnable(final int id, final long duration, final List<Integer> pool) {
			this.id = id;
			this.duration = duration;
			this.pool = pool;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(duration);
			} catch (final InterruptedException e) {
				LOG.debug(e.getLocalizedMessage(), e);
			}
			pool.add(id);
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(ExecutorServicesTest.class);

	@Test
	public void executeUpdateOperation_executionOrderPredictableWhenSingleThreaded_1() throws InterruptedException, ExecutionException {
		final ExecutorService backgroundExecutor = ExecutorServices.createBackgroundExecutor();
		final List<Integer> pool = new ArrayList<Integer>();
		final Future<?> f1 = backgroundExecutor.submit(new SleepingRunnable(1, 10, pool));
		final Future<?> f2 = backgroundExecutor.submit(new SleepingRunnable(2, 10, pool));
		final Future<?> f3 = backgroundExecutor.submit(new SleepingRunnable(3, 10, pool));
		final Future<?> f4 = backgroundExecutor.submit(new SleepingRunnable(4, 10, pool));
		final Future<?> f5 = backgroundExecutor.submit(new SleepingRunnable(5, 50, pool));
		f5.get();
		f4.get();
		f3.get();
		f2.get();
		f1.get();
		assertThat(pool).isEqualTo(Lists.newArrayList(1, 2, 3, 4, 5));
	}

	@Test
	public void executeUpdateOperation_executionOrderPredictableWhenSingleThreaded_2() throws InterruptedException, ExecutionException {
		final ExecutorService backgroundExecutor = ExecutorServices.createBackgroundExecutor();
		final List<Integer> pool = new ArrayList<Integer>();
		final Future<?> f1 = backgroundExecutor.submit(new SleepingRunnable(1, 10, pool));
		final Future<?> f5 = backgroundExecutor.submit(new SleepingRunnable(5, 50, pool));
		final Future<?> f4 = backgroundExecutor.submit(new SleepingRunnable(4, 10, pool));
		final Future<?> f3 = backgroundExecutor.submit(new SleepingRunnable(3, 10, pool));
		final Future<?> f2 = backgroundExecutor.submit(new SleepingRunnable(2, 10, pool));
		f5.get();
		f4.get();
		f3.get();
		f2.get();
		f1.get();
		assertThat(pool).isEqualTo(Lists.newArrayList(1, 5, 4, 3, 2));
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<ExecutorServices> constructor = ExecutorServices.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void registerAfterShutdown() throws InterruptedException, ExecutionException {
		final ScheduledExecutorService scheduler = ExecutorServices.createScheduler();
		final List<Integer> pool = new ArrayList<Integer>();
		scheduler.scheduleAtFixedRate(new SleepingRunnable(1, 10, pool), 0, 100, TimeUnit.MILLISECONDS);
		final ExecutorService backgroundExecutor = ExecutorServices.createBackgroundExecutor();
		backgroundExecutor.submit(new SleepingRunnable(1, 100, pool));
		ExecutorServices.shutdownAll();
	}

	@Test
	public void scheduleUpdateOperation_executionOrderPredictableWhenSingleThreaded_1() throws InterruptedException, ExecutionException {
		final ScheduledExecutorService scheduler = ExecutorServices.createScheduler();
		final List<Integer> pool = new ArrayList<Integer>();
		final ScheduledFuture<?> f1 = scheduler.scheduleAtFixedRate(new SleepingRunnable(1, 10, pool), 0, 10, TimeUnit.MILLISECONDS);
		final ScheduledFuture<?> f2 = scheduler.scheduleAtFixedRate(new SleepingRunnable(2, 10, pool), 0, 10, TimeUnit.MILLISECONDS);
		final ScheduledFuture<?> f3 = scheduler.scheduleAtFixedRate(new SleepingRunnable(3, 10, pool), 0, 10, TimeUnit.MILLISECONDS);
		final ScheduledFuture<?> f4 = scheduler.scheduleAtFixedRate(new SleepingRunnable(4, 10, pool), 0, 10, TimeUnit.MILLISECONDS);
		final ScheduledFuture<?> f5 = scheduler.scheduleAtFixedRate(new SleepingRunnable(5, 50, pool), 0, 10, TimeUnit.MILLISECONDS);
		Thread.sleep(180);
		f1.cancel(true);
		f2.cancel(true);
		f3.cancel(true);
		f4.cancel(true);
		f5.cancel(true);
		ExecutorServices.shutdownAll();

		// we cannot test the elements, because execution speed and order is not predictable, especially on build
		// servers not under our control
		assertThat(pool.isEmpty()).isFalse();
	}

	@Test
	public void scheduleUpdateOperation_executionOrderPredictableWhenSingleThreaded_2() throws InterruptedException, ExecutionException {
		final ScheduledExecutorService scheduler = ExecutorServices.createScheduler();
		final List<Integer> pool = new ArrayList<Integer>();
		final ScheduledFuture<?> f1 = scheduler.scheduleAtFixedRate(new SleepingRunnable(1, 10, pool), 0, 100, TimeUnit.MILLISECONDS);
		final ScheduledFuture<?> f5 = scheduler.scheduleAtFixedRate(new SleepingRunnable(5, 50, pool), 0, 100, TimeUnit.MILLISECONDS);
		final ScheduledFuture<?> f4 = scheduler.scheduleAtFixedRate(new SleepingRunnable(4, 10, pool), 0, 100, TimeUnit.MILLISECONDS);
		final ScheduledFuture<?> f3 = scheduler.scheduleAtFixedRate(new SleepingRunnable(3, 10, pool), 0, 100, TimeUnit.MILLISECONDS);
		final ScheduledFuture<?> f2 = scheduler.scheduleAtFixedRate(new SleepingRunnable(2, 10, pool), 0, 100, TimeUnit.MILLISECONDS);
		Thread.sleep(90);
		f1.cancel(true);
		f2.cancel(true);
		f3.cancel(true);
		f4.cancel(true);
		f5.cancel(true);
		ExecutorServices.shutdownAll();

		// we cannot test the elements, because execution speed and order is not predictable, especially on build
		// servers not under our control
		assertThat(pool.isEmpty()).isFalse();
	}

	@Test
	public void shutdown_waitingOver_abruptShutdown() throws InterruptedException, ExecutionException {
		final ExecutorService backgroundExecutor = ExecutorServices.createBackgroundExecutor();
		final List<Integer> pool = new ArrayList<Integer>();
		backgroundExecutor.submit(new SleepingRunnable(1, 1000, pool));
		backgroundExecutor.submit(new SleepingRunnable(2, 1000, pool));
		backgroundExecutor.submit(new SleepingRunnable(3, 1000, pool));
		backgroundExecutor.submit(new SleepingRunnable(4, 1000, pool));
		backgroundExecutor.submit(new SleepingRunnable(5, 5000, pool));
		ExecutorServices.shutdown(backgroundExecutor, 100, TimeUnit.MILLISECONDS);
	}

}
