package mine.archive.thread;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zero
 * @description todo
 * @date 2025-10-17
 */
public class LinkedBlockingQueueDemoTest {

	@Test
	@DisplayName("测试 put 方法。")
	public void testPut() throws InterruptedException {
		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(2);

		queue.put(1);
		System.out.println("1 OK");

		queue.put(2);
		System.out.println("2 OK");

		Runnable task = () -> {
			try {
				Thread.sleep(3000);
				System.out.println("取走队头元素。");
				queue.take();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		};
		new Thread(task).start();

		queue.put(3);
		System.out.println("3 OK");

		System.out.println("===> end.");

	}
}
