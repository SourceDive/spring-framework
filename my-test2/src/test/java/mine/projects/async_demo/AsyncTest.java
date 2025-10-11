package mine.projects.async_demo;

import mine.projects.async_demo.config.AsyncConfig;
import mine.projects.async_demo.service.AsyncService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 异步功能测试类
 * 用于测试和调试Spring的@Async功能
 */
public class AsyncTest {

    private AnnotationConfigApplicationContext context;
    private AsyncService asyncService;

    @BeforeEach
    public void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(AsyncConfig.class, AsyncService.class);
        context.refresh();
        asyncService = context.getBean(AsyncService.class);
    }

    @AfterEach
    public void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    /**
     * 测试异步方法调用
     */
    @Test
    public void testAsyncMethod() throws Exception {
        System.out.println("=== 开始测试异步方法 ===");
        System.out.println("主线程: " + Thread.currentThread().getName());
        
        // 调用异步方法
        CompletableFuture<String> future = asyncService.asyncMethod("测试消息");
        
        // 验证异步方法立即返回
        assertNotNull(future);
        System.out.println("异步方法调用后立即返回，主线程继续执行");
        
        // 等待异步方法完成
        String result = future.get(5, TimeUnit.SECONDS);
        
        // 验证结果
        assertNotNull(result);
        assertTrue(result.contains("异步处理完成"));
        assertTrue(result.contains("测试消息"));
        System.out.println("异步方法执行结果: " + result);
        
        System.out.println("=== 异步方法测试完成 ===");
    }

    /**
     * 测试异步void方法
     */
    @Test
    public void testAsyncVoidMethod() throws Exception {
        System.out.println("=== 开始测试异步void方法 ===");
        System.out.println("主线程: " + Thread.currentThread().getName());
        
        // 调用异步void方法
        asyncService.asyncVoidMethod("测试任务");
        
        System.out.println("异步void方法调用后立即返回，主线程继续执行");
        
        // 等待一段时间让异步方法执行
        Thread.sleep(2000);
        
        System.out.println("=== 异步void方法测试完成 ===");
    }

    /**
     * 测试异步计算方法
     */
    @Test
    public void testAsyncCalculate() throws Exception {
        System.out.println("=== 开始测试异步计算方法 ===");
        System.out.println("主线程: " + Thread.currentThread().getName());
        
        // 调用异步计算方法
        CompletableFuture<Integer> future = asyncService.asyncCalculate(5);
        
        // 验证异步方法立即返回
        assertNotNull(future);
        System.out.println("异步计算方法调用后立即返回，主线程继续执行");
        
        // 等待异步方法完成
        Integer result = future.get(3, TimeUnit.SECONDS);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(25, result);
        System.out.println("异步计算结果: " + result);
        
        System.out.println("=== 异步计算方法测试完成 ===");
    }

    /**
     * 测试多个异步方法并发执行
     */
    @Test
    public void testMultipleAsyncMethods() throws Exception {
        System.out.println("=== 开始测试多个异步方法并发执行 ===");
        System.out.println("主线程: " + Thread.currentThread().getName());
        
        // 启动多个异步任务
        CompletableFuture<String> future1 = asyncService.asyncMethod("任务1");
        CompletableFuture<String> future2 = asyncService.asyncMethod("任务2");
        CompletableFuture<Integer> future3 = asyncService.asyncCalculate(3);
        CompletableFuture<Integer> future4 = asyncService.asyncCalculate(4);
        
        System.out.println("所有异步方法调用完成，主线程继续执行");
        
        // 等待所有异步方法完成
        String result1 = future1.get(5, TimeUnit.SECONDS);
        String result2 = future2.get(5, TimeUnit.SECONDS);
        Integer result3 = future3.get(5, TimeUnit.SECONDS);
        Integer result4 = future4.get(5, TimeUnit.SECONDS);
        
        // 验证结果
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);
        assertNotNull(result4);
        
        assertEquals(9, result3);
        assertEquals(16, result4);
        
        System.out.println("所有异步方法执行完成:");
        System.out.println("结果1: " + result1);
        System.out.println("结果2: " + result2);
        System.out.println("结果3: " + result3);
        System.out.println("结果4: " + result4);
        
        System.out.println("=== 多个异步方法并发执行测试完成 ===");
    }
}
