package mine.archive.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zero
 * @description 测试 ExecutorService
 * @date 2025-10-14
 */
public class ExecutorServiceDemo {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Runnable task = () -> {
			System.out.println(Thread.currentThread().getName());
		};

		executorService.execute(task);
		executorService.shutdown();
	}
}
