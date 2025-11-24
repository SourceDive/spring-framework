package mine.jdk.debug.thread;

import java.util.concurrent.atomic.AtomicInteger;

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
					if (count % 2 == 0) {
						System.out.println("====>" + Thread.currentThread().getName() + ":" + count);
						count++;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						lock.notify();
					} else {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		};

		new Thread(task, "even thread").start();
	}

	public void ood() {
		Runnable task = () -> {
			synchronized (lock) {
				while (true) {
					if (count % 2 == 1) {
						System.out.println(Thread.currentThread().getName() + ":" + count);
						count++;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						lock.notify();
					} else {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		};

		new Thread(task, "odd thread").start();
	}

	public static void main(String[] args) {
		EvenOddThread evenOddThread = new EvenOddThread();

		evenOddThread.even();
		evenOddThread.ood();
	}
}
