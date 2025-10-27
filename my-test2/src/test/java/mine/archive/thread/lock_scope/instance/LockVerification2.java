package mine.archive.thread.lock_scope.instance;

/**
 * 没有锁的方法进入。
 */
public class LockVerification2 {
    
    public void methodA() {
        System.out.println(Thread.currentThread().getName() + " 进入 methodA");
        try {
            Thread.sleep(2000); // 模拟耗时操作
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " 离开 methodA");
    }
    
    public void methodB() {
        System.out.println(Thread.currentThread().getName() + " 进入 methodB");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " 离开 methodB");
    }
    
    public static void main(String[] args) {
        LockVerification2 obj = new LockVerification2();
        
        // 场景1：不同线程访问不同方法
        new Thread(() -> obj.methodA(), "Thread-1").start();
        new Thread(() -> obj.methodB(), "Thread-2").start();
        
        // 场景2：不同线程访问同一方法  
        // new Thread(() -> obj.methodA(), "Thread-3").start();
        // new Thread(() -> obj.methodA(), "Thread-4").start();
    }
}