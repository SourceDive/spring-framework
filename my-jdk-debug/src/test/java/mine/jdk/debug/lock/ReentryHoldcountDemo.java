package mine.jdk.debug.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zero
 * @description 测试 lock.getHoldCount() 方法
 * @date 2025-10-29
 */
public class ReentryHoldcountDemo {
	private ReentrantLock lock = new ReentrantLock();

	public void firstEnter() {
		lock.lock();
		try {
			System.out.println("=====");
			System.out.println("firstEnter");
			System.out.println("holdcount: " + lock.getHoldCount());
			if (lock.getHoldCount() < 6000) {
				secondEnter();
			}
		} finally {
			lock.unlock();
		}
	}

	public void secondEnter() {
		lock.lock();
		try {
			System.out.println("===");
			System.out.println("secondEnter");
			System.out.println("holdcount: " + lock.getHoldCount());
			firstEnter();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		ReentryHoldcountDemo r = new ReentryHoldcountDemo();
		r.firstEnter();
	}
}
