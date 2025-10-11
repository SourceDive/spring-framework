package mine.archive.jdk_proxy_manual;

import java.lang.reflect.Proxy;

/**
 * 使用 InvocationHandler 手动创建代理对象的示例
 */
public class ProxyExample {
    
    public static void main(String[] args) {
        // 1. 创建目标对象
        SimpleInterface target = new SimpleInterfaceImpl();
        
        // 2. 创建 InvocationHandler
        LoggingInvocationHandler handler = new LoggingInvocationHandler(target);
        
        // 3. 使用 Proxy.newProxyInstance 创建代理对象
        SimpleInterface proxy = (SimpleInterface) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),  // 类加载器
            target.getClass().getInterfaces(),   // 接口数组
            handler                              // InvocationHandler
        );
        
        // 4. 使用代理对象调用方法
        System.out.println("=== 使用代理对象调用方法 ===");
        
        // 调用 sayHello 方法
        String result1 = proxy.sayHello("张三");
        System.out.println("最终结果: " + result1);
        
        // 调用 calculate 方法
        int result2 = proxy.calculate(10, 20);
        System.out.println("最终结果: " + result2);
        
        // 5. 对比：直接调用目标对象（无代理）
        System.out.println("\n=== 直接调用目标对象（无代理） ===");
        String directResult1 = target.sayHello("李四");
        System.out.println("直接调用结果: " + directResult1);
        
        int directResult2 = target.calculate(5, 15);
        System.out.println("直接调用结果: " + directResult2);
    }
}
