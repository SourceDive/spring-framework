package mine.projects.async_demo;

import mine.projects.async_demo.config.AsyncConfig;
import mine.projects.async_demo.service.AsyncService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 异步演示主应用程序
 * 作为调试@Async功能的入口点
 */
@EnableAsync
public class AsyncDemoApplication {

    public static void main(String[] args) {
        System.out.println("=== Spring @Async 调试演示程序 ===");
        
        // 创建Spring应用上下文
        AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(AsyncConfig.class);

        try {
            // 获取异步服务
            AsyncService asyncService = context.getBean(AsyncService.class);
            
            System.out.println("主线程: " + Thread.currentThread().getName());
            System.out.println();
            
            // 演示1: 基本异步方法调用
            System.out.println("=== 演示1: 基本异步方法调用 ===");
            CompletableFuture<String> future1 = asyncService.asyncMethod("Hello Async");
            System.out.println("异步方法调用后立即返回，主线程继续执行");
            
            // 等待异步方法完成
            String result1 = future1.get(5, TimeUnit.SECONDS);
            System.out.println("异步方法执行结果: " + result1);
            System.out.println();
            
            // 演示2: 异步void方法
            System.out.println("=== 演示2: 异步void方法 ===");
            asyncService.asyncVoidMethod("Void Task");
            System.out.println("异步void方法调用后立即返回，主线程继续执行");
            Thread.sleep(2000); // 等待异步方法执行
            System.out.println();
            
            // 演示3: 异步计算方法
            System.out.println("=== 演示3: 异步计算方法 ===");
            CompletableFuture<Integer> future3 = asyncService.asyncCalculate(6);
            System.out.println("异步计算方法调用后立即返回，主线程继续执行");
            
            Integer result3 = future3.get(3, TimeUnit.SECONDS);
            System.out.println("异步计算结果: " + result3);
            System.out.println();
            
            // 演示4: 多个异步方法并发执行
            System.out.println("=== 演示4: 多个异步方法并发执行 ===");
            CompletableFuture<String> future4a = asyncService.asyncMethod("并发任务A");
            CompletableFuture<String> future4b = asyncService.asyncMethod("并发任务B");
            CompletableFuture<Integer> future4c = asyncService.asyncCalculate(7);
            CompletableFuture<Integer> future4d = asyncService.asyncCalculate(8);
            
            System.out.println("所有异步方法调用完成，主线程继续执行");
            
            // 等待所有异步方法完成
            String result4a = future4a.get(5, TimeUnit.SECONDS);
            String result4b = future4b.get(5, TimeUnit.SECONDS);
            Integer result4c = future4c.get(5, TimeUnit.SECONDS);
            Integer result4d = future4d.get(5, TimeUnit.SECONDS);
            
            System.out.println("所有异步方法执行完成:");
            System.out.println("结果A: " + result4a);
            System.out.println("结果B: " + result4b);
            System.out.println("结果C: " + result4c);
            System.out.println("结果D: " + result4d);
            System.out.println();
            
            System.out.println("=== 所有演示完成 ===");
            
        } catch (Exception e) {
            System.err.println("演示过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭应用上下文
            context.close();
        }
    }
}
