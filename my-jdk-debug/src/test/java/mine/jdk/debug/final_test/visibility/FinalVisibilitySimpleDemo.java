package mine.jdk.debug.final_test.visibility;

/**
 * @author zero
 * @description 最简单的final可见性示例
 * @date 2025-12-01
 * 
 * final关键字的可见性保证（JMM规范层面）：
 * 1. final字段的写操作不会被重排序到构造函数之外
 * 2. final字段在构造函数完成后，对所有线程立即可见
 * 3. 即使对象引用逃逸，final字段也保证可见性
 * 
 * 注意：在现代JVM上可能很难复现可见性问题，但这是规范保证
 */
public class FinalVisibilitySimpleDemo {
    
    // final字段：JMM保证可见性
    final int x;
    
    // 普通字段：不保证可见性（理论上可能因为重排序看到0）
    int y;
    
    // 静态引用，用于引用逃逸
    static FinalVisibilitySimpleDemo obj;
    
    public FinalVisibilitySimpleDemo() {
        // 关键：在赋值前就发布引用（引用逃逸）
        // 这可能导致其他线程看到部分初始化的对象
        obj = this;
        
        // 普通字段赋值
        // JMM允许：obj = this 和 y = 100 可能被重排序
        // 理论上其他线程可能先看到obj != null，但y还是0
        y = 100;
        
        // final字段赋值
        // JMM保证：x = 100 不会被重排序到 obj = this 之后
        // 如果其他线程看到obj != null，则一定能看到x = 100
        x = 100;
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== final关键字可见性演示 ===\n");
        
        // 启动读取线程
        Thread t = new Thread(() -> {
            while (obj == null) {
                Thread.yield();
            }
            // 此时对象可能还在初始化中（引用逃逸）
            int finalVal = obj.x;
            int normalVal = obj.y;
            
            System.out.println("读取final字段 x = " + finalVal);
            System.out.println("读取普通字段 y = " + normalVal);
            
            if (finalVal == 100) {
                System.out.println("✓ final字段保证可见性：看到正确值100");
            }
            if (normalVal == 0) {
                System.out.println("✗ 普通字段可见性问题：看到初始值0");
            } else {
                System.out.println("  普通字段看到100（现代JVM优化，实际可能看不到0）");
            }
        });
        
        t.start();
        
        // 创建对象（引用逃逸）
        new FinalVisibilitySimpleDemo();
        
        t.join();
        
        System.out.println("\n=== 核心概念 ===");
        System.out.println("final字段的可见性保证（JMM规范）：");
        System.out.println("1. final字段写操作不会被重排序到构造函数之外");
        System.out.println("2. 如果线程A看到对象引用，则一定能看到final字段的正确值");
        System.out.println("3. 这是Java内存模型的规范保证，即使实际运行中可能看不到差异");
        System.out.println("\n普通字段：");
        System.out.println("- 理论上可能因为重排序导致可见性问题");
        System.out.println("- 现代JVM优化可能让问题难以复现");
        System.out.println("- 但规范层面不保证可见性，需要使用volatile或synchronized");
    }
}
