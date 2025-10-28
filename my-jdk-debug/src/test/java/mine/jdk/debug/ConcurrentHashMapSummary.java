package mine.jdk.debug;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ConcurrentHashMap 关键特性和原理总结
 * 
 * 这个类总结了ConcurrentHashMap的核心概念和最佳实践
 */
public class ConcurrentHashMapSummary {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ConcurrentHashMap 学习总结 ===\n");
        
        // 演示关键特性
        demonstrateKeyFeatures();
        
        // 演示最佳实践
        demonstrateBestPractices();
        
        // 演示常见陷阱
        demonstrateCommonPitfalls();
        
        System.out.println("\n=== 学习完成 ===");
    }
    
    /**
     * 演示关键特性
     */
    private static void demonstrateKeyFeatures() throws InterruptedException {
        System.out.println("--- 关键特性演示 ---");
        
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        
        // 1. 线程安全的基本操作
        System.out.println("1. 线程安全的基本操作:");
        map.put("key1", 1);
        map.put("key2", 2);
        System.out.println("   put操作: " + map);
        
        // 2. 原子性操作
        System.out.println("2. 原子性操作:");
        map.putIfAbsent("key3", 3);  // 只有key不存在时才插入
        map.putIfAbsent("key1", 999); // 不会覆盖已存在的值
        System.out.println("   putIfAbsent: " + map);
        
        // 3. 条件更新
        System.out.println("3. 条件更新:");
        map.replace("key1", 1, 100);  // 只有当前值为1时才更新为100
        map.replace("key2", 999, 200); // 当前值不是999，不会更新
        System.out.println("   replace: " + map);
        
        // 4. 计算操作
        System.out.println("4. 计算操作:");
        map.compute("key1", (k, v) -> v == null ? 0 : v + 10);
        map.computeIfPresent("key2", (k, v) -> v * 2);
        map.computeIfAbsent("key4", k -> 400);
        System.out.println("   compute操作: " + map);
        
        // 5. 合并操作
        System.out.println("5. 合并操作:");
        map.merge("key1", 50, (oldVal, newVal) -> oldVal + newVal);
        map.merge("key5", 500, (oldVal, newVal) -> oldVal + newVal);
        System.out.println("   merge操作: " + map);
    }
    
    /**
     * 演示最佳实践
     */
    private static void demonstrateBestPractices() throws InterruptedException {
        System.out.println("\n--- 最佳实践演示 ---");
        
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        // 1. 使用原子操作避免竞态条件
        System.out.println("1. 使用原子操作:");
        for (int i = 0; i < 4; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    // 使用compute进行原子性更新
                    map.compute("counter", (k, v) -> v == null ? 1 : v + 1);
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        
        System.out.println("   最终计数: " + map.get("counter"));
        
        // 2. 使用forEach进行安全遍历
        System.out.println("2. 安全遍历:");
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        
        System.out.println("   遍历结果:");
        map.forEach((k, v) -> System.out.println("     " + k + " = " + v));
        
        // 3. 使用search进行条件查找
        System.out.println("3. 条件查找:");
        String result = map.search(1, (k, v) -> v > 2 ? k : null);
        System.out.println("   找到值大于2的key: " + result);
        
        // 4. 使用reduce进行聚合操作
        System.out.println("4. 聚合操作:");
        Integer sum = map.reduceValues(1, Integer::sum);
        System.out.println("   所有值的总和: " + sum);
    }
    
    /**
     * 演示常见陷阱
     */
    private static void demonstrateCommonPitfalls() {
        System.out.println("\n--- 常见陷阱演示 ---");
        
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        
        // 1. 复合操作不是原子的
        System.out.println("1. 复合操作陷阱:");
        map.put("count", 0);
        
        // 错误做法：先get再put不是原子的
        Integer current = map.get("count");
        map.put("count", current + 1);  // 这里存在竞态条件
        
        // 正确做法：使用原子操作
        map.compute("count", (k, v) -> v + 1);
        
        System.out.println("   正确使用原子操作后的值: " + map.get("count"));
        
        // 2. size()方法返回近似值
        System.out.println("2. size()方法特性:");
        System.out.println("   size()返回近似值，不是精确值");
        System.out.println("   当前size: " + map.size());
        
        // 3. 迭代器弱一致性
        System.out.println("3. 迭代器特性:");
        System.out.println("   迭代器提供弱一致性保证");
        System.out.println("   不会抛出ConcurrentModificationException");
        
        // 4. null值限制
        System.out.println("4. null值限制:");
        try {
            map.put("nullKey", null);  // 会抛出NullPointerException
        } catch (NullPointerException e) {
            System.out.println("   不能存储null值: " + e.getMessage());
        }
        
        try {
            map.put(null, 999);  // 会抛出NullPointerException
        } catch (NullPointerException e) {
            System.out.println("   不能使用null作为key: " + e.getMessage());
        }
    }
    
    /**
     * ConcurrentHashMap vs HashMap vs Hashtable 对比
     */
    public static void comparisonTable() {
        System.out.println("\n--- 对比分析 ---");
        System.out.println("特性对比:");
        System.out.println("┌─────────────────┬─────────────┬─────────────┬─────────────────┐");
        System.out.println("│     特性        │   HashMap   │  Hashtable  │ ConcurrentHashMap│");
        System.out.println("├─────────────────┼─────────────┼─────────────┼─────────────────┤");
        System.out.println("│   线程安全      │     否      │     是      │       是        │");
        System.out.println("│   性能          │     高      │     低      │       高        │");
        System.out.println("│   锁机制        │     无      │   全表锁    │   分段锁/CAS    │");
        System.out.println("│   并发读        │     否      │     否      │       是        │");
        System.out.println("│   并发写        │     否      │     否      │       是        │");
        System.out.println("│   null值        │     支持    │   不支持    │     不支持      │");
        System.out.println("│   迭代器        │   快速失败  │   快速失败  │    弱一致性     │");
        System.out.println("└─────────────────┴─────────────┴─────────────┴─────────────────┘");
    }
    
    /**
     * 使用建议
     */
    public static void usageRecommendations() {
        System.out.println("\n--- 使用建议 ---");
        System.out.println("1. 选择ConcurrentHashMap的场景:");
        System.out.println("   - 多线程环境下的Map操作");
        System.out.println("   - 高并发读写需求");
        System.out.println("   - 需要原子性操作");
        
        System.out.println("\n2. 避免使用的场景:");
        System.out.println("   - 单线程环境（使用HashMap更高效）");
        System.out.println("   - 需要存储null值");
        System.out.println("   - 需要精确的size()值");
        
        System.out.println("\n3. 最佳实践:");
        System.out.println("   - 使用原子操作方法（compute, merge等）");
        System.out.println("   - 避免复合操作");
        System.out.println("   - 使用forEach进行遍历");
        System.out.println("   - 合理设置初始容量和负载因子");
        
        System.out.println("\n4. 性能调优:");
        System.out.println("   - 预估容量大小，避免频繁扩容");
        System.out.println("   - 使用合适的并发级别");
        System.out.println("   - 避免不必要的锁竞争");
    }
}
