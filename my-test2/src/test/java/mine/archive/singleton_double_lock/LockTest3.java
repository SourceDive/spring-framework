package mine.archive.singleton_double_lock;


import java.util.concurrent.locks.ReentrantLock;

public class LockTest3 {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void test() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " acquired ReentrantLock");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(LockTest3::test, "T1").start();
        new Thread(LockTest3::test, "T2").start();
    }
}
