package mine.archive.jdk_proxy_manual;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 自定义的 InvocationHandler，用于添加日志功能
 */
public class LoggingInvocationHandler implements InvocationHandler {
    
    private final Object target; // 被代理的目标对象
    
    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 方法调用前的日志
        System.out.println("=== 方法调用开始 ===");
        System.out.println("调用方法: " + method.getName());
        if (args != null) {
            System.out.println("方法参数: " + java.util.Arrays.toString(args));
        }
        
        long startTime = System.currentTimeMillis();
        
        try {
            // 调用目标对象的方法
            Object result = method.invoke(target, args);
            
            long endTime = System.currentTimeMillis();
            
            // 方法调用后的日志
            System.out.println("方法返回值: " + result);
            System.out.println("方法执行时间: " + (endTime - startTime) + "ms");
            System.out.println("=== 方法调用结束 ===\n");
            
            return result;
        } catch (Exception e) {
            System.out.println("方法调用异常: " + e.getMessage());
            System.out.println("=== 方法调用结束 ===\n");
            throw e;
        }
    }
}
