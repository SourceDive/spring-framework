package mine.jdk.debug.reference;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * WeakReference 在监听器模式中的应用示例
 * 使用 WeakReference 存储监听器，避免内存泄漏
 */
public class WeakReferenceListenerDemo {
    
    // 事件监听器接口
    interface EventListener {
        void onEvent(String event);
    }
    
    // 事件源
    static class EventSource {
        private List<WeakReference<EventListener>> listeners = new ArrayList<>();
        
        public void addListener(EventListener listener) {
            listeners.add(new WeakReference<>(listener));
            System.out.println("添加监听器，当前监听器数量: " + listeners.size());
        }
        
        public void fireEvent(String event) {
            System.out.println("\n触发事件: " + event);
            
            // 清理已被回收的监听器
            Iterator<WeakReference<EventListener>> it = listeners.iterator();
            while (it.hasNext()) {
                WeakReference<EventListener> ref = it.next();
                EventListener listener = ref.get();
                
                if (listener == null) {
                    // 监听器已被回收，移除引用
                    it.remove();
                    System.out.println("  清理失效的监听器引用");
                } else {
                    // 调用监听器
                    listener.onEvent(event);
                }
            }
            
            System.out.println("剩余监听器数量: " + listeners.size());
        }
    }
    
    // 具体监听器实现
    static class MyListener implements EventListener {
        private String name;
        
        public MyListener(String name) {
            this.name = name;
        }
        
        @Override
        public void onEvent(String event) {
            System.out.println("  [" + name + "] 收到事件: " + event);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== WeakReference 监听器模式示例 ===");
        
        EventSource source = new EventSource();
        
        // 创建监听器
        MyListener listener1 = new MyListener("Listener1");
        MyListener listener2 = new MyListener("Listener2");
        
        // 添加监听器
        source.addListener(listener1);
        source.addListener(listener2);
        
        // 触发事件
        source.fireEvent("Event 1");
        
        // 断开一个监听器的强引用
        listener1 = null;
        System.out.println("\n断开 Listener1 的强引用");
        
        // 触发 GC
        System.gc();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 再次触发事件，Listener1 应该已被清理
        source.fireEvent("Event 2");
        
        // Listener2 仍然有效
        source.fireEvent("Event 3");
    }
}

