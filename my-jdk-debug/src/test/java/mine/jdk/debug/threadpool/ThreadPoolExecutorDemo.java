package mine.jdk.debug.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zero
 * @description ThreadPoolExecutor 执行流程演示
 * 
 * 核心参数说明：
 * 1. corePoolSize: 核心线程数，即使空闲也会保留的线程数
 * 2. maximumPoolSize: 最大线程数，线程池允许的最大线程数
 * 3. keepAliveTime: 非核心线程的空闲存活时间
 * 4. workQueue: 任务队列，用于存放待执行的任务
 * 5. threadFactory: 线程工厂，用于创建新线程
 * 6. handler: 拒绝策略，当线程池和队列都满时的处理方式
 * 
 * 执行流程：
 * 1. 提交任务时，如果当前线程数 < corePoolSize，创建新线程执行
 * 2. 如果当前线程数 >= corePoolSize，将任务放入队列
 * 3. 如果队列满了，且当前线程数 < maximumPoolSize，创建新线程执行
 * 4. 如果队列满了，且当前线程数 >= maximumPoolSize，执行拒绝策略
 * 
 * @date 2025-01-XX
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== ThreadPoolExecutor 执行流程演示 ==========\n");

        // 演示1：基本执行流程
        demonstrateBasicFlow();

//        Thread.sleep(1000);
//
//        // 演示2：队列满时的行为
//        demonstrateQueueFull();
//
//        Thread.sleep(1000);
//
//        // 演示3：拒绝策略
//        demonstrateRejectionPolicy();
    }

    /**
     * 演示1：基本执行流程
     * 展示任务如何被分配到核心线程、队列和额外线程
     */
    private static void demonstrateBasicFlow() throws InterruptedException {
        System.out.println("\n【演示1：基本执行流程】");
        System.out.println("配置：corePoolSize=2, maximumPoolSize=4, queueCapacity=3\n");

        // 创建线程池
        // 核心线程数=2，最大线程数=4，队列容量=3
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                          // corePoolSize: 核心线程数
                4,                          // maximumPoolSize: 最大线程数
                60L,                        // keepAliveTime: 非核心线程空闲存活时间
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3), // workQueue: 任务队列（容量3）
                new CustomThreadFactory(),    // threadFactory: 自定义线程工厂
                new ThreadPoolExecutor.CallerRunsPolicy() // handler: 拒绝策略
        );

        // 打印初始状态
        printPoolStatus(executor, "初始状态");

        // 提交8个任务，观察执行流程
        System.out.println("\n提交8个任务：");
        for (int i = 1; i <= 8; i++) {
            final int taskId = i;
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.printf("  [任务%d] 开始执行 - 线程: %s%n", taskId, threadName);
                
                try {
                    Thread.sleep(5000); // 模拟任务执行时间
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                System.out.printf("  [任务%d] 执行完成 - 线程: %s%n", taskId, threadName);
            });

            printPoolStatus(executor, String.format("提交任务%d后", taskId));
            Thread.sleep(100); // 短暂延迟，便于观察
        }

        // 等待所有任务完成
        System.out.println("\n等待所有任务完成...");
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("\n最终状态：");
        printPoolStatus(executor, "所有任务完成后");
    }

    /**
     * 演示2：队列满时的行为
     * 展示当队列满时，如何创建额外线程
     */
    private static void demonstrateQueueFull() throws InterruptedException {
        System.out.println("\n\n【演示2：队列满时的行为】");
        System.out.println("配置：corePoolSize=2, maximumPoolSize=5, queueCapacity=2\n");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                5,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2), // 队列容量只有2
                new CustomThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        printPoolStatus(executor, "初始状态");

        // 提交10个任务，观察队列满时的行为
        System.out.println("\n提交10个任务（队列容量=2，最大线程数=5）：");
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.printf("  [任务%d] 执行中 - 线程: %s%n", taskId, threadName);
                try {
                    Thread.sleep(1000); // 较长的执行时间，便于观察
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            printPoolStatus(executor, String.format("提交任务%d后", taskId));
            Thread.sleep(50);
        }

        executor.shutdown();
        executor.awaitTermination(15, TimeUnit.SECONDS);
    }

    /**
     * 演示3：拒绝策略
     * 展示当线程池和队列都满时的拒绝策略行为
     */
    private static void demonstrateRejectionPolicy() throws InterruptedException {
        System.out.println("\n\n【演示3：拒绝策略演示】");
        System.out.println("配置：corePoolSize=1, maximumPoolSize=2, queueCapacity=1\n");
        System.out.println("提交5个任务，观察拒绝策略的执行\n");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,                          // 核心线程数只有1
                2,                          // 最大线程数只有2
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1), // 队列容量只有1
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler() // 自定义拒绝策略
        );

        printPoolStatus(executor, "初始状态");

        // 提交5个任务，会触发拒绝策略
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            try {
                executor.submit(() -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.printf("  [任务%d] 执行中 - 线程: %s%n", taskId, threadName);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                System.out.printf("任务%d提交成功%n", taskId);
            } catch (RejectedExecutionException e) {
                System.out.printf("任务%d被拒绝: %s%n", taskId, e.getMessage());
            }

            printPoolStatus(executor, String.format("提交任务%d后", taskId));
            Thread.sleep(100);
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    /**
     * 打印线程池状态
     */
    private static void printPoolStatus(ThreadPoolExecutor executor, String stage) {
        System.out.printf("\n[%s]%n", stage);
        System.out.printf("  核心线程数: %d%n", executor.getCorePoolSize());
        System.out.printf("  最大线程数: %d%n", executor.getMaximumPoolSize());
        System.out.printf("  当前线程数: %d%n", executor.getPoolSize());
        System.out.printf("  活跃线程数: %d%n", executor.getActiveCount());
        System.out.printf("  队列大小: %d%n", executor.getQueue().size());
        System.out.printf("  已完成任务数: %d%n", executor.getCompletedTaskCount());
        System.out.printf("  总任务数: %d%n", executor.getTaskCount());
    }

    /**
     * 自定义线程工厂
     * 为线程设置有意义的名字，便于观察
     */
    static class CustomThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "Pool-Thread-" + threadNumber.getAndIncrement());
            t.setDaemon(false);
            System.out.printf("  [线程工厂] 创建新线程: %s%n", t.getName());
            return t;
        }
    }

    /**
     * 自定义拒绝策略
     * 打印详细信息，便于观察拒绝行为
     */
    static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.printf("\n  [拒绝策略] 任务被拒绝执行！%n");
            System.out.printf("    当前线程数: %d/%d%n", executor.getPoolSize(), executor.getMaximumPoolSize());
            System.out.printf("    队列大小: %d%n", executor.getQueue().size());
            System.out.printf("    活跃线程数: %d%n", executor.getActiveCount());
            
            // 可以选择不同的拒绝策略行为
            // 这里演示：直接丢弃任务
            System.out.println("    策略：直接丢弃任务");
        }
    }
}

