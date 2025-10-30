package mine.jdk.debug.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zero
 * @description 测试 Lock#lock 方法是否是阻塞的
 * @date 2025-10-29
 */
public class LockDemo {
	Lock lock = new ReentrantLock(true);

	public void firstLock() {
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}

	public void anotherLock() throws InterruptedException {
		Thread.sleep(100);
		lock.lock();

//		while (!lock.tryLock()) {
//		}

		System.out.println(Thread.currentThread().getName());
		System.out.println("获取到锁了。");

		lock.unlock();
	}

	public static void main(String[] args) {
		LockDemo lockDemo = new LockDemo();
		new Thread(() -> {lockDemo.firstLock();}).start();
		new Thread(() -> {
			try {
				lockDemo.anotherLock();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}).start();
	}
}
