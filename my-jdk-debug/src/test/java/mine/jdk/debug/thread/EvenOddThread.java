package mine.jdk.debug.thread;

/**
 * @author zero
 * @description 两个线程交替打印奇偶数。
 * @date 2025-11-24
 */
public class EvenOddThread {

	private int count = 0;

	private Object lock = new Object();

	public void even() {
		Runnable task = () -> {
			synchronized (lock) {
				while (true) {
					// 使用 while 循环检查条件，避免虚假唤醒
					while (count % 2 != 0) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return;
						}
					}
					System.out.println("====>" + Thread.currentThread().getName() + ":" + count);
					count++;
					lock.notify();
					// sleep 在 synchronized 内，确保交替打印的时序
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}
				}
			}
		};

		new Thread(task, "even thread").start();
	}

	public void odd() {
		Runnable task = () -> {
			synchronized (lock) {
				while (true) {
					// 使用 while 循环检查条件，避免虚假唤醒
					while (count % 2 != 1) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return;
						}
					}
					System.out.println(Thread.currentThread().getName() + ":" + count);
					count++;
					lock.notify();
					// sleep 在 synchronized 内，确保交替打印的时序
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}
				}
			}
		};

		new Thread(task, "odd thread").start();
	}

	public static void main(String[] args) {
		EvenOddThread evenOddThread = new EvenOddThread();

		evenOddThread.even();
		evenOddThread.odd();
	}
}
