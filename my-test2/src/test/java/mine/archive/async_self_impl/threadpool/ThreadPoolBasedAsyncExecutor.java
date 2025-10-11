package mine.archive.async_self_impl.threadpool;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncResult;

import java.lang.reflect.Method;
import java.util.concurrent.*;

/**
 * @author zero
 * @description todo
 * @date 2025-10-11
 */
public class ThreadPoolBasedAsyncExecutor extends ThreadPoolExecutor implements AsyncTaskExecutor {

	public ThreadPoolBasedAsyncExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	public static <T> AsyncResult<T> submit(Object target, Method method, Object[] args) {
		ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

		Future<?> result = executorService.submit(() -> {
			try {
				method.invoke(target, args);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		return new AsyncResult<T>((T) result);
	}

	@Override
	public void execute(Runnable task, long startTimeout) {

	}
}
