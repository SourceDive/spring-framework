package mine.jdk.debug;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ConcurrentHashMap 学习演示
 * 
 * 这个例子展示了ConcurrentHashMap的线程安全特性：
 * 1. 多线程并发读写操作
 * 2. 原子性操作
 * 3. 性能优势
 */
public class ConcurrentHashMapDemo {
    
    private static final int THREAD_COUNT = 10;
    private static final int OPERATION_COUNT = 1000;
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ConcurrentHashMap 演示开始 ===");
        
        // 演示1: 基本的多线程读写操作
        demonstrateBasicOperations();
        
        // 演示2: 原子性操作
        demonstrateAtomicOperations();
        
        // 演示3: 性能对比（简单版本）
        demonstratePerformance();
        
        System.out.println("=== ConcurrentHashMap 演示结束 ===");
    }
    
    /**
     * 演示基本的多线程读写操作
     */
    private static void demonstrateBasicOperations() throws InterruptedException {
        System.out.println("\n--- 演示1: 基本多线程读写操作 ---");
        
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        // 启动多个线程进行写操作
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < OPERATION_COUNT; j++) {
                        String key = "key-" + threadId + "-" + j;
                        map.put(key, j);
                        
                        // 模拟一些读操作
                        if (j % 100 == 0) {
                            map.get(key);
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        executor.shutdown();
        
        System.out.println("最终map大小: " + map.size());
        System.out.println("预期大小: " + (THREAD_COUNT * OPERATION_COUNT));
        
        // 验证数据完整性
        boolean dataIntegrity = verifyDataIntegrity(map);
        System.out.println("数据完整性检查: " + (dataIntegrity ? "通过" : "失败"));
    }
    
    /**
     * 演示原子性操作
     */
    private static void demonstrateAtomicOperations() throws InterruptedException {
        System.out.println("\n--- 演示2: 原子性操作 ---");
        
        ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        // 初始化计数器
        map.put("counter", new AtomicInteger(0));
        
        // 多个线程同时递增计数器
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < OPERATION_COUNT; j++) {
                        // 使用原子操作
                        map.computeIfPresent("counter", (key, value) -> {
                            value.incrementAndGet();
                            return value;
                        });
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        executor.shutdown();
        
        int finalValue = map.get("counter").get();
        int expectedValue = THREAD_COUNT * OPERATION_COUNT;
        
        System.out.println("最终计数器值: " + finalValue);
        System.out.println("预期值: " + expectedValue);
        System.out.println("原子性检查: " + (finalValue == expectedValue ? "通过" : "失败"));
    }
    
    /**
     * 演示性能特性
     */
    private static void demonstratePerformance() throws InterruptedException {
        System.out.println("\n--- 演示3: 性能特性 ---");
        
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        long startTime = System.currentTimeMillis();
        
        // 并发写入操作
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < OPERATION_COUNT; j++) {
                        String key = "perf-key-" + threadId + "-" + j;
                        String value = "value-" + j;
                        
                        // 使用putIfAbsent进行条件插入
                        map.putIfAbsent(key, value);
                        
                        // 使用replace进行条件更新
                        if (j % 2 == 0) {
                            map.replace(key, value, value + "-updated");
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        executor.shutdown();
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("并发操作耗时: " + duration + "ms");
        System.out.println("总操作数: " + (THREAD_COUNT * OPERATION_COUNT));
        System.out.println("平均每秒操作数: " + (THREAD_COUNT * OPERATION_COUNT * 1000 / duration));
        System.out.println("最终map大小: " + map.size());
    }
    
    /**
     * 验证数据完整性
     */
    private static boolean verifyDataIntegrity(ConcurrentHashMap<String, Integer> map) {
        // 检查是否有重复的key
        return map.size() == THREAD_COUNT * OPERATION_COUNT;
    }
}
