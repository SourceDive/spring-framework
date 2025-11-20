package mine.jdk.debug.threadpool.two;

import java.util.concurrent.atomic.AtomicInteger;

class Task implements Runnable {
		private final AtomicInteger id = new AtomicInteger(1);
		
		@Override
		public void run() {
			System.out.println("running_" + id.getAndIncrement());
		}
	}