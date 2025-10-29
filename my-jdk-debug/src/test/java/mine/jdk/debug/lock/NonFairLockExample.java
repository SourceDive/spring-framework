package mine.jdk.debug.lock;

import java.util.concurrent.locks.ReentrantLock;

public class NonFairLockExample {
    private final ReentrantLock lock = new ReentrantLock(false); // 非公平锁
    
    public void testNonFairness() {
        // 线程A先获取锁并持有较长时间
        Thread threadA = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Thread A 获取锁");
                Thread.sleep(2000); // 模拟长时间操作
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
                System.out.println("Thread A 释放锁");
            }
        });
        
        // 线程B在A持有锁期间尝试获取，会进入等待队列
        Thread threadB = new Thread(() -> {
            try {
                Thread.sleep(500); // 确保A先获取锁
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            lock.lock();
            try {
                System.out.println("Thread B 获取锁");
            } finally {
                lock.unlock();
            }
        });
        
        // 线程C在A释放锁的瞬间尝试获取
        Thread threadC = new Thread(() -> {
            try {
                Thread.sleep(1500); // 在A释放前尝试
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            lock.lock();
            try {
                System.out.println("Thread C 获取锁");
            } finally {
                lock.unlock();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }

	public static void main(String[] args) {
		NonFairLockExample example = new NonFairLockExample();
		example.testNonFairness();
	}
}