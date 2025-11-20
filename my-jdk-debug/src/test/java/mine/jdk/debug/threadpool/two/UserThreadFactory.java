package mine.jdk.debug.threadpool.two;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zero
 * @description todo
 * @date 2025-11-20
 */
public class UserThreadFactory implements ThreadFactory {

	private final String namePrefix;
	private final AtomicInteger nextId = new AtomicInteger(1);

	public UserThreadFactory(String group) {
		namePrefix = group + "-";

	}

	@Override
	public Thread newThread(Runnable task) {
		String name = namePrefix + nextId.getAndIncrement();
		Thread thread = new Thread(null, task, name, 0);
		System.out.println(thread.getName());
		return thread;
	}


}
