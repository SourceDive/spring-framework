package mine.jdk.debug.thread.holdlock;

/**
 * @author zero
 * @description 测试 Thread.holdsLock
 * @date 2025-11-30
 */
public class Person {
	private Object lock = new Object();

	public void exec() {
		boolean b = Thread.holdsLock(lock);
		System.out.println(b);

		synchronized (lock) {
			boolean b1 = Thread.holdsLock(lock);
			System.out.println(b1);
			System.out.println("执行了同步块");
			System.out.println(Thread.currentThread().getName());
		}
	}
}
