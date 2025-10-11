package mine.archive.async_self_impl.proxy;

import mine.archive.async_self_impl.threadpool.ThreadPoolBasedAsyncExecutor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zero
 * @description todo
 * @date 2025-10-11
 */
public class DynamicProxy implements InvocationHandler {

	private final Object target;

	public DynamicProxy(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return ThreadPoolBasedAsyncExecutor.submit(target, method, args);
	}
}
