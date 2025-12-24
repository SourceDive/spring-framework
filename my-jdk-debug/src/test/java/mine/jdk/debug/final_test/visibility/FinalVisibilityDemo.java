package mine.jdk.debug.final_test.visibility;

/**
 * @author zero
 * @description 演示final关键字的可见性 - 展示真正的可见性问题
 * @date 2025-12-01
 * 
 * 关键点：通过对象引用逃逸，展示普通字段可能看到0，而final字段保证看到正确值
 */
public class FinalVisibilityDemo {
    
    // final字段：保证可见性，即使对象引用逃逸也能看到正确值
    private final int finalValue;
    
    // 普通字段：可能因为重排序，其他线程看到初始值0
    private int normalValue;
    
    // 静态引用，用于对象引用逃逸
    private static FinalVisibilityDemo instance;
    
    public FinalVisibilityDemo(int value) {
        // 关键：在构造函数完成前，将this引用发布出去（引用逃逸）
        instance = this;  // 对象引用逃逸
        
        // 普通字段赋值（可能被重排序到instance = this之后）
        this.normalValue = value;
        
        // final字段赋值（不会被重排序，保证在构造函数完成前完成）
        this.finalValue = value;
    }
    
    public int getFinalValue() {
        return finalValue;
    }
    
    public int getNormalValue() {
        return normalValue;
    }
    
    public static void main(String[] args) throws InterruptedException {
        // 启动一个线程，尝试读取可能未完全初始化的对象
        Thread reader = new Thread(() -> {
            while (instance == null) {
                // 等待对象被创建
                Thread.yield();
            }
            // 此时对象可能还在初始化中
            System.out.println("读取final字段: " + instance.getFinalValue());
            System.out.println("读取普通字段: " + instance.getNormalValue());
        });
        
        reader.start();
        
        // 主线程创建对象
        new FinalVisibilityDemo(100);
        
        reader.join();
        
        System.out.println("\n说明：");
        System.out.println("- final字段保证看到100（即使对象引用逃逸）");
        System.out.println("- 普通字段可能看到0（因为重排序，normalValue=100可能在instance=this之后执行）");
    }
}
