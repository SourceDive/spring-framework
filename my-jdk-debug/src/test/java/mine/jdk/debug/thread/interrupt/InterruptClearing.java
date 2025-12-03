package mine.jdk.debug.thread.interrupt;

public class InterruptClearing {
    public static void main(String[] args) throws InterruptedException {
		System.out.println("设置中断前: " + Thread.currentThread().isInterrupted());
        Thread.currentThread().interrupt();  // 设置中断

        System.out.println("设置中断后: " + Thread.currentThread().isInterrupted());  // true
        
        try {
            Thread.sleep(1000);  // 响应中断，同时抛出 InterruptedException
        } catch (InterruptedException e) {
            System.out.println("捕获异常后: " + Thread.currentThread().isInterrupted());  // false!
            // sleep()方法在抛出异常前清除了中断状态
        }
    }
}