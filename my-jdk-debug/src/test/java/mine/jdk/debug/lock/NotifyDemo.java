package mine.jdk.debug.lock;

/**
 * @author zero
 * @description 测试 wait 和 notify 方法。
 * @date 2025-10-25
 */
public class NotifyDemo {
	public static void main(String[] args) throws InterruptedException {
		Object lock = new Object();

		Thread t1 = new Thread(() -> {
			try {
				synchronized (lock) {
					lock.wait();
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println(Thread.currentThread().getName());
		}, "线程01");
		Thread t2 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName());
			synchronized (lock) {
				lock.notify();
			}
		}, "线程02");

		t1.start();
		t2.start();

		System.out.println(Thread.currentThread().getName());
		Thread.sleep(10000);
	}
}
