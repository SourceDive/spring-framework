package mine.projects.async_demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 异步服务类
 * 包含各种异步方法用于调试
 */
@Service
public class AsyncService {

    /**
     * 简单的异步方法
     * @param message 消息
     * @return 异步结果
     */
    @Async
    public CompletableFuture<String> asyncMethod(String message) {
        System.out.println("异步方法开始执行，线程: " + Thread.currentThread().getName());
        
        try {
            // 模拟耗时操作
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture("异步方法被中断");
        }
        
        String result = "异步处理完成: " + message + " (线程: " + Thread.currentThread().getName() + ")";
        System.out.println(result);
        return CompletableFuture.completedFuture(result);
    }

    /**
     * 异步方法，无返回值
     * @param taskName 任务名称
     */
    @Async
    public void asyncVoidMethod(String taskName) {
        System.out.println("异步void方法开始执行: " + taskName + " (线程: " + Thread.currentThread().getName() + ")");
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("异步void方法被中断: " + taskName);
            return;
        }
        
        System.out.println("异步void方法执行完成: " + taskName + " (线程: " + Thread.currentThread().getName() + ")");
    }

    /**
     * 异步方法，返回基本类型
     * @param number 数字
     * @return 异步结果
     */
    @Async
    public CompletableFuture<Integer> asyncCalculate(int number) {
        System.out.println("异步计算开始: " + number + " (线程: " + Thread.currentThread().getName() + ")");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture(-1);
        }
        
        int result = number * number;
        System.out.println("异步计算完成: " + number + " * " + number + " = " + result + " (线程: " + Thread.currentThread().getName() + ")");
        return CompletableFuture.completedFuture(result);
    }
}
