package mine.jdk.debug.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * WeakReference 与 ReferenceQueue 结合使用示例
 * ReferenceQueue 可以跟踪哪些 WeakReference 引用的对象已被回收
 */
public class WeakReferenceQueueDemo {
    public static void main(String[] args) {
        System.out.println("=== WeakReference + ReferenceQueue 示例 ===");
        
        // 创建引用队列
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        
        // 创建对象
        String obj1 = new String("Object 1");
        String obj2 = new String("Object 2");
        String obj3 = new String("Object 3");
        
        // 创建带队列的弱引用
        WeakReference<String> ref1 = new WeakReference<>(obj1, queue);
        WeakReference<String> ref2 = new WeakReference<>(obj2, queue);
        WeakReference<String> ref3 = new WeakReference<>(obj3, queue);
        
        System.out.println("创建了 3 个弱引用");
        
        // 断开强引用
        obj1 = null;
        obj2 = null;
        obj3 = null;
        
        System.out.println("已断开所有强引用");
        
        // 触发 GC
        System.gc();
        
        // 等待 GC 完成
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 检查队列中的引用
        System.out.println("\n检查引用队列:");
        int count = 0;
        WeakReference<?> ref;
        while ((ref = (WeakReference<?>) queue.poll()) != null) {
            count++;
            System.out.println("队列中的引用 #" + count + ": " + ref);
            System.out.println("  引用的对象: " + ref.get()); // 应该为 null
        }
        
        System.out.println("\n总共回收了 " + count + " 个对象");
    }
}

