package mine.jdk.debug.reference;

import java.lang.ref.WeakReference;

/**
 * WeakReference 基本使用示例
 * WeakReference 是弱引用，当对象只有弱引用时，GC 会回收该对象
 */
public class WeakReferenceBasicDemo {
    public static void main(String[] args) {
        // 示例1: 基本用法
        System.out.println("=== 示例1: 基本用法 ===");
        String str = new String("Hello World");
        WeakReference<String> weakRef = new WeakReference<>(str);
        
        System.out.println("强引用存在时: " + weakRef.get());
        
        // 断开强引用
        str = null;
        
        // 建议 GC（不保证立即执行）
        System.gc();
        
        // 等待一下让 GC 有机会执行
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("GC 后: " + weakRef.get()); // 可能为 null
        
        // 示例2: 检查引用是否被回收
        System.out.println("\n=== 示例2: 检查引用是否被回收 ===");
        Object obj = new Object();
        WeakReference<Object> ref = new WeakReference<>(obj);
        
        System.out.println("引用存在: " + (ref.get() != null));
        
        obj = null;
        System.gc();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("GC 后引用存在: " + (ref.get() != null));
    }
}

