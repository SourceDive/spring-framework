package mine.archive.singleton_double_lock;

/**
 * 查看 对象锁
 */
public class LockTest1 {
    private final Object lock = new Object();

    public void test() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " acquired instance lock");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        LockTest1 test = new LockTest1();
        new Thread(test::test, "T1").start();
        new Thread(test::test, "T2").start();
    }
}
