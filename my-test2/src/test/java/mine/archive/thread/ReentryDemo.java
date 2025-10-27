package mine.archive.thread;

/**
 * @author zero
 * @description 测试 synchronized 的重入
 * @date 2025-10-27
 */
public class ReentryDemo {
	public synchronized void firstEnter() {
		System.out.println(Thread.currentThread().getName());
		System.out.println("第一次进入");
		secondEnter();
	}

	public synchronized void secondEnter() {
		System.out.println("第二次进入");
	}

	public static void main(String[] args) {
		ReentryDemo reentryDemo = new ReentryDemo();
		Runnable task = () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			reentryDemo.firstEnter();
		};
		new Thread(task, "线程01").start();
		new Thread(task, "线程02").start();
	}
}
