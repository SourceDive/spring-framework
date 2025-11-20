package mine.jdk.debug.threadpool.two;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zero
 * @description todo
 * @date 2025-11-20
 */
public class UserThreadPool {
	public static void main(String[] args) {
		BlockingQueue queue = new LinkedBlockingQueue<>(2);

		UserThreadFactory userThreadFactory01 = new UserThreadFactory("第1机房");
		UserThreadFactory userThreadFactory02 = new UserThreadFactory("第2机房");

		UserRejectHandler userRejectHandler = new UserRejectHandler();

		ThreadPoolExecutor threadPoolExecutor01 = new ThreadPoolExecutor(1, 2,
				60, TimeUnit.SECONDS, queue,
				userThreadFactory01, userRejectHandler);

		ThreadPoolExecutor threadPoolExecutor02 = new ThreadPoolExecutor(1, 2,
				60, TimeUnit.SECONDS, queue,
				userThreadFactory02, userRejectHandler);

		Task task = new Task();
		for (int i = 0; i < 200; i++) {
			threadPoolExecutor01.execute(task);
			threadPoolExecutor02.execute(task);
		}
	}
}
