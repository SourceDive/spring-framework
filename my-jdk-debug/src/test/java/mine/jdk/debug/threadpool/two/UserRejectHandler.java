package mine.jdk.debug.threadpool.two;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zero
 * @description todo
 * @date 2025-11-20
 */
public class UserRejectHandler implements RejectedExecutionHandler {
	@Override
	public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
		System.out.println("任务拒绝." + executor.toString());
	}
}
