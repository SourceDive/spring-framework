package mine.jdk.debug.reference;

import java.lang.ref.WeakReference;

/**
 * 测试 reference 的 referent
 */
public class ReferentDemo {
    public static void main(String[] args) {
        // 1. 创建对象和引用
        Object obj = new Object();
        WeakReference<Object> weakRef = new WeakReference<>(obj);
        
        System.out.println("初始状态: " + weakRef.get());  // 非null
        
        // 2. 断开强引用
        obj = null;
        
        // 3. 触发GC（建议）
        System.gc();
        
        // 4. 检查referent状态
        System.out.println("GC后: " + weakRef.get());  // 可能为null
        
        // 此时weakRef.referent字段已被GC设置为null
    }
}