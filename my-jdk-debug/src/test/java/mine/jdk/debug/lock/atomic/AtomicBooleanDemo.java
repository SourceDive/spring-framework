package mine.jdk.debug.lock.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zero
 * @description 测试原子变量作为主线程和异步线程之间的沟通媒介。
 * @date 2025-10-17
 */
public class AtomicBooleanDemo {
    public static void main(String[] args) {
        AtomicBoolean exchange = new AtomicBoolean(true);

        Runnable task = () -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName());

            exchange.set(false);
        };

        new Thread(task).start();

        while (exchange.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(String.format("Thread: %s, 等待中...",
                    Thread.currentThread().getName()));
        }

        System.out.println(exchange.get());
    }
}
