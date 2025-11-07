package mine.jdk.debug.park;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport.park()
 */
public class BasicParkExample {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            System.out.println("Worker: 开始工作");
            
            // 模拟工作
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            System.out.println("Worker: 工作完成，准备park");
            
            // 阻塞当前线程，使用"任务完成等待"作为blocker
            LockSupport.park("任务完成等待");
            
            System.out.println("Worker: 被唤醒，继续执行");
        });
        
        worker.start();
        
        // 主线程等待3秒后唤醒worker
        Thread.sleep(3000);
        System.out.println("Main: 唤醒worker线程");
        LockSupport.unpark(worker);
    }
}