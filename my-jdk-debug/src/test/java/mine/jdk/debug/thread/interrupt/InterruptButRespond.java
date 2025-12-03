package mine.jdk.debug.thread.interrupt;

/**
 * @author zero
 * @description 响应中断，提前退出。
 * @date 2025-12-03
 */
public class InterruptButRespond {
	public static void main(String[] args) {
		Thread.currentThread().interrupt();

		if (Thread.interrupted()) {
			System.out.println("提前退出。");
			return;
		}

		// 后面没有响应中断的操作
		for (int i = 0; i < 3; i++) {
			System.out.println(i);
		}

		System.out.println("end");
	}
}
