package mine.jdk.debug.final_test.visibility;

/**
 * @author zero
 * @description final关键字可见性的核心概念说明
 * @date 2025-12-01
 * 
 * 重点：理解JMM规范层面的保证，而不是试图复现问题
 */
public class FinalVisibilityConcept {
    
    // ========== 示例1：final字段保证可见性 ==========
    static class Example1 {
        final int finalValue = 100;  // final字段
        int normalValue = 100;        // 普通字段
        
        static Example1 instance;
        
        Example1() {
            instance = this;          // 引用逃逸
            normalValue = 100;        // 可能被重排序
            // finalValue = 100;      // 不会被重排序（已在声明时初始化）
        }
    }
    
    // ========== 示例2：final字段在构造函数中赋值 ==========
    static class Example2 {
        final int finalValue;
        int normalValue;
        
        static Example2 instance;
        
        Example2(int value) {
            instance = this;          // 引用逃逸
            normalValue = value;      // 可能被重排序到instance=this之后
            finalValue = value;       // 保证在构造函数完成前完成，不会被重排序
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== final关键字可见性核心概念 ===\n");
        
        System.out.println("【JMM规范保证】");
        System.out.println("1. final字段的写操作不会被重排序到构造函数之外");
        System.out.println("2. 如果线程A看到对象引用，则一定能看到final字段的正确值");
        System.out.println("3. 无需额外的同步机制（如volatile、synchronized）\n");
        
        System.out.println("【对比】");
        System.out.println("普通字段：");
        System.out.println("  - 可能因为重排序，其他线程看到初始值");
        System.out.println("  - 需要volatile或synchronized保证可见性");
        System.out.println("  - 现代JVM优化可能让问题难以复现，但规范不保证\n");
        
        System.out.println("final字段：");
        System.out.println("  - JMM保证不会被重排序");
        System.out.println("  - 构造函数完成后，所有线程立即可见");
        System.out.println("  - 这是规范层面的保证\n");
        
        System.out.println("【实际应用】");
        System.out.println("- 使用final字段可以安全地发布不可变对象");
        System.out.println("- 无需额外的同步开销");
        System.out.println("- 这是实现线程安全不可变对象的关键机制");
    }
}

