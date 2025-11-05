package mine.jdk.debug.inner_class;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zero
 * @description 演示 this escape 问题：在构造函数完成之前将 this 引用暴露给其他线程
 * @date 2025-11-04
 */
public class ThisEscape {
    private int value;
    private String name;
    
    /**
     * 问题示例：在构造函数中将 this 暴露给外部
     * 这会导致其他线程可能在对象完全初始化之前就访问到未初始化的字段
     */
    public ThisEscape(EventSource eventSource) {
        // 此时 value 和 name 还没有被初始化（都是默认值）
        // 但是我们将 this 引用传递给了 registerListener
        // 这可能导致其他线程在对象完全初始化之前就访问到它
        
        eventSource.registerListener(new EventListener() {
            @Override
            public void onEvent() {
                // 危险：这里访问的 value 和 name 可能还没有被初始化！
                // 因为构造函数的后续代码可能还没有执行
                System.out.println("EventListener 访问: value=" + value + ", name=" + name);
            }
        });
        
        // 模拟一些耗时的初始化操作
        try {
            Thread.sleep(100); // 模拟初始化延迟
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 这些字段在构造函数最后才初始化
        this.value = 42;
        this.name = "初始化完成";
        
        System.out.println("构造函数完成: value=" + value + ", name=" + name);
    }
    
    /**
     * 正确的做法：使用工厂方法和私有构造函数
     * 只有在对象完全初始化后才暴露 this 引用
     */
    public static ThisEscape safeCreate(EventSource eventSource) {
        ThisEscape instance = new ThisEscape();
        // 对象已经完全初始化后，再注册监听器
        eventSource.registerListener(new EventListener() {
            @Override
            public void onEvent() {
                // 安全：此时对象已经完全初始化
                System.out.println("安全的 EventListener 访问: value=" + instance.value + ", name=" + instance.name);
            }
        });
        return instance;
    }
    
    // 私有构造函数，确保初始化完成
    private ThisEscape() {
        this.value = 42;
        this.name = "初始化完成";
        System.out.println("私有构造函数完成: value=" + value + ", name=" + name);
    }
    
    public int getValue() {
        return value;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * 事件源接口
     */
    public static class EventSource {
        private final List<EventListener> listeners = new ArrayList<>();
        private Thread eventThread;
        
        public void registerListener(EventListener listener) {
            listeners.add(listener);
            // 模拟在另一个线程中立即触发事件（演示 this escape 问题）
            if (eventThread == null) {
                eventThread = new Thread(() -> {
                    try {
                        // 等待一小段时间，确保构造函数可能还没完成
                        Thread.sleep(50);
                        // 触发事件，此时可能对象还没有完全初始化
                        fireEvent();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                eventThread.start();
            }
        }
        
        private void fireEvent() {
            for (EventListener listener : listeners) {
                listener.onEvent();
            }
        }
        
        public void waitForEvents() throws InterruptedException {
            if (eventThread != null) {
                eventThread.join();
            }
        }
    }
    
    /**
     * 事件监听器接口
     */
    public interface EventListener {
        void onEvent();
    }
    
    /**
     * 测试 this escape 问题
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== 演示 this escape 问题 ==========");
        EventSource eventSource = new EventSource();
        ThisEscape escaped = new ThisEscape(eventSource);
        // 等待事件触发
        eventSource.waitForEvents();
        
        System.out.println("\n========== 安全的创建方式 ==========");
        EventSource safeEventSource = new EventSource();
        ThisEscape safe = ThisEscape.safeCreate(safeEventSource);
        safeEventSource.waitForEvents();
        
        System.out.println("\n========== 最终状态检查 ==========");
        System.out.println("escaped 对象: value=" + escaped.getValue() + ", name=" + escaped.getName());
        System.out.println("safe 对象: value=" + safe.getValue() + ", name=" + safe.getName());
    }
}
