package mine.archive.async_self_impl;

import mine.archive.async_self_impl.proxy.DynamicProxy;
import mine.archive.async_self_impl.service.DemoService;
import mine.archive.async_self_impl.service.DemoServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author zero
 * @description 基于代理的异步执行案例。
 * @date 2025-10-11
 */
public class ApplicationBasedProxy {
	public static void main(String[] args) throws InterruptedException {
		// 目标对象
		DemoService targetService = new DemoServiceImpl();

		// handler
		InvocationHandler handler = new DynamicProxy(targetService);

		// 创建代理对象
		DemoService proxy = (DemoService)Proxy.newProxyInstance(handler.getClass().getClassLoader(),
				targetService.getClass().getInterfaces(),
				handler
		);

		proxy.perform();
		targetService.perform();
	}
}
