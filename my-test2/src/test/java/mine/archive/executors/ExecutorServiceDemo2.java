package mine.archive.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zero
 * @description 测试 ExecutorService
 * @date 2025-10-14
 */
public class ExecutorServiceDemo2 {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 100; i++) {
			String taskNo = ":" + String.valueOf(i);
			Runnable task = () -> {
				System.out.println(Thread.currentThread().getName() + taskNo);
			};
			executorService.execute(task);
		}

		executorService.shutdown();
		while (!executorService.isTerminated()) {}
		System.out.println("===>任务全部执行完毕。");
	}
}
