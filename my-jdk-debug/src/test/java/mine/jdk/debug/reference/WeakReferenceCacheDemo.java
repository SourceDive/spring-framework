package mine.jdk.debug.reference;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * WeakReference 在缓存中的应用示例
 * 使用 WeakReference 实现自动清理的缓存，当对象不再被强引用时，缓存会自动失效
 */
public class WeakReferenceCacheDemo {
    // 使用 WeakReference 作为值的缓存
    private static Map<String, WeakReference<LargeObject>> cache = new HashMap<>();
    
    static class LargeObject {
        private String data;
        private byte[] buffer = new byte[1024 * 1024]; // 1MB 数据
        
        public LargeObject(String data) {
            this.data = data;
        }
        
        public String getData() {
            return data;
        }
        
        @Override
        public String toString() {
            return "LargeObject{data='" + data + "'}";
        }
    }
    
    /**
     * 从缓存获取对象，如果已被回收则重新创建
     */
    public static LargeObject getFromCache(String key) {
        WeakReference<LargeObject> ref = cache.get(key);
        if (ref != null) {
            LargeObject obj = ref.get();
            if (obj != null) {
                System.out.println("从缓存获取: " + key);
                return obj;
            } else {
                System.out.println("缓存已失效: " + key);
                cache.remove(key); // 清理失效的引用
            }
        }
        
        // 缓存未命中，创建新对象
        System.out.println("创建新对象: " + key);
        LargeObject obj = new LargeObject(key);
        cache.put(key, new WeakReference<>(obj));
        return obj;
    }
    
    public static void main(String[] args) {
        System.out.println("=== WeakReference 缓存示例 ===");
        
        // 创建对象并放入缓存
        LargeObject obj1 = getFromCache("key1");
        System.out.println("对象1: " + obj1);
        
        // 再次获取，应该从缓存返回
        LargeObject obj2 = getFromCache("key1");
        System.out.println("对象2: " + obj2);
        System.out.println("是否为同一对象: " + (obj1 == obj2));
        
        // 断开强引用
        obj1 = null;
        obj2 = null;
        
        // 触发 GC
        System.gc();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 再次获取，缓存应该已失效，会创建新对象
        LargeObject obj3 = getFromCache("key1");
        System.out.println("对象3: " + obj3);
        System.out.println("缓存大小: " + cache.size());
    }
}

