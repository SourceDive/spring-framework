package mine.jdk.debug.threadlocal.dirty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zero
 * @description 测试未remove的情况
 * @date 2025-12-01
 */
public class DirtyDemo {
	private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(1);

		for (int i = 0; i < 2; i++) {
			executorService.execute(new MyThread());
		}


	}

	public static class MyThread extends Thread {
		private static boolean flag = true;

		public void run() {
			if (flag) {
				threadLocal.set(this.getName());
				flag = false;
			}
			System.out.println(threadLocal.get());
		}
	}
}
