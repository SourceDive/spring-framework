package mine.jdk.debug.thread.exception;

/**
 * @author zero
 * @description 演示使用 setUncaughtExceptionHandler 捕获线程中的未捕获异常
 * @date 2025-12-01
 */
public class ThreadException {
	public static void main(String[] args) {
		System.out.println("start...");

		Runnable task = () -> {
			throw new RuntimeException("test");
		};
		
		Thread thread = new Thread(task);
		// 设置未捕获异常处理器
		thread.setUncaughtExceptionHandler((t, e) -> {
			System.err.println("线程 [" + t.getName() + "] 发生未捕获异常: " + e.getMessage());
			e.printStackTrace();
		});
		thread.start();

		for (int i = 0; i < 3; i++) {
			System.out.println(i);
		}
		System.out.println("end.");
	}
}
