package mine.jdk.debug.lock;

import java.util.concurrent.Semaphore;

/**
 * @author zero
 * @description Semaphore 测试案例。三个获取成功，其余全部失败。
 * @date 2025-11-21
 */
public class SemaphoreDemo {
	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(3);

		Runnable task = () -> {

			try {
				boolean res = semaphore.tryAcquire();
				if (!res) {
					System.out.println(Thread.currentThread().getName() + "获取失败");
					return;
				}
				System.out.println(Thread.currentThread().getName() + "获取成功");
				System.out.println("permit count: " + semaphore.availablePermits());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			semaphore.release();
			System.out.println("--------");
		};

		for (int i = 0; i < 10; i++) {
			new Thread(task).start();
		}

	}
}
