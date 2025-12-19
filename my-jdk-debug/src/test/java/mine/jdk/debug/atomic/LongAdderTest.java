package mine.jdk.debug.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author zero
 * @description 8线程共享计数器，每个线程计算100万。
 * @date 2025-12-19
 */
public class LongAdderTest {
	public static void main(String[] args) throws InterruptedException {

		LongAdder num = new LongAdder();
		CountDownLatch latch = new CountDownLatch(8);
		int THREAD_COUNT = 80;
		int EACH_THREAD_COUNT = 1000_000;

		Runnable task = () -> {
			try {
				for (int i = 0; i < EACH_THREAD_COUNT; i++) {
					num.add(1);
				}
			} finally {
				latch.countDown();
			}
		};

		long start = System.nanoTime();
		for (int i = 0; i < THREAD_COUNT; i++) {
			new Thread(task).start();
		}

		latch.await();
		long end = System.nanoTime();

		double elapsedSeconds = (end - start) / 1_000_000_000.0;

		System.out.println(String.format("当前线程数量：{%d}, 每个线程计算：{%d}",
				THREAD_COUNT, EACH_THREAD_COUNT));
		System.out.println("LongAdder计算结束。num: " + num.sum());
		System.out.println("耗时: " + elapsedSeconds + " 秒");


	}
}
