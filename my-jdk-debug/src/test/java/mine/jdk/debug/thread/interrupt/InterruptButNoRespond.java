package mine.jdk.debug.thread.interrupt;

/**
 * @author zero
 * @description 没有处理中断的逻辑，程序会正常执行。
 * @date 2025-12-03
 */
public class InterruptButNoRespond {
	public static void main(String[] args) {
		Thread.currentThread().interrupt();

		// 后面没有响应中断的操作
		for (int i = 0; i < 3; i++) {
			System.out.println(i);
		}

		System.out.println("end");
	}
}
